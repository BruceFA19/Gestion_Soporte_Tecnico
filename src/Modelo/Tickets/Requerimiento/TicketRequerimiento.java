package Modelo.Tickets.Requerimiento;
import Estructuras.ListaEnlazada;
import Modelo.Tickets.Comentario;
import Modelo.Enums.EstadoTicket;
import Modelo.Enums.NivelPrioridad;
import Modelo.Tickets.Ticket;
import Modelo.Users.Solicitante;
import java.time.LocalDateTime;
public class TicketRequerimiento extends Ticket {
    private static final long serialVersionUID = 1L;
    private TipoServicio tipoServicio;
    private boolean requiereAprobacion;
    private boolean estaAprobado;
    public TicketRequerimiento(String codigoTicket, Solicitante idSolicitante, String titulo,
                               String descripcion, TipoServicio tipoServicio) {
        super(codigoTicket, idSolicitante, titulo, descripcion);
        this.tipoServicio = tipoServicio;
        this.estaAprobado = !tipoServicio.isRequiereAprobacion();
        this.calcularPrioridadAutomatica();
    }
    public TicketRequerimiento(String codigoTicket, Solicitante idSolicitante, String idTecnicoAsignado,
                               String titulo, String descripcion, LocalDateTime fechaCreacion,
                               LocalDateTime fechaResolucion, LocalDateTime fechaLimiteResolucion,
                               EstadoTicket estado, NivelPrioridad nivelPrioridad,
                               ListaEnlazada<Comentario> historialComentarios,
                               TipoServicio tipoServicio, boolean estaAprobado) {
        super(codigoTicket, idSolicitante, idTecnicoAsignado, titulo, descripcion,
                fechaCreacion, fechaResolucion, fechaLimiteResolucion, estado, nivelPrioridad, historialComentarios);
        this.tipoServicio = tipoServicio;
        this.estaAprobado = estaAprobado;
    }
    @Override
    public long calcularTiempoEstimadoAtencion() {
        return this.tipoServicio.getTiempoEntregaHoras();
    }
    @Override
    public void calcularPrioridadAutomatica() {
        this.nivelPrioridad = NivelPrioridad.BAJA;
        long horasEstimadas = this.calcularTiempoEstimadoAtencion();
        this.fechaLimiteResolucion = this.fechaCreacion.plusHours(horasEstimadas);
    }
    public void aprobarRequerimiento() {
        this.estaAprobado = true;
    }
    public boolean isEstaAprobado() { return estaAprobado; }
    public TipoServicio getTipoServicio() { return tipoServicio; }
}
