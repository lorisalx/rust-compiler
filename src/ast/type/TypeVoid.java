package ast.type;

import ast.Ast;
import ast.AstVisitor;

public class TypeVoid implements Ast {
    @Override public <T> T accept(AstVisitor<T> visitor) {
        return visitor.visit(this);
    }

    public TypeVoid(){
    }
}
