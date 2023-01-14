package graphViz;

import java.io.FileOutputStream;
import java.io.IOException;

import ast.*;
import ast.decl.*;
import ast.expr.*;
import ast.instr.*;
import ast.leaf.LeafBoolean;
import ast.leaf.LeafIdentifier;
import ast.leaf.LeafInteger;
import ast.misc.*;
import ast.type.*;


public class GraphVizVisitor implements AstVisitor<String> {

    private int state;
    private String nodeBuffer;
    private String linkBuffer;

    public GraphVizVisitor(){
        this.state = 0;
        this.nodeBuffer = "digraph \"ast\"{\n\n\tnodesep=1;\n\tranksep=1;\n\n";
        this.linkBuffer = "\n";
    }

    public void dumpGraph(String filepath) throws IOException{

        FileOutputStream output = new FileOutputStream(filepath);

        String buffer = this.nodeBuffer + this.linkBuffer + "}";
        byte[] strToBytes = buffer.getBytes();

        output.write(strToBytes);

        output.close();

    }


    private String nextState(){
        int returnedState = this.state;
        this.state++;
        return "N"+ returnedState;
    }

    private void addTransition(String from,String dest){
        this.linkBuffer += String.format("\t%s -> %s; \n", from,dest);

    }

    private void addNode(String node,String label){
        this.nodeBuffer += String.format("\t%s [label=\"%s\", shape=\"box\"];\n", node,label);

    }

    public String visit(DeclFunction declFunction) {
        String nodeIdentifier = this.nextState();
        this.addNode(nodeIdentifier, "DeclFunc");

        // Nom de fonction
        String nameIdentifier = this.nextState();
        this.addNode(nameIdentifier, declFunction.name);
        this.addTransition(nodeIdentifier, nameIdentifier);

        // Arguments
        if (declFunction.args != null) {
            String argsIdentifier = declFunction.args.accept(this);
            this.addTransition(nodeIdentifier, argsIdentifier);
        }

        // Return type
        String returnTypeState = declFunction.returnType.accept(this);
        this.addTransition(nodeIdentifier, returnTypeState);

        // Bloc
        String blocState = declFunction.bloc.accept(this);
        this.addTransition(nodeIdentifier, blocState);

        return nodeIdentifier;
    }

    public String visit(ExprPrint exprPrint) {
        String nodeIdentifier = this.nextState();
        this.addNode(nodeIdentifier, "Print");

        String valueIdentifier = this.nextState();
        this.addNode(valueIdentifier, exprPrint.value);
        this.addTransition(nodeIdentifier, valueIdentifier);

        return nodeIdentifier;
    }

    public String visit(Program program) {
        String nodeIdentifier = this.nextState();

        this.addNode(nodeIdentifier, "Program");

        for (Ast ast: program.instructions){
            String astState = ast.accept(this);
            this.addTransition(nodeIdentifier, astState);
        }

        return nodeIdentifier;
    }

    public String visit(Bloc bloc) {
        String nodeIdentifier = this.nextState();
        this.addNode(nodeIdentifier, "Bloc");

        for (Ast ast: bloc.content){
            String astState = ast.accept(this);
            this.addTransition(nodeIdentifier, astState);
        }

        return nodeIdentifier;
    }

    @Override
    public String visit(DeclStruct declStruct) {
        String nodeIdentifier = this.nextState();
        this.addNode(nodeIdentifier, "DeclStruct");
        this.addTransition(nodeIdentifier, declStruct.name);
        if (declStruct.args != null) {
            String argsState = declStruct.args.accept(this);
            this.addTransition(nodeIdentifier, argsState);
        }
        return nodeIdentifier;
    }

    @Override
    public String visit(DeclArgs declArgs) {
        String nodeIdentifier = this.nextState();
        this.addNode(nodeIdentifier, "DeclArgs");
        for (Ast arg: declArgs.args){
            String argState = arg.accept(this);
            this.addTransition(nodeIdentifier, argState);
        }

        return nodeIdentifier;
    }

