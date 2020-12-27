package LenguajeP.util.interprete;

import LenguajeP.Antlr.Anasint;

import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class Subprograma {
    protected String identificador;
    protected List<Parametro> parametrosEntrada;
    protected List<Parametro> parametrosSalida;

    protected HashMap<String, Variable> almacenVariables;
    protected Object instruccionesSubprograma;

    // Indica si es funcion o procedimiento
    protected boolean esFuncion;

    public Subprograma(){};

    public Subprograma(String identificador, List<Parametro> parametrosEntrada, List<Parametro> parametrosSalida,
                       HashMap<String, Variable> almacenVariables, List<Anasint.Instrucciones_funcionContext> instruccionesSubprograma){
        this.identificador = identificador;
        this.parametrosEntrada = parametrosEntrada;
        this.parametrosSalida = parametrosSalida;
        this.almacenVariables = almacenVariables;
        this.instruccionesSubprograma = instruccionesSubprograma;

        this.esFuncion = true;
    }

    public Subprograma(String identificador, List<Parametro> parametrosEntrada,
                       HashMap<String, Variable> almacenVariables, List<Anasint.Instrucciones_procedimientoContext> instruccionesSubprograma){
        this.identificador = identificador;
        this.parametrosEntrada = parametrosEntrada;
        this.almacenVariables = almacenVariables;
        this.instruccionesSubprograma = instruccionesSubprograma;

        this.esFuncion = false;
    }

    public Object Execute(HashMap<String, Variable> variablesLocales, HashMap<String, Subprograma> subprogramas){
        // Si es funcion hay parametros de salida
        if(this.esFuncion) {
            for(Parametro salida: this.parametrosSalida){
                if(variablesLocales.containsKey(salida.getIdentificador())) throw new RuntimeException(String.format("Runtime Error: local variable '%s' already exists.", salida.getIdentificador()));
                variablesLocales.put(salida.getIdentificador(), new Variable(salida.getIdentificador(), salida.getTipo()));
            }
        }

        // Ejecutamos las instrucciones de la funcion/procedimiento
        InstruccionesParser instruccionesParser = new InstruccionesParser(variablesLocales, subprogramas); // No se permite anidar llamadas a funciones/procedimientos

        if(this.esFuncion){
            // Es una funcion
            for(Anasint.Instrucciones_funcionContext instruccion: (List<Anasint.Instrucciones_funcionContext>) this.instruccionesSubprograma){
                List<Object> valoresDev = null;
                Boolean esDevolver = false;

                // Comprobamos que la instruccion no sea un dev
                if(instruccion.devolver() != null){
                    valoresDev = (List<Object>) instruccionesParser.visit(instruccion);
                    esDevolver = true;
                }else{
                    // Puede devolver un valor si se ejecuta un dev dentro de un condicional o un iterativo
                    Object valor = instruccionesParser.visit(instruccion);

                    if(List.class.isInstance(valor)){
                        // Solo los dev devuelven listas
                        valoresDev = (List<Object>) valor;
                        esDevolver = true;
                    }
                }

                if(esDevolver){
                    // Comprobamos el numero de los valores devueltos
                    if(valoresDev != null && valoresDev.size() != this.parametrosSalida.size()) throw new RuntimeException(String.format("Runtime Error: invalid number of return values in '%s'",
                            this.getIdentificador()));
                    // Fin de la ejecucion
                    return valoresDev;
                }
            }
        }else{
            // Es un procedimiento

            // Variables de salida
            HashMap<String, Variable> salida = new HashMap<>();

            for(Anasint.Instrucciones_procedimientoContext instruccion: (List<Anasint.Instrucciones_procedimientoContext>) this.instruccionesSubprograma){
                Object valor = instruccionesParser.visit(instruccion);
                if(List.class.isInstance(valor)) throw new RuntimeException("Runtime Error: ilegal use of dev in procedure."); // Se ha llamado a dev en un procedimiento
            }
            // Al ser un procedimiento, la salida seran los valores actuales de los parametros de entrada
            for(Parametro var: this.parametrosEntrada){
                salida.put(var.getIdentificador(), variablesLocales.get(var.getIdentificador()));
            }
            return salida;
        }

       throw new RuntimeException(String.format("Runtime Error: function '%s' never returns.",
                this.getIdentificador()));
    }

    public String getIdentificador() {
        return identificador;
    }

    public boolean isEsFuncion() {
        return esFuncion;
    }

    public List<Parametro> getParametrosEntrada() {
        return parametrosEntrada;
    }

    public HashMap<String, Variable> getVariablesLocales() {
        return almacenVariables;
    }

    public List<Parametro> getParametrosSalida() {
        return parametrosSalida;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Subprograma that = (Subprograma) o;
        return identificador.equals(that.identificador);
    }

    @Override
    public int hashCode() {
        return Objects.hash(identificador);
    }

    @Override
    public String toString() {
        return "Subprograma{" +
                "identificador='" + identificador + '\'' +
                ", esFuncion='" + esFuncion + '\'' +
                '}';
    }
}
