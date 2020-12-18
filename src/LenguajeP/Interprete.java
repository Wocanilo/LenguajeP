package LenguajeP;

import LenguajeP.Antlr.Anasint;
import LenguajeP.Antlr.AnasintBaseVisitor;
import LenguajeP.util.InstruccionesParser;
import LenguajeP.util.VariablesParser;
import LenguajeP.util.Variable;

import java.util.HashMap;

public class Interprete extends AnasintBaseVisitor<Object> {
    private HashMap<String, Variable> almacenVariables;

    @Override
    public Object visitPrograma(Anasint.ProgramaContext ctx){

        // Creamos el almacen de variables
        VariablesParser almacen = new VariablesParser();
        this.almacenVariables = (HashMap<String, Variable>) almacen.visit(ctx.variables());

        InstruccionesParser instrucciones = new InstruccionesParser(this.almacenVariables);
        instrucciones.visit(ctx.instrucciones_programa());

        return null;
    }
}
