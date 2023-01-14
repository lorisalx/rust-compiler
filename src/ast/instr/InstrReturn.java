package ast.instr;

import ast.Ast;
import ast.AstVisitor;

public class InstrReturn implements Ast {
    public <T> T accept(AstVisitor<T> visitor){
        return visitor.visit(this);
    }

    public Ast expr;

    public InstrReturn(Ast expr) {
        this.expr = expr;
    }
}
