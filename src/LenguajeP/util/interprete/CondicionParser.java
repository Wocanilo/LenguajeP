package LenguajeP.util.interprete;

import LenguajeP.Antlr.Anasint;
import LenguajeP.Antlr.AnasintBaseVisitor;

import java.util.HashMap;
import java.util.List;

public class CondicionParser extends AnasintBaseVisitor<Object> {
    private HashMap<String, Variable> almacenVariables;
    private ExprParser exprParser;
    private HashMap<String, Subprograma> subprogramas;

    public CondicionParser(HashMap<String, Variable> almacenVariables, HashMap<String, Subprograma> subprogramas){
        this.almacenVariables = almacenVariables;
        this.subprogramas = subprogramas;
        this.exprParser = new ExprParser(this.almacenVariables, subprogramas);
    }

    // (parametro de salida valor)
    //  condicion_completa: cond1=condicion_completa CONJUNCION cond2=condicion_completa // {valor=(cond1 && cond2)}
    //  | cond1=condicion_completa DISYUNCION cond2=condicion_completa // {valor=(cond1 || cond2)}
    //  | INICIO_PARENTESIS valor=condicion_completa FIN_PARENTESIS
    //  | NEGACION cond=condicion_completa // {valor=!cond}
    //  | valor=condicion_basica
    //  ;
    @Override
    public Object visitCondicion_completa(Anasint.Condicion_completaContext ctx){
        // Comprobamos el numero de expresiones
        // TODO: hay que comprobar que se respete el orden de prioridad

        // Si es condicion basica no hay que hacer nada mas
        if(ctx.condicion_basica() != null){
            return visit(ctx.condicion_basica());
        }

        List<Anasint.Condicion_completaContext> condiciones = ctx.condicion_completa();

        if(condiciones.size() == 1){
            // Puede ser una negacion o una condicion entre parentesis
            Anasint.Condicion_completaContext condicion = condiciones.get(0);

            if(ctx.NEGACION() != null){
                // Puede contener condiciones basicas o completas
                if(condicion.condicion_basica() != null) return !((Boolean) visit(condicion.condicion_basica()));
                if(condicion.condicion_completa() != null) return !((Boolean) visit(condicion.condicion_completa(0)));
                throw new RuntimeException("Runtime error: unexpected condition in negation");
            }

            return visit(condicion.condicion_completa(0));
        }
        // Se trata de una operacion entre condiciones
        Boolean condicion1 = (Boolean) visit(condiciones.get(0));
        Boolean condicion2 = (Boolean) visit(condiciones.get(1));

        if(ctx.CONJUNCION() != null) return condicion1 && condicion2;
        if(ctx.DISYUNCION() != null) return condicion1 || condicion2;

        throw new RuntimeException("Runtime error: unexpected value in visitCondicion_completa");
    }

    // TODO: la parte que dice que las condiciones pueden tomar tres valores es un poco extrana, parece que quisiera indicar que existe shortcircuit en las condiciones
    // (parametro de salida valor)
    //  condicion_basica: expr1=expr IGUALDAD expr2=expr // {valor=(expr1 == expr2)}
    //  | expr1=expr DESIGUALDAD expr2=expr // {valor=(expr1 != expr2)}
    //  | expr1=expr MENOR_QUE expr2=expr // {valor=(expr1 < expr2)}
    //  | expr1=expr MAYOR_QUE expr2=expr // {valor=(expr1 > expr2)}
    //  | expr1=expr MAYOR_IGUAL_QUE expr2=expr // {valor=(expr1 >= expr2)}
    //  | expr1=expr MENOR_IGUAL_QUE expr2=expr // {valor=expr1 <= expr2}
    //  | CIERTO // {valor=T}
    //  | FALSO //  {valor=F}
    //  ;
    @Override
    public Object visitCondicion_basica(Anasint.Condicion_basicaContext ctx){
        // Si hay mas de una expresion se trata de una operacion booleana
        if(ctx.expr().size() == 0){
            if(ctx.getTokens(Anasint.CIERTO).size() == 1){
                return Boolean.TRUE;
            }else{
                return Boolean.FALSE;
            }
        }else{
            // Hay que calcular las expresiones y realizar las comparaciones
            Object expr1 = this.exprParser.visit(ctx.expr(0));
            Object expr2 = this.exprParser.visit(ctx.expr(1));

            // Si hay llamada a funcion desencapsulamos la variable devuelta
            if(List.class.isInstance(expr1)){
                // Comprobamos que la llamada devuelva un solo valor
                if(((List<Variable>)expr1).size() > 1) throw new RuntimeException(String.format("Runtime Error: only functions that return one value can be called in conditions. '%s'.", ctx.expr(0).getText()));
                expr1 = ((List<Variable>)expr1).get(0);
            }

            if(List.class.isInstance(expr2)){
                // Comprobamos que la llamada devuelva un solo valor
                if(((List<Variable>)expr2).size() > 1) throw new RuntimeException(String.format("Runtime Error: only functions that return one value can be called in conditions. '%s'.", ctx.expr(1).getText()));
                expr2 = ((List<Variable>)expr2).get(0);
            }

            // Desencapsulamos las variables
            if(Variable.class.isInstance(expr1)){
                expr1 = ((Variable)expr1).getValor();
            }

            if(Variable.class.isInstance(expr2)){
                expr2 = ((Variable)expr2).getValor();
            }

            // Comprobamos que las expresiones sean del mismo tipo
            if(!expr1.getClass().isInstance(expr2)) throw new RuntimeException(String.format("Runtime Error: ilegal comparison between '%s' and '%s'", ctx.expr(0).getText(), ctx.expr(1).getText()));
            // Detectamos el tipo de operacion
            if(ctx.IGUALDAD() != null) return expr1.equals(expr2);
            if(ctx.DESIGUALDAD() != null) return !expr1.equals(expr2);
            // A partir de aqui solo tiene sentido comparar enteros

            if(!Integer.class.isInstance(expr1) || !Integer.class.isInstance(expr2)) throw new RuntimeException("Runtime Error: '>', '<', '>=' and '<=' are only valid for numeric expresions");

            Integer valorExpr1 = (Integer) expr1;
            Integer valorExpr2 = (Integer) expr2;

            if(ctx.MAYOR_QUE() != null) return valorExpr1 > valorExpr2;
            if(ctx.MENOR_QUE() != null) return valorExpr1 < valorExpr2;
            if(ctx.MAYOR_IGUAL_QUE() != null) return valorExpr1 >= valorExpr2;
            if(ctx.MENOR_IGUAL_QUE() != null) return valorExpr1 <= valorExpr2;
        }
        throw new RuntimeException("Runtime Error: unknow error in visitCondicionBasica");
    }
}
