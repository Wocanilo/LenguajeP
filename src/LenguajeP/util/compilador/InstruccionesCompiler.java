package LenguajeP.util.compilador;

import LenguajeP.Antlr.Anasint;
import LenguajeP.Antlr.AnasintBaseVisitor;
import LenguajeP.util.interprete.CondicionParser;
import LenguajeP.util.interprete.Subprograma;
import LenguajeP.util.interprete.Variable;

import java.util.*;

public class InstruccionesCompiler extends AnasintBaseVisitor<Object> {
    private HashMap<String, Variable> almacenVariables;
    private ExprCompiler exprParser;
    private ExprCompiler exprParserNoPosicion;
    private HashMap<String, Subprograma> subprogramas;
    private CondicionParser condicionParser;

    public InstruccionesCompiler(HashMap<String, Variable> almacenVariables, HashMap<String, Subprograma> subprogramas){
        this.almacenVariables = almacenVariables;
        this.subprogramas = subprogramas;
        this.exprParser = new ExprCompiler(almacenVariables, subprogramas);
        this.exprParserNoPosicion = new ExprCompiler(almacenVariables, subprogramas, new HashMap<Variable, Integer>());
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
        List<Anasint.Identificador_O_AccesoContext> identificadoresOAccesos = ctx.identificador_O_Acceso();

        // 1. Comprobamos que coincida el numero de expresiones y de identificadores
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

                if(subprograma == null) throw new RuntimeException(String.format("Compilation Error: tried to call an undefined function/proc '%s'", identificador));

                if(identificadoresOAccesos.size() != subprograma.parametrosSalida.size()) throw new RuntimeException(String.format("Compilation Error: Invalid number of expressions and identifiers in an assignment. '%s'",
                        ctx.getText()));
            }else{
                throw new RuntimeException(String.format("Compilation Error: Invalid number of expressions and identifiers in an assignment. '%s'",
                        ctx.getText()));
            }
        }

        // 2. Resolvemos las variables con el almacén de variables
        List<Variable> variables = new ArrayList<>();

        for(Anasint.Identificador_O_AccesoContext var: identificadoresOAccesos){
            // Comprobamos si se trata de una secuencia o no
            String identificador = null;

            if(var.IDENTIFICADOR() != null) identificador = var.getText();
            else identificador = var.acceso_secuencia().IDENTIFICADOR().getText();

            if(!this.almacenVariables.containsKey(identificador)) throw new RuntimeException(String.format("Compilation Error: tried to access undefined variable '%s'.", identificador));
            variables.add(this.almacenVariables.get(identificador));
        }

        // 3. Obtenemos las variables implicadas en las asignaciones
        List<Object> variablesExpresiones = new ArrayList<>();
        StringBuilder res = new StringBuilder();

        // TODO: permitir llamadas a funciones
        for(Anasint.ExprContext expr: ctx.expr()){
            variablesExpresiones.add(this.exprParser.visit(expr));
        }

        // 3b. Calculamos las variables que debemos salvar antes de modificar su valor
        HashSet<Variable> variablesGuardadas = new HashSet<>();

        for(int i=0; i<variables.size(); i++){
            Object variable = variables.get(i);

            for(int e=0; e<variablesExpresiones.size(); e++){
                if(e == i) continue; // No hay colision cuando se modifica el valor de la misma variable que se está asignando.
                Object variablesExpresion = variablesExpresiones.get(e);

                if(Variable.class.isInstance(variablesExpresion)){
                     if(((Variable)variablesExpresion).equals(variable)) {
                        variablesGuardadas.add((Variable) variable);
                    }
                }
                if(HashSet.class.isInstance(variablesExpresion)){
                    if(((HashSet<Variable>)variablesExpresion).contains((Variable) variable)){
                        variablesGuardadas.add((Variable) variable);
                    }
                }
            }
        }


        StringBuilder salida = new StringBuilder();
        HashMap<Variable, Integer> posicionVariableTmp = new HashMap<>();
        // 4. Almacenamos el valor de las variables que colisionan en nuestro stack de variables.
        int index = 0;
        for(Variable var: variablesGuardadas){
            salida.append(String.format("almacenTmp.add(%s);\n", var.getIdentificador()));
            posicionVariableTmp.put(var, index);
            index++;
        }

        // 5. Resolvemos las expresiones arreglando las colisiones y creamos las asignaciones
        ExprCompiler exprParserAsignacion = new ExprCompiler(this.almacenVariables, this.subprogramas, posicionVariableTmp);
        // Para cada pareja creamos su asignacion
        for(int i=0; i<variables.size(); i++){
            salida.append(String.format("%s = %s;\n", variables.get(i).getIdentificador(), exprParserAsignacion.visit(ctx.expr(i))));
        }

        // 6. Se limpia el almacen temporal si ha sido usado
        if(!posicionVariableTmp.isEmpty()) salida.append("almacenTmp.clear();\n");

        //System.out.println(salida);
        return salida;
    }

    // condicional: SI INICIO_PARENTESIS condicion_completa FIN_PARENTESIS ENTONCES instruccion_condicionalSi+ (SINO (instruccion_condicionalSino)+)? FSI;
    @Override
    public Object visitCondicional(Anasint.CondicionalContext ctx){
        String condicion = ctx.condicion_completa().getText();
        StringBuilder instruccionesSi = new StringBuilder();
        StringBuilder instruccionesSino = null;

        for(Anasint.Instruccion_condicionalSiContext instruccion: ctx.instruccion_condicionalSi()){
            instruccionesSi.append(visit(instruccion));
        }

        if(ctx.instruccion_condicionalSino() != null && !ctx.instruccion_condicionalSino().isEmpty()){
            instruccionesSino = new StringBuilder();
            for(Anasint.Instruccion_condicionalSinoContext instruccion: ctx.instruccion_condicionalSino()){
                instruccionesSino.append(visit(instruccion));
            }
        }

        if(instruccionesSino != null) return String.format("if(%s){\n%s}else{\n%s}\n", condicion, instruccionesSi, instruccionesSino);
        return String.format("if(%s){\n%s}\n", condicion, instruccionesSi);
    }

    @Override
    public Object visitDevolver(Anasint.DevolverContext ctx){

        if(ctx.expr().size() == 1){
            return String.format("return %s;\n", this.exprParserNoPosicion.visit(ctx.expr(0)));
        }else{
            // Hay multiples valores devueltos
            List<String> valoresDevueltos = new ArrayList<>();

            for(Anasint.ExprContext expr: ctx.expr()){
                valoresDevueltos.add((String)this.exprParserNoPosicion.visit(expr));
            }

            return String.format("return new Tupla(%s);\n", String.join(", ", valoresDevueltos));
        }
    }

    @Override
    public Object visitIteracion(Anasint.IteracionContext ctx){
        String condicion = ctx.condicion_completa().getText();
        StringBuilder instrucciones = new StringBuilder();

        for(Anasint.Instruccion_iteracionContext instruccion: ctx.instruccion_iteracion()){
            Object res = visit(instruccion);

            instrucciones.append(res);
        }

        // Se devuelve una lista de devoluciones
        return String.format("while(%s){\n%s}\n", condicion, instrucciones);
    }

    @Override
    public Object visitLlamada_func_proc(Anasint.Llamada_func_procContext ctx){
        String identificador = ctx.IDENTIFICADOR().getText();

        if(identificador.equalsIgnoreCase("mostrar")){
            if(ctx.expr() != null && ctx.expr().size() > 1) throw new RuntimeException("Compilation Error: call to mostrar can only have one parameter");
            return String.format("System.out.println(String.format(\"%s->%s\", %s));\n", ctx.expr(0).getText(), "%s", ctx.expr(0).getText());
        }

        return "fffffffffffff";
    }

    @Override
    public Object visitInstruccion(Anasint.InstruccionContext ctx){

        if(ctx.asignacion() != null) return visit(ctx.asignacion());
        if(ctx.llamada_func_proc() != null) return visit(ctx.llamada_func_proc());
        if(ctx.condicional() != null) return visit(ctx.condicional());
        if(ctx.iteracion() != null) return visit(ctx.iteracion());

        throw new RuntimeException(String.format("Compilation Error: unknown instruction '%s'", ctx.getText()));
    }

    @Override
    public Object visitInstrucciones_programa(Anasint.Instrucciones_programaContext ctx){

        StringBuilder res = new StringBuilder();

        for(Anasint.InstruccionContext instruccion: ctx.instruccion()){
            res.append(String.format("%s", visit(instruccion)));
        }

        return res;
    }
}
