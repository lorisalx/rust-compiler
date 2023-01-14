package ast.expr;

import ast.Ast;
import ast.AstVisitor;

public class ExprPrint implements Ast {

    public <T> T accept(AstVisitor<T> visitor){
        return visitor.visit(this);
    }

    public String value;

    public ExprPrint(String value) {
        this.value = value;
    }
}
