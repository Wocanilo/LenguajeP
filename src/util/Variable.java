package util;

public class Variable {
    private String identificador;
    private Integer tipo;

    public Variable(String identificador, Integer tipo){
        this.identificador = identificador;
        this.tipo = tipo;
    }

    public String getIdentificador(){
        return this.identificador;
    }

    public Integer getTipo(){
        return this.tipo;
    }

    @Override
    public String toString(){
        return String.format("Variable(%s, %s)", this.identificador, this.tipo);
    }
}
