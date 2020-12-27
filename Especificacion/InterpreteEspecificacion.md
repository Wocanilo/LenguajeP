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

La equivalencia de tipos entre P y Java es la siguiente:

| Tipo en P | Tipo en Java  |
|-----------|---------------|
| NUM       | Integer       |
| LOG       | Boolean       |
| SEQ(NUM)  | List\<Integer> |
| SEQ(LOG)  | List\<Boolean> |

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
Empieza con la palabra reservada **SUBPROGRAMAS**, seguida de tantas declaraciones de funciones o procedimientos como sean necesarias.

Los subprogramas solo pueden acceder a las variables declaradas en su sección variables o a los parametros pasados en un llamada.

**Ejemplo alcance variables**

```
PROGRAMA
VARIABLES
    a:NUM;
SUBPROGRAMAS
    PROCEDIMIENTO prueba(NUM b)
        VARIABLES
            j: NUM;
        INSTRUCCIONES
            j = 25; -> Sentencia VÁLIDA
            a = 25; -> Sentencia INVÁLIDA. La variable "a" no existe en el contexto del subprograma
            b = 25; -> Sentencia VÁLIDA
    FPROCEDIMIENTO
INSTRUCCIONES
    prueba(1);    
```


#### Funciones
Las **funciones** tienen un conjunto (que puede estar vacío) de parámetros de entrada y un conjunto (no vacío) de parámetros de salida.
Los parámetros de entrada son de solo lectura y los de salida de lectura/escritura.

La funciones deben incluir en algún punto de sus instrucciones un retorno explícito de los parámetros de sálida mediate la instrucción *dev*

Las funciones pueden formar parte de una expresión si solo devuelven un valor.

**Ejemplo declaración de función:**

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

Las variables de entrada en un procedimiento son de **lectura/escritura**. Por tanto, las modificaciones realizadas a estas
variables dentro de un procedimiento se ven reflejadas en la variable pasada.

La llamada a un procedimiento nunca puede formar parte de una expresión dado que no devuelve ningún valor.

**Ejemplo declaración procedimiento:**
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
- **Asignación**
    - Puede ser tanto simple como múltiple
        - x = 1; `Simple`
        - x,y = 2,5; `Múltiple`
    - Las asignaciones multiples se realizan de forma paralela.
- **Condicional**
    - Bloque de instrucciones cuya ejecucion depende del valor de verdad de una condición
        - si-fsi `Ejecuta un bloque de código si se cumple una condición`
          ```
            si(condicion) entonces
                ...; // condición es cierta
            fsi
          ```
        - si-sino-fsi `Ejecuta un bloque de código si se cumple una condición y otro de no cumplirse`
          ```
            si(condicion) entonces
                ...; // condición es cierta
            sino
                ...; // condicion es falsa
            fsi
          ```
    - Las condiciones están formadas por expresiones del mismo tipo.
- **Iteración**
    - mientras-hacer-fmientras `Repite una serie de instrucciones mientras se cumpla una condición`
      ```
          mientras(condicion) hacer
            ...;
          fmientras
      ```
- **Ruptura de control**:
    -  Lleva al programa fuera del bloque en el que está localizada la ruptura.
       ```
       mientras(condicion) hacer
           ...;
           ruptura;
           ...; // Nunca llega a ejecutarse
       fmientras
       ```
- **Llamada a procedimiento o función**
- **Devolución de resultados de una función**
    - Interrumpe la ejecucion de una funcion y devuelve los valores especificados
      ```
        FUNCION mayor() dev (NUM e)
        VARIABLES
        INSTRUCCIONES
            ...;
            dev 42;
            ...; // Nunca llega a ejecutarse;
        FFUNCION
      ```
- **Mostrar por consola el valor de variables**
    - Permite mostrar por consola el valor de una expresion
        ```
        ...
        INSTRUCCIONES
        s = 12;
        mostrar(s); -> 
      
        ----- Salida del programa -----
        s -> 12
        ```
    
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

