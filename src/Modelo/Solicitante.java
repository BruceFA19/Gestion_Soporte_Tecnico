package Modelo;

import Estructuras.ListaEnlazada;
import Modelo.Tickets.Ticket;

// Empleado bancario que acude a soporte tecnicco presencial

public class Solicitante extends Usuario {

    private Departamento departamento;
    private String codigoEmpleado;
    private String extensionTelefonica;
    private ListaEnlazada<Ticket> misTickets;

    public Solicitante(String idUsuario, String nombre, String apellido, String cedula,
                       String usuarioRedBancaria, String contraseniaHash,
                       Departamento departamento, String codigoEmpleado, String extensionTelefonica) {
        super(idUsuario, nombre, apellido, cedula, usuarioRedBancaria, contraseniaHash, RolUsuario.SOLICITANTE);
        this.departamento = departamento;
        this.codigoEmpleado = codigoEmpleado;
        this.extensionTelefonica = extensionTelefonica;
        this.misTickets = new ListaEnlazada<>();
    }

    public Ticket solicitarTurnoPresencial(TipoProblema problema, DispositivoEquipo equipo,
                                           int alcanceUsuarios, int gradoBloqueo, String motivo) {
        // Delegar en GestorSoporteBancario.registrarLlegadaSolicitante(...) y guardar el ticket en misTickets.
        return null;
    }

    public boolean cancelarTurno(String codigoTicket) {
        // Buscar el ticket en misTickets, cambiar su estado a CANCELADO y retirarlo de la cola.
        return false;
    }

    public void calificarAtencion(String codigoTicket, int estrellas, String comentario) {
        // Guardar la calificacion (el modelo aun no define donde se almacena).
    }

    public Departamento getDepartamento() { return departamento; }
    public String getCodigoEmpleado() { return codigoEmpleado; }
    public String getExtensionTelefonica() { return extensionTelefonica; }
    public ListaEnlazada<Ticket> getMisTickets() { return misTickets; }
}