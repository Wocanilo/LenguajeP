package LenguajeP.util.compilador;

import LenguajeP.Antlr.Anasint;
import LenguajeP.Antlr.AnasintBaseVisitor;
import LenguajeP.util.compilador.ExprCompiler;
import LenguajeP.util.interprete.Subprograma;
import LenguajeP.util.interprete.Variable;

import java.util.HashMap;

public class CondicionCompiler extends AnasintBaseVisitor<Object> {
    private HashMap<String, Variable> almacenVariables;
    private ExprCompiler exprCompiler;
    private HashMap<String, Subprograma> subprogramas;

    public CondicionCompiler(HashMap<String, Variable> almacenVariables, HashMap<String, Subprograma> subprogramas){
        this.almacenVariables = almacenVariables;
        this.subprogramas = subprogramas;
        this.exprCompiler = new ExprCompiler(this.almacenVariables, subprogramas, new HashMap<Variable, Integer>());
    }

    /*
    condicion_basica: expr IGUALDAD expr
         | expr DESIGUALDAD expr
         | expr MENOR_QUE expr
         | expr MAYOR_QUE expr
         | expr MAYOR_IGUAL_QUE expr
         | expr MENOR_IGUAL_QUE expr
         | expr // Puede tratarse de una expresion que evalua a Booleano
         | CIERTO // Condicion especial, siempre cierta
         | FALSO // Condicion especial, siempre falsa
         ;
    */
    @Override
    public Object visitCondicion_basica(Anasint.Condicion_basicaContext ctx){
        if(ctx.CIERTO() != null) return "true";
        if(ctx.FALSO() != null) return "false";

        if(ctx.expr() != null && ctx.expr().size() > 1){
            String operacion = "";
            if(ctx.IGUALDAD() != null) operacion = "==";
            if(ctx.DESIGUALDAD() != null) operacion = "!=";
            if(ctx.MENOR_QUE() != null) operacion = "<";
            if(ctx.MAYOR_QUE() != null) operacion = ">";
            if(ctx.MAYOR_IGUAL_QUE() != null) operacion = ">=";
            if(ctx.MENOR_IGUAL_QUE() != null) operacion = "<=";

            // Ponemos paréntesis para asegurar el orden
            return String.format("(%s %s %s)", this.exprCompiler.visit(ctx.expr(0)), operacion, this.exprCompiler.visit(ctx.expr(1)));
        }
        return this.exprCompiler.visit(ctx);
    }

    /*
    condicion_completa: NEGACION condicion_completa
                  | condicion_completa DISYUNCION condicion_completa
                  | condicion_completa CONJUNCION condicion_completa
                  | INICIO_PARENTESIS condicion_completa FIN_PARENTESIS
                  | condicion_basica
                  ;
     */

    @Override
    public Object visitCondicion_completa(Anasint.Condicion_completaContext ctx){
        if(ctx.NEGACION() != null){
            if(ctx.condicion_completa() != null) return String.format("!%s", visit(ctx.condicion_completa(0)));
            if(ctx.condicion_basica() != null) return String.format("!%s", visit(ctx.condicion_basica()));
        }

        if(ctx.CONJUNCION() != null || ctx.DISYUNCION() != null){
            String operador = "";
            if(ctx.CONJUNCION() != null) operador = "&&";
            if(ctx.DISYUNCION() != null) operador = "||";

            return String.format("%s %s %s", visit(ctx.condicion_completa(0)), operador, visit(ctx.condicion_completa(1)));
        }

        // Condicion entre parentesis
        if(ctx.condicion_basica() != null) return visit(ctx.condicion_basica());
        return visit(ctx.condicion_completa(0));
    }
}
