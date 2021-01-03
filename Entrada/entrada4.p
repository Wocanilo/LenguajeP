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
    PROCEDIMIENTO triplica(NUM n)
        VARIABLES
        INSTRUCCIONES
            n = n * 3;
    FPROCEDIMIENTO
    FUNCION prueba(NUM a) dev(NUM b, LOG c)
        VARIABLES
        INSTRUCCIONES
            dev a,a-1;
    FFUNCION
INSTRUCCIONES
    i = 0;
    e = 0;
    // Creamos lista
    listafibo = [0,0,0,0,0,0,0,0,0,0];
    // Sustituimos sus valores
    mientras(i < ultima_posicion(listafibo)) hacer
        listafibo[i] = fibonacci(i);
        i = i + 1;
    fmientras
    mostrar(listafibo);
    // Buscamos posicion elemento
    mostrar(busca_entero_en_lista(21, listafibo));
    // Triplicamos el valor de i
    mostrar(i);
    triplica(i);
    mostrar(i);
