package Modelo.Tickets.Incidencia;

import java.io.Serializable;

public class TipoProblema implements Serializable {
    private static final long serialVersionUID = 1L;

    private String idTipoProblema;        // Ej: "PROB-001"
    private String codigoCategoria;       // Ej: "CAT-REDES"
    private String descripcionGeneral;    // Ej: "Pérdida de conexión en sucursal"
    private int nivelComplejidadBase;     // Afecta la fórmula de la ColaPrioridad

    public TipoProblema(String idTipoProblema, String codigoCategoria,
                        String descripcionGeneral, int nivelComplejidadBase) {
        this.idTipoProblema = idTipoProblema;
        this.codigoCategoria = codigoCategoria;
        this.descripcionGeneral = descripcionGeneral;
        this.nivelComplejidadBase = nivelComplejidadBase;
    }

    public String getIdTipoProblema() { return idTipoProblema; }
    public String getCodigoCategoria() { return codigoCategoria; }
    public String getDescripcionGeneral() { return descripcionGeneral; }
    public int getNivelComplejidadBase() { return nivelComplejidadBase; }
}