package LenguajeP.util;

import LenguajeP.Antlr.Anasint;
import LenguajeP.Antlr.AnasintBaseVisitor;
import org.antlr.v4.runtime.tree.TerminalNode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class InstruccionesParser extends AnasintBaseVisitor<Object> {
    /*
    Esta clase se encarga de parsear la seccion INSTRUCCIONES

    Pasar el almacen de variables al instanciar la clase permite utilizar la misma clase tanto para el programa como los subprogramas
     */
    private HashMap<String, Variable> almacenVariables;
    private ExprParser exprParser;

    public InstruccionesParser(HashMap<String, Variable> almacenVariables, List<Subprograma> subprogramas){
        this.almacenVariables = almacenVariables;
        this.exprParser = new ExprParser(this.almacenVariables, subprogramas);
    }

    // Comprueba si una expresion es funcion
    private Boolean esFuncion(Anasint.ExprContext ctx){
        if(ctx.expr_entera() != null && ctx.expr_entera().llamada_func_proc() != null) return true;
        else return false;
    }

    @Override
    public Object visitAsignacion(Anasint.AsignacionContext ctx){
        List<Object> valoresExpresiones = new ArrayList<>();

        // Comprobamos que coincida el numero de expresiones y de identificadores
        if(ctx.expr().size() != ctx.getTokens(Anasint.IDENTIFICADOR).size()){
            // Si la expresion es una funcion puede ser valida
            if(ctx.expr().size() == 1 && this.esFuncion(ctx.expr(0))){
                //TODO: comprobar que en una asignacion el numero de valores devueltos por la funcion coincide con el numero de variables
                System.out.println("NO IMPLEMENTADO: comprobar si el numero de elementos devueltos coincide");
            }else{
                throw new RuntimeException(String.format("Runtime Error: Invalid number of expressions and identifiers in an assignment. '%s'",
                        ctx.getText()));
            }
        }

        for(Anasint.ExprContext expr: ctx.expr()){
            //TODO: en caso de ser llamada a funcion hay que desempaquetar la lista, que se encargue visitFuncion
            valoresExpresiones.add(exprParser.visit(expr));
        }

        // A cada variable le asignamos su nuevo valor.
        List<TerminalNode> identificadores = ctx.getTokens(Anasint.IDENTIFICADOR);
        for(int i=0; i<identificadores.size(); i++){
            String identificador = identificadores.get(i).getText();

            if(!this.almacenVariables.containsKey(identificador)) System.out.println("INTERPRETE ERROR: Se ha tratado de acceder a una variable no definida");
            Variable variable = this.almacenVariables.get(identificador);

            // No es necesario volver a almacenarlo ya que se trata de una referencia
            variable.setValor(valoresExpresiones.get(i));
        }

        return ctx;
    }

    @Override
    public Object visitDevolver(Anasint.DevolverContext ctx){
        List<Object> res = new ArrayList<>();

        for(Anasint.ExprContext expr: ctx.expr()){
            res.add(this.exprParser.visit(expr));
        }

        return res;
    }


    @Override
    public Object visitInstruccion(Anasint.InstruccionContext ctx){

        if(ctx.asignacion() != null) return new InstruccionAsignacion((Anasint.AsignacionContext) visit(ctx.asignacion()));

        return null;
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
