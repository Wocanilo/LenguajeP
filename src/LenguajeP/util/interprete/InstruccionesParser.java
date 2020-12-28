package LenguajeP.util.interprete;

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
        // Puede ser una funcion dentro de parentesis
        if(ctx.expr_entera() != null && ctx.expr_entera().expr_entera() != null){
            Anasint.Expr_enteraContext current = ctx.expr_entera();
            // Debemos comprobar si es una llamada a funcion, sin importar la profundidad a la que se encuentre
            while(true){
                if(current.llamada_func_proc() != null) return true;
                if(current.expr_entera() != null && current.expr_entera().size() == 1){
                    current = current.expr_entera(0);
                }else{
                    return false;
                }
            }
        }
        else return false;
    }

    @Override
    public Object visitAsignacion(Anasint.AsignacionContext ctx){
        List<Object> valoresExpresiones = new ArrayList<>();
        List<Anasint.Identificador_O_AccesoContext> identificadoresOAccesos = ctx.identificador_O_Acceso();

        // Comprobamos que coincida el numero de expresiones y de identificadores
        if(ctx.expr().size() != identificadoresOAccesos.size()){
            // Si la expresion es una funcion puede ser valida
            if( ctx.expr().size() == 1 && this.esFuncion(ctx.expr(0))){
                // Comprobamos que coincida el numero de variables de la asignacion con el numero de variables devueltas por la funcion
                Subprograma subprograma = null;
                String identificador = null;

                if(ctx.expr(0).expr_entera().llamada_func_proc() != null){
                    identificador = ctx.expr(0).expr_entera().llamada_func_proc().getStart().getText();
                }else{
                    Anasint.Expr_enteraContext current = ctx.expr(0).expr_entera();
                    // Debemos obtener el identificador, sin importar la profundidad a la que se encuentre
                    while(identificador == null){
                        if(current.llamada_func_proc() != null) {
                            identificador = current.llamada_func_proc().IDENTIFICADOR().getText();
                            break;
                        }
                        if(current.expr_entera() != null && current.expr_entera().size() == 1){
                            current = current.expr_entera(0);
                        }
                    }
                }

                subprograma = subprogramas.getOrDefault(identificador, null);

                if(subprograma == null) throw new RuntimeException(String.format("Runtime Error: tried to call an undefined function/proc '%s'", identificador));

                if(identificadoresOAccesos.size() != subprograma.parametrosSalida.size()) throw new RuntimeException(String.format("Runtime Error: Invalid number of expressions and identifiers in an assignment. '%s'",
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
        for(int i=0; i<identificadoresOAccesos.size(); i++){

            // Comprobamos si se trata de una secuencia o no
            Anasint.Identificador_O_AccesoContext var = identificadoresOAccesos.get(i);
            String identificador = null;

            if(var.IDENTIFICADOR() != null) identificador = var.getText();
            else identificador = var.acceso_secuencia().IDENTIFICADOR().getText();

            if(!this.almacenVariables.containsKey(identificador)) throw new RuntimeException(String.format("Runtime Error: tried to access undefined variable '%s'.", identificador));
            Variable variable = this.almacenVariables.get(identificador);

            if(var.acceso_secuencia() != null){
                // Resolvemos el indice del acceso
                Object exprValue = this.exprParser.visit(var.acceso_secuencia().expr_entera());
                Integer indice = null;

                // Puede ser una llamada a funcion
                if(List.class.isInstance(exprValue)){
                    List<Object> exprList = (List<Object>) exprValue;
                    if(exprList.size() > 1) throw new RuntimeException("Runtime Error: functions in expressions can only return one value.");
                    exprValue = exprList.get(0);
                }

                // Puede ser una variable
                if(Variable.class.isInstance(exprValue)) exprValue = ((Variable)exprValue).getValor();
                // Comprobamos que sea un entero
                if(Integer.class.isInstance(exprValue)) indice = (Integer) exprValue;
                else throw new RuntimeException("Runtime Error: secuence indexes can only be numeric values.");

                //Obtenemos nuevo valor
                Object valorExpr = valoresExpresiones.get(i);
                // Desencapsulamos las variables
                if(Variable.class.isInstance(valorExpr)){
                    valorExpr = ((Variable)valorExpr).getValor();
                }

                // Modificamos la lista
                List<Object> listaVariable = (List<Object>) variable.getValor();
                // Comprobamos si el indice existe
                if(indice < listaVariable.size()) listaVariable.set(indice, valorExpr);
                else if(listaVariable.size() == indice) listaVariable.add(valorExpr); // Si es justo uno mas ampliamos la lista
                else throw new RuntimeException(String.format("Runtime Error: index out of bounds. '%s'", var.acceso_secuencia().getText()));

                // Al ser una lista el valor se modifica directamente en la variable
            }else{
                //Obtenemos nuevo valor
                Object valorExpr = valoresExpresiones.get(i);
                // Desencapsulamos las variables
                if(Variable.class.isInstance(valorExpr)){
                    valorExpr = ((Variable)valorExpr).getValor();
                }
                variable.setValor(valorExpr);
            }
        }

        return null;
    }

    @Override
    public Object visitDevolver(Anasint.DevolverContext ctx){
        List<Object> res = new ArrayList<>();

        for(Anasint.ExprContext expr: ctx.expr()){
            if(expr.expr_entera() != null && expr.expr_entera().llamada_func_proc() != null){
                // Se trata de funciones anidadas. No debemos volver a meter los resultados en una lista
                res.addAll((List<Object>) this.exprParser.visit(expr));
            }else{
                res.add(this.exprParser.visit(expr));
            }
        }

        return res;
    }

    @Override
    public Object visitCondicional(Anasint.CondicionalContext ctx){
        Boolean condicion = (Boolean) this.condicionParser.visit(ctx.condicion_completa());

        if(condicion){
            // Ejecutamos las instrucciones contenidas
            for(Anasint.Instruccion_condicionalSiContext instruccion: ctx.instruccion_condicionalSi()){
                if(instruccion.ruptura() != null) return Anasint.RUPTURA;
                if(instruccion.devolver() != null) return visit(instruccion.devolver()); // Solo valido en funciones
                visit(instruccion.instruccion());
            }
        }else{
            // Comprobamos si tiene un else
            if(ctx.instruccion_condicionalSino() != null){
                // Ejecutamos sus instrucciones
                for(Anasint.Instruccion_condicionalSinoContext instruccion: ctx.instruccion_condicionalSino()){
                    if(instruccion.ruptura() != null) return Anasint.RUPTURA;;
                    if(instruccion.devolver() != null) return visit(instruccion.devolver()); // Solo valido en funciones
                    visit(instruccion.instruccion());
                }
            }
        }

        return null;
    }

    @Override
    public Object visitIteracion(Anasint.IteracionContext ctx){
        // Comprobamos si la condicion se cumple
        Boolean condicion = (Boolean) this.condicionParser.visit(ctx.condicion_completa());

        // Repetimos mientras se cumpla
        while(condicion){
            // Ejecutamos las instrucciones
            for(Anasint.Instruccion_iteracionContext instruccion: ctx.instruccion_iteracion()){
                // Si es un break debemos terminar el bucle
                if(instruccion.ruptura() != null) return null;
                if(instruccion.devolver() != null) return visit(instruccion.devolver()); // Solo valido en funciones
                else {
                    // Si se recibe un Anasint.RUPTURA debemos acabar la ejecucion del bucle
                    Object res = visit(instruccion.instruccion());
                    if(Integer.class.isInstance(res) && ((Integer)res) == Anasint.RUPTURA ) return null;
                    if(List.class.isInstance(res)){
                        // Se trata de un dev anidado
                        return res;
                    }
                }
            }
            // Volvemos a comprobar la condicion
            condicion = (Boolean) this.condicionParser.visit(ctx.condicion_completa());
        }

        return null;
    }

    @Override
    public Object visitInstruccion(Anasint.InstruccionContext ctx){

        if(ctx.asignacion() != null) return visit(ctx.asignacion());
        if(ctx.llamada_func_proc() != null) return visit(ctx.llamada_func_proc());
        if(ctx.condicional() != null) return visit(ctx.condicional());
        if(ctx.iteracion() != null) return visit(ctx.iteracion());

        throw new RuntimeException(String.format("Runtime Error: unknown instruction '%s'", ctx.getText()));
    }

    @Override
    public Object visitInstrucciones_programa(Anasint.Instrucciones_programaContext ctx){
        // Se estan ejecutando instrucciones del programa principal

        for(Anasint.InstruccionContext instruccion: ctx.instruccion()){
            Object valor = visit(instruccion);

            if(List.class.isInstance(valor)) throw new RuntimeException("Runtime Error: ilegal use of dev in main program."); // Se ha llamado a dev en el programa principal

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
