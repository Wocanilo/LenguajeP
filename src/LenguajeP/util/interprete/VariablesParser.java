package LenguajeP.util.interprete;

import LenguajeP.Antlr.Anasint;
import LenguajeP.Antlr.AnasintBaseVisitor;
import org.antlr.v4.runtime.tree.TerminalNode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class VariablesParser extends AnasintBaseVisitor<Object>{
    /*
    Esta clase permite parsear una seccion variables.
     */

    // Almacen de variables
    private HashMap<String, Variable> almacenVariables = new HashMap<>();

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
    // decl_var: ident=IDENTIFICADOR (COMA ident=IDENTIFICADOR)* PyP t=tipo {almacenar cada ident con tipo t en almacen de variables};
    @Override
    public Object visitDecl_var(Anasint.Decl_varContext ctx){
        Integer tipo = (Integer)visit(ctx.tipo());

        for(TerminalNode variable: ctx.getTokens(Anasint.IDENTIFICADOR)){
            // La asignación de variables no asigna un valor a esta
            String identificador = variable.getText();
            this.almacenVariables.put(identificador, new Variable(identificador, tipo));
        }
        return this.almacenVariables;
    }

    @Override
    public Object visitVariables(Anasint.VariablesContext ctx){
        List<List<Variable>> variables = new ArrayList<>();

        for(Anasint.Decl_varContext decl_var: ctx.decl_var()){
            visit(decl_var);
        }

        return this.almacenVariables;
    }
}
