package LenguajeP;

import LenguajeP.Antlr.Anasint;
import LenguajeP.Antlr.AnasintBaseVisitor;
import org.antlr.v4.runtime.tree.TerminalNode;
import LenguajeP.util.interprete.Variable;

import java.util.*;


// Reflejar cambios de Anasint
// Entre ellos, se permite el modificar el valor de un elemento de una lista
public class Anasem extends AnasintBaseVisitor<Object> {
    // Almacena el tipo de cada variable
    private final HashMap<String, HashMap<String, Integer>> tipoVariables = new HashMap<>();
    private final List<String> funcionesYProcedimientos = new ArrayList<>();
    private final HashMap<String, List<Integer>> tipoFunciones = new HashMap<>();

    // Definimos las funciones predefinidas
    public Anasem(){
        // Declaramos las funciones predefinidas
        funcionesYProcedimientos.add("vacia");
        funcionesYProcedimientos.add("ultima_posicion");
        funcionesYProcedimientos.add("mostrar");
        // Añadimos el tipo de las funciones predefinidas
        List<Integer> tipoVacia = new ArrayList<>();
        tipoVacia.add(Anasint.LOG);
        tipoFunciones.put("vacia", tipoVacia);

        List<Integer> tipoUltimaPosicion = new ArrayList<>();
        tipoUltimaPosicion.add(Anasint.NUM);
        tipoFunciones.put("ultima_posicion", tipoUltimaPosicion);
    }

    private String scopeActual = "GLOBAL";

    // Funcion auxiliar que traduce los ID a String para los mensajes de error
    private String idToString(Integer id){
        switch(id){
            case Anasint.NUM:
                return "NUM";
            case Anasint.LOG:
                return "LOG";
            case Anasint.SEQ_LOG:
                return "SEQ_LOG";
            case Anasint.SEQ_NUM:
                return "SEQ_NUM";
            case Anasint.SEQ:
                return "SEQ";
            case Anasint.NO_TIPO:
                return "NO_TIPO";
            default:
                return "DESCONOCIDO";
        }
    }

    // (funcion declarafuncionProcedimiento(ident)
    //     si ident en funcionesYProcedimientos entonces ERROR
    //     sino almacenar ident en funcionesYProcedimientos
    //)
    private void declaraFuncionProcedimiento(String identificador){
        if(this.funcionesYProcedimientos.contains(identificador)) System.out.printf("ERROR: Funcion/Procedimiento '%s' redeclarado.%n", identificador);
        else this.funcionesYProcedimientos.add(identificador);
    }

    // Funcion auxiliar que declara una variable en el contexto pasado
    private void declaraVariable(Variable var, String scope){
        String identificador = var.getIdentificador();
        Integer tipo = var.getTipo();
        // Comprobamos que exista el scope
        if(!this.tipoVariables.containsKey(scope)){
            HashMap<String, Integer> variables = new HashMap<>();
            variables.put(identificador, tipo);
            // Almacenamos el scope
            this.tipoVariables.put(scope, variables);
        }else{
            HashMap<String, Integer> variables = this.tipoVariables.get(scope);
            // Comprobamos que no se haya redeclarado
            if(!variables.containsKey(identificador)){
                variables.put(identificador, tipo);
                this.tipoVariables.put(scope, variables);
            }else{
                // Diferenciamos entre una redeclaración del mismo tipo y una con distinto tipo
                Integer tipoAlmacenado = variables.get(identificador);

                if(tipo.intValue() == tipoAlmacenado.intValue()) System.out.printf("WARNING: Variable '%s' redeclarada como %s. Previamente declarada como: %s%n", identificador, this.idToString(tipo), this.idToString(tipoAlmacenado));
                if(tipo.intValue() != tipoAlmacenado.intValue()) System.out.printf("ERROR: Variable '%s' redeclarada como %s. Previamente declarada como: %s%n", identificador, this.idToString(tipo), this.idToString(tipoAlmacenado));

            }
        }
    }

