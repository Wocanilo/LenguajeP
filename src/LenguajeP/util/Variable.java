package LenguajeP.util;

import LenguajeP.Antlr.Anasint;

import java.util.List;
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
        this.setValor(valor);
    }

    public String getIdentificador() {
        return identificador;
    }

    public Integer getTipo() {
        return tipo;
    }

    public Object getValor() {
        if(this.valor == null) throw new RuntimeException(String.format("Runtime error: tried to access variable '%s' with null value.",
                this.getIdentificador()));
        return valor;
    }

    public void setValor(Object valor) {
        boolean throwError = false;
        if(this.getTipo() == Anasint.NUM && !Integer.class.isInstance(valor)) throwError = true;
        else if(this.getTipo() == Anasint.LOG && !Boolean.class.isInstance(valor)) throwError = true;
        else if(this.getTipo() == Anasint.SEQ_NUM) {
            if(List.class.isInstance(valor)){
                // Comprobamos que el tipo de cada elemento de la lista sea valido
                for(Object elemento: (List<Object>) valor){
                    if(Boolean.class.isInstance(elemento)){
                        throwError = true;
                        break;
                    }
                }
            }
            else throwError = true;
        }
        else if(this.getTipo() == Anasint.SEQ_LOG) {
            if(List.class.isInstance(valor)){
                // Comprobamos que el tipo de cada elemento de la lista sea valido
                for(Object elemento: (List<Object>) valor){
                    if(Integer.class.isInstance(elemento)){
                        throwError = true;
                        break;
                    }
                }
            }
            else throwError = true;
        }

        if(throwError) throw new RuntimeException(String.format("Runtime Error: variable '%s' has been assigned a value '%s' with invalid type. ",
                this.getIdentificador(), valor));

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
