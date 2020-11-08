// Generated from C:/Users/wocat/IdeaProjects/LenguajeP/src\PSint.g4 by ANTLR 4.8
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.*;
import org.antlr.v4.runtime.tree.*;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class PSint extends Parser {
	static { RuntimeMetaData.checkVersion("4.8", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		BLANCO=1, TABULADOR=2, FIN_LINEA=3, PROGRAMA=4, VARIABLES=5, SUBPROGRAMAS=6, 
		INSTRUCCIONES=7, NUM=8, LOG=9, SEQ_NUM=10, SEQ_LOG=11, SEQ=12, TRUE=13, 
		FALSE=14, MAS=15, MENOS=16, POR=17, INICIO_PARENTESIS=18, FIN_PARENTESIS=19, 
		INICIO_CORCHETE=20, FIN_CORCHETE=21, INICIO_LLAVE=22, FIN_LLAVE=23, IGUAL=24, 
		PyC=25, PyP=26, COMA=27, IGUALDAD=28, DESIGUALDAD=29, MENOR_QUE=30, MAYOR_QUE=31, 
		MAYOR_IGUAL_QUE=32, MENOR_IGUAL_QUE=33, CONJUNCION=34, DISYUNCION=35, 
		NEGACION=36, CIERTO=37, FALSO=38, FUNCION=39, FFUNCION=40, SI=41, SINO=42, 
		ENTONCES=43, DEV=44, FSI=45, FMIENTRAS=46, MIENTRAS=47, HACER=48, RUPTURA=49, 
		AVANCE=50, PROCEDIMIENTO=51, FPROCEDIMIENTO=52, PARATODO=53, EXISTE=54, 
		ENTERO=55, IDENTIFICADOR=56, COMENTARIOS_LINEA=57, COMENTARIOS_BLOQUE=58;
	public static final int
		RULE_programa = 0, RULE_tipo = 1, RULE_variables = 2, RULE_decl_var = 3, 
		RULE_llamada_func_proc = 4, RULE_expr_entera = 5, RULE_expr_booleana = 6, 
		RULE_acceso_secuencia = 7, RULE_elementos_secuencia = 8, RULE_expr_secuencia = 9, 
		RULE_expr = 10, RULE_asignacion = 11, RULE_condicion_basica = 12, RULE_condicion_completa = 13, 
		RULE_condicional = 14, RULE_ruptura = 15, RULE_iteracion = 16, RULE_instruccion = 17, 
		RULE_instrucciones_programa = 18, RULE_parametro = 19, RULE_parametros = 20, 
		RULE_devolver = 21, RULE_instrucciones_funcion = 22, RULE_instrucciones_procedimiento = 23, 
		RULE_def_func = 24, RULE_def_proc = 25, RULE_subprogramas = 26;
	private static String[] makeRuleNames() {
		return new String[] {
			"programa", "tipo", "variables", "decl_var", "llamada_func_proc", "expr_entera", 
			"expr_booleana", "acceso_secuencia", "elementos_secuencia", "expr_secuencia", 
			"expr", "asignacion", "condicion_basica", "condicion_completa", "condicional", 
			"ruptura", "iteracion", "instruccion", "instrucciones_programa", "parametro", 
			"parametros", "devolver", "instrucciones_funcion", "instrucciones_procedimiento", 
			"def_func", "def_proc", "subprogramas"
		};
	}
	public static final String[] ruleNames = makeRuleNames();

	private static String[] makeLiteralNames() {
		return new String[] {
			null, "' '", "'\t'", null, "'PROGRAMA'", "'VARIABLES'", "'SUBPROGRAMAS'", 
			"'INSTRUCCIONES'", "'NUM'", "'LOG'", "'SEQ(NUM)'", "'SEQ(LOG)'", "'SEQ'", 
			"'T'", "'F'", "'+'", "'-'", "'*'", "'('", "')'", "'['", "']'", "'{'", 
			"'}'", "'='", "';'", "':'", "','", "'=='", "'!='", "'<'", "'>'", "'>='", 
			"'<='", "'&&'", "'||'", "'!'", "'cierto'", "'falso'", "'FUNCION'", "'FFUNCION'", 
			"'si'", "'sino'", "'entonces'", "'dev'", "'fsi'", "'fmientras'", "'mientras'", 
			"'hacer'", "'ruptura'", "'avance'", "'PROCEDIMIENTO'", "'FPROCEDIMIENTO'", 
			"'PARATODO'", "'EXISTE'"
		};
	}
	private static final String[] _LITERAL_NAMES = makeLiteralNames();
	private static String[] makeSymbolicNames() {
		return new String[] {
			null, "BLANCO", "TABULADOR", "FIN_LINEA", "PROGRAMA", "VARIABLES", "SUBPROGRAMAS", 
			"INSTRUCCIONES", "NUM", "LOG", "SEQ_NUM", "SEQ_LOG", "SEQ", "TRUE", "FALSE", 
			"MAS", "MENOS", "POR", "INICIO_PARENTESIS", "FIN_PARENTESIS", "INICIO_CORCHETE", 
			"FIN_CORCHETE", "INICIO_LLAVE", "FIN_LLAVE", "IGUAL", "PyC", "PyP", "COMA", 
			"IGUALDAD", "DESIGUALDAD", "MENOR_QUE", "MAYOR_QUE", "MAYOR_IGUAL_QUE", 
			"MENOR_IGUAL_QUE", "CONJUNCION", "DISYUNCION", "NEGACION", "CIERTO", 
			"FALSO", "FUNCION", "FFUNCION", "SI", "SINO", "ENTONCES", "DEV", "FSI", 
			"FMIENTRAS", "MIENTRAS", "HACER", "RUPTURA", "AVANCE", "PROCEDIMIENTO", 
			"FPROCEDIMIENTO", "PARATODO", "EXISTE", "ENTERO", "IDENTIFICADOR", "COMENTARIOS_LINEA", 
			"COMENTARIOS_BLOQUE"
		};
	}
	private static final String[] _SYMBOLIC_NAMES = makeSymbolicNames();
	public static final Vocabulary VOCABULARY = new VocabularyImpl(_LITERAL_NAMES, _SYMBOLIC_NAMES);

	/**
	 * @deprecated Use {@link #VOCABULARY} instead.
	 */
	@Deprecated
	public static final String[] tokenNames;
	static {
		tokenNames = new String[_SYMBOLIC_NAMES.length];
		for (int i = 0; i < tokenNames.length; i++) {
			tokenNames[i] = VOCABULARY.getLiteralName(i);
			if (tokenNames[i] == null) {
				tokenNames[i] = VOCABULARY.getSymbolicName(i);
			}

			if (tokenNames[i] == null) {
				tokenNames[i] = "<INVALID>";
			}
		}
	}

	@Override
	@Deprecated
	public String[] getTokenNames() {
		return tokenNames;
	}

	@Override

	public Vocabulary getVocabulary() {
		return VOCABULARY;
	}

	@Override
	public String getGrammarFileName() { return "PSint.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public ATN getATN() { return _ATN; }

	public PSint(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	public static class ProgramaContext extends ParserRuleContext {
		public TerminalNode PROGRAMA() { return getToken(PSint.PROGRAMA, 0); }
		public VariablesContext variables() {
			return getRuleContext(VariablesContext.class,0);
		}
		public SubprogramasContext subprogramas() {
			return getRuleContext(SubprogramasContext.class,0);
		}
		public Instrucciones_programaContext instrucciones_programa() {
			return getRuleContext(Instrucciones_programaContext.class,0);
		}
		public TerminalNode EOF() { return getToken(PSint.EOF, 0); }
		public ProgramaContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_programa; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof PSintListener ) ((PSintListener)listener).enterPrograma(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof PSintListener ) ((PSintListener)listener).exitPrograma(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof PSintVisitor ) return ((PSintVisitor<? extends T>)visitor).visitPrograma(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ProgramaContext programa() throws RecognitionException {
		ProgramaContext _localctx = new ProgramaContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_programa);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(54);
			match(PROGRAMA);
			setState(55);
			variables();
			setState(56);
			subprogramas();
			setState(57);
			instrucciones_programa();
			setState(58);
			match(EOF);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class TipoContext extends ParserRuleContext {
		public TerminalNode NUM() { return getToken(PSint.NUM, 0); }
		public TerminalNode LOG() { return getToken(PSint.LOG, 0); }
		public TerminalNode SEQ_NUM() { return getToken(PSint.SEQ_NUM, 0); }
		public TerminalNode SEQ_LOG() { return getToken(PSint.SEQ_LOG, 0); }
		public TipoContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_tipo; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof PSintListener ) ((PSintListener)listener).enterTipo(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof PSintListener ) ((PSintListener)listener).exitTipo(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof PSintVisitor ) return ((PSintVisitor<? extends T>)visitor).visitTipo(this);
			else return visitor.visitChildren(this);
		}
	}

	public final TipoContext tipo() throws RecognitionException {
		TipoContext _localctx = new TipoContext(_ctx, getState());
		enterRule(_localctx, 2, RULE_tipo);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(60);
			_la = _input.LA(1);
			if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << NUM) | (1L << LOG) | (1L << SEQ_NUM) | (1L << SEQ_LOG))) != 0)) ) {
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class VariablesContext extends ParserRuleContext {
		public TerminalNode VARIABLES() { return getToken(PSint.VARIABLES, 0); }
		public List<Decl_varContext> decl_var() {
			return getRuleContexts(Decl_varContext.class);
		}
		public Decl_varContext decl_var(int i) {
			return getRuleContext(Decl_varContext.class,i);
		}
		public List<TerminalNode> PyC() { return getTokens(PSint.PyC); }
		public TerminalNode PyC(int i) {
			return getToken(PSint.PyC, i);
		}
		public VariablesContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_variables; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof PSintListener ) ((PSintListener)listener).enterVariables(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof PSintListener ) ((PSintListener)listener).exitVariables(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof PSintVisitor ) return ((PSintVisitor<? extends T>)visitor).visitVariables(this);
			else return visitor.visitChildren(this);
		}
	}

	public final VariablesContext variables() throws RecognitionException {
		VariablesContext _localctx = new VariablesContext(_ctx, getState());
		enterRule(_localctx, 4, RULE_variables);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(62);
			match(VARIABLES);
			setState(68);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==IDENTIFICADOR) {
				{
				{
				setState(63);
				decl_var();
				setState(64);
				match(PyC);
				}
				}
				setState(70);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Decl_varContext extends ParserRuleContext {
		public List<TerminalNode> IDENTIFICADOR() { return getTokens(PSint.IDENTIFICADOR); }
		public TerminalNode IDENTIFICADOR(int i) {
			return getToken(PSint.IDENTIFICADOR, i);
		}
		public TerminalNode PyP() { return getToken(PSint.PyP, 0); }
		public TipoContext tipo() {
			return getRuleContext(TipoContext.class,0);
		}
		public List<TerminalNode> COMA() { return getTokens(PSint.COMA); }
		public TerminalNode COMA(int i) {
			return getToken(PSint.COMA, i);
		}
		public Decl_varContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_decl_var; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof PSintListener ) ((PSintListener)listener).enterDecl_var(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof PSintListener ) ((PSintListener)listener).exitDecl_var(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof PSintVisitor ) return ((PSintVisitor<? extends T>)visitor).visitDecl_var(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Decl_varContext decl_var() throws RecognitionException {
		Decl_varContext _localctx = new Decl_varContext(_ctx, getState());
		enterRule(_localctx, 6, RULE_decl_var);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(71);
			match(IDENTIFICADOR);
			setState(76);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==COMA) {
				{
				{
				setState(72);
				match(COMA);
				setState(73);
				match(IDENTIFICADOR);
				}
				}
				setState(78);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(79);
			match(PyP);
			setState(80);
			tipo();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Llamada_func_procContext extends ParserRuleContext {
		public TerminalNode IDENTIFICADOR() { return getToken(PSint.IDENTIFICADOR, 0); }
		public TerminalNode INICIO_PARENTESIS() { return getToken(PSint.INICIO_PARENTESIS, 0); }
		public TerminalNode FIN_PARENTESIS() { return getToken(PSint.FIN_PARENTESIS, 0); }
		public List<ExprContext> expr() {
			return getRuleContexts(ExprContext.class);
		}
		public ExprContext expr(int i) {
			return getRuleContext(ExprContext.class,i);
		}
		public List<TerminalNode> COMA() { return getTokens(PSint.COMA); }
		public TerminalNode COMA(int i) {
			return getToken(PSint.COMA, i);
		}
		public Llamada_func_procContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_llamada_func_proc; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof PSintListener ) ((PSintListener)listener).enterLlamada_func_proc(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof PSintListener ) ((PSintListener)listener).exitLlamada_func_proc(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof PSintVisitor ) return ((PSintVisitor<? extends T>)visitor).visitLlamada_func_proc(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Llamada_func_procContext llamada_func_proc() throws RecognitionException {
		Llamada_func_procContext _localctx = new Llamada_func_procContext(_ctx, getState());
		enterRule(_localctx, 8, RULE_llamada_func_proc);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(82);
			match(IDENTIFICADOR);
			setState(83);
			match(INICIO_PARENTESIS);
			setState(92);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << TRUE) | (1L << FALSE) | (1L << INICIO_PARENTESIS) | (1L << INICIO_CORCHETE) | (1L << ENTERO) | (1L << IDENTIFICADOR))) != 0)) {
				{
				setState(84);
				expr();
				setState(89);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==COMA) {
					{
					{
					setState(85);
					match(COMA);
					setState(86);
					expr();
					}
					}
					setState(91);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				}
			}

			setState(94);
			match(FIN_PARENTESIS);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Expr_enteraContext extends ParserRuleContext {
		public TerminalNode INICIO_PARENTESIS() { return getToken(PSint.INICIO_PARENTESIS, 0); }
		public List<Expr_enteraContext> expr_entera() {
			return getRuleContexts(Expr_enteraContext.class);
		}
		public Expr_enteraContext expr_entera(int i) {
			return getRuleContext(Expr_enteraContext.class,i);
		}
		public TerminalNode FIN_PARENTESIS() { return getToken(PSint.FIN_PARENTESIS, 0); }
		public TerminalNode IDENTIFICADOR() { return getToken(PSint.IDENTIFICADOR, 0); }
		public TerminalNode ENTERO() { return getToken(PSint.ENTERO, 0); }
		public Llamada_func_procContext llamada_func_proc() {
			return getRuleContext(Llamada_func_procContext.class,0);
		}
		public Acceso_secuenciaContext acceso_secuencia() {
			return getRuleContext(Acceso_secuenciaContext.class,0);
		}
		public TerminalNode MAS() { return getToken(PSint.MAS, 0); }
		public TerminalNode MENOS() { return getToken(PSint.MENOS, 0); }
		public TerminalNode POR() { return getToken(PSint.POR, 0); }
		public Expr_enteraContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_expr_entera; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof PSintListener ) ((PSintListener)listener).enterExpr_entera(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof PSintListener ) ((PSintListener)listener).exitExpr_entera(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof PSintVisitor ) return ((PSintVisitor<? extends T>)visitor).visitExpr_entera(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Expr_enteraContext expr_entera() throws RecognitionException {
		return expr_entera(0);
	}

	private Expr_enteraContext expr_entera(int _p) throws RecognitionException {
		ParserRuleContext _parentctx = _ctx;
		int _parentState = getState();
		Expr_enteraContext _localctx = new Expr_enteraContext(_ctx, _parentState);
		Expr_enteraContext _prevctx = _localctx;
		int _startState = 10;
		enterRecursionRule(_localctx, 10, RULE_expr_entera, _p);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(105);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,4,_ctx) ) {
			case 1:
				{
				setState(97);
				match(INICIO_PARENTESIS);
				setState(98);
				expr_entera(0);
				setState(99);
				match(FIN_PARENTESIS);
				}
				break;
			case 2:
				{
				setState(101);
				match(IDENTIFICADOR);
				}
				break;
			case 3:
				{
				setState(102);
				match(ENTERO);
				}
				break;
			case 4:
				{
				setState(103);
				llamada_func_proc();
				}
				break;
			case 5:
				{
				setState(104);
				acceso_secuencia();
				}
				break;
			}
			_ctx.stop = _input.LT(-1);
			setState(118);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,6,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					setState(116);
					_errHandler.sync(this);
					switch ( getInterpreter().adaptivePredict(_input,5,_ctx) ) {
					case 1:
						{
						_localctx = new Expr_enteraContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_expr_entera);
						setState(107);
						if (!(precpred(_ctx, 8))) throw new FailedPredicateException(this, "precpred(_ctx, 8)");
						setState(108);
						match(MAS);
						setState(109);
						expr_entera(9);
						}
						break;
					case 2:
						{
						_localctx = new Expr_enteraContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_expr_entera);
						setState(110);
						if (!(precpred(_ctx, 7))) throw new FailedPredicateException(this, "precpred(_ctx, 7)");
						setState(111);
						match(MENOS);
						setState(112);
						expr_entera(8);
						}
						break;
					case 3:
						{
						_localctx = new Expr_enteraContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_expr_entera);
						setState(113);
						if (!(precpred(_ctx, 6))) throw new FailedPredicateException(this, "precpred(_ctx, 6)");
						setState(114);
						match(POR);
						setState(115);
						expr_entera(7);
						}
						break;
					}
					} 
				}
				setState(120);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,6,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			unrollRecursionContexts(_parentctx);
		}
		return _localctx;
	}

	public static class Expr_booleanaContext extends ParserRuleContext {
		public TerminalNode TRUE() { return getToken(PSint.TRUE, 0); }
		public TerminalNode FALSE() { return getToken(PSint.FALSE, 0); }
		public Expr_booleanaContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_expr_booleana; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof PSintListener ) ((PSintListener)listener).enterExpr_booleana(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof PSintListener ) ((PSintListener)listener).exitExpr_booleana(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof PSintVisitor ) return ((PSintVisitor<? extends T>)visitor).visitExpr_booleana(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Expr_booleanaContext expr_booleana() throws RecognitionException {
		Expr_booleanaContext _localctx = new Expr_booleanaContext(_ctx, getState());
		enterRule(_localctx, 12, RULE_expr_booleana);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(121);
			_la = _input.LA(1);
			if ( !(_la==TRUE || _la==FALSE) ) {
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Acceso_secuenciaContext extends ParserRuleContext {
		public TerminalNode IDENTIFICADOR() { return getToken(PSint.IDENTIFICADOR, 0); }
		public TerminalNode INICIO_CORCHETE() { return getToken(PSint.INICIO_CORCHETE, 0); }
		public Expr_enteraContext expr_entera() {
			return getRuleContext(Expr_enteraContext.class,0);
		}
		public TerminalNode FIN_CORCHETE() { return getToken(PSint.FIN_CORCHETE, 0); }
		public Acceso_secuenciaContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_acceso_secuencia; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof PSintListener ) ((PSintListener)listener).enterAcceso_secuencia(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof PSintListener ) ((PSintListener)listener).exitAcceso_secuencia(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof PSintVisitor ) return ((PSintVisitor<? extends T>)visitor).visitAcceso_secuencia(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Acceso_secuenciaContext acceso_secuencia() throws RecognitionException {
		Acceso_secuenciaContext _localctx = new Acceso_secuenciaContext(_ctx, getState());
		enterRule(_localctx, 14, RULE_acceso_secuencia);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(123);
			match(IDENTIFICADOR);
			setState(124);
			match(INICIO_CORCHETE);
			setState(125);
			expr_entera(0);
			setState(126);
			match(FIN_CORCHETE);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Elementos_secuenciaContext extends ParserRuleContext {
		public List<Expr_enteraContext> expr_entera() {
			return getRuleContexts(Expr_enteraContext.class);
		}
		public Expr_enteraContext expr_entera(int i) {
			return getRuleContext(Expr_enteraContext.class,i);
		}
		public List<Expr_booleanaContext> expr_booleana() {
			return getRuleContexts(Expr_booleanaContext.class);
		}
		public Expr_booleanaContext expr_booleana(int i) {
			return getRuleContext(Expr_booleanaContext.class,i);
		}
		public List<TerminalNode> COMA() { return getTokens(PSint.COMA); }
		public TerminalNode COMA(int i) {
			return getToken(PSint.COMA, i);
		}
		public Elementos_secuenciaContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_elementos_secuencia; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof PSintListener ) ((PSintListener)listener).enterElementos_secuencia(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof PSintListener ) ((PSintListener)listener).exitElementos_secuencia(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof PSintVisitor ) return ((PSintVisitor<? extends T>)visitor).visitElementos_secuencia(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Elementos_secuenciaContext elementos_secuencia() throws RecognitionException {
		Elementos_secuenciaContext _localctx = new Elementos_secuenciaContext(_ctx, getState());
		enterRule(_localctx, 16, RULE_elementos_secuencia);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(130);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case INICIO_PARENTESIS:
			case ENTERO:
			case IDENTIFICADOR:
				{
				setState(128);
				expr_entera(0);
				}
				break;
			case TRUE:
			case FALSE:
				{
				setState(129);
				expr_booleana();
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
			setState(139);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==COMA) {
				{
				{
				setState(132);
				match(COMA);
				setState(135);
				_errHandler.sync(this);
				switch (_input.LA(1)) {
				case INICIO_PARENTESIS:
				case ENTERO:
				case IDENTIFICADOR:
					{
					setState(133);
					expr_entera(0);
					}
					break;
				case TRUE:
				case FALSE:
					{
					setState(134);
					expr_booleana();
					}
					break;
				default:
					throw new NoViableAltException(this);
				}
				}
				}
				setState(141);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Expr_secuenciaContext extends ParserRuleContext {
		public TerminalNode INICIO_CORCHETE() { return getToken(PSint.INICIO_CORCHETE, 0); }
		public Elementos_secuenciaContext elementos_secuencia() {
			return getRuleContext(Elementos_secuenciaContext.class,0);
		}
		public TerminalNode FIN_CORCHETE() { return getToken(PSint.FIN_CORCHETE, 0); }
		public Expr_secuenciaContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_expr_secuencia; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof PSintListener ) ((PSintListener)listener).enterExpr_secuencia(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof PSintListener ) ((PSintListener)listener).exitExpr_secuencia(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof PSintVisitor ) return ((PSintVisitor<? extends T>)visitor).visitExpr_secuencia(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Expr_secuenciaContext expr_secuencia() throws RecognitionException {
		Expr_secuenciaContext _localctx = new Expr_secuenciaContext(_ctx, getState());
		enterRule(_localctx, 18, RULE_expr_secuencia);
		try {
			setState(148);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,10,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(142);
				match(INICIO_CORCHETE);
				setState(143);
				elementos_secuencia();
				setState(144);
				match(FIN_CORCHETE);
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(146);
				match(INICIO_CORCHETE);
				setState(147);
				match(FIN_CORCHETE);
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ExprContext extends ParserRuleContext {
		public Expr_enteraContext expr_entera() {
			return getRuleContext(Expr_enteraContext.class,0);
		}
		public Expr_booleanaContext expr_booleana() {
			return getRuleContext(Expr_booleanaContext.class,0);
		}
		public Expr_secuenciaContext expr_secuencia() {
			return getRuleContext(Expr_secuenciaContext.class,0);
		}
		public ExprContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_expr; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof PSintListener ) ((PSintListener)listener).enterExpr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof PSintListener ) ((PSintListener)listener).exitExpr(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof PSintVisitor ) return ((PSintVisitor<? extends T>)visitor).visitExpr(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ExprContext expr() throws RecognitionException {
		ExprContext _localctx = new ExprContext(_ctx, getState());
		enterRule(_localctx, 20, RULE_expr);
		try {
			setState(153);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case INICIO_PARENTESIS:
			case ENTERO:
			case IDENTIFICADOR:
				enterOuterAlt(_localctx, 1);
				{
				setState(150);
				expr_entera(0);
				}
				break;
			case TRUE:
			case FALSE:
				enterOuterAlt(_localctx, 2);
				{
				setState(151);
				expr_booleana();
				}
				break;
			case INICIO_CORCHETE:
				enterOuterAlt(_localctx, 3);
				{
				setState(152);
				expr_secuencia();
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class AsignacionContext extends ParserRuleContext {
		public List<TerminalNode> IDENTIFICADOR() { return getTokens(PSint.IDENTIFICADOR); }
		public TerminalNode IDENTIFICADOR(int i) {
			return getToken(PSint.IDENTIFICADOR, i);
		}
		public TerminalNode IGUAL() { return getToken(PSint.IGUAL, 0); }
		public List<ExprContext> expr() {
			return getRuleContexts(ExprContext.class);
		}
		public ExprContext expr(int i) {
			return getRuleContext(ExprContext.class,i);
		}
		public TerminalNode PyC() { return getToken(PSint.PyC, 0); }
		public List<TerminalNode> COMA() { return getTokens(PSint.COMA); }
		public TerminalNode COMA(int i) {
			return getToken(PSint.COMA, i);
		}
		public AsignacionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_asignacion; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof PSintListener ) ((PSintListener)listener).enterAsignacion(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof PSintListener ) ((PSintListener)listener).exitAsignacion(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof PSintVisitor ) return ((PSintVisitor<? extends T>)visitor).visitAsignacion(this);
			else return visitor.visitChildren(this);
		}
	}

	public final AsignacionContext asignacion() throws RecognitionException {
		AsignacionContext _localctx = new AsignacionContext(_ctx, getState());
		enterRule(_localctx, 22, RULE_asignacion);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(155);
			match(IDENTIFICADOR);
			setState(160);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==COMA) {
				{
				{
				setState(156);
				match(COMA);
				setState(157);
				match(IDENTIFICADOR);
				}
				}
				setState(162);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(163);
			match(IGUAL);
			setState(164);
			expr();
			setState(169);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==COMA) {
				{
				{
				setState(165);
				match(COMA);
				setState(166);
				expr();
				}
				}
				setState(171);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(172);
			match(PyC);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Condicion_basicaContext extends ParserRuleContext {
		public List<ExprContext> expr() {
			return getRuleContexts(ExprContext.class);
		}
		public ExprContext expr(int i) {
			return getRuleContext(ExprContext.class,i);
		}
		public TerminalNode IGUALDAD() { return getToken(PSint.IGUALDAD, 0); }
		public TerminalNode DESIGUALDAD() { return getToken(PSint.DESIGUALDAD, 0); }
		public TerminalNode MENOR_QUE() { return getToken(PSint.MENOR_QUE, 0); }
		public TerminalNode MAYOR_QUE() { return getToken(PSint.MAYOR_QUE, 0); }
		public TerminalNode MAYOR_IGUAL_QUE() { return getToken(PSint.MAYOR_IGUAL_QUE, 0); }
		public TerminalNode MENOR_IGUAL_QUE() { return getToken(PSint.MENOR_IGUAL_QUE, 0); }
		public TerminalNode CIERTO() { return getToken(PSint.CIERTO, 0); }
		public TerminalNode FALSO() { return getToken(PSint.FALSO, 0); }
		public Condicion_basicaContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_condicion_basica; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof PSintListener ) ((PSintListener)listener).enterCondicion_basica(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof PSintListener ) ((PSintListener)listener).exitCondicion_basica(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof PSintVisitor ) return ((PSintVisitor<? extends T>)visitor).visitCondicion_basica(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Condicion_basicaContext condicion_basica() throws RecognitionException {
		Condicion_basicaContext _localctx = new Condicion_basicaContext(_ctx, getState());
		enterRule(_localctx, 24, RULE_condicion_basica);
		try {
			setState(200);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,14,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(174);
				expr();
				setState(175);
				match(IGUALDAD);
				setState(176);
				expr();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(178);
				expr();
				setState(179);
				match(DESIGUALDAD);
				setState(180);
				expr();
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(182);
				expr();
				setState(183);
				match(MENOR_QUE);
				setState(184);
				expr();
				}
				break;
			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(186);
				expr();
				setState(187);
				match(MAYOR_QUE);
				setState(188);
				expr();
				}
				break;
			case 5:
				enterOuterAlt(_localctx, 5);
				{
				setState(190);
				expr();
				setState(191);
				match(MAYOR_IGUAL_QUE);
				setState(192);
				expr();
				}
				break;
			case 6:
				enterOuterAlt(_localctx, 6);
				{
				setState(194);
				expr();
				setState(195);
				match(MENOR_IGUAL_QUE);
				setState(196);
				expr();
				}
				break;
			case 7:
				enterOuterAlt(_localctx, 7);
				{
				setState(198);
				match(CIERTO);
				}
				break;
			case 8:
				enterOuterAlt(_localctx, 8);
				{
				setState(199);
				match(FALSO);
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Condicion_completaContext extends ParserRuleContext {
		public TerminalNode INICIO_PARENTESIS() { return getToken(PSint.INICIO_PARENTESIS, 0); }
		public List<Condicion_completaContext> condicion_completa() {
			return getRuleContexts(Condicion_completaContext.class);
		}
		public Condicion_completaContext condicion_completa(int i) {
			return getRuleContext(Condicion_completaContext.class,i);
		}
		public TerminalNode FIN_PARENTESIS() { return getToken(PSint.FIN_PARENTESIS, 0); }
		public TerminalNode NEGACION() { return getToken(PSint.NEGACION, 0); }
		public Condicion_basicaContext condicion_basica() {
			return getRuleContext(Condicion_basicaContext.class,0);
		}
		public TerminalNode CONJUNCION() { return getToken(PSint.CONJUNCION, 0); }
		public TerminalNode DISYUNCION() { return getToken(PSint.DISYUNCION, 0); }
		public Condicion_completaContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_condicion_completa; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof PSintListener ) ((PSintListener)listener).enterCondicion_completa(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof PSintListener ) ((PSintListener)listener).exitCondicion_completa(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof PSintVisitor ) return ((PSintVisitor<? extends T>)visitor).visitCondicion_completa(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Condicion_completaContext condicion_completa() throws RecognitionException {
		return condicion_completa(0);
	}

	private Condicion_completaContext condicion_completa(int _p) throws RecognitionException {
		ParserRuleContext _parentctx = _ctx;
		int _parentState = getState();
		Condicion_completaContext _localctx = new Condicion_completaContext(_ctx, _parentState);
		Condicion_completaContext _prevctx = _localctx;
		int _startState = 26;
		enterRecursionRule(_localctx, 26, RULE_condicion_completa, _p);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(210);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,15,_ctx) ) {
			case 1:
				{
				setState(203);
				match(INICIO_PARENTESIS);
				setState(204);
				condicion_completa(0);
				setState(205);
				match(FIN_PARENTESIS);
				}
				break;
			case 2:
				{
				setState(207);
				match(NEGACION);
				setState(208);
				condicion_completa(2);
				}
				break;
			case 3:
				{
				setState(209);
				condicion_basica();
				}
				break;
			}
			_ctx.stop = _input.LT(-1);
			setState(220);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,17,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					setState(218);
					_errHandler.sync(this);
					switch ( getInterpreter().adaptivePredict(_input,16,_ctx) ) {
					case 1:
						{
						_localctx = new Condicion_completaContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_condicion_completa);
						setState(212);
						if (!(precpred(_ctx, 5))) throw new FailedPredicateException(this, "precpred(_ctx, 5)");
						setState(213);
						match(CONJUNCION);
						setState(214);
						condicion_completa(6);
						}
						break;
					case 2:
						{
						_localctx = new Condicion_completaContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_condicion_completa);
						setState(215);
						if (!(precpred(_ctx, 4))) throw new FailedPredicateException(this, "precpred(_ctx, 4)");
						setState(216);
						match(DISYUNCION);
						setState(217);
						condicion_completa(5);
						}
						break;
					}
					} 
				}
				setState(222);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,17,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			unrollRecursionContexts(_parentctx);
		}
		return _localctx;
	}

	public static class CondicionalContext extends ParserRuleContext {
		public TerminalNode SI() { return getToken(PSint.SI, 0); }
		public TerminalNode INICIO_PARENTESIS() { return getToken(PSint.INICIO_PARENTESIS, 0); }
		public Condicion_completaContext condicion_completa() {
			return getRuleContext(Condicion_completaContext.class,0);
		}
		public TerminalNode FIN_PARENTESIS() { return getToken(PSint.FIN_PARENTESIS, 0); }
		public TerminalNode ENTONCES() { return getToken(PSint.ENTONCES, 0); }
		public TerminalNode FSI() { return getToken(PSint.FSI, 0); }
		public List<InstruccionContext> instruccion() {
			return getRuleContexts(InstruccionContext.class);
		}
		public InstruccionContext instruccion(int i) {
			return getRuleContext(InstruccionContext.class,i);
		}
		public TerminalNode SINO() { return getToken(PSint.SINO, 0); }
		public CondicionalContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_condicional; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof PSintListener ) ((PSintListener)listener).enterCondicional(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof PSintListener ) ((PSintListener)listener).exitCondicional(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof PSintVisitor ) return ((PSintVisitor<? extends T>)visitor).visitCondicional(this);
			else return visitor.visitChildren(this);
		}
	}

	public final CondicionalContext condicional() throws RecognitionException {
		CondicionalContext _localctx = new CondicionalContext(_ctx, getState());
		enterRule(_localctx, 28, RULE_condicional);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(223);
			match(SI);
			setState(224);
			match(INICIO_PARENTESIS);
			setState(225);
			condicion_completa(0);
			setState(226);
			match(FIN_PARENTESIS);
			setState(227);
			match(ENTONCES);
			setState(229); 
			_errHandler.sync(this);
			_la = _input.LA(1);
			do {
				{
				{
				setState(228);
				instruccion();
				}
				}
				setState(231); 
				_errHandler.sync(this);
				_la = _input.LA(1);
			} while ( (((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << SI) | (1L << MIENTRAS) | (1L << IDENTIFICADOR))) != 0) );
			setState(239);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==SINO) {
				{
				setState(233);
				match(SINO);
				setState(235); 
				_errHandler.sync(this);
				_la = _input.LA(1);
				do {
					{
					{
					setState(234);
					instruccion();
					}
					}
					setState(237); 
					_errHandler.sync(this);
					_la = _input.LA(1);
				} while ( (((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << SI) | (1L << MIENTRAS) | (1L << IDENTIFICADOR))) != 0) );
				}
			}

			setState(241);
			match(FSI);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class RupturaContext extends ParserRuleContext {
		public TerminalNode RUPTURA() { return getToken(PSint.RUPTURA, 0); }
		public TerminalNode PyC() { return getToken(PSint.PyC, 0); }
		public RupturaContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_ruptura; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof PSintListener ) ((PSintListener)listener).enterRuptura(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof PSintListener ) ((PSintListener)listener).exitRuptura(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof PSintVisitor ) return ((PSintVisitor<? extends T>)visitor).visitRuptura(this);
			else return visitor.visitChildren(this);
		}
	}

	public final RupturaContext ruptura() throws RecognitionException {
		RupturaContext _localctx = new RupturaContext(_ctx, getState());
		enterRule(_localctx, 30, RULE_ruptura);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(243);
			match(RUPTURA);
			setState(244);
			match(PyC);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class IteracionContext extends ParserRuleContext {
		public TerminalNode MIENTRAS() { return getToken(PSint.MIENTRAS, 0); }
		public TerminalNode INICIO_PARENTESIS() { return getToken(PSint.INICIO_PARENTESIS, 0); }
		public Condicion_completaContext condicion_completa() {
			return getRuleContext(Condicion_completaContext.class,0);
		}
		public TerminalNode FIN_PARENTESIS() { return getToken(PSint.FIN_PARENTESIS, 0); }
		public TerminalNode HACER() { return getToken(PSint.HACER, 0); }
		public TerminalNode FMIENTRAS() { return getToken(PSint.FMIENTRAS, 0); }
		public List<InstruccionContext> instruccion() {
			return getRuleContexts(InstruccionContext.class);
		}
		public InstruccionContext instruccion(int i) {
			return getRuleContext(InstruccionContext.class,i);
		}
		public List<RupturaContext> ruptura() {
			return getRuleContexts(RupturaContext.class);
		}
		public RupturaContext ruptura(int i) {
			return getRuleContext(RupturaContext.class,i);
		}
		public IteracionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_iteracion; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof PSintListener ) ((PSintListener)listener).enterIteracion(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof PSintListener ) ((PSintListener)listener).exitIteracion(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof PSintVisitor ) return ((PSintVisitor<? extends T>)visitor).visitIteracion(this);
			else return visitor.visitChildren(this);
		}
	}

	public final IteracionContext iteracion() throws RecognitionException {
		IteracionContext _localctx = new IteracionContext(_ctx, getState());
		enterRule(_localctx, 32, RULE_iteracion);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(246);
			match(MIENTRAS);
			setState(247);
			match(INICIO_PARENTESIS);
			setState(248);
			condicion_completa(0);
			setState(249);
			match(FIN_PARENTESIS);
			setState(250);
			match(HACER);
			setState(253); 
			_errHandler.sync(this);
			_la = _input.LA(1);
			do {
				{
				setState(253);
				_errHandler.sync(this);
				switch (_input.LA(1)) {
				case SI:
				case MIENTRAS:
				case IDENTIFICADOR:
					{
					setState(251);
					instruccion();
					}
					break;
				case RUPTURA:
					{
					setState(252);
					ruptura();
					}
					break;
				default:
					throw new NoViableAltException(this);
				}
				}
				setState(255); 
				_errHandler.sync(this);
				_la = _input.LA(1);
			} while ( (((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << SI) | (1L << MIENTRAS) | (1L << RUPTURA) | (1L << IDENTIFICADOR))) != 0) );
			setState(257);
			match(FMIENTRAS);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class InstruccionContext extends ParserRuleContext {
		public AsignacionContext asignacion() {
			return getRuleContext(AsignacionContext.class,0);
		}
		public CondicionalContext condicional() {
			return getRuleContext(CondicionalContext.class,0);
		}
		public IteracionContext iteracion() {
			return getRuleContext(IteracionContext.class,0);
		}
		public InstruccionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_instruccion; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof PSintListener ) ((PSintListener)listener).enterInstruccion(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof PSintListener ) ((PSintListener)listener).exitInstruccion(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof PSintVisitor ) return ((PSintVisitor<? extends T>)visitor).visitInstruccion(this);
			else return visitor.visitChildren(this);
		}
	}

	public final InstruccionContext instruccion() throws RecognitionException {
		InstruccionContext _localctx = new InstruccionContext(_ctx, getState());
		enterRule(_localctx, 34, RULE_instruccion);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(262);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case IDENTIFICADOR:
				{
				setState(259);
				asignacion();
				}
				break;
			case SI:
				{
				setState(260);
				condicional();
				}
				break;
			case MIENTRAS:
				{
				setState(261);
				iteracion();
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Instrucciones_programaContext extends ParserRuleContext {
		public TerminalNode INSTRUCCIONES() { return getToken(PSint.INSTRUCCIONES, 0); }
		public List<InstruccionContext> instruccion() {
			return getRuleContexts(InstruccionContext.class);
		}
		public InstruccionContext instruccion(int i) {
			return getRuleContext(InstruccionContext.class,i);
		}
		public Instrucciones_programaContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_instrucciones_programa; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof PSintListener ) ((PSintListener)listener).enterInstrucciones_programa(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof PSintListener ) ((PSintListener)listener).exitInstrucciones_programa(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof PSintVisitor ) return ((PSintVisitor<? extends T>)visitor).visitInstrucciones_programa(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Instrucciones_programaContext instrucciones_programa() throws RecognitionException {
		Instrucciones_programaContext _localctx = new Instrucciones_programaContext(_ctx, getState());
		enterRule(_localctx, 36, RULE_instrucciones_programa);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(264);
			match(INSTRUCCIONES);
			setState(266); 
			_errHandler.sync(this);
			_la = _input.LA(1);
			do {
				{
				{
				setState(265);
				instruccion();
				}
				}
				setState(268); 
				_errHandler.sync(this);
				_la = _input.LA(1);
			} while ( (((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << SI) | (1L << MIENTRAS) | (1L << IDENTIFICADOR))) != 0) );
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ParametroContext extends ParserRuleContext {
		public TipoContext tipo() {
			return getRuleContext(TipoContext.class,0);
		}
		public TerminalNode IDENTIFICADOR() { return getToken(PSint.IDENTIFICADOR, 0); }
		public ParametroContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_parametro; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof PSintListener ) ((PSintListener)listener).enterParametro(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof PSintListener ) ((PSintListener)listener).exitParametro(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof PSintVisitor ) return ((PSintVisitor<? extends T>)visitor).visitParametro(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ParametroContext parametro() throws RecognitionException {
		ParametroContext _localctx = new ParametroContext(_ctx, getState());
		enterRule(_localctx, 38, RULE_parametro);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(270);
			tipo();
			setState(271);
			match(IDENTIFICADOR);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ParametrosContext extends ParserRuleContext {
		public List<ParametroContext> parametro() {
			return getRuleContexts(ParametroContext.class);
		}
		public ParametroContext parametro(int i) {
			return getRuleContext(ParametroContext.class,i);
		}
		public List<TerminalNode> COMA() { return getTokens(PSint.COMA); }
		public TerminalNode COMA(int i) {
			return getToken(PSint.COMA, i);
		}
		public ParametrosContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_parametros; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof PSintListener ) ((PSintListener)listener).enterParametros(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof PSintListener ) ((PSintListener)listener).exitParametros(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof PSintVisitor ) return ((PSintVisitor<? extends T>)visitor).visitParametros(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ParametrosContext parametros() throws RecognitionException {
		ParametrosContext _localctx = new ParametrosContext(_ctx, getState());
		enterRule(_localctx, 40, RULE_parametros);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(273);
			parametro();
			setState(278);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==COMA) {
				{
				{
				setState(274);
				match(COMA);
				setState(275);
				parametro();
				}
				}
				setState(280);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class DevolverContext extends ParserRuleContext {
		public TerminalNode DEV() { return getToken(PSint.DEV, 0); }
		public List<ExprContext> expr() {
			return getRuleContexts(ExprContext.class);
		}
		public ExprContext expr(int i) {
			return getRuleContext(ExprContext.class,i);
		}
		public List<TerminalNode> COMA() { return getTokens(PSint.COMA); }
		public TerminalNode COMA(int i) {
			return getToken(PSint.COMA, i);
		}
		public DevolverContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_devolver; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof PSintListener ) ((PSintListener)listener).enterDevolver(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof PSintListener ) ((PSintListener)listener).exitDevolver(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof PSintVisitor ) return ((PSintVisitor<? extends T>)visitor).visitDevolver(this);
			else return visitor.visitChildren(this);
		}
	}

	public final DevolverContext devolver() throws RecognitionException {
		DevolverContext _localctx = new DevolverContext(_ctx, getState());
		enterRule(_localctx, 42, RULE_devolver);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(281);
			match(DEV);
			setState(282);
			expr();
			setState(287);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==COMA) {
				{
				{
				setState(283);
				match(COMA);
				setState(284);
				expr();
				}
				}
				setState(289);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Instrucciones_funcionContext extends ParserRuleContext {
		public TerminalNode INSTRUCCIONES() { return getToken(PSint.INSTRUCCIONES, 0); }
		public List<InstruccionContext> instruccion() {
			return getRuleContexts(InstruccionContext.class);
		}
		public InstruccionContext instruccion(int i) {
			return getRuleContext(InstruccionContext.class,i);
		}
		public List<DevolverContext> devolver() {
			return getRuleContexts(DevolverContext.class);
		}
		public DevolverContext devolver(int i) {
			return getRuleContext(DevolverContext.class,i);
		}
		public Instrucciones_funcionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_instrucciones_funcion; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof PSintListener ) ((PSintListener)listener).enterInstrucciones_funcion(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof PSintListener ) ((PSintListener)listener).exitInstrucciones_funcion(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof PSintVisitor ) return ((PSintVisitor<? extends T>)visitor).visitInstrucciones_funcion(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Instrucciones_funcionContext instrucciones_funcion() throws RecognitionException {
		Instrucciones_funcionContext _localctx = new Instrucciones_funcionContext(_ctx, getState());
		enterRule(_localctx, 44, RULE_instrucciones_funcion);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(290);
			match(INSTRUCCIONES);
			setState(293); 
			_errHandler.sync(this);
			_la = _input.LA(1);
			do {
				{
				setState(293);
				_errHandler.sync(this);
				switch (_input.LA(1)) {
				case SI:
				case MIENTRAS:
				case IDENTIFICADOR:
					{
					setState(291);
					instruccion();
					}
					break;
				case DEV:
					{
					setState(292);
					devolver();
					}
					break;
				default:
					throw new NoViableAltException(this);
				}
				}
				setState(295); 
				_errHandler.sync(this);
				_la = _input.LA(1);
			} while ( (((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << SI) | (1L << DEV) | (1L << MIENTRAS) | (1L << IDENTIFICADOR))) != 0) );
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Instrucciones_procedimientoContext extends ParserRuleContext {
		public TerminalNode INSTRUCCIONES() { return getToken(PSint.INSTRUCCIONES, 0); }
		public List<InstruccionContext> instruccion() {
			return getRuleContexts(InstruccionContext.class);
		}
		public InstruccionContext instruccion(int i) {
			return getRuleContext(InstruccionContext.class,i);
		}
		public List<DevolverContext> devolver() {
			return getRuleContexts(DevolverContext.class);
		}
		public DevolverContext devolver(int i) {
			return getRuleContext(DevolverContext.class,i);
		}
		public Instrucciones_procedimientoContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_instrucciones_procedimiento; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof PSintListener ) ((PSintListener)listener).enterInstrucciones_procedimiento(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof PSintListener ) ((PSintListener)listener).exitInstrucciones_procedimiento(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof PSintVisitor ) return ((PSintVisitor<? extends T>)visitor).visitInstrucciones_procedimiento(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Instrucciones_procedimientoContext instrucciones_procedimiento() throws RecognitionException {
		Instrucciones_procedimientoContext _localctx = new Instrucciones_procedimientoContext(_ctx, getState());
		enterRule(_localctx, 46, RULE_instrucciones_procedimiento);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(297);
			match(INSTRUCCIONES);
			setState(300); 
			_errHandler.sync(this);
			_la = _input.LA(1);
			do {
				{
				setState(300);
				_errHandler.sync(this);
				switch (_input.LA(1)) {
				case SI:
				case MIENTRAS:
				case IDENTIFICADOR:
					{
					setState(298);
					instruccion();
					}
					break;
				case DEV:
					{
					setState(299);
					devolver();
					}
					break;
				default:
					throw new NoViableAltException(this);
				}
				}
				setState(302); 
				_errHandler.sync(this);
				_la = _input.LA(1);
			} while ( (((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << SI) | (1L << DEV) | (1L << MIENTRAS) | (1L << IDENTIFICADOR))) != 0) );
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Def_funcContext extends ParserRuleContext {
		public TerminalNode FUNCION() { return getToken(PSint.FUNCION, 0); }
		public TerminalNode IDENTIFICADOR() { return getToken(PSint.IDENTIFICADOR, 0); }
		public List<TerminalNode> INICIO_PARENTESIS() { return getTokens(PSint.INICIO_PARENTESIS); }
		public TerminalNode INICIO_PARENTESIS(int i) {
			return getToken(PSint.INICIO_PARENTESIS, i);
		}
		public List<TerminalNode> FIN_PARENTESIS() { return getTokens(PSint.FIN_PARENTESIS); }
		public TerminalNode FIN_PARENTESIS(int i) {
			return getToken(PSint.FIN_PARENTESIS, i);
		}
		public TerminalNode DEV() { return getToken(PSint.DEV, 0); }
		public List<ParametrosContext> parametros() {
			return getRuleContexts(ParametrosContext.class);
		}
		public ParametrosContext parametros(int i) {
			return getRuleContext(ParametrosContext.class,i);
		}
		public VariablesContext variables() {
			return getRuleContext(VariablesContext.class,0);
		}
		public Instrucciones_funcionContext instrucciones_funcion() {
			return getRuleContext(Instrucciones_funcionContext.class,0);
		}
		public TerminalNode FFUNCION() { return getToken(PSint.FFUNCION, 0); }
		public Def_funcContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_def_func; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof PSintListener ) ((PSintListener)listener).enterDef_func(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof PSintListener ) ((PSintListener)listener).exitDef_func(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof PSintVisitor ) return ((PSintVisitor<? extends T>)visitor).visitDef_func(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Def_funcContext def_func() throws RecognitionException {
		Def_funcContext _localctx = new Def_funcContext(_ctx, getState());
		enterRule(_localctx, 48, RULE_def_func);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(304);
			match(FUNCION);
			setState(305);
			match(IDENTIFICADOR);
			setState(306);
			match(INICIO_PARENTESIS);
			setState(308);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << NUM) | (1L << LOG) | (1L << SEQ_NUM) | (1L << SEQ_LOG))) != 0)) {
				{
				setState(307);
				parametros();
				}
			}

			setState(310);
			match(FIN_PARENTESIS);
			setState(311);
			match(DEV);
			setState(312);
			match(INICIO_PARENTESIS);
			setState(313);
			parametros();
			setState(314);
			match(FIN_PARENTESIS);
			setState(315);
			variables();
			setState(316);
			instrucciones_funcion();
			setState(317);
			match(FFUNCION);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Def_procContext extends ParserRuleContext {
		public TerminalNode PROCEDIMIENTO() { return getToken(PSint.PROCEDIMIENTO, 0); }
		public TerminalNode IDENTIFICADOR() { return getToken(PSint.IDENTIFICADOR, 0); }
		public TerminalNode INICIO_PARENTESIS() { return getToken(PSint.INICIO_PARENTESIS, 0); }
		public TerminalNode FIN_PARENTESIS() { return getToken(PSint.FIN_PARENTESIS, 0); }
		public VariablesContext variables() {
			return getRuleContext(VariablesContext.class,0);
		}
		public Instrucciones_procedimientoContext instrucciones_procedimiento() {
			return getRuleContext(Instrucciones_procedimientoContext.class,0);
		}
		public TerminalNode FPROCEDIMIENTO() { return getToken(PSint.FPROCEDIMIENTO, 0); }
		public ParametrosContext parametros() {
			return getRuleContext(ParametrosContext.class,0);
		}
		public Def_procContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_def_proc; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof PSintListener ) ((PSintListener)listener).enterDef_proc(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof PSintListener ) ((PSintListener)listener).exitDef_proc(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof PSintVisitor ) return ((PSintVisitor<? extends T>)visitor).visitDef_proc(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Def_procContext def_proc() throws RecognitionException {
		Def_procContext _localctx = new Def_procContext(_ctx, getState());
		enterRule(_localctx, 50, RULE_def_proc);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(319);
			match(PROCEDIMIENTO);
			setState(320);
			match(IDENTIFICADOR);
			setState(321);
			match(INICIO_PARENTESIS);
			setState(323);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << NUM) | (1L << LOG) | (1L << SEQ_NUM) | (1L << SEQ_LOG))) != 0)) {
				{
				setState(322);
				parametros();
				}
			}

			setState(325);
			match(FIN_PARENTESIS);
			setState(326);
			variables();
			setState(327);
			instrucciones_procedimiento();
			setState(328);
			match(FPROCEDIMIENTO);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class SubprogramasContext extends ParserRuleContext {
		public TerminalNode SUBPROGRAMAS() { return getToken(PSint.SUBPROGRAMAS, 0); }
		public List<Def_funcContext> def_func() {
			return getRuleContexts(Def_funcContext.class);
		}
		public Def_funcContext def_func(int i) {
			return getRuleContext(Def_funcContext.class,i);
		}
		public List<Def_procContext> def_proc() {
			return getRuleContexts(Def_procContext.class);
		}
		public Def_procContext def_proc(int i) {
			return getRuleContext(Def_procContext.class,i);
		}
		public SubprogramasContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_subprogramas; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof PSintListener ) ((PSintListener)listener).enterSubprogramas(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof PSintListener ) ((PSintListener)listener).exitSubprogramas(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof PSintVisitor ) return ((PSintVisitor<? extends T>)visitor).visitSubprogramas(this);
			else return visitor.visitChildren(this);
		}
	}

	public final SubprogramasContext subprogramas() throws RecognitionException {
		SubprogramasContext _localctx = new SubprogramasContext(_ctx, getState());
		enterRule(_localctx, 52, RULE_subprogramas);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(330);
			match(SUBPROGRAMAS);
			setState(335);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==FUNCION || _la==PROCEDIMIENTO) {
				{
				setState(333);
				_errHandler.sync(this);
				switch (_input.LA(1)) {
				case FUNCION:
					{
					setState(331);
					def_func();
					}
					break;
				case PROCEDIMIENTO:
					{
					setState(332);
					def_proc();
					}
					break;
				default:
					throw new NoViableAltException(this);
				}
				}
				setState(337);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public boolean sempred(RuleContext _localctx, int ruleIndex, int predIndex) {
		switch (ruleIndex) {
		case 5:
			return expr_entera_sempred((Expr_enteraContext)_localctx, predIndex);
		case 13:
			return condicion_completa_sempred((Condicion_completaContext)_localctx, predIndex);
		}
		return true;
	}
	private boolean expr_entera_sempred(Expr_enteraContext _localctx, int predIndex) {
		switch (predIndex) {
		case 0:
			return precpred(_ctx, 8);
		case 1:
			return precpred(_ctx, 7);
		case 2:
			return precpred(_ctx, 6);
		}
		return true;
	}
	private boolean condicion_completa_sempred(Condicion_completaContext _localctx, int predIndex) {
		switch (predIndex) {
		case 3:
			return precpred(_ctx, 5);
		case 4:
			return precpred(_ctx, 4);
		}
		return true;
	}

	public static final String _serializedATN =
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\3<\u0155\4\2\t\2\4"+
		"\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13\t"+
		"\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22\t\22"+
		"\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\4\27\t\27\4\30\t\30\4\31\t\31"+
		"\4\32\t\32\4\33\t\33\4\34\t\34\3\2\3\2\3\2\3\2\3\2\3\2\3\3\3\3\3\4\3\4"+
		"\3\4\3\4\7\4E\n\4\f\4\16\4H\13\4\3\5\3\5\3\5\7\5M\n\5\f\5\16\5P\13\5\3"+
		"\5\3\5\3\5\3\6\3\6\3\6\3\6\3\6\7\6Z\n\6\f\6\16\6]\13\6\5\6_\n\6\3\6\3"+
		"\6\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\5\7l\n\7\3\7\3\7\3\7\3\7\3\7\3"+
		"\7\3\7\3\7\3\7\7\7w\n\7\f\7\16\7z\13\7\3\b\3\b\3\t\3\t\3\t\3\t\3\t\3\n"+
		"\3\n\5\n\u0085\n\n\3\n\3\n\3\n\5\n\u008a\n\n\7\n\u008c\n\n\f\n\16\n\u008f"+
		"\13\n\3\13\3\13\3\13\3\13\3\13\3\13\5\13\u0097\n\13\3\f\3\f\3\f\5\f\u009c"+
		"\n\f\3\r\3\r\3\r\7\r\u00a1\n\r\f\r\16\r\u00a4\13\r\3\r\3\r\3\r\3\r\7\r"+
		"\u00aa\n\r\f\r\16\r\u00ad\13\r\3\r\3\r\3\16\3\16\3\16\3\16\3\16\3\16\3"+
		"\16\3\16\3\16\3\16\3\16\3\16\3\16\3\16\3\16\3\16\3\16\3\16\3\16\3\16\3"+
		"\16\3\16\3\16\3\16\3\16\3\16\5\16\u00cb\n\16\3\17\3\17\3\17\3\17\3\17"+
		"\3\17\3\17\3\17\5\17\u00d5\n\17\3\17\3\17\3\17\3\17\3\17\3\17\7\17\u00dd"+
		"\n\17\f\17\16\17\u00e0\13\17\3\20\3\20\3\20\3\20\3\20\3\20\6\20\u00e8"+
		"\n\20\r\20\16\20\u00e9\3\20\3\20\6\20\u00ee\n\20\r\20\16\20\u00ef\5\20"+
		"\u00f2\n\20\3\20\3\20\3\21\3\21\3\21\3\22\3\22\3\22\3\22\3\22\3\22\3\22"+
		"\6\22\u0100\n\22\r\22\16\22\u0101\3\22\3\22\3\23\3\23\3\23\5\23\u0109"+
		"\n\23\3\24\3\24\6\24\u010d\n\24\r\24\16\24\u010e\3\25\3\25\3\25\3\26\3"+
		"\26\3\26\7\26\u0117\n\26\f\26\16\26\u011a\13\26\3\27\3\27\3\27\3\27\7"+
		"\27\u0120\n\27\f\27\16\27\u0123\13\27\3\30\3\30\3\30\6\30\u0128\n\30\r"+
		"\30\16\30\u0129\3\31\3\31\3\31\6\31\u012f\n\31\r\31\16\31\u0130\3\32\3"+
		"\32\3\32\3\32\5\32\u0137\n\32\3\32\3\32\3\32\3\32\3\32\3\32\3\32\3\32"+
		"\3\32\3\33\3\33\3\33\3\33\5\33\u0146\n\33\3\33\3\33\3\33\3\33\3\33\3\34"+
		"\3\34\3\34\7\34\u0150\n\34\f\34\16\34\u0153\13\34\3\34\2\4\f\34\35\2\4"+
		"\6\b\n\f\16\20\22\24\26\30\32\34\36 \"$&(*,.\60\62\64\66\2\4\3\2\n\r\3"+
		"\2\17\20\2\u0169\28\3\2\2\2\4>\3\2\2\2\6@\3\2\2\2\bI\3\2\2\2\nT\3\2\2"+
		"\2\fk\3\2\2\2\16{\3\2\2\2\20}\3\2\2\2\22\u0084\3\2\2\2\24\u0096\3\2\2"+
		"\2\26\u009b\3\2\2\2\30\u009d\3\2\2\2\32\u00ca\3\2\2\2\34\u00d4\3\2\2\2"+
		"\36\u00e1\3\2\2\2 \u00f5\3\2\2\2\"\u00f8\3\2\2\2$\u0108\3\2\2\2&\u010a"+
		"\3\2\2\2(\u0110\3\2\2\2*\u0113\3\2\2\2,\u011b\3\2\2\2.\u0124\3\2\2\2\60"+
		"\u012b\3\2\2\2\62\u0132\3\2\2\2\64\u0141\3\2\2\2\66\u014c\3\2\2\289\7"+
		"\6\2\29:\5\6\4\2:;\5\66\34\2;<\5&\24\2<=\7\2\2\3=\3\3\2\2\2>?\t\2\2\2"+
		"?\5\3\2\2\2@F\7\7\2\2AB\5\b\5\2BC\7\33\2\2CE\3\2\2\2DA\3\2\2\2EH\3\2\2"+
		"\2FD\3\2\2\2FG\3\2\2\2G\7\3\2\2\2HF\3\2\2\2IN\7:\2\2JK\7\35\2\2KM\7:\2"+
		"\2LJ\3\2\2\2MP\3\2\2\2NL\3\2\2\2NO\3\2\2\2OQ\3\2\2\2PN\3\2\2\2QR\7\34"+
		"\2\2RS\5\4\3\2S\t\3\2\2\2TU\7:\2\2U^\7\24\2\2V[\5\26\f\2WX\7\35\2\2XZ"+
		"\5\26\f\2YW\3\2\2\2Z]\3\2\2\2[Y\3\2\2\2[\\\3\2\2\2\\_\3\2\2\2][\3\2\2"+
		"\2^V\3\2\2\2^_\3\2\2\2_`\3\2\2\2`a\7\25\2\2a\13\3\2\2\2bc\b\7\1\2cd\7"+
		"\24\2\2de\5\f\7\2ef\7\25\2\2fl\3\2\2\2gl\7:\2\2hl\79\2\2il\5\n\6\2jl\5"+
		"\20\t\2kb\3\2\2\2kg\3\2\2\2kh\3\2\2\2ki\3\2\2\2kj\3\2\2\2lx\3\2\2\2mn"+
		"\f\n\2\2no\7\21\2\2ow\5\f\7\13pq\f\t\2\2qr\7\22\2\2rw\5\f\7\nst\f\b\2"+
		"\2tu\7\23\2\2uw\5\f\7\tvm\3\2\2\2vp\3\2\2\2vs\3\2\2\2wz\3\2\2\2xv\3\2"+
		"\2\2xy\3\2\2\2y\r\3\2\2\2zx\3\2\2\2{|\t\3\2\2|\17\3\2\2\2}~\7:\2\2~\177"+
		"\7\26\2\2\177\u0080\5\f\7\2\u0080\u0081\7\27\2\2\u0081\21\3\2\2\2\u0082"+
		"\u0085\5\f\7\2\u0083\u0085\5\16\b\2\u0084\u0082\3\2\2\2\u0084\u0083\3"+
		"\2\2\2\u0085\u008d\3\2\2\2\u0086\u0089\7\35\2\2\u0087\u008a\5\f\7\2\u0088"+
		"\u008a\5\16\b\2\u0089\u0087\3\2\2\2\u0089\u0088\3\2\2\2\u008a\u008c\3"+
		"\2\2\2\u008b\u0086\3\2\2\2\u008c\u008f\3\2\2\2\u008d\u008b\3\2\2\2\u008d"+
		"\u008e\3\2\2\2\u008e\23\3\2\2\2\u008f\u008d\3\2\2\2\u0090\u0091\7\26\2"+
		"\2\u0091\u0092\5\22\n\2\u0092\u0093\7\27\2\2\u0093\u0097\3\2\2\2\u0094"+
		"\u0095\7\26\2\2\u0095\u0097\7\27\2\2\u0096\u0090\3\2\2\2\u0096\u0094\3"+
		"\2\2\2\u0097\25\3\2\2\2\u0098\u009c\5\f\7\2\u0099\u009c\5\16\b\2\u009a"+
		"\u009c\5\24\13\2\u009b\u0098\3\2\2\2\u009b\u0099\3\2\2\2\u009b\u009a\3"+
		"\2\2\2\u009c\27\3\2\2\2\u009d\u00a2\7:\2\2\u009e\u009f\7\35\2\2\u009f"+
		"\u00a1\7:\2\2\u00a0\u009e\3\2\2\2\u00a1\u00a4\3\2\2\2\u00a2\u00a0\3\2"+
		"\2\2\u00a2\u00a3\3\2\2\2\u00a3\u00a5\3\2\2\2\u00a4\u00a2\3\2\2\2\u00a5"+
		"\u00a6\7\32\2\2\u00a6\u00ab\5\26\f\2\u00a7\u00a8\7\35\2\2\u00a8\u00aa"+
		"\5\26\f\2\u00a9\u00a7\3\2\2\2\u00aa\u00ad\3\2\2\2\u00ab\u00a9\3\2\2\2"+
		"\u00ab\u00ac\3\2\2\2\u00ac\u00ae\3\2\2\2\u00ad\u00ab\3\2\2\2\u00ae\u00af"+
		"\7\33\2\2\u00af\31\3\2\2\2\u00b0\u00b1\5\26\f\2\u00b1\u00b2\7\36\2\2\u00b2"+
		"\u00b3\5\26\f\2\u00b3\u00cb\3\2\2\2\u00b4\u00b5\5\26\f\2\u00b5\u00b6\7"+
		"\37\2\2\u00b6\u00b7\5\26\f\2\u00b7\u00cb\3\2\2\2\u00b8\u00b9\5\26\f\2"+
		"\u00b9\u00ba\7 \2\2\u00ba\u00bb\5\26\f\2\u00bb\u00cb\3\2\2\2\u00bc\u00bd"+
		"\5\26\f\2\u00bd\u00be\7!\2\2\u00be\u00bf\5\26\f\2\u00bf\u00cb\3\2\2\2"+
		"\u00c0\u00c1\5\26\f\2\u00c1\u00c2\7\"\2\2\u00c2\u00c3\5\26\f\2\u00c3\u00cb"+
		"\3\2\2\2\u00c4\u00c5\5\26\f\2\u00c5\u00c6\7#\2\2\u00c6\u00c7\5\26\f\2"+
		"\u00c7\u00cb\3\2\2\2\u00c8\u00cb\7\'\2\2\u00c9\u00cb\7(\2\2\u00ca\u00b0"+
		"\3\2\2\2\u00ca\u00b4\3\2\2\2\u00ca\u00b8\3\2\2\2\u00ca\u00bc\3\2\2\2\u00ca"+
		"\u00c0\3\2\2\2\u00ca\u00c4\3\2\2\2\u00ca\u00c8\3\2\2\2\u00ca\u00c9\3\2"+
		"\2\2\u00cb\33\3\2\2\2\u00cc\u00cd\b\17\1\2\u00cd\u00ce\7\24\2\2\u00ce"+
		"\u00cf\5\34\17\2\u00cf\u00d0\7\25\2\2\u00d0\u00d5\3\2\2\2\u00d1\u00d2"+
		"\7&\2\2\u00d2\u00d5\5\34\17\4\u00d3\u00d5\5\32\16\2\u00d4\u00cc\3\2\2"+
		"\2\u00d4\u00d1\3\2\2\2\u00d4\u00d3\3\2\2\2\u00d5\u00de\3\2\2\2\u00d6\u00d7"+
		"\f\7\2\2\u00d7\u00d8\7$\2\2\u00d8\u00dd\5\34\17\b\u00d9\u00da\f\6\2\2"+
		"\u00da\u00db\7%\2\2\u00db\u00dd\5\34\17\7\u00dc\u00d6\3\2\2\2\u00dc\u00d9"+
		"\3\2\2\2\u00dd\u00e0\3\2\2\2\u00de\u00dc\3\2\2\2\u00de\u00df\3\2\2\2\u00df"+
		"\35\3\2\2\2\u00e0\u00de\3\2\2\2\u00e1\u00e2\7+\2\2\u00e2\u00e3\7\24\2"+
		"\2\u00e3\u00e4\5\34\17\2\u00e4\u00e5\7\25\2\2\u00e5\u00e7\7-\2\2\u00e6"+
		"\u00e8\5$\23\2\u00e7\u00e6\3\2\2\2\u00e8\u00e9\3\2\2\2\u00e9\u00e7\3\2"+
		"\2\2\u00e9\u00ea\3\2\2\2\u00ea\u00f1\3\2\2\2\u00eb\u00ed\7,\2\2\u00ec"+
		"\u00ee\5$\23\2\u00ed\u00ec\3\2\2\2\u00ee\u00ef\3\2\2\2\u00ef\u00ed\3\2"+
		"\2\2\u00ef\u00f0\3\2\2\2\u00f0\u00f2\3\2\2\2\u00f1\u00eb\3\2\2\2\u00f1"+
		"\u00f2\3\2\2\2\u00f2\u00f3\3\2\2\2\u00f3\u00f4\7/\2\2\u00f4\37\3\2\2\2"+
		"\u00f5\u00f6\7\63\2\2\u00f6\u00f7\7\33\2\2\u00f7!\3\2\2\2\u00f8\u00f9"+
		"\7\61\2\2\u00f9\u00fa\7\24\2\2\u00fa\u00fb\5\34\17\2\u00fb\u00fc\7\25"+
		"\2\2\u00fc\u00ff\7\62\2\2\u00fd\u0100\5$\23\2\u00fe\u0100\5 \21\2\u00ff"+
		"\u00fd\3\2\2\2\u00ff\u00fe\3\2\2\2\u0100\u0101\3\2\2\2\u0101\u00ff\3\2"+
		"\2\2\u0101\u0102\3\2\2\2\u0102\u0103\3\2\2\2\u0103\u0104\7\60\2\2\u0104"+
		"#\3\2\2\2\u0105\u0109\5\30\r\2\u0106\u0109\5\36\20\2\u0107\u0109\5\"\22"+
		"\2\u0108\u0105\3\2\2\2\u0108\u0106\3\2\2\2\u0108\u0107\3\2\2\2\u0109%"+
		"\3\2\2\2\u010a\u010c\7\t\2\2\u010b\u010d\5$\23\2\u010c\u010b\3\2\2\2\u010d"+
		"\u010e\3\2\2\2\u010e\u010c\3\2\2\2\u010e\u010f\3\2\2\2\u010f\'\3\2\2\2"+
		"\u0110\u0111\5\4\3\2\u0111\u0112\7:\2\2\u0112)\3\2\2\2\u0113\u0118\5("+
		"\25\2\u0114\u0115\7\35\2\2\u0115\u0117\5(\25\2\u0116\u0114\3\2\2\2\u0117"+
		"\u011a\3\2\2\2\u0118\u0116\3\2\2\2\u0118\u0119\3\2\2\2\u0119+\3\2\2\2"+
		"\u011a\u0118\3\2\2\2\u011b\u011c\7.\2\2\u011c\u0121\5\26\f\2\u011d\u011e"+
		"\7\35\2\2\u011e\u0120\5\26\f\2\u011f\u011d\3\2\2\2\u0120\u0123\3\2\2\2"+
		"\u0121\u011f\3\2\2\2\u0121\u0122\3\2\2\2\u0122-\3\2\2\2\u0123\u0121\3"+
		"\2\2\2\u0124\u0127\7\t\2\2\u0125\u0128\5$\23\2\u0126\u0128\5,\27\2\u0127"+
		"\u0125\3\2\2\2\u0127\u0126\3\2\2\2\u0128\u0129\3\2\2\2\u0129\u0127\3\2"+
		"\2\2\u0129\u012a\3\2\2\2\u012a/\3\2\2\2\u012b\u012e\7\t\2\2\u012c\u012f"+
		"\5$\23\2\u012d\u012f\5,\27\2\u012e\u012c\3\2\2\2\u012e\u012d\3\2\2\2\u012f"+
		"\u0130\3\2\2\2\u0130\u012e\3\2\2\2\u0130\u0131\3\2\2\2\u0131\61\3\2\2"+
		"\2\u0132\u0133\7)\2\2\u0133\u0134\7:\2\2\u0134\u0136\7\24\2\2\u0135\u0137"+
		"\5*\26\2\u0136\u0135\3\2\2\2\u0136\u0137\3\2\2\2\u0137\u0138\3\2\2\2\u0138"+
		"\u0139\7\25\2\2\u0139\u013a\7.\2\2\u013a\u013b\7\24\2\2\u013b\u013c\5"+
		"*\26\2\u013c\u013d\7\25\2\2\u013d\u013e\5\6\4\2\u013e\u013f\5.\30\2\u013f"+
		"\u0140\7*\2\2\u0140\63\3\2\2\2\u0141\u0142\7\65\2\2\u0142\u0143\7:\2\2"+
		"\u0143\u0145\7\24\2\2\u0144\u0146\5*\26\2\u0145\u0144\3\2\2\2\u0145\u0146"+
		"\3\2\2\2\u0146\u0147\3\2\2\2\u0147\u0148\7\25\2\2\u0148\u0149\5\6\4\2"+
		"\u0149\u014a\5\60\31\2\u014a\u014b\7\66\2\2\u014b\65\3\2\2\2\u014c\u0151"+
		"\7\b\2\2\u014d\u0150\5\62\32\2\u014e\u0150\5\64\33\2\u014f\u014d\3\2\2"+
		"\2\u014f\u014e\3\2\2\2\u0150\u0153\3\2\2\2\u0151\u014f\3\2\2\2\u0151\u0152"+
		"\3\2\2\2\u0152\67\3\2\2\2\u0153\u0151\3\2\2\2%FN[^kvx\u0084\u0089\u008d"+
		"\u0096\u009b\u00a2\u00ab\u00ca\u00d4\u00dc\u00de\u00e9\u00ef\u00f1\u00ff"+
		"\u0101\u0108\u010e\u0118\u0121\u0127\u0129\u012e\u0130\u0136\u0145\u014f"+
		"\u0151";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}