    //    (parámetro de salida t)
    //    tipo: NUM {t=entero}
    //    | LOG {t=booleano}
    //    | SEQ_NUM {t=secuencia_entera}
    //    | SEQ_LOG {t=secuencia_booleana}
    //    ;
    @Override
    public Object visitTipo(Anasint.TipoContext ctx){
        // Devuelve un Integer que identifica al lexema.
        // Anasint.X permite obtener el valor numérico asignado a cada tipo. Ej: Anasint.NUM, Anasint.LOG...
        return ctx.getStart().getType();
    }

    // (parámetro de salida d)
    // decl_var: ident=IDENTIFICADOR (COMA ident=IDENTIFICADOR)* PyP t=tipo {almacenar cada ident con tipo t en d};
    @Override
    public Object visitDecl_var(Anasint.Decl_varContext ctx){
        List<Variable> variables = new ArrayList<>();
        Integer tipo = (Integer)visit(ctx.tipo());

        for(TerminalNode variable: ctx.getTokens(Anasint.IDENTIFICADOR)){
           variables.add(new Variable(variable.getText(), tipo));
        }

        return variables;
    }

    // (parámetro de salida vs)
    // variables: VARIABLES d=(decl_var PyC)* {almacenar cada d en vs} ;
    @Override
    public Object visitVariables(Anasint.VariablesContext ctx){
        List<List<Variable>> variables = new ArrayList<>();

        for(Anasint.Decl_varContext decl_var: ctx.decl_var()){
            variables.add((List<Variable>) visit(decl_var));
        }

        return variables;
    }

    // programa: PROGRAMA vs=variables subprogramas instrucciones EOF {almacenar vs en tipoVariable[GLOBAL]};
    @Override
    public Object visitPrograma(Anasint.ProgramaContext ctx){
        List<List<Variable>> variables = (List<List<Variable>>) visit(ctx.variables());

        // Almacenamos las variables en el scope global
        for(List<Variable> decl_var: variables){
            for(Variable var: decl_var){
                // Declaramos la variable
                this.declaraVariable(var, "GLOBAL");
            }
        }

        return super.visitPrograma(ctx); // Debemos visitar a los hijos por ahora
    }

    // (parámetro de salida p)
    // parametro: t=tipo ident=IDENTIFICADOR {almacenar ident con tipo t en p};
    @Override
    public Object visitParametro(Anasint.ParametroContext ctx){
        String identificador = ctx.getStop().getText();
        Integer tipo = (Integer)visit(ctx.tipo());

        return new Variable(identificador, tipo);
    }

    // (parámetro de salida ps)
    // parametros: p=parametro (COMA p=parametro)* {almacenar cada p en ps};
    @Override
    public Object visitParametros(Anasint.ParametrosContext ctx){
        List<Variable> parametros = new ArrayList<>();

        for(Anasint.ParametroContext parametro: ctx.parametro()){
            // Visitamos cada elemento parámetro para formar la lista
            parametros.add( (Variable)visit(parametro) );
        }


        return parametros;
    }

