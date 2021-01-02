# Especificación Compilador

## Objetivo
Construir un compilador de P a Java. Esto es, un programa capaz de traducir un programa escrito en P a Java.

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
Los parámetros de *entrada* son de **solo lectura** y los de *salida* de **lectura/escritura**.

La funciones deben incluir en algún punto de sus instrucciones un retorno explícito de los parámetros de sálida mediate la instrucción *dev*.

Las funciones pueden formar parte de una expresión *si solo devuelven un valor*.

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

Las variables de entrada en un procedimiento son de **lectura/escritura**. Es decir, las variables se pasan por *referencia*, por lo
que cualquier cambio realizado se ve reflejado en la variable pasada.

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
    - Se produce un salto en el flujo de ejecución del programa, que pasa a interpretar las instrucciones del subprograma llamado.
      Al finalizar la llamada, el flujo de ejecución continua desde donde fue interrumpido.
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

## Decision 1 (Variables)
Para poder compilar un programa, debemos conocer las variables declaradas,
para así poder declararlas en el lenguaje destino correctamente.

Tanto P como Java presentan un tipado fuerte, en ninguno de los dos lenguajes es posible modificar el tipo de
una variable en tiempo de ejecución y tanto P como Java permiten que las variables tengan un valor indefinido.
Además, todas las operaciones entre tipos se encuentran en el lenguaje destino, así como el orden de precedencia
presente en P.

Por tanto, podemos realizar una traducción directa entre las variables de ambos lenguajes, siguiendo la siguiente
tabla de traducción de tipos.

| Tipo en P | Tipo en Java  |
|-----------|---------------|
| NUM       | Integer       |
| LOG       | Boolean       |
| SEQ(NUM)  | List\<Integer> |
| SEQ(LOG)  | List\<Boolean> |

Dado que solo se utilizan clases para representar los tipos de P, al no inicializar una variable esta tiene un valor
indefinido o nulo por defecto.

Será necesario importar la clase *List* si el programa contiene alguna variable de tipo Lista.

Para el parseo de las variables usaremos la clase *VariablesParser* usada en el intérprete, obteniendo así una estructura de datos
con instancias de la clase *Variable*. A esta clase añadiremos un método *toJava* que devolverá la representación en el lenguaje Java
de la declaración de la variable.

**Ejemplo de traducción**

*Declaración de variables en el lenguaje P*
```
...
VARIABLES
    a,b:NUM;
    c:SEQ(LOG);    
...
```
*Declaración de variables en el lenguaje Java*
```
Integer a;
Integer b;
List<Boolean> c;
```

Estas variables serán declaradas al inicio del programa principal o de la función o procedimiento correspondiente.
Dado que Java implementa el concepto de variable local, no será necesario realizar ninguna modificación especial.
## Decisión 2 (subprogramas)
Es necesario traducir las funciones y procedimientos al lenguaje Java. Las funciones en P pueden devolver múltiples valores, mientras que en Java
solo es posible devolver un valor.

Será necesario crear una clase *Tupla* que contenga los valores devueltos por una función.

```java
class Tupla{
    /*
            Clase que encapsula los valores devueltos por una función.
     */
    // Se permite especificar un número arbitrario de valores
    public Tupla(Object... valores);
    
    public Object getValor(Integer posicion);
}
```

Toda función que devuelva múltiples valores devolverá un objeto *Tupla*. El resto de funciones devolverán
el resultado de manera directa. De esta forma seguiremos beneficiándonos de la comprobación de tipos de Java.

Por otro lado, dado que los parámetros de entrada de una función son de solo lectura, debemos asegurar que sus valores
no son modificados. En el caso de valores de tipo *Integer* y *Boolean*, al ser objetos inmutables, no debemos hacer nada,
sin embargo, en el caso de objetos de tipo lista, debemos asegurar que su contenido no es modificado. Para ello debemos asegurar
que se pasan copias de los objetos originales a las funciones llamadas con estos tipos.

## Decisión 3 (asignaciones)
Dado que las asignaciones múltiples no existen en Java, será necesario emularlas. Esto significa emular la asignación
paralela.

Para ello se comprobará si en alguna de las expresiones se hace referencia a alguna de las variables afectadas por
la asignación. De ser así, se almacenará el valor original de la variable, usando este valor almacenado en las
expresiones de la asignación.

**Resolución de expresiones en una asignación**
1. Se obtiene el *conjunto* de variables que intervienen en las expresiones de la asignación
2. Se consideran variables colisionadas a aquellas variables que aparecen en ambos lados de una asignación, cuando
   aparecen en expresiones que no son la suya
    1. ```a,b = 12, a+1;``` `Hay colisión`
    2. ```a,b = a+1,b;```  `No hay colisión`
3. Se almacena el valor original de las variables colisionadas en un almacén y se almacena su posición en el mismo
4. Se modifican las expresiones para que hagan referencia al valor contenido en el almacén.
5. Se vacía el almacén para la próxima asignación.

**Ejemplos**

*Asignación múltiple sin variables*

- *Lenguaje P*
    ```
    a,b = 5, 23;
    ```
- *Lenguaje Java*
    ```
    a = 5;
    b = 23;
    ```

*Asignación múltiple con variables*
- *Lenguaje P*
    ```
    a, b = 12, a + 1;
    ```
- *Lenguaje Java*
   ```
    almacenTmp.add(a);
    a = 12;
    b = ((Integer)almacenTmp.get(0)) + 1;
    almacenTmp.clear();
   ```

Otro problema adicional vendrá dada por las llamadas a funciones que retornan múltiples valores. Será
necesario utilizar una variable auxiliar que contenga el objeto *Tupla* devuelto por la función para luego
asignar su valor a las variables correspondientes. Dado que las tuplas contendrán objetos de manera genérica, será necesario
castear el valor al tipo correspondiente a la variable para su correcta asignación.

*Ejemplo llamadas a funciones*
- *Lenguaje P*
    ```
    a, b = devuelve_2_valores();
    ```
- *Lenguaje Java*
   ```
    Tupla tmp = devuelve_2_valores();
    a = (Integer) tmp.getValor(0);
    b = (Integer) tmp.getValor(1);
   ```

## Decisión 4 (condiciones)
Las condiciones en P se construyen a base de igualdades (==) y desigualdades (!=, <, >, <=, >=) de expresiones del mismo tipo.
Además, estas condiciones se pueden concatenar mediante operadores de conjunción (&&) y disyunción (||), además
de ser posible utilizar la negación (!).

En Java, todo lo dicho anteriormente es cierto. Sin embargo, el orden de precedencia de los operadores es distinto:

*Orden de precedencia en orden decreciente*

| P               | Java            |
|-----------------|-----------------|
| == !=           | !               |      
| < > <= >=       | < > <= >=       |
| !               | == !=           |
| && &#124;&#124; | && &#124;&#124; |

Para asegurar una correcta evaluación de las condiciones, se envolverán entre paréntesis las igualdades y desigualdades,
para así asegurar que son evaluadas en orden correcto.

## Decisión 5 (expresiones)
Las expresiones en Java y P permiten el mismo tipo de operaciones, así como siguen el mismo orden de precedencia de sus operadores.
Por tanto, no debemos realizar ninguna modificación a las expresiones, excepto en casos donde se produzcan colisiones.


