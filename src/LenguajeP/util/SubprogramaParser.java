package LenguajeP.util;

import LenguajeP.Antlr.Anasint;
import LenguajeP.Antlr.AnasintBaseVisitor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SubprogramaParser extends AnasintBaseVisitor<Object> {
    @Override
    public Object visitSubprogramas(Anasint.SubprogramasContext ctx){
        HashMap<String, Subprograma> subprogramas = new HashMap<>();

        if(ctx.def_func() != null){
            for(Anasint.Def_funcContext defFuncion: ctx.def_func()){
                Subprograma sub = (Subprograma) visit(defFuncion);
                subprogramas.put(sub.identificador, sub);
            }
        }

        if(ctx.def_proc() != null){
            for(Anasint.Def_procContext defProc: ctx.def_proc()){
                Subprograma sub = (Subprograma) visit(defProc);
                subprogramas.put(sub.identificador, sub);
            }
        }

        return subprogramas;
    }

    //  (parametro de salida subprograma)
    //  def_proc: PROCEDIMIENTO ident=IDENTIFICADOR INICIO_PARENTESIS entrada=parametros? FIN_PARENTESIS vars=variables INSTRUCCIONES instr=instrucciones_procedimiento+ FPROCEDIMIENTO;
    //  {devolver Subprograma(ident, entrada, vars, instr)}
    @Override
    public Object visitDef_proc(Anasint.Def_procContext ctx){
        // Leemos la cabecera de la funcion
        String identificador = ctx.getToken(Anasint.IDENTIFICADOR, 0).getText();
        List<Parametro> parametrosEntrada = new ArrayList<>();
        if(ctx.parametros() != null){
            parametrosEntrada = (List<Parametro>) visit(ctx.parametros());
        }

        // Creamos el almacen de variables de la funcion
        VariablesParser variablesParser = new VariablesParser();
        HashMap<String, Variable> almacenVariables = (HashMap<String, Variable>)variablesParser.visit(ctx.variables());

        // Devolvemos el Subprograma
        return new Subprograma(identificador, parametrosEntrada, almacenVariables, ctx.instrucciones_procedimiento());
    }

    // (parametro de salida subprograma)
    // def_func: FUNCION ident=IDENTIFICADOR INICIO_PARENTESIS entrada=parametros? FIN_PARENTESIS DEV INICIO_PARENTESIS salida=parametros FIN_PARENTESIS vars=variables INSTRUCCIONES instr=instrucciones_funcion+ FFUNCION;
    // {devolver Subprograma(ident, entrada, salida, vars, instr)}
    @Override
    public Object visitDef_func(Anasint.Def_funcContext ctx){
        // Leemos la cabecera de la funcion
        String identificador = ctx.getToken(Anasint.IDENTIFICADOR, 0).getText();
        List<Parametro> parametrosEntrada = new ArrayList<>();
        List<Parametro> parametrosSalida;

        // Comprobamos si tiene parametros de entrada/salida o solo de salida
        if(ctx.parametros().size() == 2){
            parametrosEntrada = (List<Parametro>) visit(ctx.parametros(0));
            parametrosSalida = (List<Parametro>) visit(ctx.parametros(1));
        }else{
            parametrosSalida = (List<Parametro>) visit(ctx.parametros(0));
        }

        // Creamos el almacen de variables de la funcion
        VariablesParser variablesParser = new VariablesParser();
        HashMap<String, Variable> almacenVariables = (HashMap<String, Variable>)variablesParser.visit(ctx.variables());

        // Devolvemos el Subprograma
        return new Subprograma(identificador, parametrosEntrada, parametrosSalida, almacenVariables, ctx.instrucciones_funcion());
    }

    // (parametro devuelto parametros)
    // parametros: parametro (COMA parametro)* // {almacenar todos los param en parametros};
    @Override
    public Object visitParametros(Anasint.ParametrosContext ctx){
        List<Parametro> parametros = new ArrayList<Parametro>();

        for(Anasint.ParametroContext parametro: ctx.parametro()){
            parametros.add((Parametro) visit(parametro));
        }

        return parametros;
    }

    // (parametro devuelto param)
    // parametro: t=tipo ident=IDENTIFICADOR // {almacenar indent con tipo t en param};
    @Override
    public Object visitParametro(Anasint.ParametroContext ctx){
        String identificador = ctx.getToken(Anasint.IDENTIFICADOR, 0).getText();
        Integer tipo = ctx.tipo().getStart().getType();

        return new Parametro(identificador, tipo);
    }

}
