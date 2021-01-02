PROGRAMA
VARIABLES
   a,b:LOG;
   i,j:NUM;
   s:SEQ(NUM);
   t:SEQ(LOG);
SUBPROGRAMAS
   FUNCION verdadero() dev (LOG r)
      VARIABLES
      INSTRUCCIONES
         r=T;
         dev r;
   FFUNCION
   FUNCION siguiente(NUM i) dev (NUM r)
         VARIABLES
         INSTRUCCIONES
            r=i+1;
            dev r;
   FFUNCION
   PROCEDIMIENTO asignar(NUM variable, NUM valor)
      VARIABLES
      INSTRUCCIONES
         variable = valor;
   FPROCEDIMIENTO
INSTRUCCIONES
   j=0;
   asignar(i,1*(j+2));
   mostrar(i);
   a=verdadero();
   mostrar(a);
   s=[i,siguiente(siguiente(1))];
   mostrar(s);
   t=[a,verdadero(),F];
   mostrar(t);
   t=[];
   mostrar(t);