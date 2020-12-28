PROGRAMA
VARIABLES
/* Comprobamos creacion de todo tipo de variable */
   a:LOG;
   i,e:NUM;
   listafibo:SEQ(NUM);
   t:SEQ(LOG);
SUBPROGRAMAS
    FUNCION fibonacci(NUM n) dev(NUM res)
        VARIABLES
        INSTRUCCIONES
            si(n <= 1) entonces
                dev n;
            fsi
            dev fibonacci(n-1) + fibonacci(n-2);
    FFUNCION
    FUNCION busca_entero_en_lista(NUM n, SEQ(NUM) lista) dev(NUM indice)
        VARIABLES
            i:NUM;
        INSTRUCCIONES
        i=0;
        si (vacia(lista)) entonces
            dev -1;
        fsi
        i = 0;
        mientras(i < ultima_posicion(lista)) hacer
            si(lista[i] == n) entonces
                dev i;
            fsi
        i=i+1;
        fmientras
        dev -1;
    FFUNCION
    PROCEDIMIENTO crealista(NUM n, SEQ(NUM) listafibo)
        VARIABLES
        INSTRUCCIONES
                mientras(ultima_posicion(listafibo) < 10) hacer
                    listafibo[ultima_posicion(listafibo)] = fibonacci(ultima_posicion(listafibo)+1);
                fmientras
    FPROCEDIMIENTO
    FUNCION nest(NUM a) dev (NUM b)
        VARIABLES
            i:NUM;
        INSTRUCCIONES
            i = a;
            i = i + 5;
            i = i * 2;
            dev i;
    FFUNCION
INSTRUCCIONES
   listafibo = [];
   /* Rellenamos la lista con los primeros numeros */
   crealista(9, listafibo);
   /* Mostramos la lista */
   mostrar(listafibo);
   /* Buscamos la posicion del elemento con valor 34 y cambiamos su valor a 1337 */
   listafibo[busca_entero_en_lista(34, listafibo)] = 1337;
   mostrar(listafibo);
   /* Funciones anidadas */
   mostrar(-nest(nest(5)));
   /* Multiples parentesis */
   i = ((((((nest(5)))))))-1;
   mostrar(i);