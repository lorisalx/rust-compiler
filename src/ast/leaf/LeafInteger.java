package ast.leaf;

import ast.Ast;
import ast.AstVisitor;

public class LeafInteger implements Ast {
    public <T> T accept(AstVisitor<T> visitor){
        return visitor.visit(this);
    }

    public int value;

    public LeafInteger(int value) {
        this.value = value;
    }
}
