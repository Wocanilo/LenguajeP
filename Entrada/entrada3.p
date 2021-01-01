PROGRAMA
VARIABLES
    x,e:NUM;
    f:SEQ(NUM);
SUBPROGRAMAS
    FUNCION test(NUM a) dev (NUM b, NUM d)
        VARIABLES
            c:NUM;
        INSTRUCCIONES
            d = 1;
            b = 1;
            si(b == 1) entonces
                dev 42,43;
            fsi
            dev test(b-1), 12;
    FFUNCION
    FUNCION flista(SEQ(NUM) lista) dev (SEQ(NUM) list)
        VARIABLES
        INSTRUCCIONES
            lista[0] = 42;
            dev lista;
    FFUNCION
    INSTRUCCIONES
    x = 12;
    mostrar(x);
    x,e = test(12);
    mostrar(x);
    mostrar(e);
    f = [1,2];
    x = 0;
    f,x = [1,2,(x+1)],f[x+1];
    mostrar(f);
    mostrar(x);


