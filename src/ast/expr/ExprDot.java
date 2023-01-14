package ast.expr;

import ast.Ast;
import ast.AstVisitor;

public class ExprDot implements Ast {
    public <T> T accept(AstVisitor<T> visitor){
        return visitor.visit(this);
    }

    public Ast left;
    public String right;

    public ExprDot(Ast left, String right){
        this.left = left;
        this.right = right;
    }
}
