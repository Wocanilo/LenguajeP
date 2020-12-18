package LenguajeP.util;

import java.util.Objects;

public class Variable {
    /*
    * Clase que implementa una variable genérica
    */
    private String identificador;
    private Integer tipo;
    private Object valor;

    // Declaración sin valor usada por el analizador semántico
    public Variable(String identificador, Integer tipo){
        this.identificador = identificador;
        this.tipo = tipo;
    }

    // Declaración con valor usada por el intérprete
    public Variable(String identificador, Integer tipo, Object valor){
        this.identificador = identificador;
        this.tipo = tipo;
        this.valor = valor;
    }

    public String getIdentificador() {
        return identificador;
    }

    public Integer getTipo() {
        return tipo;
    }

    public Object getValor() {
        return valor;
    }

    public void setIdentificador(String identificador) {
        this.identificador = identificador;
    }

    public void setTipo(Integer tipo) {
        this.tipo = tipo;
    }

    public void setValor(Object valor) {
        this.valor = valor;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Variable variable = (Variable) o;
        return getIdentificador().equals(variable.getIdentificador());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getIdentificador());
    }

    @Override
    public String toString() {
        return "Variable{" +
                "identificador='" + identificador + '\'' +
                ", tipo=" + tipo +
                ", valor=" + valor +
                '}';
    }
}
