package Modelo.Tickets;

import Estructuras.ListaEnlazada;
import Modelo.Tickets.Enums.EstadoTicket;
import Modelo.Tickets.Enums.NivelPrioridad;

import java.time.LocalDateTime;

public abstract class Ticket {
    // 1. Identificadores Básicos (Usamos Strings para la persistencia en archivos)
    protected String codigoTicket;       // Ej: "TIC-1001"
    protected String idSolicitante;      // ID del usuario que crea el ticket
    protected String idTecnicoAsignado;  // Inicia vacío hasta que un técnico lo toma

    // 2. Textos Descriptivos (Añadidos para el enfoque tipo Jira)
    protected String titulo;
    protected String descripcion;

    // 3. Tiempos
    protected LocalDateTime fechaCreacion;
    protected LocalDateTime fechaResolucion; // Inicia nulo, se llena al cerrar

    // 4. Estados y Prioridad
    protected EstadoTicket estado;
    protected NivelPrioridad nivelPrioridad;

    // 5. Comunicación Asíncrona
    protected ListaEnlazada<Comentario> historialComentarios;

    protected LocalDateTime fechaLimiteResolucion; // El famoso SLA

    /*public Ticket() {
    }*/

    public Ticket(String codigoTicket, String idSolicitante, String idTecnicoAsignado, String titulo,
                  String descripcion, LocalDateTime fechaCreacion, LocalDateTime fechaResolucion,
                  EstadoTicket estado, NivelPrioridad nivelPrioridad, ListaEnlazada<Comentario> historialComentarios) {
        this.codigoTicket = codigoTicket;
        this.idSolicitante = idSolicitante;
        this.idTecnicoAsignado = idTecnicoAsignado;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.fechaCreacion = fechaCreacion;
        this.fechaResolucion = fechaResolucion;
        this.estado = estado;
        this.nivelPrioridad = nivelPrioridad;
        this.historialComentarios = historialComentarios;
    }

    public abstract long calcularTiempoEstimadoAtencion();

    public void asignarTecnico(String idTecnico) {
        this.idTecnicoAsignado = idTecnico;
        this.estado = EstadoTicket.ASIGNADO;
    }

    public void cambiarEstado(EstadoTicket nuevoEstado) {
        this.estado = nuevoEstado;
        if (nuevoEstado == EstadoTicket.RESUELTO || nuevoEstado == EstadoTicket.CERRADO) {
            this.fechaResolucion = LocalDateTime.now();
        }
    }

    public String getCodigoTicket() {
        return codigoTicket;
    }

    public String getSolicitante() {
        return idSolicitante;
    }

    public String getTecnicoAsignado() {
        return idTecnicoAsignado;
    }

    public LocalDateTime getFechaCreacion() {
        return fechaCreacion;
    }

    public EstadoTicket getEstado() {
        return estado;
    }

    public NivelPrioridad getNivelPrioridad() {
        return nivelPrioridad;
    }
}