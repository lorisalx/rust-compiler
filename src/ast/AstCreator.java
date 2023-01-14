package ast;

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
import ast.type.*;
import parser.rustBaseVisitor;
import parser.rustParser;

import java.util.ArrayList;

public class AstCreator extends rustBaseVisitor<Ast>{

	// Rule : program: decl* EOF ;
	@Override public Ast visitProgram(rustParser.ProgramContext ctx) {
		int nbChilds = ctx.getChildCount();
		ArrayList<Ast> instructions = new ArrayList<>();

		// on parcourt toutes les instructions et on skip EOF
		for (int i = 0; i < nbChilds - 1; i++) {
			instructions.add(ctx.getChild(i).accept(this));
		}
		return new Program(instructions);
	}

	// Rule : decl: declFunction | declStruct ;
	@Override public Ast visitDecl(rustParser.DeclContext ctx) {
		return ctx.getChild(0).accept(this);
	}

	// Rule : declFunction: 'fn' IDF ('(' declArgs ')' | '()') declReturnType bloc ;
	@Override public Ast visitDeclFunction(rustParser.DeclFunctionContext ctx) {
		int lastIndex = ctx.getChildCount() - 1;
		String name = ctx.getChild(1).toString();
		DeclArgs args = null;
		Ast returnType = ctx.getChild(lastIndex - 1).accept(this);
		Ast bloc = ctx.getChild(lastIndex).accept(this);
		if (ctx.getChildCount() == 7) {
			args = (DeclArgs) ctx.getChild(3).accept(this);
		}
		return new DeclFunction(name, args, returnType, bloc);
	}

	// Rule : declArgs: (declArg ',')* declArg? ;
	@Override public Ast visitDeclArgs(rustParser.DeclArgsContext ctx) {
		ArrayList<DeclArg> args = new ArrayList<>();
		// On parcourt la liste d'arguments
		for (int i = 0; i < ctx.getChildCount(); i = i + 2) {
			args.add((DeclArg) ctx.getChild(i).accept(this));
		}
		return new DeclArgs(args);
	}

	// Rule : declArg: 'mut'? IDF ':' type ;
	@Override public Ast visitDeclArg(rustParser.DeclArgContext ctx) {
		String idf;
		Ast type;
		boolean mutable = ctx.getChild(0).toString().equals("mut");
		if (mutable) {
			idf = ctx.getChild(1).toString();
			type = ctx.getChild(3).accept(this);
		} else {
			idf = ctx.getChild(0).toString();
			type = ctx.getChild(2).accept(this);
		}
		return new DeclArg(idf, type, mutable);
	}

	// Rule : declReturnType: ('->' type)? ;
	@Override public Ast visitDeclReturnType(rustParser.DeclReturnTypeContext ctx) {
		Ast type = null;
		if (ctx.getChildCount() > 0) {
			type = ctx.getChild(1).accept(this);
		}
		return new DeclReturnType(type);
	}

	// Rule : bloc: '{' instr* '}' ;
	@Override public Ast visitBloc(rustParser.BlocContext ctx) {
		ArrayList<Ast> instrs = new ArrayList<>();
		// for without '{' and '}'
		for (int i = 1; i < ctx.getChildCount() - 1; i++) {
			instrs.add(ctx.getChild(i).accept(this));
		}
		return new Bloc(instrs);
	}

	// Rule : instr: expr ';' #instrExpr
	@Override public Ast visitInstrExpr(rustParser.InstrExprContext ctx) {
		return ctx.getChild(0).accept(this);
	}

	// Rule : instr: 'let' 'mut'? IDF':' type ('=' expr)? ';' #instrDeclLet
	@Override public Ast visitInstrDeclLet(rustParser.InstrDeclLetContext ctx) {
		String name;
		Ast type, value = null;
		boolean mutable = ctx.getChild(1).toString().equals("mut");
		int indexIdf;
		if (mutable) {
			indexIdf = 2;
		} else {
			indexIdf = 1;
		}
		name = ctx.getChild(indexIdf).toString();
		type = ctx.getChild(indexIdf + 2).accept(this);
		if (ctx.getChild(indexIdf + 3).toString().equals("=")) {
			value = ctx.getChild(indexIdf + 4).accept(this);
		}
		return new InstrDeclLet(name,type,mutable,value);
	}