    // Objetivo 1
    // def_func: FUNCION nombreFunc=IDENTIFICADOR INICIO_PARENTESIS ps=parametros? FIN_PARENTESIS DEV INICIO_PARENTESIS ps=parametros FIN_PARENTESIS vs=variables INSTRUCCIONES instrucciones_funcion+ FFUNCION;
    // {almacenar cada ps en tipoVariable[nombreFunc]} {almacenar vs en tipoVariable[nombreFunc]}
    // Objetivo 2
    // def_func: FUNCION ident=IDENTIFICADOR INICIO_PARENTESIS parametros? FIN_PARENTESIS DEV INICIO_PARENTESIS parametros FIN_PARENTESIS variables INSTRUCCIONES instrucciones_funcion+ FFUNCION;
    // {declarafuncionProcedimiento(ident)}
    // Objetivo 4
    // def_func: FUNCION ident=IDENTIFICADOR INICIO_PARENTESIS parametros? FIN_PARENTESIS DEV INICIO_PARENTESIS parametros FIN_PARENTESIS variables INSTRUCCIONES instrucciones_funcion+ FFUNCION;
    //{establecer scopeActual con el valor de ident}
    // Objetivo 4
    // def_func: FUNCION ident=IDENTIFICADOR INICIO_PARENTESIS parametros? FIN_PARENTESIS DEV INICIO_PARENTESIS ps=parametros FIN_PARENTESIS variables INSTRUCCIONES instrucciones_funcion+ FFUNCION;
    //{almacenar cada ident con los tipos contenidos en ps en almacen tipos funciones}
    @Override
    public Object visitDef_func(Anasint.Def_funcContext ctx){
        String nombreFuncion = ctx.getToken(Anasint.IDENTIFICADOR, 0).getText(); // El nombre es el unico identificador que hay en una declaracion de funcion
        // Almacenamos el scope actual
        this.scopeActual = nombreFuncion;

        List<Variable> parametrosEntrada;
        List<Variable> parametrosSalida;

        // Debemos comprobar si existen los parámetros
        if (ctx.parametros().size() == 2){
            // Hay parametros de entrada y salida
            parametrosEntrada = (List<Variable>)visit(ctx.parametros(0)); // El primer conjunto de parámetros de una función son los de entrada
            parametrosSalida = (List<Variable>)visit(ctx.parametros(1)); // El segundo conjunto de parámetros de una función son los de salida
        }else{
            // Solo hay de salida
            parametrosEntrada = new ArrayList<>();
            parametrosSalida = (List<Variable>)visit(ctx.parametros(0));
        }

        List<List<Variable>> variablesSeccion = (List<List<Variable>>)visit(ctx.variables()); // Debemos procesar la sección de variables de la función

        List<Integer> tiposSalida = new ArrayList<>();
        // Declaramos todos los parámetros
        for(Variable var: parametrosEntrada) this.declaraVariable(var, nombreFuncion);
        for(Variable var: parametrosSalida) {
            this.declaraVariable(var, nombreFuncion);

            // Almacenamos cada tipo en la lista de tipos de salida
            tiposSalida.add(var.getTipo());
        }

        // Declaramos las variables de la seccion de variables
        for(List<Variable> decl_var: variablesSeccion){
            for(Variable var: decl_var){
                // Declaramos la variable
                this.declaraVariable(var, nombreFuncion);
            }
        }

        // Declaramos la funcion
        this.declaraFuncionProcedimiento(nombreFuncion);

        // Almacenamos el tipo/tipos de la funcion
        this.tipoFunciones.put(nombreFuncion, tiposSalida);

        return super.visitDef_func(ctx); // Queremos que se sigan visitando los hijos
    }

    // Objetivo 1
    // def_proc: PROCEDIMIENTO nombreProc=IDENTIFICADOR INICIO_PARENTESIS ps=parametros? FIN_PARENTESIS vs=variables instrucciones_procedimiento+ FPROCEDIMIENTO;
    // {almacenar cada ps en tipoVariable[nombreProc]} {almacenar vs en tipoVariable[nombreProc]}
    // Objetivo 2
    // def_proc: PROCEDIMIENTO IDENTIFICADOR INICIO_PARENTESIS parametros? FIN_PARENTESIS variables instrucciones_procedimiento+ FPROCEDIMIENTO;
    // {declarafuncionProcedimiento(ident)}
    // Objetivo 4
    // def_proc: PROCEDIMIENTO ident=IDENTIFICADOR INICIO_PARENTESIS parametros? FIN_PARENTESIS variables instrucciones_procedimiento+ FPROCEDIMIENTO;
    //{establecer scopeActual con el valor de ident}
    @Override
    public Object visitDef_proc(Anasint.Def_procContext ctx){
        String nombreProc = ctx.getToken(Anasint.IDENTIFICADOR, 0).getText(); // El nombre es el unico identificador que hay en una declaracion de procedimiento

        // Almacenamos el scope actual
        this.scopeActual = nombreProc;

        // Comprobamos que existan parámetros
        if(ctx.parametros() != null) {
            List<Variable> parametrosEntrada = (List<Variable>)visit(ctx.parametros()); // Los procedimientos tan solo tienen parámetros de entrada
            for(Variable var: parametrosEntrada) this.declaraVariable(var, nombreProc);
        }

        // Debemos procesar la sección de variables del procedimiento
        List<List<Variable>> variablesSeccion = (List<List<Variable>>)visit(ctx.variables());

        // Declaramos las variables de la seccion de variables
        for(List<Variable> decl_var: variablesSeccion){
            for(Variable var: decl_var){
                // Declaramos la variable
                this.declaraVariable(var, nombreProc);
            }
        }

        // Declaramos el procedimiento
        this.declaraFuncionProcedimiento(nombreProc);

        return super.visitDef_proc(ctx); // Queremos que se sigan visitando los hijos
    }

