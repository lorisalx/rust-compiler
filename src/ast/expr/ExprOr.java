package ast.expr;

import ast.Ast;
import ast.AstVisitor;

public class ExprOr implements Ast {
    public <T> T accept(AstVisitor<T> visitor){
        return visitor.visit(this);
    }

    public Ast left;
    public Ast right;

    public ExprOr(Ast left, Ast right){
        this.left = left;
        this.right = right;
    }
}
