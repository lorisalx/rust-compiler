package tds;

import ast.Ast;
import ast.AstVisitor;
import ast.decl.*;
import ast.expr.*;
import ast.instr.*;
import ast.leaf.LeafBoolean;
import ast.leaf.LeafIdentifier;
import ast.leaf.LeafInteger;
import ast.misc.AccessTable;
import ast.misc.Bloc;
import ast.misc.PassArgs;
import ast.misc.Program;
import ast.type.TypeRef;
import ast.type.TypeRefMut;
import ast.type.TypeVec;
import ast.type.TypeVoid;
import org.antlr.v4.runtime.misc.Pair;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Stack;

public class TdsVisitor implements AstVisitor<String> {
    private ArrayList<String> errors = new ArrayList<>();

    public void showErrors() {
        System.out.println("Errors : ");
        for (String error : errors) {
            System.out.println(error);
        }
    }
    private HashMap<Pair<Integer,Integer>, Chunk> tds = new HashMap<>();

    private Stack<Integer> stackRegion = new Stack<>();

    private Stack<Integer> stackImbrication = new Stack<>();

    private int cptRegion = 0;

    public HashMap<Pair<Integer, Integer>, Chunk> getTds() {
        return tds;
    }

    private void initTds() {
        stackRegion.push(0);
        stackImbrication.push(0);
        Symbol s = new Symbol();
        s.setIdf("main");
        Chunk c = new Chunk(s);
        tds.put(new Pair<>(stackRegion.peek(), stackImbrication.peek()), c);
    }
    private void newRegion(Symbol parent) {
        Chunk chunk = new Chunk(parent);
        cptRegion++;
        int newRegion = cptRegion;
        int newImbrication = stackImbrication.peek() + 1;
        stackRegion.push(newRegion);
        stackImbrication.push(newImbrication);
        tds.put(new Pair<>(stackRegion.peek(), stackImbrication.peek()), chunk);
    }

    private void goBack() {
        stackRegion.pop();
        stackImbrication.pop();
    }

    private void addSymbol(Symbol symbol) {
        tds.get(new Pair<>(stackRegion.peek(), stackImbrication.peek())).symbols.add(symbol);
    }

    private int getCurrentRegion() {
        return stackRegion.peek();
    }

    private int getCurrentImbrication() {
        return stackImbrication.peek();
    }

    private ArrayList<Symbol> getCurrentSymbols() {
        return tds.get(new Pair<>(getCurrentRegion(), getCurrentImbrication())).symbols;
    }

    private Symbol getCurrentParent() {
        return tds.get(new Pair<>(getCurrentRegion(), getCurrentImbrication())).parent;
    }

    private ArrayList<Symbol> getSymbols(int region, int imbrication) {
        return tds.get(new Pair<>(region, imbrication)).symbols;
    }

    public void showTds() {
        for (Pair<Integer, Integer> key : tds.keySet()) {
            System.out.println("Region : " + key.a + " Imbrication : " + key.b);
            System.out.println("Symbols : ");
            for (Symbol symbol : tds.get(key).symbols) {
                System.out.println("    " + symbol);
            }
            System.out.println("Parent : " + tds.get(key).parent);
            System.out.println();
        }
    }
    @Override
    public String visit(DeclFunction declFunction) {
        SymbolFunction symbolFunction = new SymbolFunction();
        // Return type
        String returnType = declFunction.returnType.accept(this);
        // Args
        String typeParams = "";
        if (declFunction.args != null) {
            typeParams = declFunction.args.accept(this);
        }
        // Bloc
        declFunction.bloc.accept(this);

        symbolFunction.setAttributes(declFunction.name, returnType, typeParams);
        addSymbol(symbolFunction);
        return null;
    }

    @Override
    public String visit(ExprPrint exprPrint) {
        return null;
    }

    @Override
    public String visit(Program program) {
        initTds();
        for(Ast instr : program.instructions) {
            instr.accept(this);
        }

        // Tds remplie, contrôles sémantiques
        checkMain();
        return null;
    }

    private void checkMain() {
        boolean mainFound = false;
        for (Symbol s : getCurrentSymbols()) {
            if  (s instanceof SymbolFunction) {
                if (((SymbolFunction) s).idf.equals("main")) {
                    mainFound = true;
                }
            }
        }
        if (!mainFound) {
            errors.add("No main function found");
        }
    }
    @Override
    public String visit(Bloc bloc) {
        SymbolBloc blocSymbol = new SymbolBloc();
        newRegion(blocSymbol);
        for(Ast instr : bloc.content) {
            instr.accept(this);
        }
        SymbolBloc curr = new SymbolBloc();
        curr.setAttributes(getCurrentRegion(), getCurrentImbrication());
        goBack();
        blocSymbol.setAttributes(stackRegion.peek(), stackImbrication.peek());


        addSymbol(curr);
        return null;
    }


    @Override
    public String visit(DeclStruct declStruct) {
        return null;
    }

    @Override
    public String visit(DeclArgs declArgs) {
        String typeParams = "(";
        for(DeclArg declArg : declArgs.args) {
            String type = declArg.accept(this);
            typeParams += type + ",";
        }
        typeParams = typeParams.substring(0, typeParams.length() - 1) + ")";
        return typeParams;
    }