    @Override
    public String visit(DeclArg declArg) {
        String nodeIdentifier = this.nextState();
        this.addNode(nodeIdentifier, "DeclArg");

        // Nom
        String nameIdentifier = this.nextState();
        this.addNode(nameIdentifier, declArg.name);
        this.addTransition(nodeIdentifier, nameIdentifier);

        // Type
        String typeState = declArg.type.accept(this);
        this.addTransition(nodeIdentifier, typeState);

        // Mutable
        if (declArg.mutable){
            String mutIdentifier = this.nextState();
            this.addNode(mutIdentifier, "Mutable");
            this.addTransition(nodeIdentifier, mutIdentifier);
        }
        return nodeIdentifier;
    }

    @Override
    public String visit(DeclReturnType declReturnType) {
        String nodeIdentifier = this.nextState();
        this.addNode(nodeIdentifier, "DectReturnType");

        // Type
        if (declReturnType.type != null) {
            String typeState = declReturnType.type.accept(this);
            this.addTransition(nodeIdentifier, typeState);
        }

        return nodeIdentifier;
    }

    @Override
    public String visit(InstrDeclLet instrDeclLet) {
        String nodeIdentifier = this.nextState();
        this.addNode(nodeIdentifier, "DeclLet");

        // Nom
        String nameIdentifier = this.nextState();
        this.addNode(nameIdentifier, instrDeclLet.name);
        this.addTransition(nodeIdentifier, nameIdentifier);

        // Type
        String typeState = instrDeclLet.type.accept(this);
        this.addTransition(nodeIdentifier, typeState);

        // Mutable
        if (instrDeclLet.mutable){
            String mutIdentifier = this.nextState();
            this.addNode(mutIdentifier, "Mutable");
            this.addTransition(nodeIdentifier, mutIdentifier);
        }

        // Value
        if (instrDeclLet.value != null) {
            String valueState = instrDeclLet.value.accept(this);
            this.addTransition(nodeIdentifier, valueState);
        }

        return nodeIdentifier;
    }

    @Override
    public String visit(InstrDeclLetStruct instrDeclLetStruct) {
        String nodeIdentifier = this.nextState();
        this.addNode(nodeIdentifier, "DeclLetStruct");

        // Nom
        String nameIdentifier = this.nextState();
        this.addNode(nameIdentifier, instrDeclLetStruct.name);
        this.addTransition(nodeIdentifier, nameIdentifier);

        // Type
        String typeState = instrDeclLetStruct.type.accept(this);
        this.addTransition(nodeIdentifier, typeState);

        // Mutable
        if (instrDeclLetStruct.mutable){
            String mutIdentifier = this.nextState();
            this.addNode(mutIdentifier, "Mutable");
            this.addTransition(nodeIdentifier, mutIdentifier);
        }

        // Args
        String argsState = instrDeclLetStruct.args.accept(this);
        this.addTransition(nodeIdentifier, argsState);

        return nodeIdentifier;
    }

    @Override
    public String visit(InstrReturn instrReturn) {
        String nodeIdentifier = this.nextState();
        this.addNode(nodeIdentifier, "Return");

        String valState = instrReturn.expr.accept(this);
        this.addTransition(nodeIdentifier, valState);

        return nodeIdentifier;
    }

    @Override
    public String visit(InstrIfElse instrIfElse) {
        String nodeIdentifier = this.nextState();
        this.addNode(nodeIdentifier, "IfElse");

        // Expr
        String expr = instrIfElse.expr.accept(this);
        this.addTransition(nodeIdentifier, expr);

        // Bloc If
        String blocIfState = instrIfElse.blocIf.accept(this);
        this.addTransition(nodeIdentifier, blocIfState);

        // Bloc Else
        String blocElseState = instrIfElse.blocElse.accept(this);
        this.addTransition(nodeIdentifier, blocElseState);

        return nodeIdentifier;
    }

    @Override
    public String visit(InstrWhile instrWhile) {
        String nodeIdentifier = this.nextState();
        this.addNode(nodeIdentifier, "While");

        // Expr
        String expr = instrWhile.expr.accept(this);
        this.addTransition(nodeIdentifier, expr);

        // Bloc
        String blocState = instrWhile.bloc.accept(this);
        this.addTransition(nodeIdentifier, blocState);

        return nodeIdentifier;
    }