	// Rule : instr: 'let' 'mut'? IDF':' type ('=' IDF '{' declArgs '}')? ';' #instrDeclLetStruct
	@Override public Ast visitInstrDeclLetStruct(rustParser.InstrDeclLetStructContext ctx) {
		String name;
		Ast type;
		boolean mutable = false;
		DeclArgs args = (DeclArgs) ctx.getChild(7).accept(this);
		if(ctx.getChild(1).toString().equals("mut")){
			name = ctx.getChild(2).toString();
			type = ctx.getChild(4).accept(this);
			mutable = true;
			args = (DeclArgs) ctx.getChild(8).accept(this);
		} else {
			name = ctx.getChild(1).toString();
			type = ctx.getChild(3).accept(this);
		}
		return new InstrDeclLetStruct(name,type,mutable,args);
	}

	// Rule : instr: 'while' expr bloc #instrWhile
	@Override public Ast visitInstrWhile(rustParser.InstrWhileContext ctx) {
		return new InstrWhile(ctx.getChild(1).accept(this),ctx.getChild(2).accept(this));
	}

	// Rule : instr: 'return' expr? ';' #instrReturn
	@Override public Ast visitInstrReturn(rustParser.InstrReturnContext ctx) {
		if(ctx.getChildCount() > 2){
			return new InstrReturn(ctx.getChild(1).accept(this));
		} else {
			return new InstrReturn(null);
		}
	}

	// Rule : instr: 'if' expr bloc ('else' bloc)? #instrIfElse
	@Override public Ast visitInstrIfElse(rustParser.InstrIfElseContext ctx) {
		Ast blocElse = null;
		if(ctx.getChildCount() > 3){
			blocElse = ctx.getChild(4).accept(this);
		}
		return new InstrIfElse(ctx.getChild(1).accept(this), ctx.getChild(2).accept(this), blocElse);
	}

	// Rule: IDF '=' expr ';' #instrAssignation
	@Override public Ast visitInstrAssignation(rustParser.InstrAssignationContext ctx) {
		String target = ctx.getChild(0).toString();
		Ast expr = ctx.getChild(2).accept(this);
		return new InstrAssignation(target, expr);
	}

	@Override public Ast visitInstrAssignationTable(rustParser.InstrAssignationTableContext ctx) {
		Ast target = ctx.getChild(0).accept(this);
		Ast expr = ctx.getChild(2).accept(this);
		return new InstrAssignationTable(target, expr);
	}

	// Rule : accessTable: IDF('[' expr ']')+;
	@Override public Ast visitAccessTable(rustParser.AccessTableContext ctx) {
		String idf = ctx.getChild(0).toString();
		ArrayList<Ast> exprs = new ArrayList<>();
		for (int i = 2; i < ctx.getChildCount(); i += 3) {
			exprs.add(ctx.getChild(i).accept(this));
		}
		return new AccessTable(idf, exprs);
	}

	// Rule: expr1 #exprComputation
	@Override public Ast visitExprComputation(rustParser.ExprComputationContext ctx) {
		return ctx.getChild(0).accept(this);
	}
	// Rule: bloc #exprBloc
	@Override public Ast visitExprBloc(rustParser.ExprBlocContext ctx) {
		return ctx.getChild(0).accept(this);
	}

	// Rule: 'print' '!' '(' (STR | expr) ')'
	@Override public Ast visitExprPrint(rustParser.ExprPrintContext ctx) {
		String fullString = ctx.getChild(3).toString();
		String contentWithoutDoubleQuotes = fullString.substring(1, fullString.length() - 1);
		return new ExprPrint(contentWithoutDoubleQuotes);
	}