    // llamada_func_proc: ident=IDENTIFICADOR INICIO_PARENTESIS expr (COMA expr)* FIN_PARENTESIS; {si ident no existe en almacen entonces ERROR}
    @Override
    public Object visitLlamada_func_proc(Anasint.Llamada_func_procContext ctx){
        // Comprobamos si la funcion/proc existe
        String nombreFuncionProc = ctx.getStart().getText();

        // Si la funcion/proc no se encuentra declarado
        if(!this.funcionesYProcedimientos.contains(nombreFuncionProc)){
            System.out.printf("ERROR: Llamada a funcion/procedimiento '%s' no declarado%n", nombreFuncionProc);
        }

        return null; // No es necesario visitar sus hijos (no tiene)
    }

    // Objetivo 7:
    // instrucciones_programa: INSTRUCCIONES instruccion+; {establecer scopeActual a GLOBAL}
    @Override
    public Object visitInstrucciones_programa(Anasint.Instrucciones_programaContext ctx){
        this.scopeActual = "GLOBAL";

        return super.visitInstrucciones_programa(ctx);
    }

    //  (parametro de salida tipo)
    //  expr_booleana: TRUE {tipo=booleano}
    //           | FALSE {tipo=booleano}
    //  ;
    @Override
    public Integer visitExpr_booleana(Anasint.Expr_booleanaContext ctx){
        return Anasint.LOG;
    }

    // (funcion calculaTipoOPAritmetica(operando1, operando2)
    //    si operando1 o operando2 es no_tipo entonces no_tipo
    //    sino entonces NUM
    // )
    private Integer calculaTipoOPAritmetica(Anasint.Expr_enteraContext operando1, Anasint.Expr_enteraContext operando2){
        // Se trata de una suma, una multiplicacion o una resta, visitamos cada operando
        Integer tipoPrimerOperando = (Integer) visit(operando1);
        Integer tipoSegundoOperando = (Integer) visit(operando2);

        // Si alguno es no_tipo o no es de tipo entero entonces la operacion es no_tipo
        if(tipoPrimerOperando == Anasint.NO_TIPO || tipoSegundoOperando == Anasint.NO_TIPO || tipoPrimerOperando != Anasint.NUM || tipoSegundoOperando != Anasint.NUM) {
            return Anasint.NO_TIPO;
        }
        else {
            return Anasint.NUM;
        }
    }

    // (funcion calculaTipoFuncion(ident)
    //    si ident existe en tipoFunciones entonces
    //        si tipoFunciones[ident] > 1 entonces devolver no_tipo
    //        sino devolver tipo funcion
    //    sino devolver no_tipo
    // )
    private Integer calculaTipoFuncion(String identificador){
        if(!this.tipoFunciones.containsKey(identificador)) return Anasint.NO_TIPO; // Se trata de un procedimiento o no existe, no tiene tipo
        else{
            List<Integer> tiposFuncion = this.tipoFunciones.get(identificador);

            if(tiposFuncion.size() == 1) return tiposFuncion.get(0);
            else return Anasint.NO_TIPO; // No se pueden usar funciones que devuelvan varios valores en las expresiones
        }
    }

