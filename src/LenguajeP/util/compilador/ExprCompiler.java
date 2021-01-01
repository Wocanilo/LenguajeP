package LenguajeP.util.compilador;

import LenguajeP.Antlr.Anasint;
import LenguajeP.Antlr.AnasintBaseVisitor;
import LenguajeP.util.interprete.Subprograma;
import LenguajeP.util.interprete.Variable;

import java.util.*;

public class ExprCompiler extends AnasintBaseVisitor<Object>{
    private HashMap<String, Variable> almacenVariables;
    private HashMap<String, Subprograma> subprogramas;
    private HashMap<Variable, Integer> posicionVariableTmp = null;

    public ExprCompiler(HashMap<String, Variable> almacenVariables, HashMap<String, Subprograma> subprogramas){
        this.almacenVariables = almacenVariables;
        this.subprogramas = subprogramas;
    }

    public ExprCompiler(HashMap<String, Variable> almacenVariables, HashMap<String, Subprograma> subprogramas, HashMap<Variable, Integer> posicionVariableTmp){
        this.almacenVariables = almacenVariables;
        this.subprogramas = subprogramas;
        this.posicionVariableTmp = posicionVariableTmp;
    }

    private String idToString(Integer id){
        switch(id){
            case Anasint.NUM:
                return "Integer";
            case Anasint.LOG:
                return "Boolean";
            case Anasint.SEQ_LOG:
                return "List<Boolean>";
            case Anasint.SEQ_NUM:
                return "List<Integer>";
            case Anasint.SEQ:
                return "List<Object>";
            default:
                throw new RuntimeException("Compilation Errror: invalid variable type. NO_TIPO?");
        }
    }

    //(funcion getVariable(ident){
    //    Si ident in almacenVariables entonces
    //        devolver valor variable
    //    sino ERROR variable no declarada
    //)
    private Variable getVariable(String identificador){
        if(this.almacenVariables.containsKey(identificador)){
            return this.almacenVariables.get(identificador);
        }else throw new RuntimeException(String.format("Compilation Error: referenced undeclared variable '%s'", identificador));
    }

    @Override
    public Object visitExpr_entera(Anasint.Expr_enteraContext ctx){
        // Casos base
        /*
                MENOS* IDENTIFICADOR -> Interesa
                MENOS* ENTERO -> No se procesa
                MENOS* llamada_func_proc -> Hacemos checkeos pero no devuelve nada
                MENOS* acceso_secuencia -> Interesa
         */
        if(ctx.expr_entera().isEmpty()){
            if(ctx.IDENTIFICADOR() != null) {
                Variable var = getVariable(ctx.IDENTIFICADOR().getText());
                if(this.posicionVariableTmp == null) return var;
                else {
                    // Comprobamos si la variable colisiona
                    if(this.posicionVariableTmp.containsKey(var)) return String.format("((%s) almacenTmp.get(%s))", this.idToString(var.getTipo()), this.posicionVariableTmp.get(var));
                    else return ctx.getText();
                }
            }
            if(ctx.acceso_secuencia() != null) return visit(ctx.acceso_secuencia());

            if(ctx.llamada_func_proc() != null){
                System.out.println("Compilation: llamada a funcion.");
            }

            // Devolvemos el texto original
            if(this.posicionVariableTmp != null) return ctx.getText();

            return null; // En cualquier otro caso devolvemos un nulo, no nos interesa
        }else{
            /*
                expr_entera: expr_entera POR expr_entera -> Puede contener variables
                expr_entera MENOS expr_entera -> Puede contener variables
                expr_entera MAS expr_entera -> Puede contener variables
                MENOS* INICIO_PARENTESIS expr_entera FIN_PARENTESIS -> Puede contener variables
             */
            // expresion entre parentesis
            if(ctx.expr_entera() != null && ctx.expr_entera().size() == 1 && this.posicionVariableTmp == null) return visit(ctx.expr_entera(0));
            if(ctx.expr_entera() != null && ctx.expr_entera().size() == 1 && this.posicionVariableTmp != null) return String.format("(%s)", visit(ctx.expr_entera(0)));


            if(this.posicionVariableTmp == null){
                // Se devuelve un Set con las variables que intervienen en la expresión
                HashSet<Variable> variables = new HashSet<>();

                Object exprValue = visit(ctx.expr_entera(0));
                if(exprValue != null) {
                    if(HashSet.class.isInstance(exprValue)) variables.addAll((HashSet<Variable>) exprValue);
                    else variables.add((Variable) exprValue);
                }

                exprValue = visit(ctx.expr_entera(1));
                if(exprValue != null) {
                    if(HashSet.class.isInstance(exprValue)) variables.addAll((HashSet<Variable>) exprValue);
                    else variables.add((Variable) exprValue);
                }

                return variables;
            }else{
                // Resolvemos las expresiones
                Object exprValue = visit(ctx.expr_entera(0));
                Object exprValue2 = visit(ctx.expr_entera(1));

                String simbolo = "ERROR";

                if(ctx.POR() != null) simbolo = "*";
                if(ctx.MAS() != null) simbolo = "+";
                if(ctx.MENOS() != null && ctx.MENOS().size() > 0) simbolo = "-";

                return String.format("%s %s %s",exprValue, simbolo, exprValue2);
            }
        }
    }

    /*
        Obtenemos la variable usada en el acceso
     */
    @Override
    public Object visitAcceso_secuencia(Anasint.Acceso_secuenciaContext ctx){
        String identificador = ctx.getToken(Anasint.IDENTIFICADOR, 0).getText();
        // Obtenemos la variable
        Variable var = this.getVariable(identificador);

        if(var.getTipo() == Anasint.SEQ_LOG || var.getTipo() == Anasint.SEQ_NUM){
            return var;
        }else{
            // No es una secuencia
            throw new RuntimeException(String.format("Compilation Error: variable '%s' is not a sequence.",
                    identificador));
        }
    }
}
