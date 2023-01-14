package ast.misc;

import ast.Ast;
import ast.AstVisitor;

import java.util.List;

public class AccessTable implements Ast {
    public <T> T accept(AstVisitor<T> visitor){
        return visitor.visit(this);
    }

    public String idf;
    public List<Ast> exprs;

    public AccessTable(String idf, List<Ast> exprs) {
        this.idf = idf;
        this.exprs = exprs;
    }
}
