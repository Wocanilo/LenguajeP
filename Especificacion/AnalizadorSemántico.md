# Especificación analizador semántico

## OBJETIVO 1
Detectar variables redeclaradas.

Una vez declaradas, las variables son de tipo inmutable
            
### Ejemplo
```
x,y:NUM
x:LOG
(Error)
```
### Decisiones de diseño

#### Decisión 1
Para detectar redeclaraciones es necesario almacenar el identificador y el tipo de cada variable.
Por ello, se necesita almacenar esta información al declarar las variables.

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

variables: VARIABLES d=(decl_var PyC)* {almacenar cada d en tipoVariables} ;

(parámetro de salida d)
decl_var: ident=IDENTIFICADOR (COMA ident=IDENTIFICADOR)* PyP t=tipo {almacenar cada ident con tipo t en d};
```
#### Decisión 2
Las variables pueden tener dos scopes o alcances: **global o local**.

Las variables globales son aquellas declaradas en la sección **VARIABLES** del programa principal.

Las variables locales son aquellas declaradas **dentro de una función o procedimiento**, ya sea **como argumento de entrada
o de salida o dentro de la sección variables**.

Por tanto, tendremos un almacen de variables global y un almacen de variables local por cada una de las funciones o procedimientos.
##### Gramática atribuida
```
programa: PROGRAMA vs=variables subprogramas instrucciones EOF {almacenar vs en tipoVariable[GLOBAL]};

(parámetro de salida t)
tipo: NUM {t=entero}
    | LOG {t=booleano}
    | SEQ_NUM {t=secuencia_entera}
    | SEQ_LOG {t=secuencia_booleana}
    ;

(parámetro de salida vs)
variables: VARIABLES d=(decl_var PyC)* {almacenar cada d en vs} ;

(parámetro de salida d)
decl_var: ident=IDENTIFICADOR (COMA ident=IDENTIFICADOR)* PyP t=tipo {almacenar cada ident con tipo t en d};

(parámetro de salida p)
parametro: t=tipo ident=IDENTIFICADOR {almacenar ident con tipo t en p};
(parámetro de salida ps)
parametros: p=parametro (COMA p=parametro)* {almacenar cada p en ps};
devolver: DEV expr (COMA expr)*;
instrucciones_funcion: INSTRUCCIONES (instruccion|devolver)*;

def_func: FUNCION nombreFunc=IDENTIFICADOR INICIO_PARENTESIS ps=parametros? FIN_PARENTESIS DEV INICIO_PARENTESIS ps=parametros FIN_PARENTESIS vs=variables instrucciones_funcion FFUNCION; 
{almacenar cada ps en tipoVariable[nombreFunc]} {almacenar vs en tipoVariable[nombreFunc]}
def_proc: PROCEDIMIENTO nombreProc=IDENTIFICADOR INICIO_PARENTESIS ps=parametros? FIN_PARENTESIS vs=variables instrucciones FPROCEDIMIENTO;
{almacenar cada ps en tipoVariable[nombreProc]} {almacenar vs en tipoVariable[nombreProc]}

subprogramas: SUBPROGRAMAS (def_func | def_proc)*;

(funcion declaraVariable(var, scope)
si tipoVariables[scope] no existe entonces crear tipoVariables[scope] y almaenar la variable dentro
sino 
    si var.ident existe en tipoVariables[scope] entonces
       si var.tipo es distinto al almacenado entonces ERROR
       sino WARNING
    sino almacenar en tipoVariables[scope]
)
```
## Objetivo 2
Detectar funciones o procedimientos redeclarados.

Las funciones o procedimientos son inmutables. Una vez declaradas, no es posible declarar otra funcion 
o procedimiento con el mismo identificador.

### Ejemplo
```
SUBPROGRAMAS
    FUNCION mayor(NUM d) dev (LOG e, NUM i)
    VARIABLES
        j: NUM;
    INSTRUCCIONES
        j = 0;
    FFUNCION
    PROCEDIMIENTO mayor()
    VARIABLES
        j: NUM;
    INSTRUCCIONES
        j = 0;
    FPROCEDIMIENTO
(Error, redeclaracion de mayor)
```

### Decisiones de diseño

#### Decisión de diseño 1
Para poder detectar redeclaraciones es necesario almacenar el identificador de cada una de las funciones o
procedimientos declarados.

Por ello, usaremos un almacén de identificadores de funciones/proc.

| identificador |
|---------------|
| mayor         |
| mult             |
| mayor_que           |

```
def_func: FUNCION ident=IDENTIFICADOR INICIO_PARENTESIS parametros? FIN_PARENTESIS DEV INICIO_PARENTESIS parametros FIN_PARENTESIS variables instrucciones_funcion FFUNCION;
{declarafuncionProcedimiento(ident)}
def_proc: PROCEDIMIENTO IDENTIFICADOR INICIO_PARENTESIS parametros? FIN_PARENTESIS variables instrucciones FPROCEDIMIENTO;
{declarafuncionProcedimiento(ident)}

(funcion declarafuncionProcedimiento(ident)
    si ident en funcionesYProcedimientos entonces ERROR
    sino almacenar ident en funcionesYProcedimientos
)

```
