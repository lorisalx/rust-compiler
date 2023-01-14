package ast.instr;

import ast.Ast;
import ast.AstVisitor;

public class InstrIfElse implements Ast {
    public <T> T accept(AstVisitor<T> visitor) {
        return visitor.visit(this);
    }

    public Ast expr;
    public Ast blocIf;
    public Ast blocElse;

    public InstrIfElse(Ast expr, Ast blocIf, Ast blocElse) {
        this.expr = expr;
        this.blocIf = blocIf;
        this.blocElse = blocElse;
    }
}
