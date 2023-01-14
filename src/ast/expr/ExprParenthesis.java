package ast.expr;

import ast.Ast;
import ast.AstVisitor;

public class ExprParenthesis implements Ast {
    public <T> T accept(AstVisitor<T> visitor){
        return visitor.visit(this);
    }

    public Ast expr;

    public ExprParenthesis(Ast expr) {
        this.expr = expr;
    }
}
