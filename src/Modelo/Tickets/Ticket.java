package Modelo.Tickets;

import Estructuras.ListaEnlazada;
import Modelo.Enums.EstadoTicket;
import Modelo.Enums.NivelPrioridad;
import Modelo.Users.Asignatario;
import Modelo.Users.Solicitante;

import java.io.Serializable;
import java.time.LocalDateTime;

public abstract class Ticket implements Comparable<Ticket>, Serializable {
    private static final long serialVersionUID = 1L;
    protected String codigoTicket;
    protected Solicitante solicitante;
    protected Asignatario asignatario;
    protected String titulo;
    protected String descripcion;
    protected LocalDateTime fechaCreacion;
    protected LocalDateTime fechaResolucion;
    protected EstadoTicket estado;
    protected NivelPrioridad nivelPrioridad;
    protected ListaEnlazada<Comentario> historialComentarios;
    protected LocalDateTime fechaLimiteResolucion;
    protected int puntajePrioridad;
    public Ticket(String codigoTicket, Solicitante solicitante, String titulo, String descripcion) {
        this.codigoTicket = codigoTicket;
        this.solicitante = solicitante;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.asignatario = null;
        this.fechaCreacion = LocalDateTime.now();
        this.estado = EstadoTicket.ABIERTO;
        this.historialComentarios = new ListaEnlazada<>();
    }
    public Ticket(String codigoTicket, Solicitante solicitante, Asignatario asignatario, String titulo,
                  String descripcion, LocalDateTime fechaCreacion, LocalDateTime fechaResolucion,
                  LocalDateTime fechaLimiteResolucion,
                  EstadoTicket estado, NivelPrioridad nivelPrioridad, ListaEnlazada<Comentario> historialComentarios) {
        this.codigoTicket = codigoTicket;
        this.solicitante = solicitante;
        this.asignatario = asignatario;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.fechaCreacion = fechaCreacion;
        this.fechaResolucion = fechaResolucion;
        this.fechaLimiteResolucion = fechaLimiteResolucion;
        this.estado = estado;
        this.nivelPrioridad = nivelPrioridad;
        this.historialComentarios = historialComentarios;
    }
    public abstract void calcularPrioridadAutomatica();
    public abstract long calcularTiempoEstimadoAtencion();
    public void asignarTecnico(Asignatario asignatario) {
        this.asignatario = asignatario;
        this.estado = EstadoTicket.ASIGNADO;
    }
    public void cambiarEstado(EstadoTicket nuevoEstado) {
        this.estado = nuevoEstado;
        if (nuevoEstado == EstadoTicket.RESUELTO || nuevoEstado == EstadoTicket.CERRADO) {
            this.fechaResolucion = LocalDateTime.now();
        }
    }
    public void setCodigoTicket(String codigoTicket) {
        this.codigoTicket = codigoTicket;
    }
    public void setSolicitante(Solicitante solicitante) {
        this.solicitante = solicitante;
    }
    public Asignatario getAsignatario() {
        return this.asignatario;
    }
    public void setAsignatario(Asignatario asignatario) {
        this.asignatario = asignatario;
    }
    public String getTitulo() {
        return titulo;
    }
    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }
    public String getDescripcion() {
        return descripcion;
    }
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    public void setFechaCreacion(LocalDateTime fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }
    public LocalDateTime getFechaResolucion() {
        return fechaResolucion;
    }
    public void setFechaResolucion(LocalDateTime fechaResolucion) {
        this.fechaResolucion = fechaResolucion;
    }
    public void setEstado(EstadoTicket estado) {
        this.estado = estado;
    }
    public void setNivelPrioridad(NivelPrioridad nivelPrioridad) {
        this.nivelPrioridad = nivelPrioridad;
    }
    public ListaEnlazada<Comentario> getHistorialComentarios() {
        return historialComentarios;
    }
    public void setHistorialComentarios(ListaEnlazada<Comentario> historialComentarios) {
        this.historialComentarios = historialComentarios;
    }
    public LocalDateTime getFechaLimiteResolucion() {
        return fechaLimiteResolucion;
    }
    public void setFechaLimiteResolucion(LocalDateTime fechaLimiteResolucion) {
        this.fechaLimiteResolucion = fechaLimiteResolucion;
    }
    public void setPuntajePrioridad(int puntajePrioridad) {
        this.puntajePrioridad = puntajePrioridad;
    }
    public String getCodigoTicket() {
        return codigoTicket;
    }
    public Solicitante getSolicitante() {
        return solicitante;
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
    public int getPuntajePrioridad() {
        return puntajePrioridad;
    }
    @Override
    public int compareTo(Ticket otroTicket) {
        // Comparamos los puntajes.
        // Si este ticket tiene 12 de prioridad y el otro tiene 8, devolverá un número positivo.
        // Si tienen lo mismo, devolverá 0.
        return Integer.compare(this.puntajePrioridad, otroTicket.getPuntajePrioridad());
    }
}