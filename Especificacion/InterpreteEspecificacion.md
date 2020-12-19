# Especificación Intérprete

## Objetivo
Construir un intérprete de P. Esto es, un programa capaz de ejecutar un programa
escrito en el lenguaje P.

El lenguaje P consta de tres secciones bien delimitadas:

- VARIABLES
- SUBPROGRAMAS
- INSTRUCCIONES

Además, todo programa en P empieza con la palabra reservada **PROGRAMA**.

En P se admiten comentarios de línea *//* y de bloque */\* \*/*

### Sección VARIABLES
Empieza con la palabra reservada **VARIABLES**, seguida de tantas declaraciones de variables como sean necesarias.

El lenguaje P define dos clases de tipos de datos:
- **Elementales**:
    - **Entero**: constantes enteras. Conjunto Z: {...,-1,0,1,...}.
    Se declaran con la palabra reservada *NUM*
    - **Lógico o booleano**: puede tomar los valores *T* o *F*.
    Se declaran con la palabra reservada *LOG*
- **No elementales**:
    - **Secuencia entera**: secuencia de valores enteros.
    Se declara con la palabra reservada *SEQ(NUM)*.
    - **Secuencia lógica**: secuencia de valores lógicos.
    Se declara con la palabra reservada *SEQ(LOG)*.

La declaración de variables consta de dos partes:
- Identificadores separados por comas
- Tipo de las variables a declarar

Ejemplo:
```
a,b,c,d,e:NUM;
f,g:SEQ(NUM);
```

Cada variable declarada en un programa tiene un tipo **invariable**

### Sección SUBPROGRAMAS
Empieza con la palabra reservada **SUBPROGRAMAS**, seguida de tantas declaraciones de funciones o procedimientos como sean neceasrias.

Los subprogramas solo pueden acceder a las variables definidas en su declaración.

#### Funciones
Las **funciones** tienen un conjunto (que puede estar vacío) de parámetros de entrada y un conjunto (no vacío) de parámetros de salida.
Los parámetros de entrada son de solo lectura y los de salida de lectura/escritura.

La funciones deben incluir en algún punto de sus instrucciones un retorno explícito de los parámetros de sálida mediate la instrucción *dev*

Ejemplo:

```
FUNCION mayor(NUM d) dev (NUM d)
VARIABLES
    j: NUM;
INSTRUCCIONES
    j = d;
    dev j
FFUNCION
```

#### Procedimientos

Los **procedimientos** tienen un conjunto posiblemente vacío de parámetros de entrada y no tienen parámetros de salida.
No hay devolución explícita de resultados.

Las variables de entrada en un procedimiento son de **lectura/escritura**.

La llamada a un procedimiento nunca puede formar parte de una expresión.

```
PROCEDIMIENTO ejemplo(NUM res)
VARIABLES
    j,x: NUM;
INSTRUCCIONES
    j = 1;
    x = 2;
    res = j + x;
FPROCEDIMIENTO
```

### Sección INSTRUCCIONES
Empieza con la palabra reservada **INSTRUCCIONES** y está formada por tantas instrucciones como sean necesarias.

Las instrucciones son:
- Asignación
    - Puede ser tanto simple como múltiple
        - x = 1;
        - x,y = 2,5;
    - La asignaciones multiples se realizan de forma paralela
- Condicional
    - Bloque de instrucciones cuya ejecucion depende del valor de verdad de una condición
        - si-fsi
        - si-sino-fsi
    - Las condiciones tan solo se pueden realizar entre expresiones del mismo tipo.
- Iteración
    - Repite una serie de instrucciones mediante no se cumpla una condición
    - mientras-hacer-fmientras
- Ruptura de control:
    -  Lleva al programa fuera del bloque en el que está localizada la ruptura.
- Llamada a procedimiento o función
- Devolución de resultados de una función
- Mostrar por consola el valor de variables
    - Permite mostrar por consola los valores de una lista de variables
    
## Decisiones de diseño

### Decisión 1
Para poder ejecutar un programa en P, es necesario conocer qué variables existen, de qué tipo son y qué valor tienen.

Para este fin, se creará un almacén de variables, que contendrá el estado de cada variable declarada.

| identificador | tipo               | valor
|---------------|--------------------|------- |
| a             | entero             |1|
| d             | booleano           |T|
| e             | secuencia_entera   |1,2,3,4|
| g             | secuencia_booleana |T,T,F,F|

Estas variables seran representadas mediante una clase *Variable* que se encargara de asegurar su correcto tipado
en tiempo de ejecucion. Asi como de evitar el acceso a variables sin valor definido.

