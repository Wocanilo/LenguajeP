// Generated from C:/Users/wocat/IdeaProjects/LenguajeP/src\PSint.g4 by ANTLR 4.8
import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link PSint}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface PSintVisitor<T> extends ParseTreeVisitor<T> {
	/**
	 * Visit a parse tree produced by {@link PSint#programa}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPrograma(PSint.ProgramaContext ctx);
	/**
	 * Visit a parse tree produced by {@link PSint#tipo}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTipo(PSint.TipoContext ctx);
	/**
	 * Visit a parse tree produced by {@link PSint#variables}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitVariables(PSint.VariablesContext ctx);
	/**
	 * Visit a parse tree produced by {@link PSint#decl_var}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDecl_var(PSint.Decl_varContext ctx);
	/**
	 * Visit a parse tree produced by {@link PSint#llamada_func_proc}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLlamada_func_proc(PSint.Llamada_func_procContext ctx);
	/**
	 * Visit a parse tree produced by {@link PSint#expr_entera}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExpr_entera(PSint.Expr_enteraContext ctx);
	/**
	 * Visit a parse tree produced by {@link PSint#expr_booleana}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExpr_booleana(PSint.Expr_booleanaContext ctx);
	/**
	 * Visit a parse tree produced by {@link PSint#elementos_secuencia}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitElementos_secuencia(PSint.Elementos_secuenciaContext ctx);
	/**
	 * Visit a parse tree produced by {@link PSint#expr_secuencia}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExpr_secuencia(PSint.Expr_secuenciaContext ctx);
	/**
	 * Visit a parse tree produced by {@link PSint#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExpr(PSint.ExprContext ctx);
	/**
	 * Visit a parse tree produced by {@link PSint#asignacion}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAsignacion(PSint.AsignacionContext ctx);
	/**
	 * Visit a parse tree produced by {@link PSint#condicion_basica}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCondicion_basica(PSint.Condicion_basicaContext ctx);
	/**
	 * Visit a parse tree produced by {@link PSint#condicion_completa}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCondicion_completa(PSint.Condicion_completaContext ctx);
	/**
	 * Visit a parse tree produced by {@link PSint#condicional}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCondicional(PSint.CondicionalContext ctx);
	/**
	 * Visit a parse tree produced by {@link PSint#funcion_avance}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFuncion_avance(PSint.Funcion_avanceContext ctx);
	/**
	 * Visit a parse tree produced by {@link PSint#ruptura}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitRuptura(PSint.RupturaContext ctx);
	/**
	 * Visit a parse tree produced by {@link PSint#iteracion}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIteracion(PSint.IteracionContext ctx);
	/**
	 * Visit a parse tree produced by {@link PSint#instruccion}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitInstruccion(PSint.InstruccionContext ctx);
	/**
	 * Visit a parse tree produced by {@link PSint#variable_cuantificada}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitVariable_cuantificada(PSint.Variable_cuantificadaContext ctx);
	/**
	 * Visit a parse tree produced by {@link PSint#aserto}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAserto(PSint.AsertoContext ctx);
	/**
	 * Visit a parse tree produced by {@link PSint#instrucciones}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitInstrucciones(PSint.InstruccionesContext ctx);
	/**
	 * Visit a parse tree produced by {@link PSint#parametro}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitParametro(PSint.ParametroContext ctx);
	/**
	 * Visit a parse tree produced by {@link PSint#parametros}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitParametros(PSint.ParametrosContext ctx);
	/**
	 * Visit a parse tree produced by {@link PSint#devolver}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDevolver(PSint.DevolverContext ctx);
	/**
	 * Visit a parse tree produced by {@link PSint#instrucciones_funcion}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitInstrucciones_funcion(PSint.Instrucciones_funcionContext ctx);
	/**
	 * Visit a parse tree produced by {@link PSint#def_func}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDef_func(PSint.Def_funcContext ctx);
	/**
	 * Visit a parse tree produced by {@link PSint#def_proc}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDef_proc(PSint.Def_procContext ctx);
	/**
	 * Visit a parse tree produced by {@link PSint#subprogramas}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSubprogramas(PSint.SubprogramasContext ctx);
}