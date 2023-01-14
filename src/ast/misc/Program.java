package ast.misc;

import ast.Ast;
import ast.AstVisitor;

import java.util.ArrayList;

public class Program implements Ast {

    public <T> T accept(AstVisitor<T> visitor){
        return visitor.visit(this);
    }

    public ArrayList<Ast> instructions;

    public Program(ArrayList<Ast> instructions){
        this.instructions = instructions;
    }
}
