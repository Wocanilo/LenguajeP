package LenguajeP.util;

import LenguajeP.Antlr.Anasint;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Mostrar extends Subprograma{
    public Mostrar(){
        this.identificador = "mostrar";
        this.parametrosEntrada = new ArrayList<>();
        this.parametrosEntrada.add(new Parametro("a", Anasint.NO_TIPO)); // No tipo -> Admite cualquier tipo
        this.almacenVariables = new HashMap<>();

        this.parametrosSalida = new ArrayList<>();
        this.esFuncion = false;
    }

    @Override
    public HashMap<String, Variable> Execute(HashMap<String, Variable> variablesLocales){
        // Mostramos por pantalla el valor de la expresion pasada
        System.out.println(String.format("mostrar -> %s", variablesLocales.get("a").getValor()));
        return new HashMap<>();
    }
}
