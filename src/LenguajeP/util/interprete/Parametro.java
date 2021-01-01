package LenguajeP.util.interprete;

import LenguajeP.Antlr.Anasint;

import java.util.Objects;

public class Parametro {
    private String identificador;
    private Integer tipo;

    public Parametro(String identificador, Integer tipo){
        this.identificador = identificador;
        this.tipo = tipo;
    }

    public String getIdentificador() {
        return identificador;
    }

    public Integer getTipo() {
        return tipo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Parametro parametro = (Parametro) o;
        return getIdentificador().equals(parametro.getIdentificador());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getIdentificador());
    }

    @Override
    public String toString() {
        return "Parametro{" +
                "identificador='" + identificador + '\'' +
                ", tipo=" + tipo +
                '}';
    }

    private String idToString(Integer id){
        switch(id){
            case Anasint.NUM:
                return "Integer";
            case Anasint.LOG:
                return "Boolean";
            case Anasint.SEQ_LOG:
                return "List<Boolean>";
            case Anasint.SEQ_NUM:
                return "List<Integer>";
            case Anasint.SEQ:
                return "List<Object>";
            case Anasint.NO_TIPO:
                // Se trata de una funcion predefinida que acepta todo tipo de variables
                return "Object";
            default:
                throw new RuntimeException("Compilation Errror: invalid variable type. NO_TIPO?");
        }
    }

    public String toJava(){
        return String.format("%s %s", this.idToString(this.tipo), this.identificador);
    }
}
