package ast.decl;

import ast.Ast;
import ast.AstVisitor;

public class DeclStruct implements Ast {
    public <T> T accept(AstVisitor<T> visitor){
        return visitor.visit(this);
    }

    public String name;
    public DeclArgs args;

    public DeclStruct(String name, DeclArgs args) {
        this.name = name;
        this.args = args;
    }
}
