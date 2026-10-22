package Controlador;
import Estructuras.ColaPrioridad;
import Estructuras.ListaEnlazada;
import Estructuras.Pila;
import Estructuras.TablaHash;
import Modelo.Auditoria.AccionAuditoria;
import Modelo.Enums.EstadoTicket;
import Modelo.Tickets.Incidencia.TicketIncidencia;
import Modelo.Tickets.Incidencia.TipoProblema;
import Modelo.Tickets.Ticket;
import Modelo.Users.Asignatario;
import Modelo.Users.Solicitante;
import Modelo.Users.Usuario;
import Persistencia.GestorPersistencia;
import java.io.Serializable;

public class GestorSoporteBancario implements Serializable {
    private static final long serialVersionUID = 1L;
    private ColaPrioridad<Ticket> colaEsperaGlobal;
    private ListaEnlazada<Ticket> historialTicketsResueltos;
    private TablaHash<String, Usuario> usuariosRegistrados;
    private ListaEnlazada<Ticket> historicoTickets;
    private Pila<AccionAuditoria> pilaAuditoria;
    private GestorPersistencia gestorPersistencia;
    private ListaEnlazada<Asignatario> asignatariosActivos = new ListaEnlazada<>();
    private int contadorTickets;

    public GestorSoporteBancario() {
        this.colaEsperaGlobal = new ColaPrioridad<>();
        this.usuariosRegistrados = new TablaHash<>();
        this.historicoTickets = new ListaEnlazada<>();
        this.pilaAuditoria = new Pila<>();
        this.historialTicketsResueltos = new ListaEnlazada<>();
        this.contadorTickets = 1;
        this.gestorPersistencia = new GestorPersistencia();
    }
    /*
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
    */
    public ListaEnlazada<Ticket> getHistorialTicketsResueltos() {
        return historialTicketsResueltos;
    }
    public void asignarTicketAutomaticamente(Ticket nuevoTicket) {
        if (asignatariosActivos.estaVacia()) {
            colaEsperaGlobal.encolar(nuevoTicket);
            System.out.println("No hay técnicos disponibles. Ticket enviado a Cola Global de Espera.");
            return;
        }
        Asignatario tecnicoMenosCargado = null;
        int menorCarga = Integer.MAX_VALUE;
        for (int i = 0; i < asignatariosActivos.getTamanio(); i++) {
            Asignatario tec = asignatariosActivos.obtener(i);
            int cargaActual = tec.getTicketeraPersonal().getTamanio();

            if (cargaActual < menorCarga) {
                menorCarga = cargaActual;
                tecnicoMenosCargado = tec;
            }
        }
        if (tecnicoMenosCargado != null) {
            tecnicoMenosCargado.getTicketeraPersonal().agregar(nuevoTicket);
            System.out.println("Ticket " + nuevoTicket.getCodigoTicket() +
                    " asignado automáticamente a " + tecnicoMenosCargado.getNombreCompleto());
        }
    }
    public void registrarUsuario(Usuario nuevoUsuario) {
        // Usamos el ID del usuario como clave (Ej: "E-100") y el objeto completo como valor
        usuariosRegistrados.insertar(nuevoUsuario.getIdUsuario(), nuevoUsuario);
    }
    public Usuario validarAcceso(String idUsuario, String contrasena) {
        Usuario usuario = usuariosRegistrados.obtener(idUsuario);
        if (usuario != null && usuario.getContraseniaHash().equals(contrasena)) {
            return usuario;
        }
        return null;
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
    public ListaEnlazada<Asignatario> getAsignatariosActivos() {
        return asignatariosActivos;
    }
    public ListaEnlazada<Solicitante> getSolicitantesRegistrados() {
        ListaEnlazada<Solicitante> solicitantes = new ListaEnlazada<>();
        // Obtenemos todos los usuarios almacenados en la Tabla Hash
        // (Asegúrate de que tu TablaHash tenga un método para obtener sus valores,
        //  o un método obtenerValores() que devuelva una ListaEnlazada<Usuario>)
        ListaEnlazada<Usuario> todosLosUsuarios = usuariosRegistrados.obtenerValores();

        if (todosLosUsuarios != null) {
            for (int i = 0; i < todosLosUsuarios.getTamanio(); i++) {
                Usuario u = todosLosUsuarios.obtener(i);

                // Filtramos usando polimorfismo/instanceof
                if (u instanceof Solicitante) {
                    solicitantes.agregar((Solicitante) u);
                }
            }
        }

        return solicitantes;
    }
    public ListaEnlazada<Ticket> getTicketsActivos() {
        ListaEnlazada<Ticket> ticketsActivos = new ListaEnlazada<>();
        if (colaEsperaGlobal != null) {
            ListaEnlazada<Ticket> globales = colaEsperaGlobal.toListaEnlazada();
            for (int i = 0; i < globales.getTamanio(); i++) {
                ticketsActivos.agregar(globales.obtener(i));
            }
        }
        if (asignatariosActivos != null) {
            for (int i = 0; i < asignatariosActivos.getTamanio(); i++) {
                Asignatario tec = asignatariosActivos.obtener(i);

                if (tec.getTicketeraPersonal() != null) {
                    ListaEnlazada<Ticket> ticketsTecnico = tec.getTicketeraPersonal();
                    for (int j = 0; j < ticketsTecnico.getTamanio(); j++) {
                        ticketsActivos.agregar(ticketsTecnico.obtener(j));
                    }
                }
            }
        }
        return ticketsActivos;
    }
    public boolean eliminarUsuario(String idOUsuarioRed, String usuarioLogueado) {
        if (idOUsuarioRed.equalsIgnoreCase(usuarioLogueado)) {
            System.out.println("No puedes eliminar tu propia cuenta mientras estás en sesión.");
            return false;
        }
        Usuario usuarioAEliminar = usuariosRegistrados.obtener(idOUsuarioRed);
        if (usuarioAEliminar == null) {
            return false;
        }
        if (usuarioAEliminar instanceof Asignatario) {
            Asignatario asig = (Asignatario) usuarioAEliminar;
            if (asig.getTicketeraPersonal() != null && !asig.getTicketeraPersonal().estaVacia()) {
                System.out.println("⚠️ No se puede eliminar: El técnico tiene tickets pendientes en su cola.");
                return false;
            }
            removerAsignatarioActivo(asig);
        }
        return usuariosRegistrados.eliminar(idOUsuarioRed);
    }
    private void removerAsignatarioActivo(Asignatario asig) {
        for (int i = 0; i < asignatariosActivos.getTamanio(); i++) {
            if (asignatariosActivos.obtener(i).getIdUsuario().equals(asig.getIdUsuario())) {
                asignatariosActivos.eliminarEnPosicion(i);
                break;
            }
        }
    }
    public String generarNuevoIdTicket() {
        String idTicket = String.format("TCK-%03d", contadorTickets);
        contadorTickets++;
        return idTicket;
    }
    public boolean registrarYAsignarTicket(Ticket nuevoTicket) {
        if (nuevoTicket == null) {
            return false;
        }
        if (asignatariosActivos != null && !asignatariosActivos.estaVacia()) {
            Asignatario tecnicoMenosCargado = null;
            int menorCargaTickets = Integer.MAX_VALUE;
            for (int i = 0; i < asignatariosActivos.getTamanio(); i++) {
                Asignatario tec = asignatariosActivos.obtener(i);
                int cargaActual = tec.getTicketeraPersonal().getTamanio();
                if (cargaActual < menorCargaTickets) {
                    menorCargaTickets = cargaActual;
                    tecnicoMenosCargado = tec;
                }
            }
            if (tecnicoMenosCargado != null) {
                nuevoTicket.asignarTecnico(tecnicoMenosCargado);
                nuevoTicket.setEstado(EstadoTicket.EN_PROGRESO);
                tecnicoMenosCargado.getTicketeraPersonal().agregar(nuevoTicket);
                System.out.println("🤖 Ticket asignado automáticamente al técnico: "
                        + tecnicoMenosCargado.getNombreCompleto());
                return true;
            }
        }
        nuevoTicket.setEstado(EstadoTicket.EN_ESPERA_USUARIO);
        colaEsperaGlobal.encolar(nuevoTicket);
        System.out.println("No hay técnicos activos disponibles. Ticket ingresado a la Cola de Espera Global.");
        return true;
    }
    public ListaEnlazada<Ticket> obtenerTicketsActivosPorSolicitante(String idSolicitante) {
        ListaEnlazada<Ticket> ticketsActivos = new ListaEnlazada<>();
        if (idSolicitante == null || idSolicitante.isEmpty()) {
            return ticketsActivos;
        }
        if (colaEsperaGlobal != null) {
            ListaEnlazada<Ticket> listaGlobal = colaEsperaGlobal.toListaEnlazada();
            for (int i = 0; i < listaGlobal.getTamanio(); i++) {
                Ticket t = listaGlobal.obtener(i);
                if (t.getSolicitante() != null && t.getSolicitante().getIdUsuario().equals(idSolicitante)) {
                    ticketsActivos.agregar(t);
                }
            }
        }
        if (asignatariosActivos != null) {
            for (int i = 0; i < asignatariosActivos.getTamanio(); i++) {
                Asignatario tec = asignatariosActivos.obtener(i);
                if (tec.getTicketeraPersonal() != null) {
                    ListaEnlazada<Ticket> listaTecnico = tec.getTicketeraPersonal();
                    for (int j = 0; j < listaTecnico.getTamanio(); j++) {
                        Ticket t = listaTecnico.obtener(j);
                        if (t.getSolicitante() != null && t.getSolicitante().getIdUsuario().equals(idSolicitante)) {
                            ticketsActivos.agregar(t);
                        }
                    }
                }
            }
        }

        return ticketsActivos;
    }
    public ListaEnlazada<Ticket> obtenerTicketsResueltosPorSolicitante(String idSolicitante) {
        ListaEnlazada<Ticket> misResueltos = new ListaEnlazada<>();
        if (idSolicitante == null || idSolicitante.isEmpty() || historialTicketsResueltos == null) {
            return misResueltos;
        }
        // Recorremos la lista global de historial de tickets resueltos
        for (int i = 0; i < historialTicketsResueltos.getTamanio(); i++) {
            Ticket t = historialTicketsResueltos.obtener(i);
            if (t.getSolicitante() != null && t.getSolicitante().getIdUsuario().equals(idSolicitante)) {
                misResueltos.agregar(t);
            }
        }
        return misResueltos;
    }
    public int cancelarTicketPendiente(String codigoTicket, String idSolicitante) {
        if (codigoTicket == null || codigoTicket.isEmpty() || colaEsperaGlobal == null) {
            return 0;
        }
        ListaEnlazada<Ticket> listaEspera = colaEsperaGlobal.toListaEnlazada();
        int posicionEncontrada = -1;
        Ticket ticketAAnular = null;
        for (int i = 0; i < listaEspera.getTamanio(); i++) {
            Ticket t = listaEspera.obtener(i);
            if (t.getCodigoTicket().equalsIgnoreCase(codigoTicket.trim())) {
                posicionEncontrada = i;
                ticketAAnular = t;
                break;
            }
        }
        if (posicionEncontrada == -1) {
            if (asignatariosActivos != null) {
                for (int i = 0; i < asignatariosActivos.getTamanio(); i++) {
                    Asignatario tec = asignatariosActivos.obtener(i);
                    ListaEnlazada<Ticket> listaTecnico = tec.getTicketeraPersonal();
                    for (int j = 0; j < listaTecnico.getTamanio(); j++) {
                        if (listaTecnico.obtener(j).getCodigoTicket().equalsIgnoreCase(codigoTicket.trim())) {
                            return -1; // El ticket ya está EN_ATENCION y no se puede cancelar directamente
                        }
                    }
                }
            }
            return 0;
        }
        // 2. Validar que el ticket pertenezca al solicitante logueado
        if (!ticketAAnular.getSolicitante().getIdUsuario().equals(idSolicitante)) {
            return -2; // No tiene permisos para anular este ticket
        }
        // 3. Eliminación de la cola global de espera
        listaEspera.eliminarEnPosicion(posicionEncontrada);
        ticketAAnular.setEstado(EstadoTicket.CANCELADO);
        return 1;
    }
}
