package ast.instr;

import ast.Ast;
import ast.AstVisitor;
import ast.decl.DeclArgs;

public class InstrDeclLetStruct implements Ast {
    public <T> T accept(AstVisitor<T> visitor) {
        return visitor.visit(this);
    }

    public String name;
    public Ast type;
    public boolean mutable;
    public DeclArgs args;

    public InstrDeclLetStruct(String name, Ast type, boolean mutable, DeclArgs args) {
        this.name = name;
        this.type = type;
        this.mutable = mutable;
        this.args = args;
    }
}
