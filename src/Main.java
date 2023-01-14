
import java.util.Arrays;
import javax.swing.JFrame;
import javax.swing.JPanel;

import java.io.IOException;

import org.antlr.v4.gui.TreeViewer;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.RecognitionException;

import parser.*;
import parser.rustParser.ProgramContext;

import ast.*;
import graphViz.*;
import tds.TdsVisitor;

public class Main {

    public static void main(String[] args){
        if (args.length != 2){
            args = new String[]{"examples/prog_example.rs", "true"};
        }

        String testFile = args[0];
        boolean renderingAst = Boolean.parseBoolean(args[1]);

        try {
            //chargement du fichier et construction du parser
            CharStream input = CharStreams.fromFileName(testFile);
            rustLexer lexer = new rustLexer(input); 
            CommonTokenStream stream = new CommonTokenStream(lexer);
            rustParser parser = new rustParser(stream);

            // Récupération du noeud program (le noeud à la racine)
            ProgramContext program = parser.program();
            if (renderingAst) {
                // Visiteur de création de l'AST + création de l'AST
                AstCreator creator = new AstCreator();
                Ast ast = program.accept(creator);

                // Visiteur de représentation graphique + appel
                GraphVizVisitor graphViz = new GraphVizVisitor();
                ast.accept(graphViz);

                TdsVisitor tdsVisitor = new TdsVisitor();
                ast.accept(tdsVisitor);

                tdsVisitor.showTds();
                tdsVisitor.showErrors();

                graphViz.dumpGraph("./out/tree.dot");
            } else {
                // code d'affichage de l'arbre syntaxique
                JFrame frame = new JFrame("Antlr AST");
                JPanel panel = new JPanel();
                TreeViewer viewer = new TreeViewer(
                        Arrays.asList(parser.getRuleNames()),
                        program
                );
                viewer.setScale(1.5); // Scale a little
                panel.add(viewer);
                frame.add(panel);
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.pack();
                frame.setVisible(true);
            }
        } catch (IOException | RecognitionException e) {
            e.printStackTrace();
        }
    }
}