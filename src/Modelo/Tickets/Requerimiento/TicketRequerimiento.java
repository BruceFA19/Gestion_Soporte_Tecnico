package Modelo.Tickets.Requerimiento;

import Estructuras.ListaEnlazada;
import Modelo.Tickets.Comentario;
import Modelo.Enums.EstadoTicket;
import Modelo.Enums.NivelPrioridad;
import Modelo.Tickets.Ticket;

import java.time.LocalDateTime;

public class TicketRequerimiento extends Ticket {
    // 1. Atributos exclusivos de solicitudes (No hay impactos ni bloqueos)
    private TipoServicio tipoServicio;  // Ej: "Instalación de Software", "Acceso VPN"
    private boolean requiereAprobacion;     // Define si el jefe debe autorizarlo primero
    private boolean estaAprobado;           // Estado actual de esa autorización

    // ==========================================================
    // CONSTRUCTOR 1: Para crear requerimientos NUEVOS (Desde el menú)
    // ==========================================================
    public TicketRequerimiento(String codigoTicket, String idSolicitante, String titulo,
                               String descripcion, TipoServicio tipoServicio) {
        super(codigoTicket, idSolicitante, titulo, descripcion);
        this.tipoServicio = tipoServicio;
        // Lógica de negocio: Si el servicio NO requiere aprobación, nace aprobado.
        // Si SÍ requiere, nace bloqueado (false) hasta que un jefe lo apruebe.
        this.estaAprobado = !tipoServicio.isRequiereAprobacion();
        this.calcularPrioridadAutomatica();
    }

    // ==========================================================
    // CONSTRUCTOR 2: Para RECONSTRUIR requerimientos (Desde el archivo txt)
    // ==========================================================
    public TicketRequerimiento(String codigoTicket, String idSolicitante, String idTecnicoAsignado,
                               String titulo, String descripcion, LocalDateTime fechaCreacion,
                               LocalDateTime fechaResolucion, LocalDateTime fechaLimiteResolucion,
                               EstadoTicket estado, NivelPrioridad nivelPrioridad,
                               ListaEnlazada<Comentario> historialComentarios,
                               TipoServicio tipoServicio, boolean estaAprobado) { // Atributos recuperados
        super(codigoTicket, idSolicitante, idTecnicoAsignado, titulo, descripcion,
                fechaCreacion, fechaResolucion, fechaLimiteResolucion, estado, nivelPrioridad, historialComentarios);
        this.tipoServicio = tipoServicio;
        this.estaAprobado = estaAprobado;
    }
    // ==========================================================
    // MÉTODOS OBLIGATORIOS (Polimorfismo)
    // ==========================================================
    @Override
    public long calcularTiempoEstimadoAtencion() {
        // ¡Magia! El SLA ya no es un número fijo inventado.
        // Se extrae dinámicamente de lo que dicta el catálogo para este servicio.
        return this.tipoServicio.getTiempoEntregaHoras();
    }
    @Override
    public void calcularPrioridadAutomatica() {
        // Los requerimientos del catálogo siempre tienen prioridad baja/estándar
        this.nivelPrioridad = NivelPrioridad.BAJA;

        // Alimentamos la métrica del SLA
        long horasEstimadas = this.calcularTiempoEstimadoAtencion();
        this.fechaLimiteResolucion = this.fechaCreacion.plusHours(horasEstimadas);
    }
    // --- Getters y Setters ---
    public void aprobarRequerimiento() {
        this.estaAprobado = true;
    }
    public boolean isEstaAprobado() { return estaAprobado; }
    public TipoServicio getTipoServicio() { return tipoServicio; }
}
