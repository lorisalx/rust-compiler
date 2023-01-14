package ast.expr;

import ast.Ast;
import ast.AstVisitor;

public class ExprLen implements Ast {
    public <T> T accept(AstVisitor<T> visitor){
        return visitor.visit(this);
    }

    public Ast target;

    public ExprLen(Ast target) {
        this.target = target;
    }
}