    // (funcion calculaTipoVariable(ident)
    //    si scope existe en tipoVariables entonces
    //        si ident existe en tipoVariables[scope] entonces devolver tipo variable
    //        sino devolver no_tipo
    //    sino devolver no_tipo
    //)
    private Integer calculaTipoVariable(String identificador){
        // Comprobamos que exista el scope
        if(this.tipoVariables.containsKey(this.scopeActual)) {
            // Obtenemos el scope actual
            HashMap<String, Integer> variablesScope = this.tipoVariables.get(this.scopeActual);

            if (!variablesScope.containsKey(identificador)) {
                // Aviso de que la variable usada no existe en el contexto actual
                System.out.printf("ERROR: La variable '%s' no existe en el contexto actual '%s'%n", identificador, this.scopeActual);
                return Anasint.NO_TIPO;
            } else {
                Integer tipoIdent = variablesScope.get(identificador);
                // Devolvemos el tipo de la variable.
                return tipoIdent;
            }
        }else{
            // Si no existe el scope no tiene variables, por lo que es NO_TIPO
            return Anasint.NO_TIPO;
        }
    }

    // (funcion calculaTipoAccesoSecuencia(ident)
    //    si calculaTipoVariable(ident) es SEQ_LOG entonces devolver LOG
    //    sino si calculaTipoVariable(ident) es SEQ_NUM entonces devolver NUM
    //    sino devolver no_tipo
    //)
    private Integer calculaTipoAccesoSecuencia(String identificador){
        // El tipo devuelto depende el tipo de la secuencia
        Integer tipoSecuencia = this.calculaTipoVariable(identificador);

        if(tipoSecuencia != Anasint.NO_TIPO){
            if(tipoSecuencia == Anasint.SEQ_LOG) return Anasint.LOG;
            else if(tipoSecuencia == Anasint.SEQ_NUM) return Anasint.NUM;
            else return Anasint.NO_TIPO; // La variable usada no es una secuencia
        }else{
            // La variable no existe
            return Anasint.NO_TIPO;
        }
    }

    @Override
    public List<Integer> visitElementos_secuencia(Anasint.Elementos_secuenciaContext ctx){
        List<Integer> elementos = new ArrayList<>();

        // Calculamos el tipo de cada uno de los elementos de la lista
        for(Anasint.Expr_elementosSecuenciaContext elemento: ctx.expr_elementosSecuencia()){
            elementos.add((Integer)visit(elemento));
        }

        return elementos;
    }

    // (funcion calculaTipoSecuencia(elementos)
    //    si todos los elementos son de tipo X devolver SEQ(X)
    //    sino devolver no_tipo
    //)
    private Integer calculaTipoSecuencia(List<Integer> elementos){
        Set<Integer> elementosUnicos = new HashSet<>(elementos);

        if(elementosUnicos.size() > 1){
            // Hay mas de un tipo de elemento, es no_tipo
            return Anasint.NO_TIPO;
        }else{
            // Es un solo tipo
            Integer tipoSecuencia = elementos.get(0);

            if(tipoSecuencia == Anasint.NO_TIPO) return Anasint.NO_TIPO; // Si es no_tipo la secuencia es no_tipo
            if(tipoSecuencia == Anasint.LOG) return Anasint.SEQ_LOG;
            else if(tipoSecuencia == Anasint.NUM) return Anasint.SEQ_NUM;
            else System.out.printf("ERROR: Secuencia con tipo inválido. '%s'%n", this.idToString(tipoSecuencia));
            return Anasint.NO_TIPO;
        }
    }

    // (parámetro de salida tipo)
    // expr_secuencia: INICIO_CORCHETE elementos=elementos_secuencia FIN_CORCHETE {tipo=calculaTipoSecuencia(elementos)}
    //              | INICIO_CORCHETE FIN_CORCHETE {tipo=SEQ}
    //              ;
    @Override
    public Integer visitExpr_secuencia(Anasint.Expr_secuenciaContext ctx) {
        if (ctx.elementos_secuencia() != null) {
            List<Integer> elementos = (List<Integer>) visit(ctx.elementos_secuencia());
            return this.calculaTipoSecuencia(elementos);
        }else{
            // Lista vacia
            return Anasint.SEQ;
        }
    }

