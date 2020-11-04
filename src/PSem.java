import org.antlr.v4.runtime.tree.TerminalNode;

import java.util.HashMap;

public class PSem extends PSintBaseVisitor<Object>{
    // Almacena el tipo de cada variable
    private HashMap<String, HashMap<String, Integer>> tipoVariables = new HashMap<>();

    //TODO: Crear variable local que se establezca a uno cuando se produzca un error. Si se produce un error el programa no llega a interpretarse, ya que se considera incorrecto

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

    //(funcion obtenerScopeVariable(declr_var)
    //    // PROGRAMA/FUNCION/PROC -> VARIABLES -> decl_vars
    //    Si el padre del padre de declr_var es PROGRAMA entonces GLOBAL
    //    sino entonces LOCAL
    //)
    private String obtenerScopeVariable(PSint.Decl_varContext ctx){
        return ctx.getParent().getParent().getStart().getType() == PSint.PROGRAMA ? "GLOBALES" : "LOCAL";
    }

    // Comprueba si la variable existe en el scope dado.
    // Si existe comprueba si se está redeclarando con distinto tipo
    // Si no existe se añade
    private void declararVariable(String identificador, Integer tipo, String origenVariable){
        if(!tipoVariables.containsKey(origenVariable)){
            // No existe el scope de la variable
            HashMap<String, Integer> variables = new HashMap<>();
            variables.put(identificador, tipo);
            // Almacenamos el scope en tipoVariable
            tipoVariables.put(origenVariable, variables);
        }else{
            // El scope existe, comprobamos si se ha redeclarado la variable
            HashMap<String, Integer> variables = tipoVariables.get(origenVariable);
            if(!variables.containsKey(identificador)){
                // Almacenamos el tipo de la variable
                variables.put(identificador, tipo);
                // Actualizamos tipoVariables
                tipoVariables.put(origenVariable, variables);
            }
            else{
                // Debemos comprobar si la variable ha sido redeclarada con tipo distinto
                Integer tipoAlmancenado = variables.get(identificador);

                if(tipo == tipoAlmancenado) {
                    // Avisamos de que la variable ha sido redeclarada.
                    System.out.println(String.format("AVISO: Redeclaración de la variable %s", identificador));
                }else{
                    // La variable ha sido declarada con un tipo diferente -> Violación del tipado
                    // Seria conveniente almacenar cada tipo para ser mas user friendly
                    System.out.println(String.format("ERROR: Redeclaración de la variable %s. Tipo Almacenado: %s, Tipo Declaración: %s, Scope: %s",
                            identificador, this.idToString(tipoAlmancenado), this.idToString(tipo), origenVariable));
                }
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

    // decl_var: ident=IDENTIFICADOR (COMA ident=IDENTIFICADOR)* PyP t=tipo {almacenar cada ident con tipo t en tipoVariables[obtenerScopeVariable(árbol declr_var)]};
    @Override
    public Object visitDecl_var(PSint.Decl_varContext ctx){
        Integer tipo = (Integer)visit(ctx.tipo()); // El último token de una declaración es el tipo de las variables

        // Determina si la variable es local o global en función de su origen PROGRAMA/FUNCION/PROC -> VARIABLES -> decl_vars
        String origenVariable = this.obtenerScopeVariable(ctx);

        for(TerminalNode identificador: ctx.getTokens(PSint.IDENTIFICADOR)){
            String valorIdentificador = identificador.getText();
            this.declararVariable(valorIdentificador, tipo, origenVariable);
        }
        return null; // No devolvemos nada ni queremos que se visiten sus hijos.
    }

    // def_func: FUNCION IDENTIFICADOR INICIO_PARENTESIS parametros? FIN_PARENTESIS DEV INICIO_PARENTESIS parametros FIN_PARENTESIS variables instrucciones_funcion FFUNCION; {borrar tipoVariables[LOCAL]}
    @Override
    public Object visitDef_func(PSint.Def_funcContext ctx) {
        // Debemos limpiar el scope de variables locales, ya que la redeclaración de una variable local en otra función es legal
        tipoVariables.remove("LOCAL");
        return super.visitDef_func(ctx); // Debemos seguir llamando a la implementación, ya que queremos que se visiten sus hijos
    }

    // def_proc: PROCEDIMIENTO IDENTIFICADOR INICIO_PARENTESIS parametros? FIN_PARENTESIS variables instrucciones FPROCEDIMIENTO; {borrar tipoVariables[LOCAL]}
    @Override
    public Object visitDef_proc(PSint.Def_procContext ctx) {
        // Debemos limpiar el scope de variables locales, ya que la redeclaración de una variable local en otro procedimiento es legal
        tipoVariables.remove("LOCAL");
        return super.visitDef_proc(ctx); // Debemos seguir llamando a la implementación, ya que queremos que se visiten sus hijos
    }

    // parametro: t=tipo ident=IDENTIFICADOR {almacenar ident con tipo t en tipoVariables[LOCAL]};
    @Override
    public Object visitParametro(PSint.ParametroContext ctx){
        Integer tipo = (Integer)visit(ctx.tipo());

        this.declararVariable(ctx.getStop().getText(), tipo, "LOCAL");
        return null; // No devolvemos nada ni queremos que se visiten sus hijos.
    }
}
