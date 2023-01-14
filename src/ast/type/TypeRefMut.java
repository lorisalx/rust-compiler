package ast.type;

import ast.Ast;
import ast.AstVisitor;

public class TypeRefMut implements Ast {
    @Override public <T> T accept(AstVisitor<T> visitor) {
        return visitor.visit(this);
    }

    public Ast type;

    public TypeRefMut(Ast type){
        this.type = type;
    }
}