    //(parametro de salida tipo)
    //expr_entera:  expr1=expr_entera POR expr2=expr_entera {tipo=calculaTipoOPAritmetica(expr1, expr2)}
    //            | expr1=expr_entera MENOS expr2=expr_entera {tipo=calculaTipoOPAritmetica(expr1, expr2)}
    //            | expr1=expr_entera MAS expr2=expr_entera {tipo=calculaTipoOPAritmetica(expr1, expr2)}
    //            | MENOS* INICIO_PARENTESIS tipo=expr_entera FIN_PARENTESIS
    //            | MENOS* ident=IDENTIFICADOR {tipo=calculaTipoVariable(ident)}
    //            | MENOS* ENTERO {tipo=entero)
    //            | MENOS* acceso_secuencia {tipo=tipoAccesoSecuencia(acceso_secuencia)}
    //            | MENOS* llamada_func_proc {calculaTipoFuncion(llamada_func_proc)}
    //            ;
    @Override
    public Integer visitExpr_entera(Anasint.Expr_enteraContext ctx){
        // Casos base
        if(ctx.expr_entera().isEmpty()){
            String identificador = null;
            switch(ctx.getStart().getType()){
                case Anasint.ENTERO:
                    return Anasint.NUM;
                case Anasint.IDENTIFICADOR:
                    identificador = ctx.getStart().getText();
                    // Importante recordar que esta rama tambien se visita en las expresiones booleanas
                    if(ctx.llamada_func_proc() != null) {
                        // Debemos seguir visitando la llamada.
                        visit(ctx.llamada_func_proc());
                        return this.calculaTipoFuncion(identificador);

                    }else if(ctx.acceso_secuencia() != null){
                        // Se trata del acceso a una secuencia
                        // El tipo devuelto depende el tipo de la secuencia
                        return this.calculaTipoAccesoSecuencia(identificador);
                    }else{
                        // Es una variable
                        return this.calculaTipoVariable(identificador);
                    }
                case Anasint.MENOS:
                    Integer tipo = null;
                    // Es un entero
                    if(ctx.ENTERO() != null) {
                        return Anasint.NUM;
                    }
                    // Es llamada a funcion
                    if(ctx.llamada_func_proc() != null) {
                        // Debemos seguir visitando la llamada.
                        identificador = ctx.llamada_func_proc().IDENTIFICADOR().getText();
                        visit(ctx.llamada_func_proc());

                        tipo = this.calculaTipoFuncion(identificador);
                        if(tipo != Anasint.NUM) {
                            System.out.printf("ERROR: se trató de hacer negativo un valor de tipo '%s'. '%s'%n", this.idToString(tipo), ctx.getText());
                            return Anasint.NO_TIPO;
                        }

                        return Anasint.NUM;
                    }
                    // Es variable
                    if(ctx.IDENTIFICADOR() != null){
                        identificador = ctx.IDENTIFICADOR().getText();
                        tipo = this.calculaTipoVariable(identificador);

                        if(tipo != Anasint.NUM) {
                            System.out.printf("ERROR: se trató de hacer negativo un valor de tipo '%s'. '%s'%n", this.idToString(tipo), ctx.getText());
                            return Anasint.NO_TIPO;
                        }

                        return Anasint.NUM;
                    }
                    // Es acceso a secuencia
                    if(ctx.acceso_secuencia() != null){
                        identificador = ctx.acceso_secuencia().IDENTIFICADOR().getText();
                        tipo = this.calculaTipoAccesoSecuencia(identificador);

                        if(tipo != Anasint.NUM) {
                            System.out.printf("ERROR: se trató de hacer negativo un valor de tipo '%s'. '%s'%n", this.idToString(tipo), ctx.getText());
                            return Anasint.NO_TIPO;
                        }

                        return tipo;
                    }
                default:
                    // Failsafe para debug
                    System.out.println(ctx.getStart().getText());
                    System.out.println("No implementado (visitExpr_entera)");
                    return Anasint.NO_TIPO;
            }
        }
        else if(ctx.expr_entera().size() == 1){
            // Sacamos la expr anidada en los parentesis
            return (Integer) visit(ctx.expr_entera(0));
        } else{
            // Se trata de una operación aritmética
            return this.calculaTipoOPAritmetica(ctx.expr_entera(0), ctx.expr_entera(1));
        }
    }

