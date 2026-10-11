package Modelo.Tickets.Incidencia;

import Estructuras.ListaEnlazada;
import Modelo.Tickets.Comentario;
import Modelo.Tickets.Enums.EstadoTicket;
import Modelo.Tickets.Enums.NivelPrioridad;
import Modelo.Tickets.Ticket;

import java.time.LocalDateTime;

public class TicketIncidencia extends Ticket {
    // Atributos exclusivos de cuando algo se rompe
    private String idEquipoAfectado; // Ej: "PC-CAJA-03" o "SRV-BASE-DATOS"
    private String idTipoProblema;   // Ej: "REDES", "HARDWARE", "SOFTWARE"

    // Variables para la matemática de urgencia
    private int alcanceUsuarios;     // 1: Individual, 2: Grupal, 3: Masivo
    private int gradoBloqueo;        // 1: Bajo, 2: Medio, 3: Alto
    private int puntajeTotalPrioridad;

    // ==========================================================
    // CONSTRUCTOR 1: Para crear incidencias NUEVAS (Desde el menú)
    // (Este es el que ya teníamos)
    // ==========================================================
    public TicketIncidencia(String codigoTicket, String idSolicitante, String titulo,
                            String descripcion, String idEquipo, String idTipoProb,
                            int alcance, int bloqueo) {
        super(codigoTicket, idSolicitante, titulo, descripcion);
        this.idEquipoAfectado = idEquipo;
        this.idTipoProblema = idTipoProb;
        this.alcanceUsuarios = alcance;
        this.gradoBloqueo = bloqueo;
        this.calcularPrioridadAutomatica();
    }

    // ==========================================================
    // CONSTRUCTOR 2: Para RECONSTRUIR incidencias (Desde el archivo txt)
    // (¡Este es el que faltaba agregar!)
    // ==========================================================
    public TicketIncidencia(String codigoTicket, String idSolicitante, String idTecnicoAsignado,
                            String titulo, String descripcion, LocalDateTime fechaCreacion,
                            LocalDateTime fechaResolucion, LocalDateTime fechaLimiteResolucion,
                            EstadoTicket estado, NivelPrioridad nivelPrioridad,
                            ListaEnlazada<Comentario> historialComentarios,
                            // Atributos específicos recuperados del txt:
                            String idEquipoAfectado, String idTipoProblema,
                            int alcanceUsuarios, int gradoBloqueo) {

        // Reconstrucción del padre
        super(codigoTicket, idSolicitante, idTecnicoAsignado, titulo, descripcion,
                fechaCreacion, fechaResolucion, fechaLimiteResolucion, estado, nivelPrioridad, historialComentarios);

        // Reconstrucción de la hija
        this.idEquipoAfectado = idEquipoAfectado;
        this.idTipoProblema = idTipoProblema;
        this.alcanceUsuarios = alcanceUsuarios;
        this.gradoBloqueo = gradoBloqueo;

        // Nota: Aquí NO llamamos a calcularPrioridadAutomatica() porque
        // la prioridad y el SLA ya vienen calculados y recuperados del archivo txt.
    }

    //Usamos el método de tu compañero para definir las horas estándar según el puntaje
    @Override
    public long calcularTiempoEstimadoAtencion() {
        if (this.puntajeTotalPrioridad >= 6) return 2;   // Falla masiva: 2 horas
        if (this.puntajeTotalPrioridad >= 4) return 6;   // Afectación grupal: 6 horas
        if (this.puntajeTotalPrioridad == 3) return 24;  // Falla individual: 24 horas
        return 72;                                       // Bloqueo bajo: 72 horas (3 días)
    }

    // La Caja Negra Matemática
    @Override
    public void calcularPrioridadAutomatica() {
        // 1. Calculamos el puntaje (Fórmula base tuya)
        this.puntajeTotalPrioridad = this.alcanceUsuarios + this.gradoBloqueo;

        // 2. Asignamos Nivel (Enum)
        if (this.puntajeTotalPrioridad >= 6) {
            this.nivelPrioridad = NivelPrioridad.CRITICA;
        } else if (this.puntajeTotalPrioridad >= 4) {
            this.nivelPrioridad = NivelPrioridad.ALTA;
        } else if (this.puntajeTotalPrioridad == 3) {
            this.nivelPrioridad = NivelPrioridad.MEDIA;
        } else {
            this.nivelPrioridad = NivelPrioridad.BAJA;
        }

        // 3. Obtenemos las horas y fijamos la Fecha Límite de Resolución (SLA)
        long horasEstimadas = this.calcularTiempoEstimadoAtencion();

        this.fechaLimiteResolucion = this.fechaCreacion.plusHours(horasEstimadas);
    }
}
