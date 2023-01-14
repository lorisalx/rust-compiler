package ast.leaf;

import ast.Ast;
import ast.AstVisitor;

public class LeafIdentifier implements Ast {
    public <T> T accept(AstVisitor<T> visitor){
        return visitor.visit(this);
    }

    public String name;

    public LeafIdentifier(String name) {
        this.name = name;
    }
}
