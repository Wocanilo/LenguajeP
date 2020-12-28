package LenguajeP;

import LenguajeP.Antlr.Analex;
import LenguajeP.Antlr.Anasint;
import org.antlr.v4.gui.TreeViewer;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;

import javax.swing.*;
import java.util.Arrays;

public class Principal {
    // TODO: hay que cambiar el semantico para que entienda los negativos
    // TODO: revisar especificaciones
    // TODO: crear clase para LOG para mostrar T y F en la salida
    public static void main(String[] args) throws Exception{
        CharStream input = CharStreams.fromFileName(args[0]);
        Analex analex = new Analex(input);
        CommonTokenStream tokens = new CommonTokenStream(analex);
        Anasint anasint = new Anasint(tokens);
        ParseTree tree = anasint.programa();

        JFrame frame = new JFrame("Árbol de Análisis");
        JPanel panel = new JPanel();
        TreeViewer viewr = new TreeViewer(Arrays.asList(
                anasint.getRuleNames()),tree);
        viewr.setScale(1);//scale a little
        panel.add(viewr);
        frame.add(panel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500,400);
        frame.setVisible(true);
        // Analizador semantico al ataque
        Anasem anasem = new Anasem();
        //anasem.visit(tree);

        // Interprete
        Interprete interp = new Interprete();
        interp.visit(tree);

    }
}