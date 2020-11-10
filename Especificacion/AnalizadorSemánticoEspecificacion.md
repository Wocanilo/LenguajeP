# Especificación analizador semántico

## OBJETIVO 1
Detectar variables redeclaradas.

Una vez declaradas, las variables son de tipo inmutable. Es decir, no es posible cambiar el tipo de una
variable ya declarada.
            
### Ejemplo
```
x,y:NUM
x:LOG
(ERROR: X redeclarada como LOG, antes declarada como NUM)
```
### Decisiones de diseño

#### Decisión 1
Para detectar redeclaraciones, es necesario almacenar el identificador y el tipo de cada variable.
Por ello, se necesita almacenar esta información al declarar las variables.

Para este fin, se creará un almacén de variables al que llamaremos **tipoVariables**

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
{almacenar ps en tipoVariable[nombreFunc]} {almacenar vs en tipoVariable[nombreFunc]}
def_proc: PROCEDIMIENTO nombreProc=IDENTIFICADOR INICIO_PARENTESIS ps=parametros? FIN_PARENTESIS vs=variables instrucciones FPROCEDIMIENTO;
{almacenar ps en tipoVariable[nombreProc]} {almacenar vs en tipoVariable[nombreProc]}

subprogramas: SUBPROGRAMAS (def_func | def_proc)*;

(funcion declaraVariable(var, scope)
si tipoVariables[scope] no existe entonces crear tipoVariables[scope] y almacenar la variable dentro
sino 
    si var.ident existe en tipoVariables[scope] entonces
       si var.tipo es distinto al almacenado entonces ERROR
       sino WARNING
    sino almacenar en tipoVariables[scope]
)
```

ARGUMENTO DISEÑO 1: DECISIÓN DISEÑO 2=> Decision 1

## Objetivo 2
Detectar funciones o procedimientos redeclarados.

Las funciones o procedimientos son inmutables. Una vez declarados, no es posible declarar otra funcion 
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
Para poder detectar redeclaraciones, es necesario almacenar el identificador de cada una de las funciones o
procedimientos declarados.

Por ello, usaremos un almacén de identificadores de funciones/procedimientos.

| identificador |
|---------------|
| mayor         |
| mult             |
| mayor_que           |

Además, dado que P predefine dos funciones: __vacia__ y __ultima_posicion__, el almacen de funciones/procedimientos contendrá ambas funciones desde su creación.

| identificador |
|---------------|
| vacia         |
| ultima_posicion|
##### Gramatica atribuida
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

## Objetivo 3
Comprobar que no se llama a un procedimiento o funcion que no existe.

### Ejemplo

```
PROGRAMA
VARIABLES
    i,max,min:NUM;
    s:SEQ(NUM);
SUBPROGRAMAS
    FUNCION mayor(NUM de) dev (NUM e, NUM i)
    VARIABLES
        j: NUM;
    INSTRUCCIONES
        j = 0;
        dev j,j
    FFUNCION
INSTRUCCIONES
    x = noexiste(1);
(ERROR)
```

### Decisiones de diseño
#### Decision 1
Para poder decidir si una funcion o procedimiento existe, debemos almacenar cada declaracion de funcion o procedimiento.

Dado que el objetivo 2 define un almacen de funciones y procedimientos, usaremos dicho almacen.

##### Gramatica atribuida

```
llamada_func_proc: ident=IDENTIFICADOR INICIO_PARENTESIS expr (COMA expr)* FIN_PARENTESIS; {si ident no existe en almacen entonces ERROR}
```

## Objetivo 4
Analizador semantico capaz de decidir si las expresiones de un programa en el lenguaje P estan bien tipadas.

- Una expresión esta bien tipada cuando no mezcla expresiones con distinto tipo.
- Una expresión es de tipo entera si y solo si es una constante entera, una variable entera, una suma, resta o producto de
variables enteras o una llamada a una función que devuelva una constante entera.
- Una expresión es de tipo booleana si y solo si es una constante booleana, una variable booleana o una llamada a una función
que devuelva una constante booleana
- Una expresión es de tipo secuencia entera cuando si y solo si es una lista dada por extension cuyos elementos son todos enteros
 (la lista vacía cumple esta condición), una variable de tipo lista entera o una llamada a una función que devuelva una secuencia entera.
 - Una expresión es de tipo secuencia booleana cuando si y solo si es una lista dada por extension cuyos elementos son todos booleanos
    (la lista vacía cumple esta condición), una variable de tipo lista booleana o una llamada a una función que devuelva una secuencia booleana.
 - Una expresión no tiene tipo si y solo si no es posible asignarle tipo según las definiciones anteriores

### Ejemplo
```
PROGRAMA
VARIABLES
    i:NUM;
    e:LOG;
