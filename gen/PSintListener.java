// Generated from C:/Users/wocat/IdeaProjects/LenguajeP/src\PSint.g4 by ANTLR 4.8
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link PSint}.
 */
public interface PSintListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link PSint#programa}.
	 * @param ctx the parse tree
	 */
	void enterPrograma(PSint.ProgramaContext ctx);
	/**
	 * Exit a parse tree produced by {@link PSint#programa}.
	 * @param ctx the parse tree
	 */
	void exitPrograma(PSint.ProgramaContext ctx);
	/**
	 * Enter a parse tree produced by {@link PSint#tipo}.
	 * @param ctx the parse tree
	 */
	void enterTipo(PSint.TipoContext ctx);
	/**
	 * Exit a parse tree produced by {@link PSint#tipo}.
	 * @param ctx the parse tree
	 */
	void exitTipo(PSint.TipoContext ctx);
	/**
	 * Enter a parse tree produced by {@link PSint#variables}.
	 * @param ctx the parse tree
	 */
	void enterVariables(PSint.VariablesContext ctx);
	/**
	 * Exit a parse tree produced by {@link PSint#variables}.
	 * @param ctx the parse tree
	 */
	void exitVariables(PSint.VariablesContext ctx);
	/**
	 * Enter a parse tree produced by {@link PSint#decl_var}.
	 * @param ctx the parse tree
	 */
	void enterDecl_var(PSint.Decl_varContext ctx);
	/**
	 * Exit a parse tree produced by {@link PSint#decl_var}.
	 * @param ctx the parse tree
	 */
	void exitDecl_var(PSint.Decl_varContext ctx);
	/**
	 * Enter a parse tree produced by {@link PSint#llamada_func_proc}.
	 * @param ctx the parse tree
	 */
	void enterLlamada_func_proc(PSint.Llamada_func_procContext ctx);
	/**
	 * Exit a parse tree produced by {@link PSint#llamada_func_proc}.
	 * @param ctx the parse tree
	 */
	void exitLlamada_func_proc(PSint.Llamada_func_procContext ctx);
	/**
	 * Enter a parse tree produced by {@link PSint#expr_entera}.
	 * @param ctx the parse tree
	 */
	void enterExpr_entera(PSint.Expr_enteraContext ctx);
	/**
	 * Exit a parse tree produced by {@link PSint#expr_entera}.
	 * @param ctx the parse tree
	 */
	void exitExpr_entera(PSint.Expr_enteraContext ctx);
	/**
	 * Enter a parse tree produced by {@link PSint#expr_booleana}.
	 * @param ctx the parse tree
	 */
	void enterExpr_booleana(PSint.Expr_booleanaContext ctx);
	/**
	 * Exit a parse tree produced by {@link PSint#expr_booleana}.
	 * @param ctx the parse tree
	 */
	void exitExpr_booleana(PSint.Expr_booleanaContext ctx);
	/**
	 * Enter a parse tree produced by {@link PSint#elementos_secuencia}.
	 * @param ctx the parse tree
	 */
	void enterElementos_secuencia(PSint.Elementos_secuenciaContext ctx);
	/**
	 * Exit a parse tree produced by {@link PSint#elementos_secuencia}.
	 * @param ctx the parse tree
	 */
	void exitElementos_secuencia(PSint.Elementos_secuenciaContext ctx);
	/**
	 * Enter a parse tree produced by {@link PSint#expr_secuencia}.
	 * @param ctx the parse tree
	 */
	void enterExpr_secuencia(PSint.Expr_secuenciaContext ctx);
	/**
	 * Exit a parse tree produced by {@link PSint#expr_secuencia}.
	 * @param ctx the parse tree
	 */
	void exitExpr_secuencia(PSint.Expr_secuenciaContext ctx);
	/**
	 * Enter a parse tree produced by {@link PSint#expr}.
	 * @param ctx the parse tree
	 */
	void enterExpr(PSint.ExprContext ctx);
	/**
	 * Exit a parse tree produced by {@link PSint#expr}.
	 * @param ctx the parse tree
	 */
	void exitExpr(PSint.ExprContext ctx);
	/**
	 * Enter a parse tree produced by {@link PSint#asignacion}.
	 * @param ctx the parse tree
	 */
	void enterAsignacion(PSint.AsignacionContext ctx);
	/**
	 * Exit a parse tree produced by {@link PSint#asignacion}.
	 * @param ctx the parse tree
	 */
	void exitAsignacion(PSint.AsignacionContext ctx);
	/**
	 * Enter a parse tree produced by {@link PSint#condicion_basica}.
	 * @param ctx the parse tree
	 */
	void enterCondicion_basica(PSint.Condicion_basicaContext ctx);
	/**
	 * Exit a parse tree produced by {@link PSint#condicion_basica}.
	 * @param ctx the parse tree
	 */
	void exitCondicion_basica(PSint.Condicion_basicaContext ctx);
	/**
	 * Enter a parse tree produced by {@link PSint#condicion_completa}.
	 * @param ctx the parse tree
	 */
	void enterCondicion_completa(PSint.Condicion_completaContext ctx);
	/**
	 * Exit a parse tree produced by {@link PSint#condicion_completa}.
	 * @param ctx the parse tree
	 */
	void exitCondicion_completa(PSint.Condicion_completaContext ctx);
	/**
	 * Enter a parse tree produced by {@link PSint#condicional}.
	 * @param ctx the parse tree
	 */
	void enterCondicional(PSint.CondicionalContext ctx);
	/**
	 * Exit a parse tree produced by {@link PSint#condicional}.
	 * @param ctx the parse tree
	 */
	void exitCondicional(PSint.CondicionalContext ctx);
	/**
	 * Enter a parse tree produced by {@link PSint#funcion_avance}.
	 * @param ctx the parse tree
	 */
	void enterFuncion_avance(PSint.Funcion_avanceContext ctx);
	/**
	 * Exit a parse tree produced by {@link PSint#funcion_avance}.
	 * @param ctx the parse tree
	 */
	void exitFuncion_avance(PSint.Funcion_avanceContext ctx);
	/**
	 * Enter a parse tree produced by {@link PSint#ruptura}.
	 * @param ctx the parse tree
	 */
	void enterRuptura(PSint.RupturaContext ctx);
	/**
	 * Exit a parse tree produced by {@link PSint#ruptura}.
	 * @param ctx the parse tree
	 */
	void exitRuptura(PSint.RupturaContext ctx);
	/**
	 * Enter a parse tree produced by {@link PSint#iteracion}.
	 * @param ctx the parse tree
	 */
	void enterIteracion(PSint.IteracionContext ctx);
	/**
	 * Exit a parse tree produced by {@link PSint#iteracion}.
	 * @param ctx the parse tree
	 */
	void exitIteracion(PSint.IteracionContext ctx);
	/**
	 * Enter a parse tree produced by {@link PSint#instruccion}.
	 * @param ctx the parse tree
	 */
	void enterInstruccion(PSint.InstruccionContext ctx);
	/**
	 * Exit a parse tree produced by {@link PSint#instruccion}.
	 * @param ctx the parse tree
	 */
	void exitInstruccion(PSint.InstruccionContext ctx);
	/**
	 * Enter a parse tree produced by {@link PSint#variable_cuantificada}.
	 * @param ctx the parse tree
	 */
	void enterVariable_cuantificada(PSint.Variable_cuantificadaContext ctx);
	/**
	 * Exit a parse tree produced by {@link PSint#variable_cuantificada}.
	 * @param ctx the parse tree
	 */
	void exitVariable_cuantificada(PSint.Variable_cuantificadaContext ctx);
	/**
	 * Enter a parse tree produced by {@link PSint#aserto}.
	 * @param ctx the parse tree
	 */
	void enterAserto(PSint.AsertoContext ctx);
	/**
	 * Exit a parse tree produced by {@link PSint#aserto}.
	 * @param ctx the parse tree
	 */
	void exitAserto(PSint.AsertoContext ctx);
	/**
	 * Enter a parse tree produced by {@link PSint#instrucciones}.
	 * @param ctx the parse tree
	 */
	void enterInstrucciones(PSint.InstruccionesContext ctx);
	/**
	 * Exit a parse tree produced by {@link PSint#instrucciones}.
	 * @param ctx the parse tree
	 */
	void exitInstrucciones(PSint.InstruccionesContext ctx);
	/**
	 * Enter a parse tree produced by {@link PSint#parametro}.
	 * @param ctx the parse tree
	 */
	void enterParametro(PSint.ParametroContext ctx);
	/**
	 * Exit a parse tree produced by {@link PSint#parametro}.
	 * @param ctx the parse tree
	 */
	void exitParametro(PSint.ParametroContext ctx);
	/**
	 * Enter a parse tree produced by {@link PSint#parametros}.
	 * @param ctx the parse tree
	 */
	void enterParametros(PSint.ParametrosContext ctx);
	/**
	 * Exit a parse tree produced by {@link PSint#parametros}.
	 * @param ctx the parse tree
	 */
	void exitParametros(PSint.ParametrosContext ctx);
	/**
	 * Enter a parse tree produced by {@link PSint#devolver}.
	 * @param ctx the parse tree
	 */
	void enterDevolver(PSint.DevolverContext ctx);
	/**
	 * Exit a parse tree produced by {@link PSint#devolver}.
	 * @param ctx the parse tree
	 */
	void exitDevolver(PSint.DevolverContext ctx);
	/**
	 * Enter a parse tree produced by {@link PSint#instrucciones_funcion}.
	 * @param ctx the parse tree
	 */
	void enterInstrucciones_funcion(PSint.Instrucciones_funcionContext ctx);
	/**
	 * Exit a parse tree produced by {@link PSint#instrucciones_funcion}.
	 * @param ctx the parse tree
	 */
	void exitInstrucciones_funcion(PSint.Instrucciones_funcionContext ctx);
	/**
	 * Enter a parse tree produced by {@link PSint#def_func}.
	 * @param ctx the parse tree
	 */
	void enterDef_func(PSint.Def_funcContext ctx);
	/**
	 * Exit a parse tree produced by {@link PSint#def_func}.
	 * @param ctx the parse tree
	 */
	void exitDef_func(PSint.Def_funcContext ctx);
	/**
	 * Enter a parse tree produced by {@link PSint#def_proc}.
	 * @param ctx the parse tree
	 */
	void enterDef_proc(PSint.Def_procContext ctx);
	/**
	 * Exit a parse tree produced by {@link PSint#def_proc}.
	 * @param ctx the parse tree
	 */
	void exitDef_proc(PSint.Def_procContext ctx);
	/**
	 * Enter a parse tree produced by {@link PSint#subprogramas}.
	 * @param ctx the parse tree
	 */
	void enterSubprogramas(PSint.SubprogramasContext ctx);
	/**
	 * Exit a parse tree produced by {@link PSint#subprogramas}.
	 * @param ctx the parse tree
	 */
	void exitSubprogramas(PSint.SubprogramasContext ctx);
}