package Modelo;

import java.time.LocalDateTime;

public abstract class Ticket {

    protected String codigoTicket;
    protected Solicitante solicitante;
    protected Tecnico tecnicoAsignado;
    protected DispositivoEquipo equipoAfectado;
    protected TipoProblema problema;
    protected LocalDateTime LocalDateTimeLlegada;
    protected EstadoTicket estado;
    protected int alcanceUsuarios;        // 1: Individual, 2: Grupal, 3: Masivo
    protected int gradoBloqueo;           // 1: Bajo, 2: Medio, 3: Alto
    protected int puntajeTotalPrioridad;  // 3 a 9
    protected NivelPrioridad nivelPrioridad;

    public Ticket(String codigoTicket, Solicitante solicitante, DispositivoEquipo equipoAfectado,
                  TipoProblema problema, LocalDateTime LocalDateTimeLlegada, int alcanceUsuarios, int gradoBloqueo) {
        this.codigoTicket = codigoTicket;
        this.solicitante = solicitante;
        this.equipoAfectado = equipoAfectado;
        this.problema = problema;
        this.LocalDateTimeLlegada = LocalDateTimeLlegada;
        this.alcanceUsuarios = alcanceUsuarios;
        this.gradoBloqueo = gradoBloqueo;
        this.estado = EstadoTicket.EN_ESPERA;
    }

    public abstract long calcularTiempoEstimadoAtencion();

    public void asignarTecnico(Tecnico tecnico) {
        this.tecnicoAsignado = tecnico;
    }

    public void cambiarEstado(EstadoTicket nuevoEstado) {
        this.estado = nuevoEstado;
    }

    public void registrarEvaluacionPrioridad(int alcance, int bloqueo, int puntajeTotal, NivelPrioridad nivel) {
        this.alcanceUsuarios = alcance;
        this.gradoBloqueo = bloqueo;
        this.puntajeTotalPrioridad = puntajeTotal;
        this.nivelPrioridad = nivel;
    }

    public String getCodigoTicket() { return codigoTicket; }
    public Solicitante getSolicitante() { return solicitante; }
    public Tecnico getTecnicoAsignado() { return tecnicoAsignado; }
    public DispositivoEquipo getEquipoAfectado() { return equipoAfectado; }
    public TipoProblema getProblema() { return problema; }
    public LocalDateTime getLocalDateTimeLlegada() { return LocalDateTimeLlegada; }
    public EstadoTicket getEstado() { return estado; }
    public int getAlcanceUsuarios() { return alcanceUsuarios; }
    public int getGradoBloqueo() { return gradoBloqueo; }
    public int getPuntajeTotalPrioridad() { return puntajeTotalPrioridad; }
    public NivelPrioridad getNivelPrioridad() { return nivelPrioridad; }
}