Estas variables serán representadas mediante una clase *Variable* que se encargara de asegurar su correcto tipado
en tiempo de ejecución. Así como de evitar el acceso a variables sin valor definido.

```java
public class Variable {
    /*
    * Clase que implementa una variable genérica
    */
    private String identificador;
    private Integer tipo;
    private Object valor;
    private Boolean RW = true; // Por defecto las variables son de lectura/escritura

    // Declaración sin valor usada por el analizador semántico
    public Variable(String identificador, Integer tipo);

    // Declaración con valor usada por el intérprete
    public Variable(String identificador, Integer tipo, Object valor);
    public Variable(String identificador, Integer tipo, Object valor, Boolean RW);

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
// (parámetro de salida var)
decl_var: ident=IDENTIFICADOR (COMA IDENTIFICADOR)* PyP t=tipo; // {var=Varible(ident, tipo)}

tipo: NUM // {t=entero}
    | LOG // {t=booleano}
    | SEQ_NUM // {t=secuencia_entera}
    | SEQ_LOG // {t=secuencia_booleana}
    ;

// (salida almacen de variables)
variables: VARIABLES var=(decl_var PyC)* // {almacenar cada var en almacen de variables} ;
```
##### Decision 2
Para interpretar un programa, es necesario calcular el valor de sus expresiones.

Dado que la resolución de una expresión depende de su contexto, es decir, depende de donde se encuentre
ubicada la expresión en el programa, se creara una clase genérica encargada de resolver una expresión a partir
de un contexto dado.

A grandes rasgos, la clase *ExprParser* recibe un almacén de variables y una lista con las funciones y procedimientos
disponibles y basándose en esta información, resuelve la expresión a un valor.

```java
public class ExprParser extends AnasintBaseVisitor<Object> {
    private HashMap<String, Variable> almacenVariables;
    private HashMap<String, Subprograma> subprogramas;

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

acceso_secuencia: ident=IDENTIFICADOR INICIO_CORCHETE elemento=expr_entera FIN_CORCHETE; // {getValorAcceso(ident, elemento)

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
//        devolver variable
//    sino ERROR variable no declarada
// )
// (funcion getValorAcceso(ident, elemento){
//      variable = getVariable(ident)
//      si variable es SEQ_LOG o SEQ_NUM entonces
//         si elemento < variable.size() entonces
//           devolver variable.valor[elemento]
//      devolver ERROR
//      
// (funcion suma(expr1, expr2){
//      valor1 = resuelve expr1
//      valor2 = resuelve expr2
//      devuelve valor1 + valor2
// )
// (funcion resta(expr1, expr2){
//      valor1 = resuelve expr1
//      valor2 = resuelve expr2
//      devuelve valor1 - valor2
// )
// (funcion multiplica(expr1, expr2){
//      valor1 = resuelve expr1
//      valor2 = resuelve expr2
//      devuelve valor1 * valor2
// )
```

##### Decision 3
Para interpretar un programa, es necesario conocer qué funciones y procedimientos existen, asi como conocer sus parametros
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
    public Object Execute(HashMap<String, Variable> variablesLocales, HashMap<String, Subprograma> subprogramas);
}
```
A su vez, los parametros de una llamada a función/procedimiento se encuentran encapsulados
en una clase *Parámetro*.

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

#### Decisión 4
Para poder interpretar un programa es necesario evaluar sus condiciones. Dado que las condiciones contienen expresiones,
su resolución depende del contexto y será gestionada por la clase *CondicionParser*.

Las comparaciones realizadas siempre deben de realizarse entre expresiones del mismo tipo.


**Clase CondicionParser**

```java
public class CondicionParser extends AnasintBaseVisitor<Object> {
    private HashMap<String, Variable> almacenVariables;
    private ExprParser exprParser;
    private HashMap<String, Subprograma> subprogramas;

