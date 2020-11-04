# Especificación analizador semántico

## OBJETIVO 1
Calcular el tipo de una expresión.

Las expresiones pueden ser de tipo entera, booleana, secuencia entera, secuencia booleana o indefinida. \
Las expresiones indefinidas son erróneas.
            
### Razón
Conocer el tipo de las expresiones es el primer bloque para evaluar si un programa es correcto.

### Ejemplo
```
x: entero
y: booleano

x + y + 5 (tipo indefinido)
```
### Decisiones de diseño

#### Decisión 1
Para decidir si una expresión está bien tipada, es necesario conocer el tipo de las variables implicadas.
Por ello, se necesita almacenar está información al procesar la asignación de variables.

Para este fin se creará un almacén de variables al que llamaremos **tipoVariables**

| identificador | tipo     |
|---------------|----------|
| a,b,c         | entero      |
| d             | booleano      |
| e,f           | secuencia_entera |
| g             | secuencia_booleana |

##### Gramática atribuida
```
programa: PROGRAMA variables subprogramas instrucciones EOF;

(parámetro de salida t)
tipo: NUM {t=entero}
    | LOG {t=booleano}
    | SEQ_NUM {t=secuencia_entera}
    | SEQ_LOG {t=secuencia_booleana}
    ;

variables: VARIABLES (decl_var PyC)*;

decl_var: ident=IDENTIFICADOR (COMA ident=IDENTIFICADOR)* PyP t=tipo {almacenar cada ident con tipo t en tipoVariables};
```
#### Decisión 2
Las variables pueden tener dos scopes o alcances: **global o local**.
Debemos conocer el scope de las variables a la hora de resolver las expresiones para conocer si una variable existe en el contexto en el que ha sido utilizada

Las variables globales son aquellas declaradas en la sección **VARIABLES** del programa principal.

Las variables locales son aquellas declaradas **dentro de una función o procedimiento**, ya sea **como argumento de entrada
o de salida o dentro de la sección variables**.

Por tanto, tendremos un almacen de variables global y un almacen de variables locales que se borrará al analizar cada función/procedimiento
(ya que **no existe colisión entre las variables de dos funciones/procedimientos distintos**)

##### Gramática atribuida
```
programa: PROGRAMA variables subprogramas instrucciones EOF;

(parámetro de salida t)
tipo: NUM {t=entero}
    | LOG {t=booleano}
    | SEQ_NUM {t=secuencia_entera}
    | SEQ_LOG {t=secuencia_booleana}
    ;

variables: VARIABLES (decl_var PyC)*;

decl_var: ident=IDENTIFICADOR (COMA ident=IDENTIFICADOR)* PyP t=tipo {almacenar cada ident con tipo t en tipoVariables[obtenerScopeVariable(árbol declr_var)]};

parametro: t=tipo ident=IDENTIFICADOR {almacenar ident con tipo t en tipoVariables[LOCAL]};
parametros: parametro (COMA parametro)*;
devolver: DEV expr (COMA expr)*;
instrucciones_funcion: INSTRUCCIONES (instruccion|devolver)*;

def_func: FUNCION IDENTIFICADOR INICIO_PARENTESIS parametros? FIN_PARENTESIS DEV INICIO_PARENTESIS parametros FIN_PARENTESIS variables instrucciones_funcion FFUNCION; {borrar tipoVariables[LOCAL]}
def_proc: PROCEDIMIENTO IDENTIFICADOR INICIO_PARENTESIS parametros? FIN_PARENTESIS variables instrucciones FPROCEDIMIENTO; {borrar tipoVariables[LOCAL]}

subprogramas: SUBPROGRAMAS (def_func | def_proc)*;

(funcion obtenerScopeVariable(declr_var)
    // PROGRAMA/FUNCION/PROC -> VARIABLES -> decl_vars
    Si el padre del padre de declr_var es PROGRAMA entonces GLOBAL
    sino LOCAL
)

```