package LenguajeP;

import LenguajeP.Antlr.Anasint;
import LenguajeP.Antlr.AnasintBaseVisitor;
import LenguajeP.util.compilador.InstruccionesCompiler;
import LenguajeP.util.interprete.*;
import LenguajeP.util.interprete.subprograma.Mostrar;
import LenguajeP.util.interprete.subprograma.Ultima_Posicion;
import LenguajeP.util.interprete.subprograma.Vacia;

import java.util.*;

public class Compilador extends AnasintBaseVisitor<Object> {
    private HashMap<String, Variable> almacenVariables;
    private HashMap<String, Subprograma> subprogramas;
    // TODO: lo que haremos será analizar el árbol dos veces, la primera vez se identifican errores, como devolver en un procedimiento o devolver más valores de los declarados en una función
    // Luego el segundo análisis compila el programa con la información obtenida del primer parseo
    // Asi podemos decidir qué subprogramas necesitan una declaración de almacenTmp y demas. Asi como conocer los imports necesarios para que funcione el programa

    private String tuplaClass = "    static class Tupla {\n" +
            "        private Object[] valores;\n" +
            "        public Tupla(Object... valores){\n" +
            "            this.valores = valores;\n" +
            "        }\n" +
            "        public Object getValor(Integer elemento) {\n" +
            "            if(elemento < this.valores.length) return this.valores[elemento];\n" +
            "            throw new RuntimeException(\"Runtime Error: out of bound access in Tupla\");\n" +
            "        }\n" +
            "    }\n\n";

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

        // Imports
        Set<String> imports = new TreeSet<>();

        // Cadena de la funcion main
        StringBuilder variablesMain = new StringBuilder();

        // Procesamos las variables locales
        VariablesParser variablesParser = new VariablesParser();
        this.almacenVariables = (HashMap<String, Variable>) variablesParser.visit(ctx.variables());

        // Declaramos las variables en el main
        for(Variable var: this.almacenVariables.values()){
            // Debemos importar la clase Lista si existe alguna variable de tipo Lista
            if(var.getTipo() == Anasint.SEQ_NUM || var.getTipo() == Anasint.SEQ_LOG) imports.add("java.util.List");
            // Espaciado
            variablesMain.append(String.format("%s\n",var.toJava()));
        }

        // Procesamos los subprogramas
        SubprogramaParser subprogramasParser = new SubprogramaParser();
        this.subprogramas = (HashMap<String, Subprograma>) subprogramasParser.visit(ctx.subprogramas());
        // Definimos predicados predefinidos
        subprogramas.put("mostrar", new Mostrar());
        subprogramas.put("vacia", new Vacia());
        subprogramas.put("ultima_posicion", new Ultima_Posicion());

        // Traducimos los programas
        StringBuilder subprogramasJava = new StringBuilder();

        for(Subprograma sub: subprogramas.values()){
            if(sub.getIdentificador().equalsIgnoreCase("mostrar")) continue;
            if(sub.getIdentificador().equalsIgnoreCase("vacia")) continue;
            if(sub.getIdentificador().equalsIgnoreCase("ultima_posicion")) continue;

            subprogramasJava.append(String.format("%s\n",subprogramaToJava(sub)));
        }

        // Formamos el programa
        StringBuilder programa = new StringBuilder();

        // Añadimos los imports
        for(String importUnico: imports){
            programa.append(String.format("import %s;\n", importUnico));
        }


        // Procesamos las instrucciones del programa
        StringBuilder instrucciones = new StringBuilder();

        InstruccionesCompiler instruccionesParser = new InstruccionesCompiler(this.almacenVariables, subprogramas);
        instrucciones.append("List<Object> almacenTmp = new ArrayList<>();\n");
        instrucciones.append("Tupla almacenFuncion;\n");

        instrucciones.append(instruccionesParser.visit(ctx.instrucciones_programa()));

        /*programa.append("\nclass ProgramaP {\n" +
                this.tuplaClass +
                subprogramasJava +
                "   public static void main(String[] args) {\n" +
                variablesMain +
                instrucciones +
                "   }\n" +
                "}");*/

        programa.append("\nclass EjemploCompilado {\n" +
                this.tuplaClass +
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

        return null;
    }

    String subprogramaToJava(Subprograma sub){
        HashMap<String, Variable> variablesLocales = new HashMap<>();
        variablesLocales.putAll(sub.getVariablesLocales()); // Variables locales declaradas en la seccion VARIABLES
        // Parametros de entrad
        String entrada = "";
        // Parametros de salida
        String salida = "";
        // Seccion VARIABLES
        StringBuilder seccionVariables = new StringBuilder();

        for(Variable var: sub.getVariablesLocales().values()){
            seccionVariables.append(String.format("%s\n", var.toJava()));
        }

        // Procesamos las instrucciones del subprograma
        StringBuilder instrucciones = new StringBuilder();
        InstruccionesCompiler instruccionesParser = new InstruccionesCompiler(variablesLocales, this.subprogramas);
        instrucciones.append("List<Object> almacenTmp = new ArrayList<>();\n");

        if(sub.parametrosEntrada != null){
            List<String> paramEntrada = new ArrayList<>();
            for(Parametro par: sub.parametrosEntrada){
                paramEntrada.add(par.toJava());

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
                paramSalida.add(String.format("%s %s;", this.idToString(par.getTipo()), par.getIdentificador()));
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
                    // TODO: detectar llamadas a devolver
                    instrucciones.append(instruccionesParser.visit(instruccion));
                }
            }
            return String.format("public static void %s(%s){\n%s\n%s}", sub.getIdentificador(), entrada, seccionVariables, instrucciones);
        }
    }
}
