package LenguajeP.util.interprete.subprograma;

import LenguajeP.Antlr.Anasint;
import LenguajeP.util.interprete.Parametro;
import LenguajeP.util.interprete.Subprograma;
import LenguajeP.util.interprete.Variable;

import java.util.ArrayList;
import java.util.HashMap;

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
        System.out.printf("%s -> %s%n", var.getIdentificador(), var.getValor());
        return new HashMap<>();
    }
}