SUBPROGRAMAS
INSTRUCCIONES
    i = i + e;
(ERROR)
```
### Decisiones de diseño
#### Decision 1
Para poder decidir el tipado de una expresion, es necesario conocer el tipo de las funciones que la forman, por ello
usaremos un almacen de tipos de funciones.

| identificador | tipo     |
|---------------|----------|
| mayor        | entero      |
| mayor_de             | booleano, entero      |

Además, dado que P predefine dos funciones: __vacia__ y __ultima_posicion__, el almacen de tipos de funciones
se creará con el siguiente estado:

| identificador | tipo     |
|---------------|----------|
| vacia           | booleano    |
| ultima_posicion | entero      |

##### Gramatica atribuida
```
(parametro de salida e)
parametro: t=tipo ident=IDENTIFICADOR; {almacenar ident con tipo t en e}
(parametro de salida ps)
parametros: p=parametro (COMA p=parametro)*; {almacenar p en ps}

def_func: FUNCION ident=IDENTIFICADOR INICIO_PARENTESIS parametros? FIN_PARENTESIS DEV INICIO_PARENTESIS ps=parametros FIN_PARENTESIS variables instrucciones_funcion FFUNCION;
{almacenar cada parametro contenido en ps en almacen tiposFunciones}

```

#### Decision 2
Para poder decidir el tipado de las variables, es necesario conocer el contexto en el que se encuentra la expresion, ya que dependiendo
del contexto en el que se encuentren existirán unas variables u otras. 

Por ello, guardaremos el scope actual para poder consultarlo al resolver una expresión.
#####  Gramatica atribuida
```

def_func: FUNCION ident=IDENTIFICADOR INICIO_PARENTESIS parametros? FIN_PARENTESIS DEV INICIO_PARENTESIS parametros FIN_PARENTESIS variables instrucciones_funcion FFUNCION;
{establecer scopeActual con el valor de ident}
def_proc: PROCEDIMIENTO ident=IDENTIFICADOR INICIO_PARENTESIS parametros? FIN_PARENTESIS variables instrucciones_procedimiento FPROCEDIMIENTO;
{establecer scopeActual con el valor de ident}

instrucciones_programa: INSTRUCCIONES instruccion+; {establecer scopeActual a GLOBAL}

```
#### Decision 3
El cálculo del tipo de una expresión se basa en las siguientes funciones

##### Gramatica atribuida
```

(funcion calculaTipoOPAritmetica(operando1, operando2)
    si operando1 o operando2 es no_tipo entonces no_tipo
    sino entonces NUM
)

(funcion calculaTipoFuncion(ident)
    si ident existe en tipoFunciones entonces
        si tipoFunciones[ident] > 1 entonces devolver no_tipo
        sino devolver tipo funcion
    sino devolver no_tipo
)

(funcion calculaTipoVariable(ident)
    si scope existe en tipoVariables entonces
        si ident existe en tipoVariables[scope] entonces devolver tipo variable
        sino devolver no_tipo
    sino devolver no_tipo
)

