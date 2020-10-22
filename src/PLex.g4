lexer grammar PLex;

BLANCO: ' ' -> skip;
TABULADOR: '\t' -> skip;
FIN_LINEA: '\r'?'\n' -> skip;

fragment DIGITO: [0-9];
fragment LETRA: [a-z]|('-'|'_');

PROGRAMA: 'PROGRAMA';
VARIABLES: 'VARIABLES';
SUBPROGRAMAS: 'SUBPROGRAMAS';
INSTRUCCIONES: 'INSTRUCCIONES';

// Tipos
NUM: 'NUM';
LOG: 'LOG';
SEQ: 'SEQ';

// Simbolos
TRUE: 'T';
FALSE: 'F';
MAS: '+';
MENOS: '-';
POR: '*';
INICIO_PARENTESIS: '(';
FIN_PARENTESIS: ')';
INICIO_CORCHETE: '[';
FIN_CORCHETE: ']';
INICIO_LLAVE: '{';
FIN_LLAVE: '}';
IGUAL: '=';
PyC: ';';
PyP: ':';
COMA: ',';

// Simbolos comparaciones
IGUALDAD: '==';
DESIGUALDAD: '!=';
MENOR_QUE: '<';
MAYOR_QUE: '>';
MAYOR_IGUAL_QUE: '>=';
MENOR_IGUAL_QUE: '<=';
CONJUNCION: '&&';
DISYUNCION: '||';
NEGACION: '!';
CIERTO: 'cierto';
FALSO: 'falso';

FUNCION: 'FUNCION';
FFUNCION: 'FFUNCION';
SI: 'si';
SINO: 'sino';
ENTONCES: 'entonces';
DEV: 'dev';
FSI: 'fsi';
FMIENTRAS: 'fmientras';
MIENTRAS: 'mientras';
HACER: 'hacer';
RUPTURA: 'ruptura';
AVANCE: 'avance';
PROCEDIMIENTO: 'PROCEDIMIENTO';
FPROCEDIMIENTO: 'FPROCEDIMIENTO';
PARATODO: 'PARATODO';
EXISTE: 'EXISTE';

// Esto va al final o se lia con las palabras clave
ENTERO: ('-')?DIGITO+;
IDENTIFICADOR: LETRA(LETRA|DIGITO)*;

// Saltar comentarios
COMENTARIOS_LINEA: '//' .*? -> skip;
COMENTARIOS_BLOQUE: '/*' .*? '*/' -> skip;
