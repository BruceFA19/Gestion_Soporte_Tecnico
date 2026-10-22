package Modelo.Tickets.Requerimiento;
import java.io.Serializable;

public class TipoServicio implements Serializable {
    private static final long serialVersionUID = 1L;
    private String idServicio;
    private String nombreServicio;
    private boolean requiereAprobacion;
    private int tiempoEntregaHoras;
    public TipoServicio(String idServicio, String nombreServicio,
                        boolean requiereAprobacion, int tiempoEntregaHoras) {
        this.idServicio = idServicio;
        this.nombreServicio = nombreServicio;
        this.requiereAprobacion = requiereAprobacion;
        this.tiempoEntregaHoras = tiempoEntregaHoras;
    }
    public String getIdServicio() { return idServicio; }
    public String getNombreServicio() { return nombreServicio; }
    public boolean isRequiereAprobacion() { return requiereAprobacion; }
    public int getTiempoEntregaHoras() { return tiempoEntregaHoras; }
}
