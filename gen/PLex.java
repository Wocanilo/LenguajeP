// Generated from C:/Users/wocat/IdeaProjects/LenguajeP/src\PLex.g4 by ANTLR 4.8
import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.misc.*;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class PLex extends Lexer {
	static { RuntimeMetaData.checkVersion("4.8", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		BLANCO=1, TABULADOR=2, FIN_LINEA=3, PROGRAMA=4, VARIABLES=5, SUBPROGRAMAS=6, 
		INSTRUCCIONES=7, NUM=8, LOG=9, SEQ_NUM=10, SEQ_LOG=11, TRUE=12, FALSE=13, 
		MAS=14, MENOS=15, POR=16, INICIO_PARENTESIS=17, FIN_PARENTESIS=18, INICIO_CORCHETE=19, 
		FIN_CORCHETE=20, INICIO_LLAVE=21, FIN_LLAVE=22, IGUAL=23, PyC=24, PyP=25, 
		COMA=26, IGUALDAD=27, DESIGUALDAD=28, MENOR_QUE=29, MAYOR_QUE=30, MAYOR_IGUAL_QUE=31, 
		MENOR_IGUAL_QUE=32, CONJUNCION=33, DISYUNCION=34, NEGACION=35, CIERTO=36, 
		FALSO=37, FUNCION=38, FFUNCION=39, SI=40, SINO=41, ENTONCES=42, DEV=43, 
		FSI=44, FMIENTRAS=45, MIENTRAS=46, HACER=47, RUPTURA=48, AVANCE=49, PROCEDIMIENTO=50, 
		FPROCEDIMIENTO=51, PARATODO=52, EXISTE=53, ENTERO=54, IDENTIFICADOR=55, 
		COMENTARIOS_LINEA=56, COMENTARIOS_BLOQUE=57;
	public static String[] channelNames = {
		"DEFAULT_TOKEN_CHANNEL", "HIDDEN"
	};

	public static String[] modeNames = {
		"DEFAULT_MODE"
	};

	private static String[] makeRuleNames() {
		return new String[] {
			"BLANCO", "TABULADOR", "FIN_LINEA", "DIGITO", "LETRA", "PROGRAMA", "VARIABLES", 
			"SUBPROGRAMAS", "INSTRUCCIONES", "NUM", "LOG", "SEQ_NUM", "SEQ_LOG", 
			"TRUE", "FALSE", "MAS", "MENOS", "POR", "INICIO_PARENTESIS", "FIN_PARENTESIS", 
			"INICIO_CORCHETE", "FIN_CORCHETE", "INICIO_LLAVE", "FIN_LLAVE", "IGUAL", 
			"PyC", "PyP", "COMA", "IGUALDAD", "DESIGUALDAD", "MENOR_QUE", "MAYOR_QUE", 
			"MAYOR_IGUAL_QUE", "MENOR_IGUAL_QUE", "CONJUNCION", "DISYUNCION", "NEGACION", 
			"CIERTO", "FALSO", "FUNCION", "FFUNCION", "SI", "SINO", "ENTONCES", "DEV", 
			"FSI", "FMIENTRAS", "MIENTRAS", "HACER", "RUPTURA", "AVANCE", "PROCEDIMIENTO", 
			"FPROCEDIMIENTO", "PARATODO", "EXISTE", "ENTERO", "IDENTIFICADOR", "COMENTARIOS_LINEA", 
			"COMENTARIOS_BLOQUE"
		};
	}
	public static final String[] ruleNames = makeRuleNames();

	private static String[] makeLiteralNames() {
		return new String[] {
			null, "' '", "'\t'", null, "'PROGRAMA'", "'VARIABLES'", "'SUBPROGRAMAS'", 
			"'INSTRUCCIONES'", "'NUM'", "'LOG'", "'SEQ(NUM)'", "'SEQ(LOG)'", "'T'", 
			"'F'", "'+'", "'-'", "'*'", "'('", "')'", "'['", "']'", "'{'", "'}'", 
			"'='", "';'", "':'", "','", "'=='", "'!='", "'<'", "'>'", "'>='", "'<='", 
			"'&&'", "'||'", "'!'", "'cierto'", "'falso'", "'FUNCION'", "'FFUNCION'", 
			"'si'", "'sino'", "'entonces'", "'dev'", "'fsi'", "'fmientras'", "'mientras'", 
			"'hacer'", "'ruptura'", "'avance'", "'PROCEDIMIENTO'", "'FPROCEDIMIENTO'", 
			"'PARATODO'", "'EXISTE'"
		};
	}
	private static final String[] _LITERAL_NAMES = makeLiteralNames();
	private static String[] makeSymbolicNames() {
		return new String[] {
			null, "BLANCO", "TABULADOR", "FIN_LINEA", "PROGRAMA", "VARIABLES", "SUBPROGRAMAS", 
			"INSTRUCCIONES", "NUM", "LOG", "SEQ_NUM", "SEQ_LOG", "TRUE", "FALSE", 
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


	public PLex(CharStream input) {
		super(input);
		_interp = new LexerATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@Override
	public String getGrammarFileName() { return "PLex.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public String[] getChannelNames() { return channelNames; }

	@Override
	public String[] getModeNames() { return modeNames; }

	@Override
	public ATN getATN() { return _ATN; }

	public static final String _serializedATN =
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\2;\u01c0\b\1\4\2\t"+
		"\2\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13"+
		"\t\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22\t\22"+
		"\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\4\27\t\27\4\30\t\30\4\31\t\31"+
		"\4\32\t\32\4\33\t\33\4\34\t\34\4\35\t\35\4\36\t\36\4\37\t\37\4 \t \4!"+
		"\t!\4\"\t\"\4#\t#\4$\t$\4%\t%\4&\t&\4\'\t\'\4(\t(\4)\t)\4*\t*\4+\t+\4"+
		",\t,\4-\t-\4.\t.\4/\t/\4\60\t\60\4\61\t\61\4\62\t\62\4\63\t\63\4\64\t"+
		"\64\4\65\t\65\4\66\t\66\4\67\t\67\48\t8\49\t9\4:\t:\4;\t;\4<\t<\3\2\3"+
		"\2\3\2\3\2\3\3\3\3\3\3\3\3\3\4\5\4\u0083\n\4\3\4\3\4\3\4\3\4\3\5\3\5\3"+
		"\6\5\6\u008c\n\6\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\b\3\b\3\b\3\b\3"+
		"\b\3\b\3\b\3\b\3\b\3\b\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t"+
		"\3\t\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3\13\3\13"+
		"\3\13\3\13\3\f\3\f\3\f\3\f\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\16\3"+
		"\16\3\16\3\16\3\16\3\16\3\16\3\16\3\16\3\17\3\17\3\20\3\20\3\21\3\21\3"+
		"\22\3\22\3\23\3\23\3\24\3\24\3\25\3\25\3\26\3\26\3\27\3\27\3\30\3\30\3"+
		"\31\3\31\3\32\3\32\3\33\3\33\3\34\3\34\3\35\3\35\3\36\3\36\3\36\3\37\3"+
		"\37\3\37\3 \3 \3!\3!\3\"\3\"\3\"\3#\3#\3#\3$\3$\3$\3%\3%\3%\3&\3&\3\'"+
		"\3\'\3\'\3\'\3\'\3\'\3\'\3(\3(\3(\3(\3(\3(\3)\3)\3)\3)\3)\3)\3)\3)\3*"+
		"\3*\3*\3*\3*\3*\3*\3*\3*\3+\3+\3+\3,\3,\3,\3,\3,\3-\3-\3-\3-\3-\3-\3-"+
		"\3-\3-\3.\3.\3.\3.\3/\3/\3/\3/\3\60\3\60\3\60\3\60\3\60\3\60\3\60\3\60"+
		"\3\60\3\60\3\61\3\61\3\61\3\61\3\61\3\61\3\61\3\61\3\61\3\62\3\62\3\62"+
		"\3\62\3\62\3\62\3\63\3\63\3\63\3\63\3\63\3\63\3\63\3\63\3\64\3\64\3\64"+
		"\3\64\3\64\3\64\3\64\3\65\3\65\3\65\3\65\3\65\3\65\3\65\3\65\3\65\3\65"+
		"\3\65\3\65\3\65\3\65\3\66\3\66\3\66\3\66\3\66\3\66\3\66\3\66\3\66\3\66"+
		"\3\66\3\66\3\66\3\66\3\66\3\67\3\67\3\67\3\67\3\67\3\67\3\67\3\67\3\67"+
		"\38\38\38\38\38\38\38\39\59\u0199\n9\39\69\u019c\n9\r9\169\u019d\3:\3"+
		":\3:\7:\u01a3\n:\f:\16:\u01a6\13:\3;\3;\3;\3;\7;\u01ac\n;\f;\16;\u01af"+
		"\13;\3;\3;\3<\3<\3<\3<\7<\u01b7\n<\f<\16<\u01ba\13<\3<\3<\3<\3<\3<\4\u01ad"+
		"\u01b8\2=\3\3\5\4\7\5\t\2\13\2\r\6\17\7\21\b\23\t\25\n\27\13\31\f\33\r"+
		"\35\16\37\17!\20#\21%\22\'\23)\24+\25-\26/\27\61\30\63\31\65\32\67\33"+
		"9\34;\35=\36?\37A C!E\"G#I$K%M&O\'Q(S)U*W+Y,[-]._/a\60c\61e\62g\63i\64"+
		"k\65m\66o\67q8s9u:w;\3\2\4\3\2\62;\5\2//aac|\2\u01c4\2\3\3\2\2\2\2\5\3"+
		"\2\2\2\2\7\3\2\2\2\2\r\3\2\2\2\2\17\3\2\2\2\2\21\3\2\2\2\2\23\3\2\2\2"+
		"\2\25\3\2\2\2\2\27\3\2\2\2\2\31\3\2\2\2\2\33\3\2\2\2\2\35\3\2\2\2\2\37"+
		"\3\2\2\2\2!\3\2\2\2\2#\3\2\2\2\2%\3\2\2\2\2\'\3\2\2\2\2)\3\2\2\2\2+\3"+
		"\2\2\2\2-\3\2\2\2\2/\3\2\2\2\2\61\3\2\2\2\2\63\3\2\2\2\2\65\3\2\2\2\2"+
		"\67\3\2\2\2\29\3\2\2\2\2;\3\2\2\2\2=\3\2\2\2\2?\3\2\2\2\2A\3\2\2\2\2C"+
		"\3\2\2\2\2E\3\2\2\2\2G\3\2\2\2\2I\3\2\2\2\2K\3\2\2\2\2M\3\2\2\2\2O\3\2"+
		"\2\2\2Q\3\2\2\2\2S\3\2\2\2\2U\3\2\2\2\2W\3\2\2\2\2Y\3\2\2\2\2[\3\2\2\2"+
		"\2]\3\2\2\2\2_\3\2\2\2\2a\3\2\2\2\2c\3\2\2\2\2e\3\2\2\2\2g\3\2\2\2\2i"+
		"\3\2\2\2\2k\3\2\2\2\2m\3\2\2\2\2o\3\2\2\2\2q\3\2\2\2\2s\3\2\2\2\2u\3\2"+
		"\2\2\2w\3\2\2\2\3y\3\2\2\2\5}\3\2\2\2\7\u0082\3\2\2\2\t\u0088\3\2\2\2"+
		"\13\u008b\3\2\2\2\r\u008d\3\2\2\2\17\u0096\3\2\2\2\21\u00a0\3\2\2\2\23"+
		"\u00ad\3\2\2\2\25\u00bb\3\2\2\2\27\u00bf\3\2\2\2\31\u00c3\3\2\2\2\33\u00cc"+
		"\3\2\2\2\35\u00d5\3\2\2\2\37\u00d7\3\2\2\2!\u00d9\3\2\2\2#\u00db\3\2\2"+
		"\2%\u00dd\3\2\2\2\'\u00df\3\2\2\2)\u00e1\3\2\2\2+\u00e3\3\2\2\2-\u00e5"+
		"\3\2\2\2/\u00e7\3\2\2\2\61\u00e9\3\2\2\2\63\u00eb\3\2\2\2\65\u00ed\3\2"+
		"\2\2\67\u00ef\3\2\2\29\u00f1\3\2\2\2;\u00f3\3\2\2\2=\u00f6\3\2\2\2?\u00f9"+
		"\3\2\2\2A\u00fb\3\2\2\2C\u00fd\3\2\2\2E\u0100\3\2\2\2G\u0103\3\2\2\2I"+
		"\u0106\3\2\2\2K\u0109\3\2\2\2M\u010b\3\2\2\2O\u0112\3\2\2\2Q\u0118\3\2"+
		"\2\2S\u0120\3\2\2\2U\u0129\3\2\2\2W\u012c\3\2\2\2Y\u0131\3\2\2\2[\u013a"+
		"\3\2\2\2]\u013e\3\2\2\2_\u0142\3\2\2\2a\u014c\3\2\2\2c\u0155\3\2\2\2e"+
		"\u015b\3\2\2\2g\u0163\3\2\2\2i\u016a\3\2\2\2k\u0178\3\2\2\2m\u0187\3\2"+
		"\2\2o\u0190\3\2\2\2q\u0198\3\2\2\2s\u019f\3\2\2\2u\u01a7\3\2\2\2w\u01b2"+
		"\3\2\2\2yz\7\"\2\2z{\3\2\2\2{|\b\2\2\2|\4\3\2\2\2}~\7\13\2\2~\177\3\2"+
		"\2\2\177\u0080\b\3\2\2\u0080\6\3\2\2\2\u0081\u0083\7\17\2\2\u0082\u0081"+
		"\3\2\2\2\u0082\u0083\3\2\2\2\u0083\u0084\3\2\2\2\u0084\u0085\7\f\2\2\u0085"+
		"\u0086\3\2\2\2\u0086\u0087\b\4\2\2\u0087\b\3\2\2\2\u0088\u0089\t\2\2\2"+
		"\u0089\n\3\2\2\2\u008a\u008c\t\3\2\2\u008b\u008a\3\2\2\2\u008c\f\3\2\2"+
		"\2\u008d\u008e\7R\2\2\u008e\u008f\7T\2\2\u008f\u0090\7Q\2\2\u0090\u0091"+
		"\7I\2\2\u0091\u0092\7T\2\2\u0092\u0093\7C\2\2\u0093\u0094\7O\2\2\u0094"+
		"\u0095\7C\2\2\u0095\16\3\2\2\2\u0096\u0097\7X\2\2\u0097\u0098\7C\2\2\u0098"+
		"\u0099\7T\2\2\u0099\u009a\7K\2\2\u009a\u009b\7C\2\2\u009b\u009c\7D\2\2"+
		"\u009c\u009d\7N\2\2\u009d\u009e\7G\2\2\u009e\u009f\7U\2\2\u009f\20\3\2"+
		"\2\2\u00a0\u00a1\7U\2\2\u00a1\u00a2\7W\2\2\u00a2\u00a3\7D\2\2\u00a3\u00a4"+
		"\7R\2\2\u00a4\u00a5\7T\2\2\u00a5\u00a6\7Q\2\2\u00a6\u00a7\7I\2\2\u00a7"+
		"\u00a8\7T\2\2\u00a8\u00a9\7C\2\2\u00a9\u00aa\7O\2\2\u00aa\u00ab\7C\2\2"+
		"\u00ab\u00ac\7U\2\2\u00ac\22\3\2\2\2\u00ad\u00ae\7K\2\2\u00ae\u00af\7"+
		"P\2\2\u00af\u00b0\7U\2\2\u00b0\u00b1\7V\2\2\u00b1\u00b2\7T\2\2\u00b2\u00b3"+
		"\7W\2\2\u00b3\u00b4\7E\2\2\u00b4\u00b5\7E\2\2\u00b5\u00b6\7K\2\2\u00b6"+
		"\u00b7\7Q\2\2\u00b7\u00b8\7P\2\2\u00b8\u00b9\7G\2\2\u00b9\u00ba\7U\2\2"+
		"\u00ba\24\3\2\2\2\u00bb\u00bc\7P\2\2\u00bc\u00bd\7W\2\2\u00bd\u00be\7"+
		"O\2\2\u00be\26\3\2\2\2\u00bf\u00c0\7N\2\2\u00c0\u00c1\7Q\2\2\u00c1\u00c2"+
		"\7I\2\2\u00c2\30\3\2\2\2\u00c3\u00c4\7U\2\2\u00c4\u00c5\7G\2\2\u00c5\u00c6"+
		"\7S\2\2\u00c6\u00c7\7*\2\2\u00c7\u00c8\7P\2\2\u00c8\u00c9\7W\2\2\u00c9"+
		"\u00ca\7O\2\2\u00ca\u00cb\7+\2\2\u00cb\32\3\2\2\2\u00cc\u00cd\7U\2\2\u00cd"+
		"\u00ce\7G\2\2\u00ce\u00cf\7S\2\2\u00cf\u00d0\7*\2\2\u00d0\u00d1\7N\2\2"+
		"\u00d1\u00d2\7Q\2\2\u00d2\u00d3\7I\2\2\u00d3\u00d4\7+\2\2\u00d4\34\3\2"+
		"\2\2\u00d5\u00d6\7V\2\2\u00d6\36\3\2\2\2\u00d7\u00d8\7H\2\2\u00d8 \3\2"+
		"\2\2\u00d9\u00da\7-\2\2\u00da\"\3\2\2\2\u00db\u00dc\7/\2\2\u00dc$\3\2"+
		"\2\2\u00dd\u00de\7,\2\2\u00de&\3\2\2\2\u00df\u00e0\7*\2\2\u00e0(\3\2\2"+
		"\2\u00e1\u00e2\7+\2\2\u00e2*\3\2\2\2\u00e3\u00e4\7]\2\2\u00e4,\3\2\2\2"+
		"\u00e5\u00e6\7_\2\2\u00e6.\3\2\2\2\u00e7\u00e8\7}\2\2\u00e8\60\3\2\2\2"+
		"\u00e9\u00ea\7\177\2\2\u00ea\62\3\2\2\2\u00eb\u00ec\7?\2\2\u00ec\64\3"+
		"\2\2\2\u00ed\u00ee\7=\2\2\u00ee\66\3\2\2\2\u00ef\u00f0\7<\2\2\u00f08\3"+
		"\2\2\2\u00f1\u00f2\7.\2\2\u00f2:\3\2\2\2\u00f3\u00f4\7?\2\2\u00f4\u00f5"+
		"\7?\2\2\u00f5<\3\2\2\2\u00f6\u00f7\7#\2\2\u00f7\u00f8\7?\2\2\u00f8>\3"+
		"\2\2\2\u00f9\u00fa\7>\2\2\u00fa@\3\2\2\2\u00fb\u00fc\7@\2\2\u00fcB\3\2"+
		"\2\2\u00fd\u00fe\7@\2\2\u00fe\u00ff\7?\2\2\u00ffD\3\2\2\2\u0100\u0101"+
		"\7>\2\2\u0101\u0102\7?\2\2\u0102F\3\2\2\2\u0103\u0104\7(\2\2\u0104\u0105"+
		"\7(\2\2\u0105H\3\2\2\2\u0106\u0107\7~\2\2\u0107\u0108\7~\2\2\u0108J\3"+
		"\2\2\2\u0109\u010a\7#\2\2\u010aL\3\2\2\2\u010b\u010c\7e\2\2\u010c\u010d"+
		"\7k\2\2\u010d\u010e\7g\2\2\u010e\u010f\7t\2\2\u010f\u0110\7v\2\2\u0110"+
		"\u0111\7q\2\2\u0111N\3\2\2\2\u0112\u0113\7h\2\2\u0113\u0114\7c\2\2\u0114"+
		"\u0115\7n\2\2\u0115\u0116\7u\2\2\u0116\u0117\7q\2\2\u0117P\3\2\2\2\u0118"+
		"\u0119\7H\2\2\u0119\u011a\7W\2\2\u011a\u011b\7P\2\2\u011b\u011c\7E\2\2"+
		"\u011c\u011d\7K\2\2\u011d\u011e\7Q\2\2\u011e\u011f\7P\2\2\u011fR\3\2\2"+
		"\2\u0120\u0121\7H\2\2\u0121\u0122\7H\2\2\u0122\u0123\7W\2\2\u0123\u0124"+
		"\7P\2\2\u0124\u0125\7E\2\2\u0125\u0126\7K\2\2\u0126\u0127\7Q\2\2\u0127"+
		"\u0128\7P\2\2\u0128T\3\2\2\2\u0129\u012a\7u\2\2\u012a\u012b\7k\2\2\u012b"+
		"V\3\2\2\2\u012c\u012d\7u\2\2\u012d\u012e\7k\2\2\u012e\u012f\7p\2\2\u012f"+
		"\u0130\7q\2\2\u0130X\3\2\2\2\u0131\u0132\7g\2\2\u0132\u0133\7p\2\2\u0133"+
		"\u0134\7v\2\2\u0134\u0135\7q\2\2\u0135\u0136\7p\2\2\u0136\u0137\7e\2\2"+
		"\u0137\u0138\7g\2\2\u0138\u0139\7u\2\2\u0139Z\3\2\2\2\u013a\u013b\7f\2"+
		"\2\u013b\u013c\7g\2\2\u013c\u013d\7x\2\2\u013d\\\3\2\2\2\u013e\u013f\7"+
		"h\2\2\u013f\u0140\7u\2\2\u0140\u0141\7k\2\2\u0141^\3\2\2\2\u0142\u0143"+
		"\7h\2\2\u0143\u0144\7o\2\2\u0144\u0145\7k\2\2\u0145\u0146\7g\2\2\u0146"+
		"\u0147\7p\2\2\u0147\u0148\7v\2\2\u0148\u0149\7t\2\2\u0149\u014a\7c\2\2"+
		"\u014a\u014b\7u\2\2\u014b`\3\2\2\2\u014c\u014d\7o\2\2\u014d\u014e\7k\2"+
		"\2\u014e\u014f\7g\2\2\u014f\u0150\7p\2\2\u0150\u0151\7v\2\2\u0151\u0152"+
		"\7t\2\2\u0152\u0153\7c\2\2\u0153\u0154\7u\2\2\u0154b\3\2\2\2\u0155\u0156"+
		"\7j\2\2\u0156\u0157\7c\2\2\u0157\u0158\7e\2\2\u0158\u0159\7g\2\2\u0159"+
		"\u015a\7t\2\2\u015ad\3\2\2\2\u015b\u015c\7t\2\2\u015c\u015d\7w\2\2\u015d"+
		"\u015e\7r\2\2\u015e\u015f\7v\2\2\u015f\u0160\7w\2\2\u0160\u0161\7t\2\2"+
		"\u0161\u0162\7c\2\2\u0162f\3\2\2\2\u0163\u0164\7c\2\2\u0164\u0165\7x\2"+
		"\2\u0165\u0166\7c\2\2\u0166\u0167\7p\2\2\u0167\u0168\7e\2\2\u0168\u0169"+
		"\7g\2\2\u0169h\3\2\2\2\u016a\u016b\7R\2\2\u016b\u016c\7T\2\2\u016c\u016d"+
		"\7Q\2\2\u016d\u016e\7E\2\2\u016e\u016f\7G\2\2\u016f\u0170\7F\2\2\u0170"+
		"\u0171\7K\2\2\u0171\u0172\7O\2\2\u0172\u0173\7K\2\2\u0173\u0174\7G\2\2"+
		"\u0174\u0175\7P\2\2\u0175\u0176\7V\2\2\u0176\u0177\7Q\2\2\u0177j\3\2\2"+
		"\2\u0178\u0179\7H\2\2\u0179\u017a\7R\2\2\u017a\u017b\7T\2\2\u017b\u017c"+
		"\7Q\2\2\u017c\u017d\7E\2\2\u017d\u017e\7G\2\2\u017e\u017f\7F\2\2\u017f"+
		"\u0180\7K\2\2\u0180\u0181\7O\2\2\u0181\u0182\7K\2\2\u0182\u0183\7G\2\2"+
		"\u0183\u0184\7P\2\2\u0184\u0185\7V\2\2\u0185\u0186\7Q\2\2\u0186l\3\2\2"+
		"\2\u0187\u0188\7R\2\2\u0188\u0189\7C\2\2\u0189\u018a\7T\2\2\u018a\u018b"+
		"\7C\2\2\u018b\u018c\7V\2\2\u018c\u018d\7Q\2\2\u018d\u018e\7F\2\2\u018e"+
		"\u018f\7Q\2\2\u018fn\3\2\2\2\u0190\u0191\7G\2\2\u0191\u0192\7Z\2\2\u0192"+
		"\u0193\7K\2\2\u0193\u0194\7U\2\2\u0194\u0195\7V\2\2\u0195\u0196\7G\2\2"+
		"\u0196p\3\2\2\2\u0197\u0199\7/\2\2\u0198\u0197\3\2\2\2\u0198\u0199\3\2"+
		"\2\2\u0199\u019b\3\2\2\2\u019a\u019c\5\t\5\2\u019b\u019a\3\2\2\2\u019c"+
		"\u019d\3\2\2\2\u019d\u019b\3\2\2\2\u019d\u019e\3\2\2\2\u019er\3\2\2\2"+
		"\u019f\u01a4\5\13\6\2\u01a0\u01a3\5\13\6\2\u01a1\u01a3\5\t\5\2\u01a2\u01a0"+
		"\3\2\2\2\u01a2\u01a1\3\2\2\2\u01a3\u01a6\3\2\2\2\u01a4\u01a2\3\2\2\2\u01a4"+
		"\u01a5\3\2\2\2\u01a5t\3\2\2\2\u01a6\u01a4\3\2\2\2\u01a7\u01a8\7\61\2\2"+
		"\u01a8\u01a9\7\61\2\2\u01a9\u01ad\3\2\2\2\u01aa\u01ac\13\2\2\2\u01ab\u01aa"+
		"\3\2\2\2\u01ac\u01af\3\2\2\2\u01ad\u01ae\3\2\2\2\u01ad\u01ab\3\2\2\2\u01ae"+
		"\u01b0\3\2\2\2\u01af\u01ad\3\2\2\2\u01b0\u01b1\b;\2\2\u01b1v\3\2\2\2\u01b2"+
		"\u01b3\7\61\2\2\u01b3\u01b4\7,\2\2\u01b4\u01b8\3\2\2\2\u01b5\u01b7\13"+
		"\2\2\2\u01b6\u01b5\3\2\2\2\u01b7\u01ba\3\2\2\2\u01b8\u01b9\3\2\2\2\u01b8"+
		"\u01b6\3\2\2\2\u01b9\u01bb\3\2\2\2\u01ba\u01b8\3\2\2\2\u01bb\u01bc\7,"+
		"\2\2\u01bc\u01bd\7\61\2\2\u01bd\u01be\3\2\2\2\u01be\u01bf\b<\2\2\u01bf"+
		"x\3\2\2\2\13\2\u0082\u008b\u0198\u019d\u01a2\u01a4\u01ad\u01b8\3\b\2\2";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}