package LenguajeP.util;

import LenguajeP.Antlr.Anasint;
import LenguajeP.Antlr.AnasintBaseVisitor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ExprParser extends AnasintBaseVisitor<Object> {
    private HashMap<String, Variable> almacenVariables;

    public ExprParser(HashMap<String, Variable> almacenVariables){
        this.almacenVariables = almacenVariables;
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
        // Se trata de una operacion aritmetica
        Object operadorIzq = visit(ctx.expr_entera(0));
        Object operadorDer = visit(ctx.expr_entera(1));

        Integer valorOperadorIzq;
        Integer valorOperadorDer;

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
    //            | acceso_secuencia {tipo=valorAccesoSecuencia(acceso_secuencia)}
    //            | llamada_func_proc {valor=ejecutaFuncion(llamada_func_proc)}
    //            ;
    @Override
    public Object visitExpr_entera(Anasint.Expr_enteraContext ctx){
        if(!ctx.expr_entera().isEmpty()) {
            // Si hay expresiones enteras dentro no es caso base
            if(ctx.expr_entera().size() == 1){
                // Se trata de una expr en parentesis
                return visit(ctx.expr_entera(0));
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
                        return visit(ctx.llamada_func_proc());
                    }else if(ctx.acceso_secuencia() != null){
                        // Se trata del acceso a una secuencia
                        return visit(ctx.acceso_secuencia());
                    }else{
                        // Es una variable, obtenemos su valor
                        return getVariable(ctx.getStart().getText());
                    }
                default:
                    // Failsafe para debug
                    System.out.println(ctx.getStart().getText());
                    System.out.println("No implementado (visitExpr_entera)");
                    return null;
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
        else return visit(ctx.expr_entera());
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
            Integer indice = (Integer) visit(ctx.expr_entera());
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
}
