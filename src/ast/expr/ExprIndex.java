package ast.expr;

import ast.Ast;
import ast.AstVisitor;

import java.util.List;

public class ExprIndex implements Ast {
    public <T> T accept(AstVisitor<T> visitor){
        return visitor.visit(this);
    }

    public Ast target;
    public List<Ast> index;

    public ExprIndex(Ast target, List<Ast> index){
        this.target = target;
        this.index = index;
    }
}
