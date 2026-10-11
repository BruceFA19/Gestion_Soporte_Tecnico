package Modelo.Tickets.Incidencia;
// 1. Ya no es abstracta, es una clase normal instanciable
public class TipoProblema {
    private String idTipoProblema;        // Ej: "PROB-001"
    private String codigoCategoria;       // Ej: "CAT-REDES"
    private String descripcionGeneral;    // Ej: "Pérdida de conexión en sucursal"
    // 2. Se mantiene: Sumará puntos a tu fórmula matemática en TicketIncidencia
    private int nivelComplejidadBase;
    // 3. ¡Nuevo! Nos dirá a qué técnico asignarle el ticket automáticamente
    private String especialidadRequerida; // Ej: "SOPORTE_REDES"
    public TipoProblema(String idTipoProblema, String codigoCategoria, String descripcionGeneral,
                        int nivelComplejidadBase, String especialidadRequerida) {
        this.idTipoProblema = idTipoProblema;
        this.codigoCategoria = codigoCategoria;
        this.descripcionGeneral = descripcionGeneral;
        this.nivelComplejidadBase = nivelComplejidadBase;
        this.especialidadRequerida = especialidadRequerida;
    }
    // --- Getters ---
    public String getIdTipoProblema() { return idTipoProblema; }
    public String getCodigoCategoria() { return codigoCategoria; }
    public String getDescripcionGeneral() { return descripcionGeneral; }
    public int getNivelComplejidadBase() { return nivelComplejidadBase; }
    public String getEspecialidadRequerida() { return especialidadRequerida; }
}