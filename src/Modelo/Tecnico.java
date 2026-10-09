package Modelo;

// Personal Informatico De Atencion Presencial

public class Tecnico extends Usuario {

    private String especialidad;
    private boolean estadoDisponibilidad;
    private int turnosAtendidosContador;

    public Tecnico(String idUsuario, String nombre, String apellido, String cedula,
                   String usuarioRedBancaria, String contrasenia, String especialidad) {
        super(idUsuario, nombre, apellido, cedula, usuarioRedBancaria, contrasenia, RolUsuario.TECNICO);
        this.especialidad = especialidad;
        this.estadoDisponibilidad = true;
        this.turnosAtendidosContador = 0;
    }

    public void cambiarDisponibilidad(boolean disponible) {
        this.estadoDisponibilidad = disponible;
    }

    public Ticket llamarSiguienteSolicitante() {
        // Pedir al GestorSoporteBancario el siguiente ticket de la cola de prioridad.
        return null;
    }

    public void finalizarAtencionTurno(String codigoTicket, String diagnostico, String solucion) {
        // Buscar el ticket, marcarlo FINALIZADO, registrar diagnostico/solucion
        //       e incrementar turnosAtendidosContador.
    }

    public String getEspecialidad() { return especialidad; }
    public boolean isEstadoDisponibilidad() { return estadoDisponibilidad; }
    public int getTurnosAtendidosContador() { return turnosAtendidosContador; }
}