    @Override
    public String visit(InstrAssignation instrAssignation) {
        String nodeIdentifier = this.nextState();
        this.addNode(nodeIdentifier, "Assignation");
        // Nom
        String nameIdentifier = this.nextState();
        this.addNode(nameIdentifier, instrAssignation.target);
        this.addTransition(nodeIdentifier, nameIdentifier);

        // Expr
        String exprState = instrAssignation.expr.accept(this);
        this.addTransition(nodeIdentifier, exprState);

        return nodeIdentifier;
    }

    @Override
    public String visit(InstrAssignationTable instrAssignationTable) {
        String nodeIdentifier = this.nextState();
        this.addNode(nodeIdentifier, "Assignation Table");

        // Access Table
        String accessTable = instrAssignationTable.target.accept(this);
        this.addTransition(nodeIdentifier, accessTable);

        // Expr
        String exprState = instrAssignationTable.expr.accept(this);
        this.addTransition(nodeIdentifier, exprState);

        return nodeIdentifier;
    }

    @Override
    public String visit(ExprLen exprLen) {
        String nodeIdentifier = this.nextState();
        this.addNode(nodeIdentifier, "Len");

        String exprState = exprLen.target.accept(this);
        this.addTransition(nodeIdentifier, exprState);

        return nodeIdentifier;
    }

    @Override
    public String visit(ExprFunc exprFunc) {
        String nodeIdentifier = this.nextState();
        this.addNode(nodeIdentifier, "Func");

        // Nom
        String nameIdentifier = this.nextState();
        this.addNode(nameIdentifier, exprFunc.name);
        this.addTransition(nodeIdentifier, nameIdentifier);

        // Args
        String argsState = exprFunc.args.accept(this);
        this.addTransition(nodeIdentifier, argsState);

        return nodeIdentifier;
    }

    @Override
    public String visit(AccessTable accessTable) {
        String nodeIdentifier = this.nextState();

        this.addNode(nodeIdentifier, "AccessTable");
        // IDF
        String nameIdentifier = this.nextState();
        this.addNode(nameIdentifier, accessTable.idf);
        this.addTransition(nodeIdentifier, nameIdentifier);

        // Args
        for (Ast expr: accessTable.exprs) {
            String argState = expr.accept(this);
            this.addTransition(nodeIdentifier, argState);
        }

        return nodeIdentifier;
    }

    @Override
    public String visit(ExprVec exprVec) {
        String nodeIdentifier = this.nextState();
        this.addNode(nodeIdentifier, "Vec");

        // Args
        String argState = exprVec.args.accept(this);
        this.addTransition(nodeIdentifier, argState);

        return nodeIdentifier;
    }

    @Override
    public String visit(ExprOr exprOr) {
        String nodeIdentifier = this.nextState();
        this.addNode(nodeIdentifier,"OR");

        String leftState = exprOr.left.accept(this);
        this.addTransition(nodeIdentifier, leftState);

        String rightState = exprOr.right.accept(this);
        this.addTransition(nodeIdentifier, rightState);

        return nodeIdentifier;
    }

    @Override
    public String visit(ExprAnd exprAnd) {
        String nodeIdentifier = this.nextState();
        this.addNode(nodeIdentifier,"AND");

        String leftState = exprAnd.left.accept(this);
        this.addTransition(nodeIdentifier, leftState);

        String rightState = exprAnd.right.accept(this);
        this.addTransition(nodeIdentifier, rightState);

        return nodeIdentifier;
    }

    @Override
    public String visit(ExprCond exprCond) {
        String nodeIdentifier = this.nextState();
        this.addNode(nodeIdentifier,"Cond");

        String rightState = exprCond.right.accept(this);
        this.addTransition(nodeIdentifier, rightState);

        String condIdentifier = this.nextState();
        this.addNode(condIdentifier, exprCond.condition);
        this.addTransition(nodeIdentifier, condIdentifier);

        String leftState = exprCond.left.accept(this);
        this.addTransition(nodeIdentifier, leftState);

        return nodeIdentifier;
    }

    @Override
    public String visit(PassArgs passArgs) {
        String nodeIdentifier = this.nextState();
        this.addNode(nodeIdentifier, "PassArgs");

        // Args
        for (Ast expr: passArgs.exprs) {
            String argState = expr.accept(this);
            this.addTransition(nodeIdentifier, argState);
        }

        return nodeIdentifier;
    }