	// Rule : expr1: ((expr2 '||' expr1) | expr2);
	@Override public Ast visitExprOr(rustParser.ExprOrContext ctx) {
		if (ctx.getChildCount() == 1) {
			// expr1
			return ctx.getChild(0).accept(this);
		} else {
			// expr2 '||' expr1
			Ast left = ctx.getChild(0).accept(this);
			Ast right = ctx.getChild(2).accept(this);
			return new ExprOr(left, right);
		}
	}

	// Rule: ((expr3 '&&' expr2) | expr3) #exprAnd;
	@Override public Ast visitExprAnd(rustParser.ExprAndContext ctx) {
		if (ctx.getChildCount() == 1) {
			// expr3
			return ctx.getChild(0).accept(this);
		} else {
			Ast left = ctx.getChild(0).accept(this);
			Ast right = ctx.getChild(2).accept(this);
			return new ExprAnd(left, right);
		}
	}

	// Rule: expr3: ((expr4 ('==' | '!=' | '<' | '<=' | '>' | '>=') expr3) | expr4);
	@Override public Ast visitExprCond(rustParser.ExprCondContext ctx) {
		if (ctx.getChildCount() == 1) {
			return ctx.getChild(0).accept(this);
		} else {
			Ast right = ctx.getChild(0).accept(this);
			String operator = ctx.getChild(1).toString();
			Ast left = ctx.getChild(2).accept(this);
			return new ExprCond(left, operator, right);
		}
	}

	// Rule: expr4: ((expr5 ('+' | '-') expr4) | expr5);
	@Override public Ast visitExprPlusOrMinus(rustParser.ExprPlusOrMinusContext ctx) {
		if (ctx.getChildCount() == 1) {
			return ctx.getChild(0).accept(this);
		} else {
			Ast right = ctx.getChild(0).accept(this);
			String operator = ctx.getChild(1).toString();
			Ast left = ctx.getChild(2).accept(this);
			return new ExprPlusOrMinus(left, operator, right);
		}
	}

	// Rule: expr5: ((expr6 ('*' | '/' | '%') expr5) | expr6)
	@Override public Ast visitExprMultOrDiv(rustParser.ExprMultOrDivContext ctx) {
		if (ctx.getChildCount() == 1) {
			return ctx.getChild(0).accept(this);
		} else {
			Ast right = ctx.getChild(0).accept(this);
			String operator = ctx.getChild(1).toString();
			Ast left = ctx.getChild(2).accept(this);
			return new ExprMultOrDiv(left, operator, right);
		}
	}

	// Rule : expr6: (('!' | '-' | '*' | '&' | '&mut') expr6 | expr7)
	@Override public Ast visitExprUnary(rustParser.ExprUnaryContext ctx) {
		String operator;
		Ast target;
		if (ctx.getChildCount() == 1) {
			return ctx.getChild(0).accept(this);
		} else if (ctx.getChildCount() == 3) {
			// & mut
			operator = "&mut";
			target = ctx.getChild(2).accept(this);
		} else {
			operator = ctx.getChild(0).toString();
			target = ctx.getChild(1).accept(this);
		}
		return new ExprUnary(operator, target);
	}

	// Rule : (expr8 '.' IDF | expr8) #exprDot
	@Override public Ast visitExprDot(rustParser.ExprDotContext ctx) {
		if (ctx.getChildCount() == 1) {
			// expr8
			return ctx.getChild(0).accept(this);
		} else {
			Ast left = ctx.getChild(0).accept(this);
			String right = ctx.getChild(2).toString();
			return new ExprDot(left, right);
		}
	}

	// Rule: ((expr8 ('[' expr ']')+) | expr8) #exprIndex
	@Override public Ast visitExprIndex(rustParser.ExprIndexContext ctx) {
		if (ctx.getChildCount() == 1) {
			return ctx.getChild(0).accept(this);
		} else {
			Ast target = ctx.getChild(0).accept(this);
			ArrayList<Ast> indexes = new ArrayList<>();
			for (int i = 2; i < ctx.getChildCount(); i += 3) {
				indexes.add(ctx.getChild(i).accept(this));
			}
			return new ExprIndex(target, indexes);
		}
	}

