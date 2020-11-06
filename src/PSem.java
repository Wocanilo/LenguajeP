import org.antlr.v4.runtime.tree.TerminalNode;
import util.Variable;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class PSem extends PSintBaseVisitor<Object>{
    // Almacena el tipo de cada variable
    private final HashMap<String, HashMap<String, Integer>> tipoVariables = new HashMap<>();
    private final List<String> funcionesYProcedimientos = new ArrayList<>();

    // Funcion auxiliar que traduce los ID a String para los mensajes de error
    private String idToString(Integer id){
        switch(id){
            case PSint.NUM:
                return "NUM";
            case PSint.LOG:
                return "LOG";
            case PSint.SEQ_LOG:
                return "SEQ_LOG";
            case PSint.SEQ_NUM:
                return "SEQ_NUM";
            default:
                return "DESCONOCIDO";
        }
    }

    // (funcion declarafuncionProcedimiento(ident)
    //    si ident igual a vacia o ultima_posicion entonces ERROR
    //    sino
    //        si ident en funcionesYProcedimientos entonces ERROR
    //        sino almacenar ident en funcionesYProcedimientos
    //)
    private void declaraFuncionProcedimiento(String identificador){
        if(identificador.equalsIgnoreCase("vacia")) System.out.println("ERROR: Funcion/Procedimiento utiliza la palabra reservada 'vacia'");
        else if (identificador.equalsIgnoreCase("ultima_posicion")) System.out.println("ERROR: Funcion/Procedimiento utiliza la palabra reservada 'ultima_posicion'");
        // Si no sobreescribe las funciones/procedimientos predefinidos declaramos la funcion/proc.
        else {
            if(this.funcionesYProcedimientos.contains(identificador)) System.out.println(String.format("ERROR: Funcion/Procedimiento '%s' redeclarado.", identificador));
            else this.funcionesYProcedimientos.add(identificador);
        }
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

                if(tipo.intValue() == tipoAlmacenado.intValue()) System.out.println(String.format("WARNING: Variable '%s' redeclarada como %s. Previamente declarada como: %s", identificador, this.idToString(tipo), this.idToString(tipoAlmacenado)));
                if(tipo.intValue() != tipoAlmacenado.intValue()) System.out.println(String.format("ERROR: Variable '%s' redeclarada como %s. Previamente declarada como: %s", identificador, this.idToString(tipo), this.idToString(tipoAlmacenado)));

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
    public Object visitTipo(PSint.TipoContext ctx){
        // Devuelve un Integer que identifica al lexema.
        // PSint.X permite obtener el valor numérico asignado a cada tipo. Ej: PSint.NUM, PSint.LOG...
        return ctx.getStart().getType();
    }

    // (parámetro de salida d)
    // decl_var: ident=IDENTIFICADOR (COMA ident=IDENTIFICADOR)* PyP t=tipo {almacenar cada ident con tipo t en d};
    @Override
    public Object visitDecl_var(PSint.Decl_varContext ctx){
        List<Variable> variables = new ArrayList<>();
        Integer tipo = (Integer)visit(ctx.tipo());

        for(TerminalNode variable: ctx.getTokens(PSint.IDENTIFICADOR)){
           variables.add(new Variable(variable.getText(), tipo));
        }

        return variables;
    }

    // (parámetro de salida vs)
    // variables: VARIABLES d=(decl_var PyC)* {almacenar cada d en vs} ;
    @Override
    public Object visitVariables(PSint.VariablesContext ctx){
        List<List<Variable>> variables = new ArrayList<>();

        for(PSint.Decl_varContext decl_var: ctx.decl_var()){
            variables.add((List<Variable>) visit(decl_var));
        }

        return variables;
    }

    // programa: PROGRAMA vs=variables subprogramas instrucciones EOF {almacenar vs en tipoVariable[GLOBAL]};
    @Override
    public Object visitPrograma(PSint.ProgramaContext ctx){
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
    public Object visitParametro(PSint.ParametroContext ctx){
        String identificador = ctx.getStop().getText();
        Integer tipo = (Integer)visit(ctx.tipo());

        return new Variable(identificador, tipo);
    }

    // (parámetro de salida ps)
    // parametros: p=parametro (COMA p=parametro)* {almacenar cada p en ps};
    @Override
    public Object visitParametros(PSint.ParametrosContext ctx){
        List<Variable> parametros = new ArrayList<>();


        for(PSint.ParametroContext parametro: ctx.parametro()){
            // Visitamos cada elemento parámetro para formar la lista
            parametros.add( (Variable)visit(parametro) );
        }


        return parametros;
    }

    // Objetivo 1
    // def_func: FUNCION nombreFunc=IDENTIFICADOR INICIO_PARENTESIS ps=parametros? FIN_PARENTESIS DEV INICIO_PARENTESIS ps=parametros FIN_PARENTESIS vs=variables instrucciones_funcion FFUNCION;
    // {almacenar cada ps en tipoVariable[nombreFunc]} {almacenar vs en tipoVariable[nombreFunc]}
    // Objetivo 2
    // def_func: FUNCION ident=IDENTIFICADOR INICIO_PARENTESIS parametros? FIN_PARENTESIS DEV INICIO_PARENTESIS parametros FIN_PARENTESIS variables instrucciones_funcion FFUNCION;
    // {declarafuncionProcedimiento(ident)}
    @Override
    public Object visitDef_func(PSint.Def_funcContext ctx){
        String nombreFuncion = ctx.getToken(PSint.IDENTIFICADOR, 0).getText(); // El nombre es el unico identificador que hay en una declaracion de funcion
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
            parametrosSalida = (List<Variable>)visit(ctx.parametros(0)); // El segundo conjunto de parámetros de una función son los de salida
        }

        List<List<Variable>> variablesSeccion = (List<List<Variable>>)visit(ctx.variables()); // Debemos procesar la sección de variables de la función

        // Declaramos todos los parámetros
        for(Variable var: parametrosEntrada) this.declaraVariable(var, nombreFuncion);
        for(Variable var: parametrosSalida) this.declaraVariable(var, nombreFuncion);

        // Declaramos las variables de la seccion de variables
        for(List<Variable> decl_var: variablesSeccion){
            for(Variable var: decl_var){
                // Declaramos la variable
                this.declaraVariable(var, nombreFuncion);
            }
        }

        // Declaramos la funcion
        this.declaraFuncionProcedimiento(nombreFuncion);

        return super.visitDef_func(ctx); // Queremos que se sigan visitando los hijos
    }

    // Objetivo 1
    // def_proc: PROCEDIMIENTO nombreProc=IDENTIFICADOR INICIO_PARENTESIS ps=parametros? FIN_PARENTESIS vs=variables instrucciones FPROCEDIMIENTO;
    // {almacenar cada ps en tipoVariable[nombreProc]} {almacenar vs en tipoVariable[nombreProc]}
    // Objetivo 2
    // def_proc: PROCEDIMIENTO IDENTIFICADOR INICIO_PARENTESIS parametros? FIN_PARENTESIS variables instrucciones FPROCEDIMIENTO;
    // {declarafuncionProcedimiento(ident)}
    @Override
    public Object visitDef_proc(PSint.Def_procContext ctx){
        String nombreProc = ctx.getToken(PSint.IDENTIFICADOR, 0).getText(); // El nombre es el unico identificador que hay en una declaracion de procedimiento

        // Comprobamos que existan parámetros
        if(ctx.parametros() != null) {
            List<Variable> parametrosEntrada = (List<Variable>)visit(ctx.parametros()); // Los procedimientos tan solo tienen parámetros de entrada
            for(Variable var: parametrosEntrada) this.declaraVariable(var, nombreProc);
        }

        // Declaramos el procedimiento
        this.declaraFuncionProcedimiento(nombreProc);

        return super.visitDef_proc(ctx); // Queremos que se sigan visitando los hijos
    }

    // llamada_func_proc: ident=IDENTIFICADOR INICIO_PARENTESIS expr (COMA expr)* FIN_PARENTESIS; {si ident no existe en almacen entonces ERROR}
    @Override
    public Object visitLlamada_func_proc(PSint.Llamada_func_procContext ctx){
        // Comprobamos si la funcion/proc existe
        String nombreFuncionProc = ctx.getStart().getText();

        if(!this.funcionesYProcedimientos.contains(nombreFuncionProc)) System.out.println(String.format("ERROR: Llamada a funcion/procedimiento '%s' no declarado", nombreFuncionProc));

        return null; // No es necesario visitar sus hijos (no tiene)
    }
}
