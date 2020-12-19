package LenguajeP;

import LenguajeP.Antlr.Anasint;
import LenguajeP.Antlr.AnasintBaseVisitor;
import LenguajeP.util.*;

import java.util.HashMap;
import java.util.List;

public class Interprete extends AnasintBaseVisitor<Object> {
    private HashMap<String, Variable> almacenVariables;

    //TODO: reflejar cambios de Anasint en el analizador semantico
    @Override
    public Object visitPrograma(Anasint.ProgramaContext ctx){

        // Creamos el almacen de variables
        VariablesParser variablesParser = new VariablesParser();
        this.almacenVariables = (HashMap<String, Variable>) variablesParser.visit(ctx.variables());

        // Creamos el almacen de funciones y procedimientos
        SubprogramaParser subprogramasParser = new SubprogramaParser();
        List<Subprograma> subprogramas = (List<Subprograma>) subprogramasParser.visit(ctx.subprogramas());

        System.out.println(subprogramas);

        // Procesamos las instrucciones del programa
        InstruccionesParser instruccionesParser = new InstruccionesParser(this.almacenVariables, subprogramas);
        instruccionesParser.visit(ctx.instrucciones_programa());



        return null;
    }
}
