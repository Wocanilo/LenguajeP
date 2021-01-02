package LenguajeP.util.interprete;

import LenguajeP.Antlr.Anasint;
import LenguajeP.Antlr.AnasintBaseVisitor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ExprParser extends AnasintBaseVisitor<Object> {
    private HashMap<String, Variable> almacenVariables;
    private HashMap<String, Subprograma> subprogramas;

    public ExprParser(HashMap<String, Variable> almacenVariables, HashMap<String, Subprograma> subprogramas){
        this.almacenVariables = almacenVariables;
        this.subprogramas = subprogramas;
    }

    //(funcion getVariable(ident){
    //    Si ident in almacenVariables entonces
    //        devolver valor variable
    //    sino ERROR variable no declarada
    //)
    private Variable getVariable(String identificador){
        if(this.almacenVariables.containsKey(identificador)){
            return this.almacenVariables.get(identificador);
        }else throw new RuntimeException(String.format("Runtime Error: referenced undeclared variable '%s'", identificador));
    }

    private Integer calculaOPAritmetica(Anasint.Expr_enteraContext ctx){
        Object operadorIzq = visit(ctx.expr_entera(0));
        Object operadorDer = visit(ctx.expr_entera(1));

        Integer valorOperadorIzq;
        Integer valorOperadorDer;

        if(operadorIzq == null || operadorDer == null) throw new RuntimeException("Runtime Error: expresion resolved to null in calculaOPAritmetica.");

        // Puede tratarse de una llamada a funcion
        if(List.class.isInstance(operadorIzq)){
            List<Object> listaIzq = (List<Object>) operadorIzq;

            if(listaIzq.size() != 1) throw new RuntimeException(String.format("Runtime Error: functions can only return one value when used in arithmetic operations. '%s'", ctx.expr_entera(0).getText()));

            operadorIzq = listaIzq.get(0);
        }

        if(List.class.isInstance(operadorDer)){
            List<Object> listaDer = (List<Object>) operadorDer;

            if(listaDer.size() != 1) throw new RuntimeException(String.format("Runtime Error: functions can only return one value when used in arithmetic operations. '%s'", ctx.expr_entera(1).getText()));

            operadorDer = listaDer.get(0);
        }

        // Debemos comprobar si se trata de un tipo basico o una variable
        if(Variable.class.isInstance(operadorIzq)){
            // Comprobamos que se trata de una operacion legal
            if(((Variable)operadorIzq).getTipo() != Anasint.NUM) throw new RuntimeException(String.format("Runtime Error: Ilegal arithmetic operation. %s is not of type NUM",
                    ((Variable)operadorIzq).getIdentificador()));
            // Debemos obtener el valor de la variable.
            valorOperadorIzq = (Integer) ((Variable) operadorIzq).getValor();

        }else{
            valorOperadorIzq = (Integer) operadorIzq;
        }

        if(Variable.class.isInstance(operadorDer)){
            // Comprobamos que se trata de una operacion legal
            if(((Variable)operadorDer).getTipo() != Anasint.NUM) throw new RuntimeException(String.format("Runtime Error: Ilegal arithmetic operation. %s is not of type NUM",
                    ((Variable)operadorDer).getIdentificador()));
            // Debemos obtener el valor de la variable
            valorOperadorDer = (Integer) ((Variable) operadorDer).getValor();
        } else{
            valorOperadorDer = (Integer) operadorDer;
        }

        if(ctx.getToken(Anasint.MAS, 0) != null) return valorOperadorIzq + valorOperadorDer;
        if(ctx.getToken(Anasint.MENOS, 0) != null) return valorOperadorIzq - valorOperadorDer;
        if(ctx.getToken(Anasint.POR, 0) != null) return valorOperadorIzq * valorOperadorDer;

        throw new RuntimeException(String.format("Arithmetic operation not found: %s", ctx.getText()));
    }

    //(parametro de salida valor)
    //expr_entera: expr1=expr_entera MAS expr2=expr_entera {valor=suma(expr1, expr2)}
    //            | expr1=expr_entera MENOS expr2=expr_entera {valor=resta(expr1, expr2)}
    //            | expr1=expr_entera POR expr2=expr_entera {valor=multiplica(expr1, expr2)}
    //            | INICIO_PARENTESIS valor=expr_entera FIN_PARENTESIS
    //            | ident=IDENTIFICADOR {valor=getVariable(ident)}
    //            | ENTERO {valor=entero)
    //            | MENOS expr_entera // Permite usar numeros negativos
    //            | acceso_secuencia {tipo=valorAccesoSecuencia(acceso_secuencia)}
    //            | llamada_func_proc {valor=ejecutaFuncion(llamada_func_proc)}
    //            ;
    @Override
    public Object visitExpr_entera(Anasint.Expr_enteraContext ctx){
        if(!ctx.expr_entera().isEmpty()) {
            // Si hay expresiones enteras dentro no es caso base
            if(ctx.expr_entera().size() == 1){
                Object expr = visit(ctx.expr_entera(0));

                // Se trata de una expr en parentesis
                if(ctx.MENOS() != null && ctx.MENOS().size() > 0){
                    // Debemos negar el resultado
                    Boolean eraLista = false; // Indica si se ha desencapsulado una lista
                    Variable varOriginal = null; // Referencia a variable desencapsulada

                    if(expr != null && List.class.isInstance(expr)) {
                        if(((List<Object>) expr).size() > 1) throw new RuntimeException("Runtime Error: functions call in expresions can only return one value.");

                        expr = ((List<Object>) expr).get(0);
                        eraLista = true;
                    }

                    // Si es variable desencapsulamos
                    if(expr != null && Variable.class.isInstance(expr)) {
                        varOriginal = (Variable)expr;
                        expr = ((Variable)expr).getValor();
                    }

                    // Si no es entero ERROR
                    Integer negaciones = ctx.MENOS().size(); // Necesario para calcular signo final
                    if(expr != null && Integer.class.isInstance(expr)) {
                        Object res = null;
                        if(negaciones % 2 == 0) res = ((Integer)expr);
                        else res = -((Integer)expr);

                        if(varOriginal != null){
                            // La variable puede ser de solo lectura, al venir de una funcion
                            res = new Variable(varOriginal.getIdentificador(), varOriginal.getTipo(), res);
                        }

                        if(eraLista){
                            List<Object> resLista = new ArrayList<>();
                            resLista.add(res);
                            res = resLista;
                        }

                        return res;
                    }

                    throw new RuntimeException("Runtime Error: only numbers can be negative.");
                }

                return expr;
            }else{
                // Se trata de una operacion aritmetica
                return calculaOPAritmetica(ctx);
            }
        }else{
            // Casos base
            switch(ctx.getStart().getType()){
                case Anasint.ENTERO:
                    return Integer.parseInt(ctx.getText());
                case Anasint.IDENTIFICADOR:
                    String identificador = ctx.getStart().getText();
                    // Importante recordar que esta rama tambien se visita en las expresiones booleanas
                    if(ctx.llamada_func_proc() != null) {
                        // Devolvemos el resultado de la llamada a funcion
                        // Comprobamos que se trate de una funcion, si es un procedimiento no es valido
                        Subprograma sub = this.subprogramas.get(ctx.llamada_func_proc().IDENTIFICADOR().getText());

                        if(sub == null) throw new RuntimeException(String.format("Runtime error: function/procedure %s not declared", ctx.llamada_func_proc().IDENTIFICADOR().getText()));
                        if(!sub.isEsFuncion()) throw new RuntimeException(String.format("Runtime error: ilegal procedure call in expresion '%s'", ctx.llamada_func_proc().getText()));

                        return visit(ctx.llamada_func_proc());
                    }else if(ctx.acceso_secuencia() != null){
                        // Se trata del acceso a una secuencia
                        return visit(ctx.acceso_secuencia());
                    }else{
                        // Es una variable, obtenemos su valor
                        return getVariable(ctx.getStart().getText());
                    }
                case Anasint.MENOS:
                    // Expresion negativa
                    //System.out.println(ctx.getText());
                    Boolean esPositivo = ctx.MENOS().size() % 2 == 0; // Necesario para calcular signo final
                    if(ctx.ENTERO() != null) {
                        if(esPositivo) return Integer.parseInt(ctx.ENTERO().getText());
                        else return -Integer.parseInt(ctx.ENTERO().getText());
                    }
                    // Llamada a una funcion
                    if(ctx.llamada_func_proc() != null) {
                        Object expr = visit(ctx.llamada_func_proc());
                        Boolean eraLista = false; // Indica si se ha desencapsulado una lista
                        Variable varOriginal = null; // Referencia a variable desencapsulada

                        // Si era llamada a funcion desencapsulamos el valor
                        if(expr != null && List.class.isInstance(expr)) {
                            if(((List<Object>) expr).size() > 1) throw new RuntimeException("Runtime Error: functions call in expresions can only return one value.");

                            expr = ((List<Object>) expr).get(0);
                            eraLista = true;
                        }

                        // Si es variable desencapsulamos
                        if(expr != null && Variable.class.isInstance(expr)) {
                            varOriginal = (Variable)expr;
                            expr = ((Variable)expr).getValor();
                        }

                        // Si no es entero ERROR
                        if(expr != null && Integer.class.isInstance(expr)) {
                            Object res = null;
                            if(esPositivo) res = ((Integer)expr);
                            else res = -((Integer)expr);

                            if(varOriginal != null){
                                // La variable puede ser de solo lectura, al venir de una funcion
                                res = new Variable(varOriginal.getIdentificador(), varOriginal.getTipo(), res);
                            }

                            if(eraLista){
                                List<Object> resLista = new ArrayList<>();
                                resLista.add(res);
                                res = resLista;
                            }

                            return res;
                        }

                        throw new RuntimeException("Runtime Error: only numbers can be negative.");
                    }

                    if(ctx.IDENTIFICADOR() != null){
                        Variable var = getVariable(ctx.IDENTIFICADOR().getText());
                        // Comprobamos su tipo
                        if(var.getTipo() == Anasint.NUM){
                            if(esPositivo) return var;

                            var.setValor(-((Integer)var.getValor()));
                            return var;
                        }
                        throw new RuntimeException(String.format("Runtime Error: only NUM variables can be negative. '%s'", ctx.IDENTIFICADOR().getText()));
                    }

                    if(ctx.acceso_secuencia() != null){
                        Variable var = getVariable(ctx.acceso_secuencia().IDENTIFICADOR().getText());
                        if(var.getTipo() == Anasint.SEQ_NUM){
                            Integer valorAcceso = (Integer) visit(ctx.acceso_secuencia());

                            if(esPositivo) return valorAcceso;
                            return -valorAcceso;
                        }

                        throw new RuntimeException(String.format("Runtime Error: only NUM variables can be negative. '%s'", ctx.acceso_secuencia().getText()));
                    }

                default:
                    // Failsafe para debug
                    throw new RuntimeException(String.format("No implementado (visitExpr_entera). '%s'", ctx.getText()));
            }
        }
    }

    //(parametro de salida valor)
    //expr_booleana: TRUE {valor=true}
    //             | FALSE {valor=false}
    //             ;
    @Override
    public Object visitExpr_booleana(Anasint.Expr_booleanaContext ctx){
        if(ctx.FALSE() != null) return Boolean.FALSE;
        else return Boolean.TRUE;
    }

    // (parametro de salida valor)
    // expr_elementosSecuencia: valor=expr_entera
    //                       | valor=expr_booleana
    //                       ;
    @Override
    public Object visitExpr_elementosSecuencia(Anasint.Expr_elementosSecuenciaContext ctx){
        if(ctx.expr_booleana() != null) return visit(ctx.expr_booleana());
        else{
            if(ctx.expr_entera().llamada_func_proc() != null){
                // La funcion solo puede devolver un valor y hay que desencapsularlo
                Object res = visit(ctx.expr_entera());
                if(List.class.isInstance(res)){
                    return ((List<Object>)res).get(0);
                }
            }
            return visit(ctx.expr_entera());
        }
    }

    // (parametro de salida elementos)
    // elementos_secuencia: elemento=expr_elementosSecuencia (COMA elemento=expr_elementosSecuencia)*; {Almacena cada elemento en elementos}
    @Override
    public Object visitElementos_secuencia(Anasint.Elementos_secuenciaContext ctx){
        List<Object> elementos = new ArrayList<>();

        for(Anasint.Expr_elementosSecuenciaContext elemento: ctx.expr_elementosSecuencia()){
            elementos.add(visit(elemento));
        }

        return elementos;
    }

    // (parametro de salida secuencia)
    // expr_secuencia: INICIO_CORCHETE elementos_secuencia FIN_CORCHETE {secuencia=elementos_secuencia}
    //              | INICIO_CORCHETE FIN_CORCHETE {secuencia=[]}
    //              ;
    @Override
    public Object visitExpr_secuencia(Anasint.Expr_secuenciaContext ctx){
        if(ctx.elementos_secuencia() == null) return new ArrayList<Object>();
        else{
            return visit(ctx.elementos_secuencia());
        }
    }

    // acceso_secuencia: ident=IDENTIFICADOR INICIO_CORCHETE elemento=expr_entera FIN_CORCHETE; {obtiene
    //    la secuencia del almacen de variables y devuelve la posicion valor}
    @Override
    public Object visitAcceso_secuencia(Anasint.Acceso_secuenciaContext ctx){
        String identificador = ctx.getToken(Anasint.IDENTIFICADOR, 0).getText();
        // Obtenemos la variable
        Variable var = this.getVariable(identificador);

        if(var.getTipo() == Anasint.SEQ_LOG || var.getTipo() == Anasint.SEQ_NUM){
            // Obtenemos el indice
            Object exprValor = visit(ctx.expr_entera());
            Integer indice = null;

            // Desencapsulamos los valores
            if(List.class.isInstance(exprValor)) {
                if (((List<Object>) exprValor).size() > 1)
                    throw new RuntimeException("Runtime Error: functions call in expresions can only return one value.");

                exprValor = ((List<Object>) exprValor).get(0);
            }

            // Hay que desencapsular las variables
            if(Variable.class.isInstance(exprValor)){
                exprValor = ((Variable)exprValor).getValor();
            }else{
                exprValor = exprValor;
            }

            indice = (Integer) exprValor;

            List<Object> secuencia = (List<Object>) var.getValor();

            // Comprobamos que exista indice
            if(indice < secuencia.size()){
                return secuencia.get(indice);
            }else{
                throw new RuntimeException(String.format("Runtime Error: Index '%s' out of bounds for variable '%s'",
                        indice, identificador));
            }
        }else{
            // No es una secuencia
            throw new RuntimeException(String.format("Runtime Error: variable '%s' is not a sequence.",
                    identificador));
        }
    }

    @Override
    public Object visitLlamada_func_proc(Anasint.Llamada_func_procContext ctx){
        String identificador = ctx.getToken(Anasint.IDENTIFICADOR, 0).getText();
        Subprograma subprograma = null;

        // Comprobamos que exista la funcion/procedimiento a llamar
        subprograma = subprogramas.getOrDefault(identificador, null);

        // El subprograma no existe
        if(subprograma == null) throw new RuntimeException(String.format("Runtime error: function/procedure %s not declared", identificador));

        // Formamos el almacen de variables locales
        HashMap<String, Variable> variablesLocales = new HashMap<>(subprograma.getVariablesLocales()); // Variables locales declaradas en la seccion VARIABLES

        Object resultado;
        // Para los procedimientos, debemos poder traducir entre la variable de salida y la orignal
        HashMap<Variable, Variable> salidaOriginal = new HashMap<>();

        if(ctx.expr() == null){
            // No hay parametros de entrada, ejecutamos directamente la funcion/procedimiento
            resultado = subprograma.Execute(variablesLocales, this.subprogramas);
        }else{
            // Debemos resolver las expresiones pasadas como parametros
            List<Anasint.ExprContext> expresiones = ctx.expr();
            List<Parametro> parametrosEntrada = subprograma.getParametrosEntrada();

            // Comprobamos que el numero de expresiones coincidan
            if(expresiones.size() != parametrosEntrada.size()) throw new RuntimeException(String.format("Runtime Error: function/procedure call to '%s' with invalid number of parameters '%s'",
                    identificador, expresiones.size()));

            for(int i = 0; i<expresiones.size(); i++){
                Anasint.ExprContext expresion = expresiones.get(i);
                Object expr = visit(expresion);
                Object exprValue;

                // Si se trata de una funcion anidada, esta solo podra devolver un valor
                if(expresion.expr_entera() != null && expresion.expr_entera().llamada_func_proc() != null){
                    Subprograma sub = this.subprogramas.getOrDefault(expresion.expr_entera().llamada_func_proc().IDENTIFICADOR().getText(), null);

                    if(sub == null) throw new RuntimeException(String.format("Runtime error: function/procedure %s not declared", identificador));

                    if(!sub.isEsFuncion()) throw new RuntimeException(String.format("Runtime error: procedures can not be used as parameters '%s'", identificador));
                    if(sub.getParametrosSalida().size() != 1) throw new RuntimeException(String.format("Runtime error: functions that returns multiple values can not " +
                            "be used as parameters '%s'", identificador));

                    // Debemos desempaquetar el valor devuelto por la funcion
                    expr = ((List<Object>) expr).get(0);
                }

                // Hay que desencapsular las variables
                if(Variable.class.isInstance(expr)){
                    // Si es llamada a procedimiento accedemos directamente el valor, haciendo bypass a la proteccion de lectura de variables indefinidas
                    if(!subprograma.isEsFuncion()) exprValue = ((Variable)expr).getValorSinComprobacion();
                    else exprValue = ((Variable)expr).getValor();
                }else{
                    exprValue = expr;
                }

                Parametro param = parametrosEntrada.get(i);

                // Si la variable ya existe ERROR
                if(variablesLocales.containsKey(param.getIdentificador())) throw new RuntimeException(String.format("Runtime Error: function/procedure parameter name collision with local variables '%s'",
                        param.getIdentificador()));

                Variable variableLocal;
                // Creamos la variable de entrada local
                if(subprograma.isEsFuncion()) {
                    // Las variables de entrada de una funcion son de solo lectura
                    variableLocal = new Variable(param.getIdentificador(), param.getTipo(), exprValue, false);
                }else{
                    // Si el nombre de una variable es un asterisco, sustituimos el identificador por el nombre original de la variable.
                    // Usado por mostrar
                    if(param.getIdentificador() == "*") variableLocal = new Variable(expresion.getText(), param.getTipo(), exprValue);
                    else variableLocal = new Variable(param.getIdentificador(), param.getTipo(), exprValue);

                    if(Variable.class.isInstance(expr)) {
                        // Como se ha desencapsulado, hay que guardar la referencia a la original para poder cambiar su valor al terminar
                        salidaOriginal.put(variableLocal, (Variable) expr);
                    }
                }

                variablesLocales.put(param.getIdentificador(), variableLocal);
            }

            // Ejecutamos la funcion/procedimiento
            resultado = subprograma.Execute(variablesLocales, this.subprogramas);
        }

        // Si era un procedimiento, modificamos los parametros de entrada en el scope global
         if(!subprograma.isEsFuncion()){
            // Tan solo se modifican las variables que hayan sido traducidas, el resto no existen en el scope global.
            // Pueden no existir porque se paso como parametro un entero, por ejemplo. Por tanto, son descartadas.
            for(Variable var: ((HashMap<String, Variable>) resultado).values()){
                if(salidaOriginal.containsKey(var)){
                    // Modificamos el valor de la variable original
                    Variable original = salidaOriginal.get(var);
                    original.setValor(var.getValor());
                }
            }
            return null;
        }else{
            // Si era una funcion, devolvemos los valores indicados por dev
            return (List<Object>) resultado;
        }
    }
}
