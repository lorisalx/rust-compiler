package ast.expr;

import ast.Ast;
import ast.AstVisitor;

public class ExprCond implements Ast {
    public <T> T accept(AstVisitor<T> visitor){
        return visitor.visit(this);
    }

    public String condition;
    public Ast left;
    public Ast right;

    public ExprCond(Ast left, String condition, Ast right){
        this.condition = condition;
        this.left = left;
        this.right = right;
    }
}