```java
public class Variable {
    /*
    * Clase que implementa una variable genérica
    */
    private String identificador;
    private Integer tipo;
    private Object valor;

    // Declaración sin valor usada por el analizador semántico
    public Variable(String identificador, Integer tipo);

    // Declaración con valor usada por el intérprete
    public Variable(String identificador, Integer tipo, Object valor);
    
    /*
    *   No existen metodos que permitan cambiar el identificador ni el tipo de una variable tras su creacion
    */
    public String getIdentificador();
    public Integer getTipo();
    
    // Evita el acceso a una variable sin valor
    public Object getValor();

    // Comprueba que los valores asignados coincidan con el tipo de la variable
    public void setValor(Object valor);
}
```


#### Gramática atribuida
```antlrv4
programa: PROGRAMA variables subprogramas instrucciones EOF;

// (parámetro de salida t)
tipo: NUM // {t=entero}
    | LOG // {t=booleano}
    | SEQ_NUM // {t=secuencia_entera}
    | SEQ_LOG // {t=secuencia_booleana}
    ;

variables: VARIABLES d=(decl_var PyC)* // {almacenar cada d en almacen de variables} ;
```
##### Decision 2
Para interpretar un programa, es necesario calcular el valor de sus expresiones.

Dado que la resolucion de una expresion depende de su contexto, es decir, depende de donde se encuentre
ubicada la expresion en el programa, se creara una clase generica encargada de resolver una expresion a partir
de un contexto dado.

A grandes rasgos, la clase *ExprParser* recibe un almacen de variables y una lista con las funciones y procedimientos
disponibles y en base a esta informacion, resuelve la expresion a un valor.

```java
public class ExprParser extends AnasintBaseVisitor<Object> {
    private HashMap<String, Variable> almacenVariables;
    private List<Subprograma> subprogramas;

    public ExprParser(HashMap<String, Variable> almacenVariables, List<Subprograma> subprogramas);
}

```


###### Gramatica atribuida

```antlrv4
// (parametro de salida valor)
expr_entera: expr1=expr_entera MAS expr2=expr_entera // {valor=suma(expr1, expr2)}
            | expr1=expr_entera MENOS expr2=expr_entera // {valor=resta(expr1, expr2)}
            | expr1=expr_entera POR expr2=expr_entera // {valor=multiplica(expr1, expr2)}
            | INICIO_PARENTESIS valor=expr_entera FIN_PARENTESIS
            | ident=IDENTIFICADOR // {valor=getValorVariable(ident)}
            | ENTERO // {valor=entero)
            | acceso_secuencia // {valor=acceso_secuencia}
            | llamada_func_proc // {valor=ejecutaFuncion(llamada_func_proc)}
            ;

acceso_secuencia: ident=IDENTIFICADOR INICIO_CORCHETE elemento=expr_entera FIN_CORCHETE; // {obtiene
    // la secuencia del almacen de variables y devuelve el valor de la posicion} 

// (parametro de salida valor)
expr_booleana: TRUE {valor=true}
             | FALSE {valor=false}
             ;

// (parametro de salida valor)
expr_elementosSecuencia: valor=expr_entera
                       | valor=expr_booleana
                       ;

// (parametro de salida elementos)
elementos_secuencia: elemento=expr_elementosSecuencia (COMA elemento=expr_elementosSecuencia)*; // {Almacena cada elemento en elementos}

// (parametro de salida secuencia)
expr_secuencia: INICIO_CORCHETE elementos_secuencia FIN_CORCHETE // {secuencia=elementos_secuencia}
              | INICIO_CORCHETE FIN_CORCHETE // {secuencia=[]}
              ;

// Se utilizan las siguientes funciones:

// (funcion getVariable(ident){
//    Si ident in almacenVariables entonces
//        devolver valor variable
//    sino ERROR variable no declarada
// )
```

##### Decision 3
Para interpretar un programa, es necesario conocer que funciones y procedimientos existen, asi como conocer sus parametros
de entrada/salida.

Por ello, definimos un almacen de funciones/procedimientos que contiene cada una de las funciones y procedimientos del programa.

| Identificador | Entrada                 | Salida                  | Variables locales            | Instrucciones |
|---------------|-------------------------|-------------------------|------------------------------|---------------|
| mayor         | [Parametro("a", "NUM")] | [Parametro("d", "LOG")] | [Variable("b", "NUM", null)] | d = T;        |
| vacio         | [Parametro("d", "LOG")] | null                    | [Variable("c", "NUM", null)] | d = F;        |

Esta informacion se encapsula en una clase *Subprograma* generica, que contiene toda la informacion necesaria
para ejecutar una funcion o procedimiento.

