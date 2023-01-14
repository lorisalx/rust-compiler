package ast.expr;

import ast.Ast;
import ast.AstVisitor;
import ast.misc.PassArgs;

public class ExprFunc implements Ast {

        public <T> T accept(AstVisitor<T> visitor){
            return visitor.visit(this);
        }

        public String name;
        public Ast args;

        public ExprFunc(String name, Ast args){
            this.name = name;
            this.args = args;
        }

}
