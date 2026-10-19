package Controlador;


import Estructuras.ColaPrioridad;
import Estructuras.ListaEnlazada;
import Estructuras.Pila;
import Estructuras.TablaHash;
import Modelo.Auditoria.AccionAuditoria;
import Modelo.Tickets.Incidencia.TicketIncidencia;
import Modelo.Tickets.Incidencia.TipoProblema;
import Modelo.Tickets.Ticket;
import Modelo.Users.Asignatario;
import Modelo.Users.Solicitante;
import Modelo.Users.Usuario;
import Persistencia.GestorPersistencia;

public class GestorSoporteBancario {

    // TUS ESTRUCTURAS DE DATOS (El núcleo de tu calificación)
    private ColaPrioridad<Ticket> colaEsperaGlobal;
    private TablaHash<String, Usuario> usuariosRegistrados;
    private ListaEnlazada<Ticket> historicoTickets;
    private Pila<AccionAuditoria> pilaAuditoria;
    private GestorPersistencia gestorPersistencia;
    ListaEnlazada<Asignatario> asignatariosActivos = new ListaEnlazada<>();

    public GestorSoporteBancario() {
        this.colaEsperaGlobal = new ColaPrioridad<>();
        this.usuariosRegistrados = new TablaHash<>();
        this.historicoTickets = new ListaEnlazada<>();
        this.pilaAuditoria = new Pila<>();
        // El evaluador de prioridad fue eliminado, el ticket se calcula solo.
        this.gestorPersistencia = new GestorPersistencia("data/usuarios.txt", "data/tickets.txt", "data/historial.txt");
    }

    public Ticket registrarLlegadaIncidencia(Solicitante solicitante, String idEquipo, TipoProblema problema,
                                             int alcanceUsuarios, int gradoBloqueo) {
        // 1. Creamos el ticket. ¡Él mismo calcula su prioridad internamente!
        int criticidadDepto = solicitante.getDepartamento().getCriticidadDepartamento();

        // Generamos un código falso por ahora (Ej: INC-001)
        String codigoGenerado = "INC-" + (historicoTickets.getTamanio() + 1);

        TicketIncidencia nuevoTicket = new TicketIncidencia(
                codigoGenerado, solicitante.getIdUsuario(), "Falla reportada", "Descripción automática",
                idEquipo, problema, alcanceUsuarios, gradoBloqueo, criticidadDepto
        );

        // ====================================================================
        // ¡EL CAMBIO MAESTRO AQUÍ!
        // En lugar de encolar ciegamente, invocamos a la Asignación Inteligente
        // ====================================================================
        asignarTicketAutomaticamente(nuevoTicket);

        // 3. Auditoría de creación
        AccionAuditoria accion = new AccionAuditoria("CREACION_TICKET",
                nuevoTicket.getCodigoTicket(),
                "Ticket de incidencia registrado para el usuario " + solicitante.getIdUsuario());
        pilaAuditoria.apilar(accion);

        return nuevoTicket;
    }

    public void asignarTicketAutomaticamente(Ticket nuevoTicket) {
        // Si no hay nadie trabajando, el sistema explota (o lo guardamos en la cola central como respaldo)
        if (asignatariosActivos.estaVacia()) {
            System.out.println("ALERTA: No hay asignatarios activos. El ticket irá a la sala de espera.");
            // ¡Lo guardamos en la cola global! (Sigue ordenándose por prioridad automáticamente)
            colaEsperaGlobal.encolar(nuevoTicket);
            // Registramos en auditoría que quedó en espera
            AccionAuditoria accion = new AccionAuditoria("EN_ESPERA", nuevoTicket.getCodigoTicket(),
                    "Ticket en cola central por falta de personal");
            pilaAuditoria.apilar(accion);
            return; // Terminamos la ejecución para que no intente asignarlo a nadie
        }
        Asignatario mejorAsignatario = null;

        int menorCargaDeTrabajo = Integer.MAX_VALUE;

        for (Asignatario assig : asignatariosActivos) {
            if (assig.getCantidadTicketsPendientes() < menorCargaDeTrabajo) {
                mejorAsignatario = assig;
                menorCargaDeTrabajo = assig.getCantidadTicketsPendientes();
            }
        }
        if (mejorAsignatario != null) {
            // Le inyectamos el ticket directamente a su ticketera personal
            mejorAsignatario.recibirTicketAsignado(nuevoTicket);
            System.out.println("Ticket " + nuevoTicket.getCodigoTicket() +
                    " asignado automáticamente a: " + mejorAsignatario.getNombreCompleto());

            // Opcional: Registrar en la auditoría
            AccionAuditoria accion = new AccionAuditoria("ASIGNACION_AUTO", nuevoTicket.getCodigoTicket(),
                    "Asignado a " + mejorAsignatario.getNombreCompleto());
            pilaAuditoria.apilar(accion);
        }
    }

    public void registrarUsuario(Usuario nuevoUsuario) {
        // Usamos el ID del usuario como clave (Ej: "E-100") y el objeto completo como valor
        usuariosRegistrados.insertar(nuevoUsuario.getIdUsuario(), nuevoUsuario);
    }

    public Usuario validarAcceso(String idUsuario, String contrasena) {
        // La Tabla Hash busca al usuario instantáneamente
        Usuario usuario = usuariosRegistrados.obtener(idUsuario);

        // Si el usuario existe y la contraseña es correcta
        if (usuario != null && usuario.getContraseniaHash().equals(contrasena)) {
            return usuario; // Login exitoso
        }

        return null; // Login fallido (no existe o clave incorrecta)
    }

    public Ticket buscarTicketPorCodigo(String codigoTicket) {
        // TODO: Buscar en colaTurnosPendientes y en historicoTickets.
        return null;
    }

    public boolean deshacerUltimaAccion() {
        // TODO: Sacar la ultima AccionAuditoria de pilaAuditoria y revertirla.
        return false;
    }

    public void registrarAsignatarioActivo(Asignatario asignatario) {
        // Asumiendo que tu lista se llama asignatariosActivos
        asignatariosActivos.agregar(asignatario);
    }

    /*public boolean guardarEstadoSistema() {
        return gestorPersistencia.guardarUsuariosEnArchivo(listaUsuarios)
                && gestorPersistencia.guardarTicketsEnArchivo(colaEsperaGlobal, historicoTickets);
    }*/

    public boolean cargarEstadoSistema() {
        // TODO: Cargar usuarios y tickets con gestorPersistencia y reconstruir la cola y el historico.
        return false;
    }
}
