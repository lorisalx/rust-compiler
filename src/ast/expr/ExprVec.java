package ast.expr;

import ast.Ast;
import ast.AstVisitor;
import ast.misc.PassArgs;

public class ExprVec implements Ast {
    public <T> T accept(AstVisitor<T> visitor){
        return visitor.visit(this);
    }

    public Ast args;

    public ExprVec(Ast args){
        this.args = args;
    }
}
