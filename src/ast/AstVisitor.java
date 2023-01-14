package ast;

import ast.decl.*;
import ast.expr.*;
import ast.instr.*;
import ast.leaf.LeafBoolean;
import ast.leaf.LeafIdentifier;
import ast.leaf.LeafInteger;
import ast.misc.*;
import ast.type.*;

public interface AstVisitor<T> {
    T visit(DeclFunction declFunction);
    T visit(ExprPrint exprPrint);
    T visit(Program program);
    T visit(Bloc bloc);
    T visit(DeclStruct declStruct);
    T visit(DeclArgs declArgs);
    T visit(DeclArg declArg);
    T visit(DeclReturnType declReturnType);
    T visit(InstrDeclLet instrDeclLet);
    T visit(InstrDeclLetStruct instrDeclLetStruct);
    T visit(InstrReturn instrReturn);
    T visit(InstrIfElse instrIfElse);
    T visit(InstrWhile instrWhile);
    T visit(InstrAssignation instrAssignation);
    T visit(InstrAssignationTable assignationTable);
    T visit(ExprLen exprLen);
    T visit(ExprFunc exprFunc);
    T visit(AccessTable accessTable);
    T visit(ExprVec exprVec);
    T visit(ExprOr exprOr);
    T visit(ExprAnd exprAnd);
    T visit(ExprCond exprCond);
    T visit(PassArgs passArgs);
    T visit(ExprPlusOrMinus exprPlusOrMinus);
    T visit(ExprMultOrDiv exprMultOrDiv);
    T visit(ExprDot exprDot);
    T visit(ExprUnary exprUnary);
    T visit(ExprIndex exprIndex);
    T visit(ExprParenthesis exprParenthesis);
    T visit(LeafIdentifier leafIdentifiant);
    T visit(LeafInteger leafInteger);
    T visit(LeafBoolean leafBoolean);
    T visit(TypeRef vec);
    T visit(TypeRefMut vec);
    T visit(TypeVoid vec);
    T visit(TypeVec typeVec);
}