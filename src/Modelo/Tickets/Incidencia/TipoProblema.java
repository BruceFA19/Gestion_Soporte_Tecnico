package Modelo.Tickets.Incidencia;
import java.io.Serializable;
public class TipoProblema implements Serializable {
    private static final long serialVersionUID = 1L;
    private String idTipoProblema;        // Ej: "PROB-001"
    private String codigoCategoria;       // Ej: "CAT-REDES"
    private int nivelComplejidadBase;     // Afecta la fórmula de la ColaPrioridad
    public TipoProblema(String idTipoProblema, String codigoCategoria,
                        int nivelComplejidadBase) {
        this.idTipoProblema = idTipoProblema;
        this.codigoCategoria = codigoCategoria;
        this.nivelComplejidadBase = nivelComplejidadBase;
    }
    public String getIdTipoProblema() { return idTipoProblema; }
    public String getCodigoCategoria() { return codigoCategoria; }
    public int getNivelComplejidadBase() { return nivelComplejidadBase; }
}