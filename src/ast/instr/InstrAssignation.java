package ast.instr;

import ast.Ast;
import ast.AstVisitor;
import ast.misc.AccessTable;

public class InstrAssignation implements Ast {
    public <T> T accept(AstVisitor<T> visitor){
        return visitor.visit(this);
    }

    public String target;
    public Ast expr;

    public InstrAssignation(String target, Ast expr) {
        this.target = target;
        this.expr = expr;
    }
}