(funcion calculaTipoAccesoSecuencia(ident)
    si calculaTipoVariable(ident) es SEQ_LOG entonces devolver LOG
    sino si calculaTipoVariable(ident) es SEQ_NUM entonces devolver NUM
    sino devolver no_tipo
)

(funcion calculaTipoSecuencia(elementos)
    si todos los elementos son de tipo X devolver SEQ(X)
    sino devolver no_tipo
)


(parametro de salida tipo)
expr_entera: expr1=expr_entera MAS expr2=expr_entera {tipo=calculaTipoOPAritmetica(expr1, expr2)}
            | expr1=expr_entera MENOS expr2=expr_entera {tipo=calculaTipoOPAritmetica(expr1, expr2)}
            | expr1=expr_entera POR expr2=expr_entera {tipo=calculaTipoOPAritmetica(expr1, expr2)}
            | INICIO_PARENTESIS tipo=expr_entera FIN_PARENTESIS
            | ident=IDENTIFICADOR {tipo=calculaTipoVariable(ident)}
            | ENTERO {tipo=entero)
            | acceso_secuencia {tipo=tipoAccesoSecuencia(acceso_secuencia)}
            | llamada_func_proc {calculaTipoFuncion(llamada_func_proc)}
            ;

(parametro de salida tipo)
expr_booleana: TRUE {tipo=booleano}
             | FALSE {tipo=booleano}
             ;

(parametro de salida elementos)
elementos_secuencia: elemento=(expr_entera|expr_booleana) (COMA elemento=(expr_entera|expr_booleana))*; {almacenar cada elemento en elementos}

(parametro de salida tipo)
acceso_secuencia: IDENTIFICADOR INICIO_CORCHETE expr=expr_entera FIN_CORCHETE; {calculaTipoAccesoSecuencia(expr)}

(parámetro de salida tipo)
expr_secuencia: INICIO_CORCHETE elementos=elementos_secuencia FIN_CORCHETE {tipo=calculaTipoSecuencia(elementos)} 
              | INICIO_CORCHETE FIN_CORCHETE {tipo=SEQ}
              ;

(parametro de salida tipo)
expr: tipo=expr_entera
    | tipo=expr_booleana
    | tipo=expr_secuencia
    ;
```
## Objetivo 5
Analizador semántico capaz de decidir si las asignaciones de un programa son validas.

- Una asignación es válida si y solo si el tipo de las expresiones y de las variables coinciden.
- Una asignación es válida si y solo si el número de variables es igual al número de expresiones
- En cualquier otro caso la asignación es inválida
### Ejemplo
```
PROGRAMA
VARIABLES
    i,max,min,x:NUM;
SUBPROGRAMAS
INSTRUCCIONES
    x = 1 + 2; (OK)
    x = T; (ERROR)
    x,i = 1, 22; (OK)
    x = 22, 1+2; (ERROR)

```
### Decisiones de diseño
Una asignación está bien tipada si y solo si el tipo de las expresiones y las variables coinciden, y 
el número de variables coincide con el número de expresiones.

Debemos tener en cuenta el caso límite en el que la variable es una secuencia y la expresión una secuencia vacía.

#### Decision 1

```
(parametro de salida tipo)
expr: tipo=expr_entera
    | tipo=expr_booleana
    | tipo=expr_secuencia
    ;

asignacion: ident=IDENTIFICADOR (COMA ident=IDENTIFICADOR)* IGUAL tipo=expr (COMA tipo=expr)* PyC;
{por cada pareja ident,expr calculaTipoAsignacion(ident, expr), si son impares ERROR, si varios ident
y una sola expr comprobar si es funcion y los tipos coinciden sino ERROR}

(funcion calculaTipoAsignacion(ident, expr)
si calculaTipoVariable(ident) es no_tipo entonces devolver no_tipo
sino
    si calculaTipoVariable(ident) es igual a tipo expr devolver tipo
    sino 
        si calculaTipoVariable(ident) es igual a SEQ_NUM o SEQ_LOG y tipo expr es SEQ devolver tipo variable
        devolver no_tipo
)
```