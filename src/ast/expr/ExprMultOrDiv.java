package ast.expr;

import ast.Ast;
import ast.AstVisitor;

public class ExprMultOrDiv implements Ast {
    public <T> T accept(AstVisitor<T> visitor){
        return visitor.visit(this);
    }

    public Ast left;
    public Ast right;
    public String op;

    public ExprMultOrDiv(Ast left, String op, Ast right){
        this.left = left;
        this.right = right;
        this.op = op;
    }
}
