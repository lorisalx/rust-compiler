package ast.decl;

import ast.Ast;
import ast.AstVisitor;

import java.util.ArrayList;

public class DeclArgs implements Ast {
    public <T> T accept(AstVisitor<T> visitor){
        return visitor.visit(this);
    }

    public ArrayList<DeclArg> args;

    public DeclArgs(ArrayList<DeclArg> args) {
        this.args = args;
    }
}
