package LenguajeP.util.interprete;

import LenguajeP.Antlr.Anasint;
import org.antlr.v4.runtime.tree.TerminalNode;

import java.util.List;

public class InstruccionAsignacion{



    public InstruccionAsignacion(Anasint.AsignacionContext ctx){
        List<TerminalNode> variablesAsignacion = ctx.getTokens(Anasint.IDENTIFICADOR);


    }

}
