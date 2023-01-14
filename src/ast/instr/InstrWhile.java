package ast.instr;

import ast.Ast;
import ast.AstVisitor;

public class InstrWhile implements Ast {
    public <T> T accept(AstVisitor<T> visitor){
        return visitor.visit(this);
    }

    public Ast expr;
    public Ast bloc;

    public InstrWhile(Ast expr, Ast bloc) {
        this.expr = expr;
        this.bloc = bloc;
    }
}
