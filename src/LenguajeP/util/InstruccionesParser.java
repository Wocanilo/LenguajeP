package LenguajeP.util;

import LenguajeP.Antlr.Anasint;
import LenguajeP.Antlr.AnasintBaseVisitor;

import java.util.HashMap;

public class InstruccionesParser extends AnasintBaseVisitor<Object> {
    /*
    Esta clase se encarga de parsear la seccion INSTRUCCIONES

    Pasar el almacen de variables al instanciar la clase permite utilizar la misma clase tanto para el programa como los subprogramas
     */
    private HashMap<String, Variable> almacenVariables;

    public InstruccionesParser(HashMap<String, Variable> almacenVariables){
        this.almacenVariables = almacenVariables;
    }

    @Override
    public Object visitInstrucciones_programa(Anasint.Instrucciones_programaContext ctx){
        // Se estan ejecutando instrucciones del programa principal

        for(Anasint.InstruccionContext instruccion: ctx.instruccion()){
            visit(instruccion);
        }

        return null;
    }

}
