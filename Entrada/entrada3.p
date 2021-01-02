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
    PROCEDIMIENTO pruebamodificacion(NUM n, NUM f, NUM d)
        VARIABLES
        INSTRUCCIONES
            n = 55;
    FPROCEDIMIENTO
INSTRUCCIONES
    i = 0;
    listafibo = [1,2,3,4,5,6,7,8,9,10];
    mientras(i < ultima_posicion(listafibo)) hacer
        listafibo[i] = fibonacci(i);
        i = i + 1;
    fmientras
    mostrar(listafibo);
    mostrar(busca_entero_en_lista(21, listafibo));
    mostrar(i);
    pruebamodificacion(i, 12, listafibo);
    mostrar(i);
    mostrar(e);

