package Modelo;

import java.time.LocalDateTime;


// atencion completa, el tecnico se dirije en persona al area del problema para solucionarlo

public class TicketMantenimiento extends Ticket {

    private String ubicacionCustodia;
    private boolean requiereRepuesto;
    private LocalDateTime fechaEstimadaEntrega;

    public TicketMantenimiento(String codigoTicket, Solicitante solicitante, DispositivoEquipo equipoAfectado,
                               TipoProblema problema, LocalDateTime LocalDateTimeLlegada, int alcanceUsuarios,
                               int gradoBloqueo, boolean requiereRepuesto) {
        super(codigoTicket, solicitante, equipoAfectado, problema, LocalDateTimeLlegada, alcanceUsuarios, gradoBloqueo);
        this.requiereRepuesto = requiereRepuesto;
    }

    // Por ahora usa el tiempo estimado del problema; podemos cambiarlo (p. ej. si requiere repuesto)
    @Override
    public long calcularTiempoEstimadoAtencion() {
        return problema.getTiempoEstimadoMinutos();
    }

    public void registrarCustodia(String ubicacion) {
        this.ubicacionCustodia = ubicacion;
    }

    public String getUbicacionCustodia() { return ubicacionCustodia; }
    public boolean isRequiereRepuesto() { return requiereRepuesto; }
    public void setRequiereRepuesto(boolean requiereRepuesto) { this.requiereRepuesto = requiereRepuesto; }
    public LocalDateTime getFechaEstimadaEntrega() { return fechaEstimadaEntrega; }
    public void setFechaEstimadaEntrega(LocalDateTime fechaEstimadaEntrega) {
        this.fechaEstimadaEntrega = fechaEstimadaEntrega;
    }
}