package Modelo.Tickets.Incidencia;
import java.io.Serializable;
public class TipoProblema implements Serializable {
    private static final long serialVersionUID = 1L;
    private String idTipoProblema;
    private String codigoCategoria;
    private int nivelComplejidadBase;
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