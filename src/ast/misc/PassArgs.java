package ast.misc;

import ast.Ast;
import ast.AstVisitor;

import java.util.List;

public class PassArgs implements Ast {
    public <T> T accept(AstVisitor<T> visitor){
        return visitor.visit(this);
    }

    public List<Ast> exprs;

    public PassArgs(List<Ast> exprs) {
        this.exprs = exprs;
    }
}
