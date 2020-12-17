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
```p
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

### Decisión 1 - Variables
Para poder ejecutar un programa en P, es necesario conocer qué variables existen, de qué tipo son y qué valor tienen.

Para este fin, se creará un almacén de variables, que contendrá el estado de cada variable declarada.

| identificador | tipo               | valor
|---------------|--------------------|------- |
| a             | entero             |1|
| d             | booleano           |T|
| e             | secuencia_entera   |1,2,3,4|
| g             | secuencia_booleana |T,T,F,F|

#### Gramática atribuida
```
programa: PROGRAMA variables subprogramas instrucciones EOF;

(parámetro de salida t)
tipo: NUM {t=entero}
    | LOG {t=booleano}
    | SEQ_NUM {t=secuencia_entera}
    | SEQ_LOG {t=secuencia_booleana}
    ;

variables: VARIABLES d=(decl_var PyC)* {almacenar cada d en almacen de variables} ;
```