    // (parametro de salida tipo)
    //expr: tipo=expr_entera
    //    | tipo=expr_booleana
    //    | tipo=expr_secuencia
    //    ;
    @Override
    public Integer visitExpr(Anasint.ExprContext ctx){
        // Devolvemos el tipo de la expresion
        Integer tipo;
        if(ctx.expr_entera() != null){
            tipo = (Integer)visit(ctx.expr_entera());
        }else if(ctx.expr_booleana() != null){
            tipo = (Integer)visit(ctx.expr_booleana());
        }else{
            tipo = (Integer)visit(ctx.expr_secuencia());
        }

        // Expresion sin tipo, mensaje de error
        if(tipo == Anasint.NO_TIPO) System.out.printf("ERROR: Expresion '%s' sin tipo%n", ctx.getText());

        return tipo;
    }

    // (funcion calculaTipoAsignacion(ident, expr)
    // si calculaTipoVariable(ident) es no_tipo entonces devolver no_tipo
    // sino
    //    si calculaTipoVariable(ident) es igual a tipo expr devolver tipo
    //    sino devolver no_tipo
    // )
    private Integer calculaTipoAsignacion(String identificador, Anasint.ExprContext expr, Boolean esAccesoSecuencia){
        Integer tipoVariable = this.calculaTipoVariable(identificador);
        // Si se trata de un acceso a secuencia lo tratamos como un tipo basico
        if(tipoVariable == Anasint.SEQ_NUM && esAccesoSecuencia) tipoVariable = Anasint.NUM;
        if(tipoVariable == Anasint.SEQ_LOG && esAccesoSecuencia) tipoVariable = Anasint.LOG;

        Integer tipoExpr = (Integer) visit(expr);

        if(tipoVariable == Anasint.NO_TIPO || tipoExpr == Anasint.NO_TIPO){
            // Alguno de los dos es no_tipo
            System.out.printf("ERROR: Asignación indefinida. Se trató de asignar a la variable '%s' con tipo '%s' " +
                    "el valor de la expresión '%s' de tipo '%s'%n", identificador, this.idToString(tipoVariable), expr.getText(), this.idToString(tipoExpr));

            return Anasint.NO_TIPO;
        }else if(tipoVariable != tipoExpr){
            // Los tipos no coinciden
            if( (tipoVariable == Anasint.SEQ_LOG || tipoVariable == Anasint.SEQ_NUM) && tipoExpr == Anasint.SEQ){
                // Se trata de la asignación de una lista vacía a una secuencia, es válido
                // El tipo de la asignación es igual al tipo de la variable
                return tipoVariable;
            }

            if(esAccesoSecuencia) System.out.printf("ERROR: Los tipos de la asignación no coinciden. Se trató de asignar a un elemento de '%s' de tipo '%s' " +
                    "el valor de la expresión '%s' de tipo '%s'%n", identificador, this.idToString(tipoVariable), expr.getText(), this.idToString(tipoExpr));
            else System.out.printf("ERROR: Los tipos de la asignación no coinciden. Se trató de asignar a la variable '%s' con tipo '%s' " +
                    "el valor de la expresión '%s' de tipo '%s'%n", identificador, this.idToString(tipoVariable), expr.getText(), this.idToString(tipoExpr));

            return Anasint.NO_TIPO;
        }

        // Los tipos coinciden
        return tipoVariable;
    }

