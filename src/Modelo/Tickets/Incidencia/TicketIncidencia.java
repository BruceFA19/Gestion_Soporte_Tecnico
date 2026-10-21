package Modelo.Tickets.Incidencia;

import Estructuras.ListaEnlazada;
import Modelo.Tickets.Comentario;
import Modelo.Enums.EstadoTicket;
import Modelo.Enums.NivelPrioridad;
import Modelo.Tickets.Ticket;
import Modelo.Users.Asignatario;
import Modelo.Users.Solicitante;

import java.time.LocalDateTime;

public class TicketIncidencia extends Ticket {
    private static final long serialVersionUID = 1L;
    private String idEquipoAfectado;
    private TipoProblema tipoProblema;
    private int alcanceUsuarios;
    private int gradoBloqueo;
    private int criticidadDepartamento;
    private int puntajeTotalPrioridad;
    // CONSTRUCTOR 1: Para crear incidencias NUEVAS (Desde el menú)
    public TicketIncidencia(String codigoTicket, Solicitante solicitante, String titulo,
                            String descripcion, String idEquipo, TipoProblema tipoProblema,
                            int alcance, int bloqueo, int criticidadDepto) {
        super(codigoTicket, solicitante, titulo, descripcion);
        this.idEquipoAfectado = idEquipo;
        this.tipoProblema = tipoProblema;
        this.alcanceUsuarios = alcance;
        this.gradoBloqueo = bloqueo;
        this.criticidadDepartamento = criticidadDepto;
        this.calcularPrioridadAutomatica();
    }
    public TicketIncidencia(String codigoTicket, Solicitante solicitante, Asignatario asignatario,
                            String titulo, String descripcion, LocalDateTime fechaCreacion,
                            LocalDateTime fechaResolucion, LocalDateTime fechaLimiteResolucion,
                            EstadoTicket estado, NivelPrioridad nivelPrioridad,
                            ListaEnlazada<Comentario> historialComentarios,
                            String idEquipoAfectado, TipoProblema tipoProblema,
                            int alcanceUsuarios, int gradoBloqueo, int criticidadDepto) {
        super(codigoTicket, solicitante, asignatario, titulo, descripcion,
                fechaCreacion, fechaResolucion, fechaLimiteResolucion, estado, nivelPrioridad, historialComentarios);
        this.idEquipoAfectado = idEquipoAfectado;
        this.tipoProblema = tipoProblema;
        this.alcanceUsuarios = alcanceUsuarios;
        this.gradoBloqueo = gradoBloqueo;
        this.criticidadDepartamento = criticidadDepto;
    }
    @Override
    public long calcularTiempoEstimadoAtencion() {
        if (this.puntajeTotalPrioridad >= 10) return 2;
        if (this.puntajeTotalPrioridad >= 7) return 6;
        if (this.puntajeTotalPrioridad >= 4) return 24;
        return 72;
    }
    @Override
    public void calcularPrioridadAutomatica() {
        this.puntajeTotalPrioridad = this.alcanceUsuarios
                + this.gradoBloqueo
                + this.criticidadDepartamento
                + this.tipoProblema.getNivelComplejidadBase();
        if (this.puntajeTotalPrioridad >= 10) {
            this.nivelPrioridad = NivelPrioridad.CRITICA;
        } else if (this.puntajeTotalPrioridad >= 7) {
            this.nivelPrioridad = NivelPrioridad.ALTA;
        } else if (this.puntajeTotalPrioridad >= 4) {
            this.nivelPrioridad = NivelPrioridad.MEDIA;
        } else {
            this.nivelPrioridad = NivelPrioridad.BAJA;
        }
        long horasEstimadas = this.calcularTiempoEstimadoAtencion();
        this.fechaLimiteResolucion = this.fechaCreacion.plusHours(horasEstimadas);
    }
    // Getters
    public int getPuntajeTotalPrioridad() { return puntajeTotalPrioridad; }
    public String getIdEquipoAfectado() { return idEquipoAfectado; }
    public TipoProblema getTipoProblema() { return tipoProblema; }
}
