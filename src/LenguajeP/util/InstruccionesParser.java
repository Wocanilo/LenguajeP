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
    private HashMap<String, Subprograma> subprogramas;
    private CondicionParser condicionParser;

    public InstruccionesParser(HashMap<String, Variable> almacenVariables, HashMap<String, Subprograma> subprogramas){
        this.almacenVariables = almacenVariables;
        this.subprogramas = subprogramas;
        this.exprParser = new ExprParser(almacenVariables, subprogramas);
        this.condicionParser = new CondicionParser(almacenVariables, subprogramas);
    }

    // Comprueba si una expresion es funcion
    private Boolean esFuncion(Anasint.ExprContext ctx){
        if(ctx.expr_entera() != null && ctx.expr_entera().llamada_func_proc() != null) return true;
        else return false;
    }

    @Override
    public Object visitAsignacion(Anasint.AsignacionContext ctx){
        List<Object> valoresExpresiones = new ArrayList<>();
        List<TerminalNode> identificadores = ctx.getTokens(Anasint.IDENTIFICADOR);

        // Comprobamos que coincida el numero de expresiones y de identificadores
        if(ctx.expr().size() != identificadores.size()){
            // Si la expresion es una funcion puede ser valida
            if(ctx.expr().size() == 1 && this.esFuncion(ctx.expr(0))){
                // Comprobamos que coincida el numero de variables de la asignacion con el numero de variables devueltas por la funcion
                Subprograma subprograma = null;
                String identificador = ctx.expr(0).expr_entera().llamada_func_proc().getStart().getText();

                subprograma = subprogramas.getOrDefault(identificador, null);

                if(subprograma == null) throw new RuntimeException(String.format("Runtime Error: tried to call an undefined function/proc '%s'", identificador));

                if(identificadores.size() != subprograma.parametrosSalida.size()) throw new RuntimeException(String.format("Runtime Error: Invalid number of expressions and identifiers in an assignment. '%s'",
                        ctx.getText()));
            }else{
                throw new RuntimeException(String.format("Runtime Error: Invalid number of expressions and identifiers in an assignment. '%s'",
                        ctx.getText()));
            }
        }

        for(Anasint.ExprContext expr: ctx.expr()){
            Object exprValue = exprParser.visit(expr);
            if(this.esFuncion(expr)){
                // Se trata de una funcion, hay que desempaquetar los valores devueltos
                for(Object elemento: (List<Object>) exprValue){
                    valoresExpresiones.add(elemento);
                }
            }else{
                valoresExpresiones.add(exprValue);
            }
        }

        // A cada variable le asignamos su nuevo valor.
        for(int i=0; i<identificadores.size(); i++){
            String identificador = identificadores.get(i).getText();

            if(!this.almacenVariables.containsKey(identificador)) System.out.println("INTERPRETE ERROR: Se ha tratado de acceder a una variable no definida");
            Variable variable = this.almacenVariables.get(identificador);

            // No es necesario volver a almacenarlo ya que se trata de una referencia
            Object valorExpr = valoresExpresiones.get(i);
            // Desencapsulamos las variables
            if(Variable.class.isInstance(valorExpr)){
                valorExpr = ((Variable)valorExpr).getValor();
            }

            variable.setValor(valorExpr);
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
    public Object visitCondicional(Anasint.CondicionalContext ctx){
        Boolean condicion = (Boolean) this.condicionParser.visit(ctx.condicion_completa());

        if(condicion){
            // Ejecutamos las instrucciones contenidas
            for(Anasint.InstruccionContext instruccion: ctx.instruccion()){
                visit(instruccion);
            }
        }else{
            // Comprobamos si tiene un else
            if(ctx.instruccion_condicionalSino() != null){
                // Ejecutamos sus instrucciones
                for(Anasint.InstruccionContext instruccion: ctx.instruccion_condicionalSino().instruccion()){
                    visit(instruccion);
                }
            }
        }

        return null;
    }


    @Override
    public Object visitInstruccion(Anasint.InstruccionContext ctx){

        if(ctx.asignacion() != null) visit(ctx.asignacion());
        if(ctx.llamada_func_proc() != null) visit(ctx.llamada_func_proc());
        if(ctx.condicional() != null) visit(ctx.condicional());

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

    @Override
    public Object visitLlamada_func_proc(Anasint.Llamada_func_procContext ctx){
        // Se trata de una funcion/procedimiento en linea, no devuelve nada.
        // TODO: por ahora se permite llamar a funciones ya que no impacta en el resultado final. Lo ideal seria solo permitir la llamada a procedimientos
        this.exprParser.visit(ctx);
        return null;
    }

}
