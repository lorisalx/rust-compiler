package ast.misc;

import ast.Ast;
import ast.AstVisitor;

import java.util.ArrayList;
import java.util.List;

public class Bloc implements Ast {

    public <T> T accept(AstVisitor<T> visitor){
        return visitor.visit(this);
    }

    public List<Ast> content;

    public Bloc(List<Ast> content) {
        this.content = content;
    }
}
