package ast.decl;

import ast.Ast;
import ast.AstVisitor;

public class DeclReturnType implements Ast {
    public <T> T accept(AstVisitor<T> visitor){
        return visitor.visit(this);
    }

    public Ast type;

    public DeclReturnType(Ast type) {
        this.type = type;
    }
}