    // Comprueba si una expresion es funcion
    private Boolean esFuncion(Anasint.ExprContext ctx){
        if(ctx.expr_entera() != null && ctx.expr_entera().llamada_func_proc() != null) return true;
        else return false;
    }

    // asignacion: ident=identificador_O_Acceso (COMA ident=identificador_O_Acceso)* IGUAL expr (COMA expr)* PyC;
    // {por cada pareja ident,expr calculaTipoAsignacion(ident, expr), si son impares ERROR, si varios ident
    // y una sola expr comprobar si es funcion y los tipos coinciden sino ERROR. Si es acceso_secuencia devolver tipo de la secuencia (si existe)}
    @Override
    public Object visitAsignacion(Anasint.AsignacionContext ctx){
        List<Anasint.Identificador_O_AccesoContext> variablesAsignacion = ctx.identificador_O_Acceso();
        List<Anasint.ExprContext> expresionesAsignacion = ctx.expr();

        // Si hay varias variables y una sola expresion puede tratarse de una llamada a funcion
        if(expresionesAsignacion.size() == 1 && (variablesAsignacion.size() > expresionesAsignacion.size()) ){
            // Comprobamos que sea funcion
            if(this.esFuncion(expresionesAsignacion.get(0))){
                // Obtenemos la lista de tipos devueltos
                String identificador = expresionesAsignacion.get(0).expr_entera().llamada_func_proc().getStart().getText(); // Identificador funcion

                if(!this.tipoFunciones.containsKey(identificador)) return Anasint.NO_TIPO; // Se trata de un procedimiento o no existe, no tiene tipo
                else{
                    List<Integer> tiposFuncion = this.tipoFunciones.get(identificador);

                    // Si no concide el numero de elementos devueltos por la funcion con el numero de identificadores -> ERROR
                    if(variablesAsignacion.size() != tiposFuncion.size()) System.out.printf("ERROR: Asignacion múltiple erronea, no coincide el número de variables con el número" +
                            " de parámetros devueltos '%s'%n", ctx.getText());
                    else{
                        // Coinciden en número, comprobamos si coinciden en tipo
                        for(int i=0; i<tiposFuncion.size(); i++){
                            Integer tipoVariable = this.calculaTipoVariable(variablesAsignacion.get(i).getText());
                            Integer tipoFuncion = tiposFuncion.get(i);
                            // No coinciden en tipo -> ERROR
                            if(tipoVariable != tipoFuncion) System.out.printf("ERROR: Los tipos de la asignación no coinciden. Se trató de asignar a la variable '%s' con tipo '%s' " +
                                    "el valor de la funcion '%s' de tipo '%s'%n", variablesAsignacion.get(i).getText(), this.idToString(tipoVariable), identificador, this.idToString(tipoFuncion));
                        }
                    }
                }
            }else{
                // No es llamada a función -> ERROR
                System.out.printf("ERROR: Numero incorrecto de elementos en la asignación '%s'%n", ctx.getText());
            }
        }else{
            // Comprobamos que haya el mismo número de variables que de expresiones
            // Si no hay el mismo numero no podemos comprobar el tipado
            if(variablesAsignacion.size() != expresionesAsignacion.size()) System.out.printf("ERROR: número incorrecto de elementos en la asignación '%s'%n", ctx.getText());
            else{
                // Comprobamos el tipado de cada pareja de la asignacion
                for(int i=0; i<expresionesAsignacion.size(); i++){
                    Anasint.Identificador_O_AccesoContext var = variablesAsignacion.get(i);

                    if(var.IDENTIFICADOR() != null) this.calculaTipoAsignacion(var.getText(), expresionesAsignacion.get(i), false);
                    else this.calculaTipoAsignacion(var.acceso_secuencia().IDENTIFICADOR().getText(), expresionesAsignacion.get(i), true);
                }
            }
        }
        return null; // No queremos que se vuelvan a visitar sus hijos
    }
}