	// (expr8 '.' 'len' '()' | expr8) #exprLen
	@Override public Ast visitExprLen(rustParser.ExprLenContext ctx) {
		if (ctx.getChildCount() == 1) {
			return ctx.getChild(0).accept(this);
		} else {
			return new ExprLen(ctx.getChild(0).accept(this));
		}
	}

	// (IDF '(' passArgs ')' | expr8) #exprFunc
	@Override public Ast visitExprFunc(rustParser.ExprFuncContext ctx) {
		if (ctx.getChildCount() == 1) {
			return ctx.getChild(0).accept(this);
		} else {
			String idf = ctx.getChild(0).toString();
			Ast args = ctx.getChild(2).accept(this);
			return new ExprFunc(idf, args);
		}
	}

	// Rule: ('vec' '!' '[' passArgs ']' | expr8) #exprVec
	@Override public Ast visitExprVec(rustParser.ExprVecContext ctx) {
		if (ctx.getChildCount() == 1) {
			return ctx.getChild(0).accept(this);
		} else {
			Ast args = ctx.getChild(3).accept(this);
			return new ExprVec(args);
		}
	}

	// Rule: expr8: (('(' expr ')') | expr9) #exprParenthesis;
	@Override public Ast visitExprParenthesis(rustParser.ExprParenthesisContext ctx) {
		if (ctx.getChildCount() == 1) {
			return ctx.getChild(0).accept(this);
		} else {
			return ctx.getChild(1).accept(this);
		}
	}

	// Rule: IDF #exprIdentifier
	@Override public Ast visitExprIdentifier(rustParser.ExprIdentifierContext ctx) {
		return new LeafIdentifier(ctx.getChild(0).toString());
	}
	// Rule: INT #exprInteger
	@Override public Ast visitExprInteger(rustParser.ExprIntegerContext ctx) {
		return new LeafInteger(Integer.parseInt(ctx.getChild(0).toString()));
	}
	// Rule: BOOLEAN #exprBoolean
	@Override public Ast visitExprBoolean(rustParser.ExprBooleanContext ctx) {
		return new LeafBoolean(Boolean.parseBoolean(ctx.getChild(0).toString()));
	}

	// Rule: expr: (expr ',')* expr;
	@Override public Ast visitPassArgs(rustParser.PassArgsContext ctx) {
		ArrayList<Ast> args = new ArrayList<>();
		for (int i = 0; i < ctx.getChildCount(); i = i + 2) {
			args.add(ctx.getChild(i).accept(this));
		}
		return new PassArgs(args);
	}

	// Rule : declStruct: 'struct' IDF '{' declArgs? '}' ;
	@Override public Ast visitDeclStruct(rustParser.DeclStructContext ctx) {
		String name = ctx.getChild(1).toString();
		DeclArgs args = (DeclArgs) ctx.getChild(3).accept(this);
		return new DeclStruct(name, args);
	}

	// Rule: IDF #typeIdentifier
	@Override public Ast visitTypeIdentifier(rustParser.TypeIdentifierContext ctx) {
		return new LeafIdentifier(ctx.getChild(0).toString());
	}
	// Rule: 'Vec<'type'>' #typeVec
	@Override public Ast visitTypeVec(rustParser.TypeVecContext ctx) {
		return new TypeVec(ctx.getChild(1).accept(this));
	}
	// Rule: '&' type #typeRef
	@Override public Ast visitTypeRef(rustParser.TypeRefContext ctx) {
		return new TypeRef(ctx.getChild(1).accept(this));
	}
	// Rule: ('&' 'mut' | '&mut') type #typeRefMut
	@Override public Ast visitTypeRefMut(rustParser.TypeRefMutContext ctx) {
		return new TypeRefMut(ctx.getChild(1).accept(this));
	}
	// Rule: '()' #typeVoid
	@Override public Ast visitTypeVoid(rustParser.TypeVoidContext ctx) {
		return new TypeVoid();
	}
}