// Generated from rust.g4 by ANTLR 4.9.2

    package parser;

import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link rustParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface rustVisitor<T> extends ParseTreeVisitor<T> {
	/**
	 * Visit a parse tree produced by {@link rustParser#program}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitProgram(rustParser.ProgramContext ctx);
	/**
	 * Visit a parse tree produced by {@link rustParser#decl}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDecl(rustParser.DeclContext ctx);
	/**
	 * Visit a parse tree produced by {@link rustParser#declFunction}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDeclFunction(rustParser.DeclFunctionContext ctx);
	/**
	 * Visit a parse tree produced by {@link rustParser#declArgs}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDeclArgs(rustParser.DeclArgsContext ctx);
	/**
	 * Visit a parse tree produced by {@link rustParser#declArg}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDeclArg(rustParser.DeclArgContext ctx);
	/**
	 * Visit a parse tree produced by {@link rustParser#declReturnType}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDeclReturnType(rustParser.DeclReturnTypeContext ctx);
	/**
	 * Visit a parse tree produced by {@link rustParser#bloc}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBloc(rustParser.BlocContext ctx);
	/**
	 * Visit a parse tree produced by the {@code instrExpr}
	 * labeled alternative in {@link rustParser#instr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitInstrExpr(rustParser.InstrExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code instrDeclLet}
	 * labeled alternative in {@link rustParser#instr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitInstrDeclLet(rustParser.InstrDeclLetContext ctx);
	/**
	 * Visit a parse tree produced by the {@code instrDeclLetStruct}
	 * labeled alternative in {@link rustParser#instr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitInstrDeclLetStruct(rustParser.InstrDeclLetStructContext ctx);
	/**
	 * Visit a parse tree produced by the {@code instrWhile}
	 * labeled alternative in {@link rustParser#instr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitInstrWhile(rustParser.InstrWhileContext ctx);
	/**
	 * Visit a parse tree produced by the {@code instrReturn}
	 * labeled alternative in {@link rustParser#instr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitInstrReturn(rustParser.InstrReturnContext ctx);
	/**
	 * Visit a parse tree produced by the {@code instrIfElse}
	 * labeled alternative in {@link rustParser#instr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitInstrIfElse(rustParser.InstrIfElseContext ctx);
	/**
	 * Visit a parse tree produced by the {@code instrAssignation}
	 * labeled alternative in {@link rustParser#instr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitInstrAssignation(rustParser.InstrAssignationContext ctx);
	/**
	 * Visit a parse tree produced by the {@code instrAssignationTable}
	 * labeled alternative in {@link rustParser#instr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitInstrAssignationTable(rustParser.InstrAssignationTableContext ctx);
	/**
	 * Visit a parse tree produced by {@link rustParser#accessTable}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAccessTable(rustParser.AccessTableContext ctx);
	/**
	 * Visit a parse tree produced by the {@code exprComputation}
	 * labeled alternative in {@link rustParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExprComputation(rustParser.ExprComputationContext ctx);
	/**
	 * Visit a parse tree produced by the {@code exprBloc}
	 * labeled alternative in {@link rustParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExprBloc(rustParser.ExprBlocContext ctx);
	/**
	 * Visit a parse tree produced by the {@code exprPrint}
	 * labeled alternative in {@link rustParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExprPrint(rustParser.ExprPrintContext ctx);
	/**
	 * Visit a parse tree produced by the {@code exprOr}
	 * labeled alternative in {@link rustParser#expr1}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExprOr(rustParser.ExprOrContext ctx);
	/**
	 * Visit a parse tree produced by the {@code exprAnd}
	 * labeled alternative in {@link rustParser#expr2}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExprAnd(rustParser.ExprAndContext ctx);
	/**
	 * Visit a parse tree produced by the {@code exprCond}
	 * labeled alternative in {@link rustParser#expr3}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExprCond(rustParser.ExprCondContext ctx);
	/**
	 * Visit a parse tree produced by the {@code exprPlusOrMinus}
	 * labeled alternative in {@link rustParser#expr4}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExprPlusOrMinus(rustParser.ExprPlusOrMinusContext ctx);
	/**
	 * Visit a parse tree produced by the {@code exprMultOrDiv}
	 * labeled alternative in {@link rustParser#expr5}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExprMultOrDiv(rustParser.ExprMultOrDivContext ctx);
	/**
	 * Visit a parse tree produced by the {@code exprUnary}
	 * labeled alternative in {@link rustParser#expr6}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExprUnary(rustParser.ExprUnaryContext ctx);
	/**
	 * Visit a parse tree produced by the {@code exprDot}
	 * labeled alternative in {@link rustParser#expr7}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExprDot(rustParser.ExprDotContext ctx);
	/**
	 * Visit a parse tree produced by the {@code exprIndex}
	 * labeled alternative in {@link rustParser#expr7}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExprIndex(rustParser.ExprIndexContext ctx);
	/**
	 * Visit a parse tree produced by the {@code exprLen}
	 * labeled alternative in {@link rustParser#expr7}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExprLen(rustParser.ExprLenContext ctx);
	/**
	 * Visit a parse tree produced by the {@code exprFunc}
	 * labeled alternative in {@link rustParser#expr7}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExprFunc(rustParser.ExprFuncContext ctx);
	/**
	 * Visit a parse tree produced by the {@code exprVec}
	 * labeled alternative in {@link rustParser#expr7}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExprVec(rustParser.ExprVecContext ctx);
	/**
	 * Visit a parse tree produced by the {@code exprParenthesis}
	 * labeled alternative in {@link rustParser#expr8}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExprParenthesis(rustParser.ExprParenthesisContext ctx);
	/**
	 * Visit a parse tree produced by the {@code exprIdentifier}
	 * labeled alternative in {@link rustParser#expr9}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExprIdentifier(rustParser.ExprIdentifierContext ctx);
	/**
	 * Visit a parse tree produced by the {@code exprInteger}
	 * labeled alternative in {@link rustParser#expr9}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExprInteger(rustParser.ExprIntegerContext ctx);
	/**
	 * Visit a parse tree produced by the {@code exprBoolean}
	 * labeled alternative in {@link rustParser#expr9}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExprBoolean(rustParser.ExprBooleanContext ctx);
	/**
	 * Visit a parse tree produced by {@link rustParser#passArgs}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPassArgs(rustParser.PassArgsContext ctx);
	/**
	 * Visit a parse tree produced by {@link rustParser#declStruct}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDeclStruct(rustParser.DeclStructContext ctx);
	/**
	 * Visit a parse tree produced by the {@code typeIdentifier}
	 * labeled alternative in {@link rustParser#type}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTypeIdentifier(rustParser.TypeIdentifierContext ctx);
	/**
	 * Visit a parse tree produced by the {@code typeVec}
	 * labeled alternative in {@link rustParser#type}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTypeVec(rustParser.TypeVecContext ctx);
	/**
	 * Visit a parse tree produced by the {@code typeRef}
	 * labeled alternative in {@link rustParser#type}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTypeRef(rustParser.TypeRefContext ctx);
	/**
	 * Visit a parse tree produced by the {@code typeRefMut}
	 * labeled alternative in {@link rustParser#type}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTypeRefMut(rustParser.TypeRefMutContext ctx);
	/**
	 * Visit a parse tree produced by the {@code typeVoid}
	 * labeled alternative in {@link rustParser#type}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTypeVoid(rustParser.TypeVoidContext ctx);
}