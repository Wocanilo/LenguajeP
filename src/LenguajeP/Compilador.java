package LenguajeP;

import LenguajeP.Antlr.Anasint;
import LenguajeP.Antlr.AnasintBaseVisitor;
import LenguajeP.util.compilador.InstruccionesCompiler;
import LenguajeP.util.interprete.*;
import LenguajeP.util.interprete.subprograma.Mostrar;
import LenguajeP.util.interprete.subprograma.Ultima_Posicion;
import LenguajeP.util.interprete.subprograma.Vacia;

import java.io.PrintWriter;
import java.util.*;

public class Compilador extends AnasintBaseVisitor<Object> {
    private HashMap<String, Subprograma> subprogramas;

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
            default:
                throw new RuntimeException("Compilation Errror: invalid variable type. NO_TIPO?");
        }
    }

    @Override
    public Object visitPrograma(Anasint.ProgramaContext ctx){

        // Procesamos las variables locales
        StringBuilder variablesMain = new StringBuilder();
        VariablesParser variablesParser = new VariablesParser();
        HashMap<String, Variable> almacenVariables = (HashMap<String, Variable>) variablesParser.visit(ctx.variables());

        // Declaramos las variables en el main
        for(Variable var: almacenVariables.values()){
            // Espaciado
            variablesMain.append(String.format("%s\n",var.toJava()));
        }

        // Procesamos los subprogramas
        SubprogramaParser subprogramasParser = new SubprogramaParser();
        this.subprogramas = (HashMap<String, Subprograma>) subprogramasParser.visit(ctx.subprogramas());
        // Definimos subprogramas predefinidos
        subprogramas.put("mostrar", new Mostrar());
        subprogramas.put("vacia", new Vacia());
        subprogramas.put("ultima_posicion", new Ultima_Posicion());

        // Traducimos los subprogramas
        StringBuilder subprogramasJava = new StringBuilder();

        for(Subprograma sub: subprogramas.values()){
            if(sub.getIdentificador().equalsIgnoreCase("mostrar")) continue;
            if(sub.getIdentificador().equalsIgnoreCase("vacia")) continue;
            if(sub.getIdentificador().equalsIgnoreCase("ultima_posicion")) continue;

            subprogramasJava.append(String.format("%s\n",subprogramaToJava(sub)));
        }

        /*
            Empieza formacion programa
         */
        StringBuilder programa = new StringBuilder();

        // Añadimos los imports
        programa.append("import java.util.ArrayList;\n");
        programa.append("import java.util.List;\n");

        // Procesamos las instrucciones del programa
        StringBuilder instrucciones = new StringBuilder();

        InstruccionesCompiler instruccionesParser = new InstruccionesCompiler(almacenVariables, subprogramas);
        // Creamos variables auxiliares
        instrucciones.append("List<Object> almacenTmp = new ArrayList<>();\n");
        instrucciones.append("Tupla almacenFuncion;\n");

        instrucciones.append(instruccionesParser.visit(ctx.instrucciones_programa()));

        // Formamos todo el programa
        String tuplaClass = "    static class Tupla {\n" +
                "        private Object[] valores;\n" +
                "        public Tupla(Object... valores){\n" +
                "            this.valores = valores;\n" +
                "        }\n" +
                "        public Object getValor(Integer elemento) {\n" +
                "            if(elemento < this.valores.length) return this.valores[elemento];\n" +
                "            throw new RuntimeException(\"Runtime Error: out of bound access in Tupla\");\n" +
                "        }\n" +
                "    }\n\n";

        programa.append("\nclass EjemploCompilado {\n" +
                tuplaClass +
                subprogramasJava +
                "public static void main(String[] args) {\n" +
                "// Seccion VARIABLES\n" +
                variablesMain +
                "// Seccion INSTRUCCIONES\n" +
                instrucciones +
                "   }\n" +
                "}");


        System.out.println("----- Salida -----");
        System.out.println(programa);

        try (PrintWriter out = new PrintWriter("../Salida/EjemploCompilado.java")) {
            out.println(programa);
        }catch (java.io.FileNotFoundException e){
            System.out.println("Error al abrir el archivo de salida.");
        }

        return null;
    }

    String subprogramaToJava(Subprograma sub){
        HashMap<String, Variable> variablesLocales = new HashMap<>(sub.getVariablesLocales()); // Variables locales declaradas en la seccion VARIABLES
        // Parametros de entrad
        String entrada = "";
        // Parametros de salida
        String salida = "";
        // Seccion VARIABLES
        StringBuilder seccionVariables = new StringBuilder();
        // Salida implícita
        List<String> salidaImplicita = new ArrayList<>();

        for(Variable var: sub.getVariablesLocales().values()){
            seccionVariables.append(String.format("%s\n", var.toJava()));
        }

        // Procesamos las instrucciones del subprograma
        StringBuilder instrucciones = new StringBuilder();
        InstruccionesCompiler instruccionesParser = new InstruccionesCompiler(variablesLocales, this.subprogramas);
        instrucciones.append("List<Object> almacenTmp = new ArrayList<>();\n");
        instrucciones.append("Tupla almacenFuncion;\n");


        if(sub.parametrosEntrada != null){
            List<String> paramEntrada = new ArrayList<>();
            for(Parametro par: sub.parametrosEntrada){
                paramEntrada.add(par.toJava());
                salidaImplicita.add(String.format("%s", par.getIdentificador()));

                Variable var = new Variable(par.getIdentificador(), par.getTipo());
                if(variablesLocales.containsKey(var)) throw new RuntimeException(String.format("Compilation Error: redeclared variable '%s' in subprogram '%s'", var.getIdentificador(),
                        sub.identificador));
                variablesLocales.put(var.getIdentificador(), var);
            }
            // Los unimos
            entrada = String.join(", ", paramEntrada);
        }

        // Si hay
        if(sub.isEsFuncion()){
            // Debe de haber parametros de salida
            List<String> paramSalida = new ArrayList<>();
            for(Parametro par: sub.parametrosSalida){
                Variable var = new Variable(par.getIdentificador(), par.getTipo());
                if(variablesLocales.containsKey(var)) throw new RuntimeException(String.format("Compilation Error: redeclared variable '%s' in subprogram '%s'", var.getIdentificador(),
                        sub.identificador));
                variablesLocales.put(var.getIdentificador(), var);
                paramSalida.add(String.format("%s %s = null;", this.idToString(par.getTipo()), par.getIdentificador()));
            }
            salida = String.join("\n", paramSalida);

            // Procesamos instrucciones
            Object instruccionesSubprograma = sub.getInstruccionesSubprograma();
            if(instruccionesSubprograma != null){ // Si es nulo es un subprograma predefinido
                for(Anasint.Instrucciones_funcionContext instruccion: (List<Anasint.Instrucciones_funcionContext>) instruccionesSubprograma){
                    instrucciones.append(instruccionesParser.visit(instruccion));
                }
            }

            if(sub.getParametrosSalida().size() == 1) return String.format("public static %s %s(%s){\n%s\n%s\n%s}", this.idToString(sub.getParametrosSalida().get(0).getTipo()), sub.getIdentificador(), entrada,
                    salida, seccionVariables, instrucciones);

            return String.format("public static Tupla %s(%s){\n%s\n%s\n%s}", sub.getIdentificador(), entrada, salida, seccionVariables, instrucciones);
        }else{
            // Procesamos instrucciones
            Object instruccionesSubprograma = sub.getInstruccionesSubprograma();
            if(instruccionesSubprograma != null) { // Si es nulo es un subprograma predefinido
                for (Anasint.Instrucciones_procedimientoContext instruccion : (List<Anasint.Instrucciones_procedimientoContext>) instruccionesSubprograma) {
                    instrucciones.append(instruccionesParser.visit(instruccion));
                }
            }
            // Devolución implícita de valores de entrada
            instrucciones.append(String.format("return new Tupla(%s);\n", String.join(", ", salidaImplicita)));

            return String.format("public static Tupla %s(%s){\n%s\n%s}", sub.getIdentificador(), entrada, seccionVariables, instrucciones);
        }
    }
}
