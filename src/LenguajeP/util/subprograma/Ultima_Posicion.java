package LenguajeP.util.subprograma;

import LenguajeP.Antlr.Anasint;
import LenguajeP.util.Parametro;
import LenguajeP.util.Subprograma;
import LenguajeP.util.Variable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Ultima_Posicion extends Subprograma {
    public Ultima_Posicion(){
        // TODO: esta solucion es elegante pero no muestra el nombre de la variable original debido a la traduccion realizada
        // o se ofrece la posibilidad de hacer bypass de la traduccion o se hace un bypass de los subprogramas.
        this.identificador = "ultima_Posicion";
        this.parametrosEntrada = new ArrayList<>();
        this.parametrosEntrada.add(new Parametro("a", Anasint.NO_TIPO)); // No tipo -> Admite cualquier tipo
        this.almacenVariables = new HashMap<>();

        this.parametrosSalida = new ArrayList<>();
        this.parametrosSalida.add(new Parametro("salida", Anasint.NUM));
        this.esFuncion = true;
    }

    @Override
    public Object Execute(HashMap<String, Variable> variablesLocales, HashMap<String, Subprograma> subprogramas){
        Object entrada = variablesLocales.get("a").getValor();

        if(!List.class.isInstance(entrada)) throw new RuntimeException("Runtime Error: vacia can only be used with lists");
        // Comprobamos si la lista esta vacia
        List<Variable> salida = new ArrayList<>();
        salida.add(new Variable("salida", Anasint.NUM, ((List<Object>) entrada).size()));

        return salida;
    }
}
