package ast.leaf;

import ast.Ast;
import ast.AstVisitor;

public class LeafBoolean implements Ast {
    public <T> T accept(AstVisitor<T> visitor){
        return visitor.visit(this);
    }

    public boolean value;

    public LeafBoolean(boolean value) {
        this.value = value;
    }
}
