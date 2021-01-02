package LenguajeP.util.interprete.subprograma;

import LenguajeP.Antlr.Anasint;
import LenguajeP.util.interprete.Parametro;
import LenguajeP.util.interprete.Subprograma;
import LenguajeP.util.interprete.Variable;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Mostrar extends Subprograma {
    public Mostrar(){
        this.identificador = "mostrar";
        this.parametrosEntrada = new ArrayList<>();
        this.parametrosEntrada.add(new Parametro("*", Anasint.NO_TIPO)); // No tipo -> Admite cualquier tipo
        this.almacenVariables = new HashMap<>();

        this.parametrosSalida = new ArrayList<>();
        this.esFuncion = false;
    }

    @Override
    public HashMap<String, Variable> Execute(HashMap<String, Variable> variablesLocales, HashMap<String, Subprograma> subprogramas){
        // Mostramos por pantalla el valor de la expresion pasada
        Variable var = variablesLocales.get("*");

        Object valor = var.getValor();

        if(Boolean.class.isInstance(valor)){
            String salida = "F";
            if(((Boolean)valor)) salida = "T";
            valor = salida;
        }

        if(List.class.isInstance(valor)){
            List<Object> salida = new ArrayList<>();
            for(Object elemento: (List<Object>) valor){
                if(Boolean.class.isInstance(elemento)){
                    String valorLOG = "F";
                    if(((Boolean)elemento)) valorLOG = "T";

                    salida.add(valorLOG);
                }else{
                    salida.add(elemento);
                }
            }
            valor = salida;
        }

        System.out.printf("%s -> %s%n", var.getIdentificador(), valor);
        return new HashMap<>();
    }
}
