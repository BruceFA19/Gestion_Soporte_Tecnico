package Modelo;

import Estructuras.ColaPrioridad;
import Estructuras.TablaHash;
import Estructuras.ListaEnlazada;
import Modelo.Tickets.Ticket;

//Guarda y carga usuarios, tickets en archivos

public class GestorPersistencia {

    private String rutaArchivoUsuarios;
    private String rutaArchivoTickets;
    private String rutaArchivoHistorial;

    public GestorPersistencia(String rutaArchivoUsuarios, String rutaArchivoTickets, String rutaArchivoHistorial) {
        this.rutaArchivoUsuarios = rutaArchivoUsuarios;
        this.rutaArchivoTickets = rutaArchivoTickets;
        this.rutaArchivoHistorial = rutaArchivoHistorial;
    }

    public boolean guardarUsuariosEnArchivo(TablaHash<String, Usuario> usuarios) {
        // TODO
        return false;
    }

    public TablaHash<String, Usuario> cargarUsuariosDesdeArchivo() {
        // TODO
        return null;
    }

    public boolean guardarTicketsEnArchivo(ColaPrioridad<Ticket> cola, ListaEnlazada<Ticket> historico) {
        // TODO
        return false;
    }

    public ListaEnlazada<Ticket> cargarTicketsDesdeArchivo() {
        // TODO
        return null;
    }

    public String getRutaArchivoUsuarios() { return rutaArchivoUsuarios; }
    public String getRutaArchivoTickets() { return rutaArchivoTickets; }
    public String getRutaArchivoHistorial() { return rutaArchivoHistorial; }
}