    @Override
    public String visit(DeclArg declArg) {
        String type = declArg.type.accept(this);
        addSymbol(new SymbolParam(declArg.name, type));
        return type;
    }

    @Override
    public String visit(DeclReturnType declReturnType) {
        if (declReturnType.type != null) {
            return declReturnType.type.accept(this);
        }
        return "";
    }

    @Override
    public String visit(InstrDeclLet instrDeclLet) {
        String name = instrDeclLet.name;
        String type = instrDeclLet.type.accept(this);
        boolean mutable = instrDeclLet.mutable;
        SymbolVar symbolVar = new SymbolVar(name, type, mutable);
        addSymbol(symbolVar);
        return null;
    }

    @Override
    public String visit(InstrDeclLetStruct instrDeclLetStruct) {
        return null;
    }

    @Override
    public String visit(InstrReturn instrReturn) {
        return null;
    }

    @Override
    public String visit(InstrIfElse instrIfElse) {
        instrIfElse.blocIf.accept(this);
        instrIfElse.blocElse.accept(this);
        return null;
    }

    @Override
    public String visit(InstrWhile instrWhile) {
        instrWhile.bloc.accept(this);
        return null;
    }

    @Override
    public String visit(InstrAssignation instrAssignation) {
        return null;
    }

    @Override
    public String visit(InstrAssignationTable assignationTable) {
        return null;
    }

    @Override
    public String visit(ExprLen exprLen) {
        return null;
    }

    @Override
    public String visit(ExprFunc exprFunc) {
        return null;
    }

    @Override
    public String visit(AccessTable accessTable) {
        return null;
    }

    @Override
    public String visit(ExprVec exprVec) {
        return null;
    }

    @Override
    public String visit(ExprOr exprOr) {
        return null;
    }

    @Override
    public String visit(ExprAnd exprAnd) {
        return null;
    }

    @Override
    public String visit(ExprCond exprCond) {
        return null;
    }

    @Override
    public String visit(PassArgs passArgs) {
        return null;
    }

    @Override
    public String visit(ExprPlusOrMinus exprPlusOrMinus) {
        String leftType = exprPlusOrMinus.left.accept(this);
        String rightType = exprPlusOrMinus.right.accept(this);
        if (leftType != null && rightType != null) {
            if (leftType.equals(rightType)) {
                return leftType;
            }
            else {
                errors.add("Type mismatch : " + leftType + " and " + rightType);
                return null;
            }
        }
        else {
            return null;
        }
    }

    @Override
    public String visit(ExprMultOrDiv exprMultOrDiv) {
        String leftType = exprMultOrDiv.left.accept(this);
        String rightType = exprMultOrDiv.right.accept(this);
        if (leftType != null && rightType != null) {
            if (leftType.equals(rightType)) {
                return leftType;
            }
            else {
                errors.add("Type mismatch : " + leftType + " and " + rightType);
                return null;
            }
        }
        else {
            return null;
        }
    }

    @Override
    public String visit(ExprDot exprDot) {
        return null;
    }

    @Override
    public String visit(ExprUnary exprUnary) {
        return null;
    }

    @Override
    public String visit(ExprIndex exprIndex) {
        return null;
    }

    @Override
    public String visit(ExprParenthesis exprParenthesis) {
        return null;
    }

    @Override
    public String visit(LeafIdentifier leafIdentifiant) {
        return leafIdentifiant.name;
    }

    @Override
    public String visit(LeafInteger leafInteger) {
        return Integer.toString(leafInteger.value);
    }

    @Override
    public String visit(LeafBoolean leafBoolean) {
        return Boolean.toString(leafBoolean.value);
    }


    @Override
    public String visit(TypeRef vec) {
        return "&" + vec.type.accept(this);
    }

    @Override
    public String visit(TypeRefMut vec) {
        return "&mut " + vec.type.accept(this);
    }

    @Override
    public String visit(TypeVoid vec) {
        return "void";
    }

    @Override
    public String visit(TypeVec typeVec) {
        return "Vec<" + typeVec.type.accept(this) + ">";
    }

    private boolean searchRegion(int region, int imbrication, String s){
        ArrayList<Symbol> symbols = getSymbols(region,imbrication);
        for(int i = 0 ; i < symbols.size(); i++){
            if(symbols.get(i).idf.equals(s))
                return true;
        }
        return false;
    }

    private Symbol findDeclaration(String name){
        return null;
    }

    // Un élément est à porté lors de son utilisation
    private void checkScope(String name){
        int r = getCurrentRegion();
        int i = getCurrentImbrication();
        if(!searchRegion(r,i,name)){
            // Parent
            Symbol p = getCurrentParent();
            while(p instanceof SymbolBloc) {
                SymbolBloc bloc = (SymbolBloc) p;
                if (searchRegion(bloc.region, bloc.imbrication, name)) {
                    return;
                }
                p = tds.get(new Pair<>(bloc.region, bloc.imbrication)).parent;
            }
        }
        errors.add(name + " is not initizalized");
    }


}
