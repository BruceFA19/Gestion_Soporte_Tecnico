package Modelo.Users;
import Estructuras.ColaPrioridad;
import Estructuras.ListaEnlazada;
import Modelo.Enums.RolUsuario;
import Modelo.Tickets.Ticket;
public class Asignatario extends Usuario {
    private ListaEnlazada<Ticket> ticketeraPersonal; // ticketera personal
    public Asignatario(String idUsuario, String nombre, String apellido, String cedula,
                        String contraseniaHash, RolUsuario rol) {
        super(idUsuario, nombre, apellido, cedula, contraseniaHash, rol);
        this.ticketeraPersonal = new ListaEnlazada<>();
    }
    public int getCantidadTicketsPendientes() {
        return this.ticketeraPersonal.getTamanio();
    }
    public ListaEnlazada<Ticket> getTicketeraPersonal() {
        return ticketeraPersonal;
    }
    public void setTicketeraPersonal(ListaEnlazada<Ticket> ticketeraPersonal) {
        this.ticketeraPersonal = ticketeraPersonal;
    }
}