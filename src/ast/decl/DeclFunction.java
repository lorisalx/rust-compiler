package ast.decl;

import ast.Ast;
import ast.AstVisitor;

import java.util.ArrayList;

public class DeclFunction implements Ast {

    public <T> T accept(AstVisitor<T> visitor){
        return visitor.visit(this);
    }

    public String name;
    public Ast args;
    public Ast returnType;
    public Ast bloc;

    public DeclFunction(String name, Ast args, Ast returnType, Ast bloc) {
        this.name = name;
        this.args = args;
        this.returnType = returnType;
        this.bloc = bloc;
    }
}
