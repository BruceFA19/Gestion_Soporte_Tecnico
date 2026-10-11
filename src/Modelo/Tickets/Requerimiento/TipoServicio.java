package Modelo.Tickets.Requerimiento;

public class TipoServicio {
    private String idServicio;             // Ej: "SERV-010"
    private String nombreServicio;         // Ej: "Solicitud de Nuevo Monitor"
    private boolean requiereAprobacion;    // ¿El jefe del área debe autorizarlo?
    private int tiempoEntregaHoras;        // Ej: 72 horas (SLA estático para este servicio)
    private String especialidadRequerida;  // Ej: "HARDWARE" (Para enrutar al técnico correcto)
    public TipoServicio(String idServicio, String nombreServicio, boolean requiereAprobacion,
                        int tiempoEntregaHoras, String especialidadRequerida) {
        this.idServicio = idServicio;
        this.nombreServicio = nombreServicio;
        this.requiereAprobacion = requiereAprobacion;
        this.tiempoEntregaHoras = tiempoEntregaHoras;
        this.especialidadRequerida = especialidadRequerida;
    }
    // Getters
    public String getIdServicio() { return idServicio; }
    public String getNombreServicio() { return nombreServicio; }
    public boolean isRequiereAprobacion() { return requiereAprobacion; }
    public int getTiempoEntregaHoras() { return tiempoEntregaHoras; }
    public String getEspecialidadRequerida() { return especialidadRequerida; }
}
