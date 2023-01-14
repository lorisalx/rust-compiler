package ast.instr;

import ast.Ast;
import ast.AstVisitor;
import ast.misc.AccessTable;

public class InstrAssignationTable implements Ast {
    public <T> T accept(AstVisitor<T> visitor){
        return visitor.visit(this);
    }

    public Ast target;
    public Ast expr;

    public InstrAssignationTable(Ast target, Ast expr) {
        this.target = target;
        this.expr = expr;
    }
}