```java
public class Subprograma {
    protected String identificador;
    protected List<Parametro> parametrosEntrada;
    protected List<Parametro> parametrosSalida;

    protected HashMap<String, Variable> almacenVariables;
    protected Object instruccionesSubprograma;

    // Indica si es funcion o procedimiento
    protected boolean esFuncion;

    // Crea un subprograma de tipo funcion
    public Subprograma(String identificador, List<Parametro> parametrosEntrada, List<Parametro> parametrosSalida,
                       HashMap<String, Variable> almacenVariables, List<Anasint.Instrucciones_funcionContext> instruccionesSubprograma);
    
    // Crea un subprograma de tipo procedimiento
    public Subprograma(String identificador, List<Parametro> parametrosEntrada,
                       HashMap<String, Variable> almacenVariables, List<Anasint.Instrucciones_procedimientoContext> instruccionesSubprograma);

    // Ejecuta una funcion o procedimiento y devuelve su resultado
    public Object Execute(HashMap<String, Variable> variablesLocales);
}
```
A su vez, los parametros de una llamada a funcion/procedimiento se encuentran encapsulados
en una clase *Parametro*.

```java
public class Parametro {
    private String identificador;
    private Integer tipo;

    public Parametro(String identificador, Integer tipo);

    public String getIdentificador();
    public Integer getTipo();
}
```

###### Gramatica atribuida

```antlrv4
// (parametro devuelto param)
parametro: t=tipo ident=IDENTIFICADOR // {almacenar indent con tipo t en param};
// (parametro devuelto parametros)
parametros: parametro (COMA parametro)* // {almacenar todos los param en parametros};

// (parametro de salida subprograma)
def_func: FUNCION ident=IDENTIFICADOR INICIO_PARENTESIS entrada=parametros? FIN_PARENTESIS DEV INICIO_PARENTESIS salida=parametros FIN_PARENTESIS vars=variables INSTRUCCIONES instr=instrucciones_funcion+ FFUNCION;
// {devolver Subprograma(ident, entrada, salida, vars, instr)}

// (parametro de salida subprograma)
def_proc: PROCEDIMIENTO ident=IDENTIFICADOR INICIO_PARENTESIS entrada=parametros? FIN_PARENTESIS vars=variables INSTRUCCIONES instr=instrucciones_procedimiento+ FPROCEDIMIENTO;
// {devolver Subprograma(ident, entrada, vars, instr)}
```

#### Decision 4
Interpretar un programa es interpretar secuencialmente sus instrucciones.

##### Interpretar(asignaciones)
La instruccion de asignacion actualiza el valor de las variables contenidas en el almacen de variables con el valor de una expresion.

Esta asignacion puede ser simple, cuando se trata de una sola variable, o multiple, cuando se trata de varias.

```
asignacion: IDENTIFICADOR (COMA IDENTIFICADOR)* IGUAL expr (COMA expr)* PyC
{
    Si (coincide numero de variables y expresiones) entonces
        Se calcula el valor de todas las expresiones implicadas
        Se actualiza cada variable con su nuevo valor
    Sino ERROR
}
```


**Ejemplo de evaluacion**
(*Solo se muestran los cambios al almacen*)
```
VARIABLES
a,b,c,d,e:NUM; -> Almacen: {"a": Variable("a", "NUM", null), "b": Variable("b", "NUM", null), "c": Variable("c", "NUM", null),
"d": Variable("d", "NUM", null), "e": Variable("e", "NUM", null)}
INSTRUCCIONES
a = 1; -> Almacen: {"a": Variable("a", "NUM", 1)}
b = a + 2; -> Almacen: {"a": Variable("a", "NUM", 3)}
c = mayor([1,2]); -> Almacen: {"c": Variable("c", "NUM", 2)}
d,e = coordenadas(); -> Almacen: {"d": Variable("d", "NUM", 37), "e": Variable("e", "NUM", -5)}
``` 

Es importante tener en cuenta que cuando se trata de una funcion, esta puede devolver varios valores. Estos valores
se asignan a las variables en orden de retorno.

###### Gramatica atribuida
```antlrv4
asignacion: ident=IDENTIFICADOR (COMA ident=IDENTIFICADOR)* IGUAL exp=expr (COMA exp=expr)* PyC;
// {para cada ident actualizar su valor en el almacen con su exp pareja}
// {Si solo hay una expr y es funcion, almacenar cada resultado en su variable pareja}
```

##### Interpretar(llamadas)
Un programa en P puede realizar llamadas a procedimientos como una instruccion

```
llamada_procedimiento: IDENTIFICADOR INICIO_PARENTESIS (expr (COMA expr)*)? FIN_PARENTESIS
{
    Si existe el procedimiento llamar al procedimiento y modificar el valor de las variables pasadas como parametros
    si no ERROR
}
```


