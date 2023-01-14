package ast.instr;

import ast.Ast;
import ast.AstVisitor;

public class InstrDeclLet implements Ast {
    public <T> T accept(AstVisitor<T> visitor){
        return visitor.visit(this);
    }

    public String name;
    public Ast type;
    public boolean mutable;
    public Ast value;

    public InstrDeclLet(String name, Ast type, boolean mutable, Ast value) {
        this.name = name;
        this.type = type;
        this.mutable = mutable;
        this.value = value;
    }
}
