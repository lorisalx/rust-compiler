package ast.decl;

import ast.Ast;
import ast.AstVisitor;

public class DeclArg implements Ast {
    public <T> T accept(AstVisitor<T> visitor){
        return visitor.visit(this);
    }

    public String name;
    public Ast type;
    public boolean mutable;

    public DeclArg(String name, Ast type, boolean mutable) {
        this.name = name;
        this.type = type;
        this.mutable = mutable;
    }
}
