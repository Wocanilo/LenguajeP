PROGRAMA
VARIABLES
   a:LOG;
   i,e:NUM;
   listafibo:SEQ(NUM);
   lista_menores:SEQ(LOG);
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
    FUNCION elementos_menores(NUM n, SEQ(NUM) lista) dev(SEQ(LOG) salida)
        VARIABLES
            i:NUM;
        INSTRUCCIONES
            i = 0;
            salida = [];
            mientras(i < ultima_posicion(lista)) hacer
                si(lista[i] < n) entonces
                    salida[ultima_posicion(salida)] = T;
                sino
                    salida[ultima_posicion(salida)] = F;
                fsi
                i = i + 1;
            fmientras
            dev salida;
    FFUNCION
    //Prueba de comentario en una linea
    PROCEDIMIENTO invierte_lista(SEQ(NUM) lista)
        VARIABLES
            i:NUM;
            salida:SEQ(NUM);
        INSTRUCCIONES
            i = ultima_posicion(lista) - 1;
            salida = [];
            mientras(i >= 0) hacer
                salida[ultima_posicion(salida)] = lista[i];
                i = i - 1;
            fmientras
            lista = salida;
    FPROCEDIMIENTO
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
   /* Creacion lista de valores LOG */
   lista_menores = elementos_menores(13, listafibo);
   mostrar(lista_menores);
   /* Invertimos la lista de fibo */
   invierte_lista(listafibo); // Prueba de comentario en una linea
   mostrar(listafibo);

