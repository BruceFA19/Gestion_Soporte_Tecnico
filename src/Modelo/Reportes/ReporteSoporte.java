package Modelo.Reportes;
import Estructuras.ListaEnlazada;
import Modelo.Tickets.Ticket;
import java.io.Serializable;
import java.time.LocalDateTime;
public class ReporteSoporte implements Serializable {
    private static final long serialVersionUID = 1L;
    private LocalDateTime fechaReporte;
    private int totalTicketsAtendidos;
    private double tiempoPromedioEspera;
    private double tiempoPromedioAtencion;
    public ReporteSoporte(LocalDateTime fechaReporte) {
        this.fechaReporte = fechaReporte;
    }
    public void generarEstadisticas(ListaEnlazada<Ticket> listaTickets) {
    }
    public String exportarResumenAFormatoTexto() {
        return "REPORTE DE SOPORTE TÉCNICO"
                + "\nFecha: " + fechaReporte
                + "\nTickets atendidos: " + totalTicketsAtendidos
                + "\nTiempo promedio de espera: " + tiempoPromedioEspera
                + "\nTiempo promedio de atención: " + tiempoPromedioAtencion;
    }
    public LocalDateTime getFechaReporte() { return fechaReporte; }
    public int getTotalTicketsAtendidos() { return totalTicketsAtendidos; }
    public double getTiempoPromedioEspera() { return tiempoPromedioEspera; }
    public double getTiempoPromedioAtencion() { return tiempoPromedioAtencion; }
}