    @Override
    public String visit(ExprPlusOrMinus exprPlusOrMinus) {
        String nodeIdentifier = this.nextState();
        this.addNode(nodeIdentifier, exprPlusOrMinus.op);

        String rightState = exprPlusOrMinus.right.accept(this);
        this.addTransition(nodeIdentifier, rightState);

        String leftState = exprPlusOrMinus.left.accept(this);
        this.addTransition(nodeIdentifier, leftState);

        return nodeIdentifier;
    }

    @Override
    public String visit(ExprMultOrDiv exprMultOrDiv) {
        String nodeIdentifier = this.nextState();
        this.addNode(nodeIdentifier, exprMultOrDiv.op);

        String rightState = exprMultOrDiv.right.accept(this);
        this.addTransition(nodeIdentifier, rightState);

        String leftState = exprMultOrDiv.left.accept(this);
        this.addTransition(nodeIdentifier, leftState);

        return nodeIdentifier;
    }

    @Override
    public String visit(ExprDot exprDot) {
        String nodeIdentifier = this.nextState();
        this.addNode(nodeIdentifier, "Dot");

        String leftState = exprDot.left.accept(this);
        this.addTransition(nodeIdentifier, leftState);

        String rightState = exprDot.right;
        this.addTransition(nodeIdentifier, rightState);

        return nodeIdentifier;
    }

    @Override
    public String visit(ExprUnary exprUnary) {
        String nodeIdentifier = this.nextState();
        this.addNode(nodeIdentifier, exprUnary.operator);

        String targetState = exprUnary.target.accept(this);
        this.addTransition(nodeIdentifier, targetState);

        return nodeIdentifier;
    }

    @Override
    public String visit(ExprIndex exprIndex) {
        String nodeIdentifier = this.nextState();
        this.addNode(nodeIdentifier, "Index");
        /*
        String sourceState = exprIndex.source.accept(this);
        this.addTransition(nodeIdentifier, sourceState);

        String index = exprIndex.index.accept(this);
        this.addTransition(nodeIdentifier, index);
        */
        return nodeIdentifier;
    }

    @Override
    public String visit(ExprParenthesis exprParenthesis) {
        String nodeIdentifier = this.nextState();
        this.addNode(nodeIdentifier, "Parenthesis");

        String targetState = exprParenthesis.expr.accept(this);
        this.addTransition(nodeIdentifier, targetState);

        return nodeIdentifier;
    }

    @Override
    public String visit(LeafIdentifier leafIdentifiant) {
        String nodeIdentifier = this.nextState();
        this.addNode(nodeIdentifier, leafIdentifiant.name);

        return nodeIdentifier;
    }

    @Override
    public String visit(LeafInteger leafInteger) {
        String nodeIdentifier = this.nextState();
        this.addNode(nodeIdentifier, Integer.toString(leafInteger.value));

        return nodeIdentifier;
    }

    @Override
    public String visit(LeafBoolean leafBoolean) {
        String nodeIdentifier = this.nextState();
        this.addNode(nodeIdentifier, Boolean.toString(leafBoolean.value));

        return nodeIdentifier;
    }

    @Override
    public String visit(TypeRef vec) {
        String nodeIdentifier = this.nextState();
        String type = vec.type.accept(this);
        this.addNode(nodeIdentifier, "&" + type );

        return nodeIdentifier;
    }

    @Override
    public String visit(TypeRefMut refMut) {
        String nodeIdentifier = this.nextState();
        this.addNode(nodeIdentifier, "RefMut");

        String typeState = refMut.type.accept(this);
        this.addTransition(nodeIdentifier, typeState);

        return nodeIdentifier;
    }

    @Override
    public String visit(TypeVoid voidType) {
        String nodeIdentifier = this.nextState();
        this.addNode(nodeIdentifier, "VOID");

        return nodeIdentifier;
    }

    @Override
    public String visit(TypeVec typeVec) {
        String nodeIdentifier = this.nextState();
        this.addNode(nodeIdentifier, "Vec");

        String typeState = typeVec.type.accept(this);
        this.addTransition(nodeIdentifier, typeState);

        return nodeIdentifier;
    }

}
