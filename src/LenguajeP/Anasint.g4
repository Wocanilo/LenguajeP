parser grammar Anasint;

options{
    tokenVocab=Analex;
}

programa: PROGRAMA variables subprogramas instrucciones_programa EOF;

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
llamada_func_proc: IDENTIFICADOR INICIO_PARENTESIS (expr (COMA expr)*)? FIN_PARENTESIS;

// Los enteros pueden sumarse, restarse y multiplicarse. Tambien se pueden realizar llamadas a funciones
expr_entera: expr_entera POR expr_entera // Ponemos la mult. primero para asegurar el orden de prioridad
            | expr_entera MENOS expr_entera
            | expr_entera MAS expr_entera
            | MENOS* INICIO_PARENTESIS expr_entera FIN_PARENTESIS // Se pueden utilizar parentesis para modificar la prioridad de los operadores
            | MENOS* IDENTIFICADOR
            | MENOS* ENTERO
            | MENOS* llamada_func_proc
            | MENOS* acceso_secuencia
            ;

// Los boleanos no tienen operaciones.
expr_booleana: TRUE
             | FALSE
             ;

acceso_secuencia: IDENTIFICADOR INICIO_CORCHETE expr_entera FIN_CORCHETE; // Acceso elemento secuencia

// Tipo especial de expr usada en los elementos de una secuencia para asegurar el parseo secuencial
expr_elementosSecuencia: expr_entera
                       | expr_booleana
                       ;

elementos_secuencia: expr_elementosSecuencia (COMA expr_elementosSecuencia)*; // De forma implicita permite la llamada a funciones
expr_secuencia: INICIO_CORCHETE elementos_secuencia FIN_CORCHETE // Definicion secuencia
              | INICIO_CORCHETE FIN_CORCHETE // Definicion lista vacia
              ;

// Definimos los tipos posibles de expresion
expr: expr_entera
    | expr_booleana
    | expr_secuencia
    ;

// La asignacion de variables puede ser simple o multiple. Se permite modificar elementos de una secuencia
asignacion: identificador_O_Acceso (COMA identificador_O_Acceso)* IGUAL expr (COMA expr)* PyC;

identificador_O_Acceso: IDENTIFICADOR
                      |acceso_secuencia
                      ;

// P es un lenguaje fuertemente tipado, no se permite "mezclar" tipos en las condiciones. Es necesario comprobar el tipo
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

// Las condiciones se puden concatenar con operadores, cuya prioridad se puede romper con parentesis
condicion_completa: NEGACION condicion_completa
                  | condicion_completa DISYUNCION condicion_completa
                  | condicion_completa CONJUNCION condicion_completa
                  | INICIO_PARENTESIS condicion_completa FIN_PARENTESIS
                  | condicion_basica
                  ;

// Estructura condicional si-sino-fsi
condicional: SI INICIO_PARENTESIS condicion_completa FIN_PARENTESIS ENTONCES instruccion_condicionalSi+ (SINO (instruccion_condicionalSino)+)? FSI;
instruccion_condicionalSi: instruccion
                         | ruptura
                         | devolver
                         ;

instruccion_condicionalSino: instruccion
                           | ruptura
                           | devolver
                           ;

// Estructura iterativa mientras-hacer-fmientras
ruptura: RUPTURA PyC;
iteracion: MIENTRAS INICIO_PARENTESIS condicion_completa FIN_PARENTESIS HACER instruccion_iteracion+ FMIENTRAS;
instruccion_iteracion: instruccion
                     | ruptura
                     | devolver
                     ;

instruccion: (asignacion|condicional|iteracion|(llamada_func_proc PyC));

instrucciones_programa: INSTRUCCIONES instruccion*;

// Seccion de subprogramas. Se ha separado la definicion de parametros para facilitar su parseo
parametro: tipo IDENTIFICADOR;
parametros: parametro (COMA parametro)*;
devolver: DEV expr (COMA expr)* PyC;

instrucciones_funcion:(asignacion|condicional|iteracion|(llamada_func_proc PyC)|devolver); // Las funciones sin instrucciones no son correctas
instrucciones_procedimiento: (asignacion|condicional|iteracion|(llamada_func_proc PyC)); // Los procedimintos sin instrucciones no son correctas

def_func: FUNCION IDENTIFICADOR INICIO_PARENTESIS parametros? FIN_PARENTESIS DEV INICIO_PARENTESIS parametros FIN_PARENTESIS variables INSTRUCCIONES instrucciones_funcion+ FFUNCION;
def_proc: PROCEDIMIENTO IDENTIFICADOR INICIO_PARENTESIS parametros? FIN_PARENTESIS variables INSTRUCCIONES instrucciones_procedimiento+ FPROCEDIMIENTO;

// La seccion de subprogramas esta formado por 0 o varias declaraciones de funciones y/o procedimientos
subprogramas: SUBPROGRAMAS (def_func | def_proc)*;