package LenguajeP.util;

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
}
