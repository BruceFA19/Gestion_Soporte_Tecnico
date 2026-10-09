package Modelo;

// importar  Estructuras.ListaEnlazada cuando se haga

import Estructuras.ListaEnlazadas;
import java.time.LocalDateTime;

// Reporte con estadisticas de desempeño del soporte

public class ReporteSoporte {

    private LocalDateTime fechaReporte;
    private int totalTicketsAtendidos;
    private double tiempoPromedioEspera;
    private double tiempoPromedioAtencion;

    public ReporteSoporte(LocalDateTime fechaReporte) {
        this.fechaReporte = fechaReporte;
    }

    public void generarEstadisticas(ListaEnlazadas<Ticket> listaTickets) {
        // Recorrer listaTickets y calcular totalTicketsAtendidos y los tiempos promedio
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