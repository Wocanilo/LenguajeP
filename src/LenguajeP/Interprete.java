package LenguajeP;

import LenguajeP.Antlr.Anasint;
import LenguajeP.Antlr.AnasintBaseVisitor;
import LenguajeP.util.interprete.*;
import LenguajeP.util.interprete.subprograma.Mostrar;
import LenguajeP.util.interprete.subprograma.Ultima_Posicion;
import LenguajeP.util.interprete.subprograma.Vacia;

import java.util.HashMap;

public class Interprete extends AnasintBaseVisitor<Object> {
    private HashMap<String, Variable> almacenVariables;

    @Override
    public Object visitPrograma(Anasint.ProgramaContext ctx){

        // Creamos el almacen de variables
        VariablesParser variablesParser = new VariablesParser();
        this.almacenVariables = (HashMap<String, Variable>) variablesParser.visit(ctx.variables());

        // Creamos el almacen de funciones y procedimientos
        SubprogramaParser subprogramasParser = new SubprogramaParser();
        HashMap<String, Subprograma> subprogramas = (HashMap<String, Subprograma>) subprogramasParser.visit(ctx.subprogramas());
        // Definimos predicados predefinidos
        subprogramas.put("mostrar", new Mostrar());
        subprogramas.put("vacia", new Vacia());
        subprogramas.put("ultima_posicion", new Ultima_Posicion());

        // Procesamos las instrucciones del programa
        InstruccionesParser instruccionesParser = new InstruccionesParser(this.almacenVariables, subprogramas);
        instruccionesParser.visit(ctx.instrucciones_programa());

        return null;
    }
}
