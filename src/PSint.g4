parser grammar PSint;

options{
    tokenVocab=PLex;
}

programa: PROGRAMA variables subprogramas instrucciones EOF;

tipo: NUM
    | LOG
    | SEQ_NUM
    | SEQ_LOG
    ;

// La seccion de declaracion de variables puede estar formada por 0 o varias declaraciones
variables: VARIABLES (decl_var PyC)*;
// Cada declaracion de variables está formada por al menos un identificador y un tipo basico o compuesto
decl_var: IDENTIFICADOR (COMA IDENTIFICADOR)* PyP tipo;

// Llamada a funcion/procedimiento.
llamada_func_proc: IDENTIFICADOR INICIO_PARENTESIS expr (COMA expr)* FIN_PARENTESIS;

// Los enteros pueden sumarse, restarse y multiplicarse. Tambien se pueden realizar llamadas a funciones
expr_entera: expr_entera MAS expr_entera
            | expr_entera MENOS expr_entera
            | expr_entera POR expr_entera
            | INICIO_PARENTESIS expr_entera FIN_PARENTESIS // Se pueden utilizar parentesis para modificar la prioridad de los operadores
            | IDENTIFICADOR
            | ENTERO
            | llamada_func_proc
            ;

// Los boleanos no tienen operaciones. Se pueden realizar llamadas a funciones
expr_booleana: TRUE
             | FALSE
             | IDENTIFICADOR
             | llamada_func_proc
             ;

elementos_secuencia: (expr_entera|expr_booleana) (COMA (expr_entera|expr_booleana))*; // De forma implicita permite la llamada a funciones
expr_secuencia: INICIO_CORCHETE elementos_secuencia FIN_CORCHETE // Definicion secuencia
              | IDENTIFICADOR INICIO_CORCHETE expr_entera FIN_CORCHETE // Acceso elemento secuencia
              | INICIO_CORCHETE FIN_CORCHETE // Definicion lista vacia
              ;

// Definimos los tipos posibles de expresion
expr: expr_entera
    | expr_booleana
    | expr_secuencia
    ;

// La asignacion de variables puede ser simple o multiple.
asignacion: IDENTIFICADOR (COMA IDENTIFICADOR)* IGUAL expr (COMA expr)* PyC;

// P es un lenguaje fuertemente tipado, no se permite "mezclar" tipos en las condiciones. Es necesario comprobar el tipo
condicion_basica: expr IGUALDAD expr
         | expr DESIGUALDAD expr
         | expr MENOR_QUE expr
         | expr MAYOR_QUE expr
         | expr MAYOR_IGUAL_QUE expr
         | expr MENOR_IGUAL_QUE expr
         | CIERTO // Condicion especial, siempre cierta
         | FALSO // Condicion especial, siempre falsa
         ;

// Las condiciones se puden concatenar con operadores, cuya prioridad se puede romper con parentesis
condicion_completa: condicion_completa CONJUNCION condicion_completa
                  | condicion_completa DISYUNCION condicion_completa
                  | INICIO_PARENTESIS condicion_completa FIN_PARENTESIS
                  | NEGACION condicion_completa
                  | condicion_basica
                  ;

// Estructura condicional si-sino-fsi
condicional: SI INICIO_PARENTESIS condicion_completa FIN_PARENTESIS ENTONCES instruccion+ (SINO instruccion+)? FSI;

// Estructura iterativa mientras-hacer-fmientras
funcion_avance: INICIO_LLAVE AVANCE PyP IDENTIFICADOR INICIO_PARENTESIS expr COMA expr FIN_PARENTESIS FIN_LLAVE;
ruptura: RUPTURA PyC;
iteracion: MIENTRAS INICIO_PARENTESIS condicion_completa FIN_PARENTESIS HACER funcion_avance? (instruccion|ruptura)+ FMIENTRAS;

instruccion: (asignacion|condicional|iteracion|aserto);

variable_cuantificada: IDENTIFICADOR PyP INICIO_CORCHETE elementos_secuencia FIN_CORCHETE COMA condicion_completa;
aserto: INICIO_LLAVE (PARATODO|EXISTE) INICIO_PARENTESIS variable_cuantificada FIN_PARENTESIS FIN_LLAVE;

instrucciones: INSTRUCCIONES instruccion*; // La seccion de INSTRUCCIONES es una secuencia de 0 o mas instrucciones

// Seccion de subprogramas. Se ha separado la definicion de parametros para facilitar su parseo
parametro: tipo IDENTIFICADOR;
parametros: parametro (COMA parametro)*;
devolver: DEV expr (COMA expr)*;
instrucciones_funcion: INSTRUCCIONES (instruccion|devolver)*; // Puede que sea buena idea impedir la creacion de funciones sin instrucciones..

def_func: FUNCION IDENTIFICADOR INICIO_PARENTESIS parametros? FIN_PARENTESIS DEV INICIO_PARENTESIS parametros FIN_PARENTESIS variables instrucciones_funcion FFUNCION;
def_proc: PROCEDIMIENTO IDENTIFICADOR INICIO_PARENTESIS parametros? FIN_PARENTESIS variables instrucciones FPROCEDIMIENTO;

// La seccion de subprogramas esta formado por 0 o varias declaraciones de funciones y/o procedimientos
subprogramas: SUBPROGRAMAS (def_func | def_proc)*;