    public CondicionParser(HashMap<String, Variable> almacenVariables, HashMap<String, Subprograma> subprogramas);
}
```


##### Gramática atribuida

```antlrv4
// (parametro de salida valor)
condicion_basica: expr1=expr IGUALDAD expr2=expr // {valor=(expr1 == expr2)}
| expr1=expr DESIGUALDAD expr2=expr // {valor=(expr1 != expr2)}
| expr1=expr MENOR_QUE expr2=expr // {valor=(expr1 < expr2)}
| expr1=expr MAYOR_QUE expr2=expr // {valor=(expr1 > expr2)}
| expr1=expr MAYOR_IGUAL_QUE expr2=expr // {valor=(expr1 >= expr2)}
| expr1=expr MENOR_IGUAL_QUE expr2=expr // {valor=expr1 <= expr2}
| CIERTO // {valor=T}
| FALSO //  {valor=F}
;
// (parametro de salida valor)
condicion_completa: cond1=condicion_completa CONJUNCION cond2=condicion_completa // {valor=(cond1 && cond2)}  
| cond1=condicion_completa DISYUNCION cond2=condicion_completa // {valor=(cond1 || cond2)}
| INICIO_PARENTESIS valor=condicion_completa FIN_PARENTESIS
| NEGACION cond=condicion_completa // {valor=!cond}
| valor=condicion_basica
;
```

#### Decision 5
Interpretar un programa es interpretar secuencialmente sus instrucciones.

Para analizar las instrucciones definiremos una clase *InstruccionesParser* que se encargará
de la interpretación de las instrucciones.

```java
public class InstruccionesParser extends AnasintBaseVisitor<Object> {
    /*
    Esta clase se encarga de parsear la seccion INSTRUCCIONES

    Pasar el almacen de variables al instanciar la clase permite utilizar la misma clase tanto para el programa como los subprogramas
     */
    private HashMap<String, Variable> almacenVariables;
    private ExprParser exprParser;
    private HashMap<String, Subprograma> subprogramas;

