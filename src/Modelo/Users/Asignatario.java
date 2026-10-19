package Modelo.Users;
// Personal Informatico De Atencion Presencial
import Estructuras.ColaPrioridad;
import Modelo.Enums.RolUsuario;
import Modelo.Tickets.Ticket;

public class Asignatario extends Usuario {
    private ColaPrioridad<Ticket> ticketeraPersonal; // ticketera personal
    public Asignatario(String idUsuario, String nombre, String apellido, String cedula,
                       String usuarioRedBancaria, String contraseniaHash, RolUsuario rol) {

        super(idUsuario, nombre, apellido, cedula, usuarioRedBancaria, contraseniaHash, rol);
        this.ticketeraPersonal = new ColaPrioridad<>();
    }

    public void recibirTicketAsignado(Ticket nuevoTicket) {
        this.ticketeraPersonal.encolar(nuevoTicket);
    }

    public Ticket atenderSiguienteTicketPersonal() {
        return this.ticketeraPersonal.desencolar();
    }

    public int getCantidadTicketsPendientes() {
        return this.ticketeraPersonal.getTamanio();
    }
}