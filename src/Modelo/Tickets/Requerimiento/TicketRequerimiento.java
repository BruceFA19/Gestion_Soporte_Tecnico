package Modelo.Tickets.Requerimiento;

import Estructuras.ListaEnlazada;
import Modelo.Tickets.Comentario;
import Modelo.Tickets.Enums.EstadoTicket;
import Modelo.Tickets.Enums.NivelPrioridad;
import Modelo.Tickets.Ticket;

import java.time.LocalDateTime;

public class TicketRequerimiento extends Ticket {
    // 1. Atributos exclusivos de solicitudes (No hay impactos ni bloqueos)
    private String tipoServicioSolicitado;  // Ej: "Instalación de Software", "Acceso VPN"
    private boolean requiereAprobacion;     // Define si el jefe debe autorizarlo primero
    private boolean estaAprobado;           // Estado actual de esa autorización

    // ==========================================================
    // CONSTRUCTOR 1: Para crear requerimientos NUEVOS (Desde el menú)
    // ==========================================================
    public TicketRequerimiento(String codigoTicket, String idSolicitante, String titulo,
                               String descripcion, String tipoServicio, boolean requiereAprobacion) {

        // Datos base al padre
        super(codigoTicket, idSolicitante, titulo, descripcion);

        // Datos específicos
        this.tipoServicioSolicitado = tipoServicio;
        this.requiereAprobacion = requiereAprobacion;

        // Si no requiere aprobación del jefe, nace aprobado automáticamente
        this.estaAprobado = !requiereAprobacion;

        // Fijamos el SLA estandarizado
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
                               // Atributos específicos recuperados del txt:
                               String tipoServicioSolicitado, boolean requiereAprobacion, boolean estaAprobado) {

        // Reconstrucción de la parte padre
        super(codigoTicket, idSolicitante, idTecnicoAsignado, titulo, descripcion,
                fechaCreacion, fechaResolucion, fechaLimiteResolucion, estado, nivelPrioridad, historialComentarios);

        // Reconstrucción de la parte hija
        this.tipoServicioSolicitado = tipoServicioSolicitado;
        this.requiereAprobacion = requiereAprobacion;
        this.estaAprobado = estaAprobado;
    }

    // ==========================================================
    // MÉTODOS OBLIGATORIOS (Polimorfismo)
    // ==========================================================

    @Override
    public long calcularTiempoEstimadoAtencion() {
        return 48; // Todo requerimiento estándar toma hasta 48 horas hábiles
    }

    @Override
    public void calcularPrioridadAutomatica() {
        // Los requerimientos suelen ser rutinarios
        this.nivelPrioridad = NivelPrioridad.BAJA;

        // Alimentamos la métrica del SLA
        long horasEstimadas = this.calcularTiempoEstimadoAtencion();
        this.fechaLimiteResolucion = this.fechaCreacion.plusHours(horasEstimadas);
    }
}
