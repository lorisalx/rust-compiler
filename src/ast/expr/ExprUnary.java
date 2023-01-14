package ast.expr;

import ast.Ast;
import ast.AstVisitor;

public class ExprUnary implements Ast {
    public <T> T accept(AstVisitor<T> visitor){
        return visitor.visit(this);
    }

    public String operator;
    public Ast target;

    public ExprUnary(String operator, Ast target){
        this.operator = operator;
        this.target = target;
    }
}
