package Modelo;

import java.time.LocalDateTime;

 // Atencion rapida en ventanilla )reseteos,desbloqeo de tokens....
 
public class TicketExpress extends Ticket {

    private int tiempoLimiteMinutos;
    private boolean resueltoEnVentanilla;

    public TicketExpress(String codigoTicket, Solicitante solicitante, DispositivoEquipo equipoAfectado,
                         TipoProblema problema, LocalDateTime LocalDateTimeLlegada, int alcanceUsuarios,
                         int gradoBloqueo, int tiempoLimiteMinutos) {
        super(codigoTicket, solicitante, equipoAfectado, problema, LocalDateTimeLlegada, alcanceUsuarios, gradoBloqueo);
        this.tiempoLimiteMinutos = tiempoLimiteMinutos;
        this.resueltoEnVentanilla = false;
    }

    //Tiempo estimado del problema, sin superar el lomite de una atencion express
    @Override
    public long calcularTiempoEstimadoAtencion() {
        return Math.min(problema.getTiempoEstimadoMinutos(), tiempoLimiteMinutos);
    }

    // Es inmediata si el tiempo estimado del problema cabe dentro del limite express.
    public boolean esAtencionInmediata() {
        return problema.getTiempoEstimadoMinutos() <= tiempoLimiteMinutos;
    }

    public int getTiempoLimiteMinutos() { return tiempoLimiteMinutos; }
    public boolean isResueltoEnVentanilla() { return resueltoEnVentanilla; }
    public void setResueltoEnVentanilla(boolean resueltoEnVentanilla) {
        this.resueltoEnVentanilla = resueltoEnVentanilla;
    }
}