    public InstruccionesParser(HashMap<String, Variable> almacenVariables, HashMap<String, Subprograma> subprogramas);
}
```

Al delegar la interpretación de las instrucciones a una clase propia, el mismo codigo puede
ser utilizado para interpretar los subprogramas, dado que al instanciar la clase debemos proporcionar
información sobre el contexto sobre el que se interpreta.

##### Interpretar(asignaciones)
La instruccion de asignacion actualiza el valor de las variables contenidas en el almacen de variables con el valor de una expresion.

Esta asignacion puede ser simple, cuando se trata de una sola variable, o multiple, cuando se trata de varias.

```
asignacion: IDENTIFICADOR (COMA IDENTIFICADOR)* IGUAL expr (COMA expr)* PyC
{
    Si (coincide numero de variables y expresiones) entonces
        Se calcula el valor de todas las expresiones implicadas
        Se actualiza cada variable con su nuevo valor
    Sino 
        si hay una sola expresion y es una llamada a funcion entonces
            si parametrosSalidaFuncion.size() es igual a variables.size() entonces
               Se ejecuta la funcion y se actualiza cada variable con su nuevo valor
    El cualquier otro caso ERROR
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
Los subprogramas pueden ser llamados en las siguientes situaciones:
- Como una instrucción independiente (Solo procedimientos)
  - Las llamadas a funciones no producen cambios en el estado del programa.
- Como parte de una expresión (Solo funciones)
    - En una asignación (Puede devolver múltiples valores).
    - En cualquier otro caso (Tan solo puede devolver un valor).

Las llamadas a funciones/procedimientos funcionan de la siguiente forma:
1. Se comprueba que el subprograma existe
2. De existir se crea un almacén de variables locales para la función/procedimiento
3. Se incluyen en el almacén de variables las variables declaradas en la sección VARIABLES del subprograma
4. Si hay parámetros de entrada se resuelven las expresiones y se añaden los parámetros de entrada con su valor correspondiente.
    1. Si la expresión es una variable, se almacena la correspondencia entre variable pasada y local (En el caso de procedimientos)
5. Si hay parámetros de salida se definen en el almacén de variables locales.
6. Se ejecutan las instrucciones del subprograma
7. Si es función, se devuelven los valores devueltos por la función
8. Si es procedimiento, se modifican los valores de las variables pasadas al procedimiento

Una vez terminada la ejecución de una función/procedimiento, el flujo de ejecución continua normalmente. 

**Ejemplo de llamada a funcion**
```
PROGRAMA
VARIABLES
    valor:NUM;
SUBPROGRAMAS
    FUNCION AskTheUniverse(NUM entrada) dev (NUM salida)
    VARIABLES
        j: NUM;
    INSTRUCCIONES
        dev 42;
    FFUNCION
INSTRUCCIONES
   valor = 1;
   AskTheUniverse(valor); <- Asumimos que esta es la siguiente instruccion a interpretar
```
*Estado inicial*
```
almacenVariables = {"valor": Variable("valor", NUM, 1)}
almacenSubprogramas = {"AskTheUniverse": Subprograma("AskTheUniverse", [Parametro("entrada", NUM)],
[Parametro("salida", NUM)], [...])}
```

1. Se comprueba si el subprograma existe  
```¿AskTheUniverse se encuentra en almacenSubprogramas? -> Sí```
2. Creamos el almacen de variables locales de la funcion  
```almacenVariablesLocales = {}```
3. Añadimos al almacen las variables declaradas en la función.   
```almacenVariablesLocales = {"j": Variable("j", NUM, null)}```
4. Resolvemos las expresiones.
```valor -> 1```
    1. Añadimos los parametros de entrada al almacen local con su valor correspondiente  
```almacenVariablesLocales = {"j": Variable("j", NUM, null), Variable("entrada", NUM, 1)}```
    2. Añadimos los parametros de salida al almacen de variables  
```almacenVariablesLocales = {"j": Variable("j", NUM, null), Variable("entrada", NUM, 1), "salida": Variable("salida", NUM, null)}```
6. Ejecutamos las instrucciones de la funcion
7. El valor `42` es devuelto como resultado.

**Ejemplo de llamada a procedimiento**
```
PROGRAMA
VARIABLES
    valor:NUM;
SUBPROGRAMAS
    PROCEDIMIENTO AskTheUniverse(NUM entrada)
    VARIABLES
        j: NUM;
    INSTRUCCIONES
        entrada = 42;
    FPROCEDIMIENTO
INSTRUCCIONES
   valor = 1;
   AskTheUniverse(valor); <- Asumimos que esta es la siguiente instruccion a interpretar
```
*Estado inicial*
```
almacenVariables = {"valor": Variable("valor", NUM, 1)}
almacenSubprogramas = {"AskTheUniverse": Subprograma("AskTheUniverse", [Parametro("entrada", NUM)], null, [...])}
```

1. Se comprueba si el subprograma existe  
   ```¿AskTheUniverse se encuentra en almacenSubprogramas? -> Sí```
2. Creamos el almacen de variables locales del procedimiento  
   ```almacenVariablesLocales = {}```
3. Añadimos al almacen las variables declaradas en el procedimiento.   
   ```almacenVariablesLocales = {"j": Variable("j", NUM, null)}```
4. Resolvemos las expresiones.
   ```valor -> 1```
    1. Añadimos los parametros de entrada al almacen local con su valor correspondiente  
       ```almacenVariablesLocales = {"j": Variable("j", NUM, null), Variable("entrada", NUM, 1)}```
    2. Mantenemos la traduccion entre parametros de entrada y variables pasadas  
       ```variablesOriginales = {"entrada": Variable("valor", NUM, 1)}```
5. Ejecutamos las instrucciones del procedimiento.
6. Modificamos el valor de las variables pasadas como parámetros con el valor del almacen local.
    1. Traducimos el nombre de la variable local usando el traductor *variablesOriginales*
    ```entrada -> Variable("valor", NUM, 1)```
    2. Modificamos la variable
    ```Variable("valor", NUM, 1).setValor(42)```

##### Interpretar(condicional)
Las instrucciones condicionales ejecutan un bloque de código en función de si se cumple una condición o no.

Existen dos tipos de estructuras condicionales:
- si-fsi `Ejecuta el bloque de código contenido de cumplirse la condición`
- si-sino-fsi `De no cumplirse la condición ejecuta otro bloque de código`

Su implementación es sencilla:
1. Se resuelve la condición.
2. Si la condición es `cierta` se interpreta el primer bloque de código contenido.
3. Si la condición es `falsa` se interpreta el segundo bloque de código (Caso si-sino-fsi).
4. Se continúa la interpretación por la siguiente instrucción del programa.

**Ejemplo condicional**

*Se cumple la condición*
```
PROGRAMA
VARIABLES
    i:NUM;
SUBPROGRAMAS
INSTRUCCIONES
   i = 1;
   si (i == 1) entonces // La condición es cierta
        mostrar(i); // Se interpreta el bloque de código
   sino
        mostrar(2); // Nunca llega a interpretarse
   fsi
   -------------------- Salida --------------------
   i -> 1
```
*No se cumple la condición*
```
PROGRAMA
VARIABLES
    i:NUM;
SUBPROGRAMAS
INSTRUCCIONES
   i = 1;
   si (i == 2) entonces // La condición es falsa
        mostrar(i); // Nunca llega a interpretarse
   sino
        mostrar(2); // Se interpreta el bloque de código
   fsi
   -------------------- Salida --------------------
   2 -> 2
```

##### Interpretar(iteración)
Las instrucciones iterativas permiten repetir la interpretación de un bloque de código mientras
se cumpla una condición.

Su implementación es la siguiente:

1. Se resuelve la condición, de ser cierta el programa entra en un bucle
    1. Se interpretan las instrucciones del bloque de código
    2. Se resuelve la condición, de ser cierta continua el bucle.
2. De ser falsa la condición, se continúa la interpretación por la siguiente instrucción del programa.    

**Ejemplo de iteración**
```
PROGRAMA
VARIABLES
    i:NUM;
SUBPROGRAMAS
INSTRUCCIONES
   i = 1;
   mientras(i < 5) hacer
       mostrar(i);
       i = i + 1;
   fmientras
   -------------------- Salida --------------------
    i -> 1
    i -> 2
    i -> 3
    i -> 4
```
##### Interpretar(devolución)
La instrucción de devolución es usada en las funciones para indicar los valores que deben ser devueltos por la función.

Esta instrucción provoca el fin de la interpretación de las instrucciones de la función.

**Ejemplo devolución**
```
PROGRAMA
VARIABLES
    valor:NUM;
SUBPROGRAMAS
    FUNCION AskTheUniverse() dev (NUM salida)
    VARIABLES
    INSTRUCCIONES
        dev 42; // La función devuelve el valor 42
    FFUNCION
INSTRUCCIONES
   valor = AskTheUniverse(); <- la llamada a la función se resuelve al valor '42'
```

##### Intepretar(ruptura)
La instrucción de ruptura provoca que el programa abandone el bloque de código en el que se encuentra la instrucción.

**Ejemplo ruptura**
```
PROGRAMA
VARIABLES
    i:NUM;
SUBPROGRAMAS
INSTRUCCIONES
    i = 1;
    mientras(10 > i) hacer
        mostrar(i);
        i = i + 1;
        ruptura; // Termina la ejecución del bloque
    fmientras
    mostrar(i);
----- Salida -----
i -> 1
i -> 1
```

##### Intepretar(mostrar resultados por consola)
A efectos prácticos consideraremos que se trata de un procedimiento, por lo que no recibe un tratamiento especial
por parte del intérprete.