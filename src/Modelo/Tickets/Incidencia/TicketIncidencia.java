package Modelo.Tickets.Incidencia;

import Estructuras.ListaEnlazada;
import Modelo.Tickets.Comentario;
import Modelo.Tickets.Enums.EstadoTicket;
import Modelo.Tickets.Enums.NivelPrioridad;
import Modelo.Tickets.Ticket;

import java.time.LocalDateTime;

public class TicketIncidencia extends Ticket {
    private String idEquipoAfectado; // Ej: "PC-CAJA-03" o "SRV-BASE-DATOS"
    private TipoProblema tipoProblema;   // Ej: "REDES", "HARDWARE", "SOFTWARE"
    private int alcanceUsuarios;     // 1: Individual, 2: Grupal, 3: Masivo
    private int gradoBloqueo;        // 1: Bajo, 2: Medio, 3: Alto
    private int criticidadDepartamento;
    private int puntajeTotalPrioridad;
    // CONSTRUCTOR 1: Para crear incidencias NUEVAS (Desde el menú)
    public TicketIncidencia(String codigoTicket, String idSolicitante, String titulo,
                            String descripcion, String idEquipo, TipoProblema tipoProblema,
                            int alcance, int bloqueo, int criticidadDepto) {
        super(codigoTicket, idSolicitante, titulo, descripcion);
        this.idEquipoAfectado = idEquipo;
        this.tipoProblema = tipoProblema;
        this.alcanceUsuarios = alcance;
        this.gradoBloqueo = bloqueo;
        this.criticidadDepartamento = criticidadDepto;
        this.calcularPrioridadAutomatica();
    }
    // CONSTRUCTOR 2: Para RECONSTRUIR incidencias (Desde el archivo txt)
    public TicketIncidencia(String codigoTicket, String idSolicitante, String idTecnicoAsignado,
                            String titulo, String descripcion, LocalDateTime fechaCreacion,
                            LocalDateTime fechaResolucion, LocalDateTime fechaLimiteResolucion,
                            EstadoTicket estado, NivelPrioridad nivelPrioridad,
                            ListaEnlazada<Comentario> historialComentarios,
                            String idEquipoAfectado, TipoProblema tipoProblema,
                            int alcanceUsuarios, int gradoBloqueo, int criticidadDepto) {
        super(codigoTicket, idSolicitante, idTecnicoAsignado, titulo, descripcion,
                fechaCreacion, fechaResolucion, fechaLimiteResolucion, estado, nivelPrioridad, historialComentarios);
        this.idEquipoAfectado = idEquipoAfectado;
        this.tipoProblema = tipoProblema;
        this.alcanceUsuarios = alcanceUsuarios;
        this.gradoBloqueo = gradoBloqueo;
        this.criticidadDepartamento = criticidadDepto;
        // Nota: Aquí NO llamamos a calcularPrioridadAutomatica() porque
        // la prioridad y el SLA ya vienen calculados y recuperados del archivo txt.
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
