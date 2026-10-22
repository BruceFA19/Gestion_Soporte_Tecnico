package Vista;
import Controlador.GestorSoporteBancario;
import Estructuras.ListaEnlazada;
import Modelo.Enums.EstadoTicket;
import Modelo.Enums.RolUsuario;
import Modelo.Tickets.Incidencia.TicketIncidencia;
import Modelo.Tickets.Incidencia.TipoProblema;
import Modelo.Tickets.Requerimiento.TicketRequerimiento;
import Modelo.Tickets.Requerimiento.TipoServicio;
import Modelo.Tickets.Ticket;
import Modelo.Tickets.Comentario;
import Modelo.Users.*;
import Persistencia.GestorPersistencia;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;
public class VistaConsola {
    private GestorSoporteBancario gestor;
    private Scanner scanner;
    private Usuario usuarioLogueado;
    public VistaConsola(GestorSoporteBancario gestor) {
        this.gestor = gestor;
        this.scanner = new Scanner(System.in);
    }
    //Metodo de inicio de programa
    public void iniciar() {
        int opcion = 0;
        do {
            System.out.println("\n========================================");
            System.out.println("   SISTEMA DE HELP DESK BANCARIO");
            System.out.println("========================================");
            System.out.println("1. Iniciar Sesión");
            System.out.println("2. Salir del Sistema");
            System.out.print("Seleccione una opción: ");

            //En caso de meter String
            try {
                opcion = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                opcion = 0;
            }
            //en caso e meter opcion numerica no valida
            switch (opcion) {
                case 1:
                    procesarLogin();
                    break;
                case 2:
                    System.out.println("Saliendo el sistema...");
                    break;
                default:
                    System.out.println("Opción no válida. Intente de nuevo.");
            }
        } while (opcion != 2);
    }

    //Metodo de login
    private void procesarLogin() {
        System.out.println("\n--- INICIO DE SESIÓN ---");
        System.out.print("Ingrese su ID de Usuario: ");
        String id = scanner.nextLine();
        System.out.print("Ingrese su Contraseña: ");
        String pass = scanner.nextLine();
        Usuario usuarioAutenticado = gestor.validarAcceso(id, pass);
        if (usuarioAutenticado == null) {
            System.out.println("Credenciales incorrectas o usuario no encontrado.");
            return;
        }
        this.usuarioLogueado = usuarioAutenticado;
        System.out.println("¡Bienvenido, " + usuarioLogueado.getNombreCompleto() + "!");
        if (usuarioLogueado instanceof Asignatario) {
            mostrarMenuAsignatario((Asignatario) usuarioLogueado);
        } else if (usuarioLogueado instanceof Solicitante) {
            mostrarMenuSolicitante((Solicitante) usuarioLogueado);
        } else if (usuarioLogueado instanceof Administrador) {
            mostrarMenuAdministrador((Administrador) usuarioLogueado);
        } else {
            System.out.println("Tipo de usuario no reconocido por el menú.");
        }
    }

    //metodos de invocacion de menus
    private void mostrarMenuAsignatario(Asignatario asignatario) {
        int opcion = 0;
        do {
            System.out.println("\n         PANEL DE ASIGNATARIO");
            System.out.println("----------------------------------------------------");
            System.out.println("1. Ver mi cola de tickets");
            System.out.println("2. Atender siguiente ticket");
            System.out.println("3. Resolver ticket en atención");
            System.out.println("4. Ver detalle de un ticket");
            System.out.println("5. Agregar comentario a un ticket");
            System.out.println("6. Aprobar / Rechazar requerimientos");
            System.out.println("7. Ver mi historial de tickets resueltos");
            System.out.println("8. Cerrar Sesión");
            System.out.print("Seleccione una opción: ");
            try {
                opcion = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                opcion = 0;
            }
            switch (opcion) {
                case 1:
                    mostrarMiColaTickets();
                    break;
                case 2:
                    atenderSiguienteTicket();
                    break;
                case 3:
                    resolverTicketEnAtencion();
                    break;
                case 4:
                    verDetalleTicket();
                    break;
                case 5:
                    agregarComentarioTicket();
                    break;
                case 6:
                    gestionarAprobacionRequerimientos();
                    break;
                case 7:
                    mostrarMiHistorialResueltos();
                    break;
                case 8:
                    System.out.println("Cerrando sesión de asignatario...");
                    break;
                default:
                    System.out.println("Opción no válida. Intente nuevamente.");
            }
        } while (opcion != 8);
        // Aquí pondremos las opciones de ver tickets, atender siguiente, etc.
    }
    private void mostrarMenuSolicitante(Solicitante solicitante) {
        int opcion = 0;
        do {
            System.out.println("\n         PANEL DE SOLICITANTE");
            System.out.println("----------------------------------------------------");
            System.out.println("1. Crear nuevo ticket (Incidencia / Requerimiento)");
            System.out.println("2. Ver estado de mis tickets activos");
            System.out.println("3. Ver historial de mis tickets resueltos");
            System.out.println("4. Cancelar un ticket pendiente");
            System.out.println("5. Ver mi perfil y departamento");
            System.out.println("6. Cerrar Sesión");
            System.out.print("Seleccione una opción: ");
            try {
                opcion = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                opcion = 0;
            }
            switch (opcion) {
                case 1:
                    crearNuevoTicket();
                    break;
                case 2:
                    mostrarMisTicketsActivos();
                    break;
                case 3:
                    mostrarMisTicketsResueltos();
                    break;
                case 4:
                    cancelarTicketPendiente();
                    break;
                case 5:
                    mostrarMiPerfil();
                    break;
                case 6:
                    System.out.println("Cerrando sesión de solicitante...");
                    break;
                default:
                    System.out.println("Opción no válida. Intente nuevamente.");
            }
        } while (opcion != 6);
    }
    private void mostrarMenuAdministrador(Administrador administrador) {
        int opcion = 0;
        do {
            System.out.println("\n            PANEL DE ADMINISTRADOR");
            System.out.println("----------------------------------------------------");
            System.out.println("1. Agregar nuevo usuario (Solicitante/Asignatario)");
            System.out.println("2. Ver listas (Lista de Solicitantes/Asignatario)");
            System.out.println("3. Ver listas (Lista de Solicitantes/Asignatario)");
            System.out.println("4. Ver todos los tickets activos (En espera y en atención)");
            System.out.println("5. Ver tickets resueltos (Historial)");
            System.out.println("6. Eliminar usuario (Solicitante/Asignatario)");
            System.out.println("7. Cerrar Sesión");
            System.out.print("Seleccione una opción: ");
            try {
                opcion = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                opcion = 0;
            }
            switch (opcion) {
                case 1:
                    crearNuevoUsuario();
                    break;
                case 2:
                    mostrarOpcionesListas();
                    break;
                case 3:
                    break;
                case 4:
                    mostrarTicketsActivos();
                    break;
                case 5:
                    mostrarTicketsResueltos();
                    break;
                case 6:
                    eliminarUsuario();
                    break;
                case 7:
                    System.out.println("Cerrando sesión de administrador...");
                    break;
                default:
                    System.out.println("Opción no válida. Intente nuevamente.");
            }
        } while (opcion != 7);
    }
    //Metodos para el menu administrador
    private void crearNuevoUsuario() {
        System.out.println("\n---REGISTRO DE NUEVO USUARIO ---");
        System.out.println("¿Qué tipo de rol tendrá el nuevo usuario?");
        System.out.println("1. Solicitante (Empleado Regular)");
        System.out.println("2. Asignatario (Técnico de Soporte)");
        System.out.println("3. Cancelar");
        System.out.print("Seleccione (1-3): ");
        int tipoRol = 0;
        try {
            tipoRol = Integer.parseInt(scanner.nextLine().trim());
        } catch (Exception e) {
            System.out.println("Entrada inválida. Operación cancelada.");
            return;
        }
        if (tipoRol == 3) {
            System.out.println("Operación cancelada por el usuario.");
            return;
        }
        if (tipoRol != 1 && tipoRol != 2) {
            System.out.println("Opción no válida. Operación cancelada.");
            return;
        }
        System.out.print("Nombre: ");
        String nombre = scanner.nextLine().trim();
        System.out.print("Apellido: ");
        String apellido = scanner.nextLine().trim();
        System.out.print("Cédula: ");
        String cedula = scanner.nextLine().trim();
        System.out.print("Contraseña: ");
        String pass = scanner.nextLine().trim();

        if (tipoRol == 1) {
            System.out.println("\n---SELECCIÓN DE DEPARTAMENTO ---");
            System.out.println("Departamentos disponibles:");
            gestor.listarDepartamentos();
            System.out.print("\nIngrese el código del Departamento (Ej. DEP-001): ");
            String idDeptoSeleccionado = scanner.nextLine().trim();
            Departamento deptoExistente = gestor.buscarDepartamentoPorId(idDeptoSeleccionado);
            if (deptoExistente == null) {
                System.out.println("El código de departamento ingresado no existe. Operación cancelada.");
                return;
            }
            String idUsuarioAuto = gestor.generarNuevoIdUsuario("SOLICITANTE");
            Solicitante nuevoSolicitante = new Solicitante(
                    idUsuarioAuto, nombre, apellido, cedula, pass, RolUsuario.SOLICITANTE, deptoExistente
            );
            if (gestor.registrarUsuario(nuevoSolicitante)) {
                System.out.println("\nSolicitante registrado exitosamente en el departamento: " + deptoExistente.getNombreArea());
                System.out.println("CÓDIGO ASIGNADO: " + idUsuarioAuto);

                GestorPersistencia.guardarEstado(this.gestor);
            } else {
                System.out.println("No se pudo registrar el Solicitante.");
            }
        } else if (tipoRol == 2) {
            String idUsuarioAuto = gestor.generarNuevoIdUsuario("ASIGNATARIO");

            Asignatario nuevoAsignatario = new Asignatario(
                    idUsuarioAuto, nombre, apellido, cedula, pass, RolUsuario.ASIGNATARIO
            );

            if (gestor.registrarUsuario(nuevoAsignatario)) {
                gestor.registrarAsignatarioActivo(nuevoAsignatario);
                System.out.println("\nAsignatario técnico registrado y activado para recibir tickets.");
                System.out.println("CÓDIGO ASIGNADO: " + idUsuarioAuto);
                GestorPersistencia.guardarEstado(this.gestor);
            } else {
                System.out.println("No se pudo registrar el Asignatario.");
            }
        }
    }
    private void mostrarAsignatariosActivos() {
        System.out.println("\n---LISTA DE ASIGNATARIOS TÉCNICOS ACTIVOS ---");
        ListaEnlazada<Asignatario> asignatarios = gestor.getAsignatariosActivos();
        if (asignatarios == null || asignatarios.estaVacia()) {
            System.out.println("No hay asignatarios técnicos registrados o activos en el sistema.");
            return;
        }
        System.out.printf("%-10s | %-25s | %-15s | %-15s\n", "ID", "NOMBRE COMPLETO", "USUARIO RED", "TICKETS EN COLA");
        System.out.println("------------------------------------------------------------------------------");
        for (int i = 0; i < asignatarios.getTamanio(); i++) {
            Asignatario asig = asignatarios.obtener(i);
            String nombreCompleto = asig.getNombreCompleto();
            int cantTickets = asig.getTicketeraPersonal().getTamanio();
            System.out.printf("%-10s | %-25s | %-15d\n",
                    asig.getIdUsuario(),
                    nombreCompleto,
                    cantTickets);
        }
    }
    private void mostrarSolicitantesRegistrados() {
        System.out.println("\n---LISTA DE SOLICITANTES REGISTRADOS ---");
        ListaEnlazada<Solicitante> solicitantes = gestor.getSolicitantesRegistrados();
        if (solicitantes == null || solicitantes.estaVacia()) {
            System.out.println("No hay solicitantes registrados en la Tabla Hash.");
            return;
        }
        System.out.printf("%-10s | %-25s | %-12s | %-20s | %-15s\n",
                "ID", "NOMBRE COMPLETO", "CÉDULA", "DEPARTAMENTO", "USUARIO RED");
        System.out.println("-----------------------------------------------------------------------------------------");
        for (int i = 0; i < solicitantes.getTamanio(); i++) {
            Solicitante sol = solicitantes.obtener(i);
            String nombreCompleto = sol.getNombreCompleto();
            String nombreDepto = (sol.getDepartamento() != null) ? sol.getDepartamento().getNombreArea() : "Sin Asignar";
            System.out.printf("%-10s | %-25s | %-12s | %-20s \n",
                    sol.getIdUsuario(),
                    nombreCompleto,
                    sol.getCedula(),
                    nombreDepto);
        }
    }
    private void mostrarTicketsActivos() {
        System.out.println("\n---TICKETS ACTIVOS EN EL SISTEMA ---");
        ListaEnlazada<Ticket> tickets = gestor.getTicketsActivos();
        if (tickets == null || tickets.estaVacia()) {
            System.out.println("No hay tickets activos en este momento. Todas las solicitudes e incidencias están resueltas.");
            return;
        }
        System.out.printf("%-10s | %-13s | %-20s | %-10s | %-20s | %-20s\n",
                "ID", "TIPO", "TÍTULO", "PRIORIDAD", "SOLICITANTE", "DETALLE CATÁLOGO");
        System.out.println("---------------------------------------------------------------------------------------------------");
        for (int i = 0; i < tickets.getTamanio(); i++) {
            Ticket t = tickets.obtener(i);
            String tipo = (t instanceof TicketIncidencia) ? "Incidencia" : "Requerimiento";
            String detalleCat = "N/A";
            if (t instanceof TicketIncidencia) {
                TicketIncidencia inc = (TicketIncidencia) t;
                if (inc.getTipoProblema() != null) {
                    detalleCat = inc.getTipoProblema().getCodigoCategoria();
                }
            } else if (t instanceof TicketRequerimiento) {
                TicketRequerimiento req = (TicketRequerimiento) t;
                if (req.getTipoServicio() != null) {
                    detalleCat = req.getTipoServicio().getNombreServicio();
                }
            }
            String solicitanteStr = (t.getSolicitante() != null)
                    ? t.getSolicitante().getNombreCompleto()
                    : "Anónimo";
            System.out.printf("%-10s | %-13s | %-20s | %-10d | %-20s | %-20s\n",
                    t.getCodigoTicket(),
                    tipo,
                    t.getTitulo(),
                    t.getNivelPrioridad(),
                    solicitanteStr,
                    detalleCat);
        }
    }
    private void mostrarTicketsResueltos() {
        System.out.println("\n---HISTORIAL DE TICKETS RESUELTOS ---");
        ListaEnlazada<Ticket> resueltos = gestor.getHistorialTicketsResueltos();
        if (resueltos == null || resueltos.estaVacia()) {
            System.out.println("Aún no hay tickets registrados como resueltos en el historial.");
            return;
        }
        System.out.printf("%-10s | %-13s | %-25s | %-20s | %-15s\n",
                "ID", "TIPO", "TÍTULO", "SOLICITANTE", "ESTADO");
        System.out.println("-----------------------------------------------------------------------------------------");
        for (int i = 0; i < resueltos.getTamanio(); i++) {
            Ticket t = resueltos.obtener(i);
            String tipo = (t instanceof TicketIncidencia) ? "Incidencia" : "Requerimiento";
            String solicitanteStr = (t.getSolicitante() != null)
                    ? t.getSolicitante().getNombreCompleto()
                    : "Anónimo";
            System.out.printf("%-10s | %-13s | %-25s | %-20s | %-15s\n",
                    t.getCodigoTicket(),
                    tipo,
                    t.getTitulo(),
                    solicitanteStr,
                    "RESUELTO");
        }
    }
    private void eliminarUsuario() {
        System.out.println("\n---ELIMINAR USUARIO ---");
        System.out.print("Ingrese el ID o Usuario de Red de la cuenta a eliminar: ");
        String identificador = scanner.nextLine().trim();
        if (identificador.isEmpty()) {
            System.out.println("El identificador no puede estar vacío.");
            return;
        }
        System.out.print("¿Está seguro de que desea eliminar al usuario '" + identificador + "'? (S/N): ");
        String confirmacion = scanner.nextLine().trim();
        if (confirmacion.equalsIgnoreCase("S")) {
            boolean exito = gestor.eliminarUsuario(identificador, usuarioLogueado.getIdUsuario());
            if (exito) {
                System.out.println("Usuario '" + identificador + "' eliminado exitosamente del catálogo.");
            } else {
                System.out.println("No se pudo eliminar el usuario. Verifique el identificador o si tiene tickets asignados.");
            }
        } else {
            System.out.println("Operación cancelada.");
        }
    }
    private void mostrarOpcionesListas() {
        System.out.println("\n---SUBMENÚ: CONSULTA DE LISTAS ---");
        System.out.println("1. Ver lista de Asignatarios activos");
        System.out.println("2. Ver lista de Solicitantes registrados");
        System.out.println("0. Volver al menú de administrador");
        System.out.print("Seleccione una opción: ");
        String opcionStr = scanner.nextLine().trim();
        switch (opcionStr) {
            case "1":
                mostrarAsignatariosActivos();
                break;
            case "2":
                mostrarSolicitantesRegistrados();
                break;
            case "0":
                System.out.println("Regresando al menú principal...");
                break;
            default:
                System.out.println("Opción inválida. Intente de nuevo.");
                break;
        }
    }
    //Metodos para el menu asignatario
    private void mostrarMiColaTickets() {
        System.out.println("\n--- MI COLA DE TICKETS ---");
        if (!(this.usuarioLogueado instanceof Asignatario)) {
            System.out.println("Error: No hay una sesión de asignatario activa.");
            return;
        }
        Asignatario tecnico = (Asignatario) this.usuarioLogueado;
        ListaEnlazada<Ticket> cola = tecnico.getTicketeraPersonal();
        if (cola == null || cola.estaVacia()) {
            System.out.println("No tienes tickets asignados en este momento.");
            return;
        }
        int n = cola.getTamanio();
        Ticket[] ordenados = new Ticket[n];
        for (int i = 0; i < n; i++) {
            ordenados[i] = cola.obtener(i);
        }
        for (int i = 1; i < n; i++) {
            Ticket actual = ordenados[i];
            int j = i - 1;
            while (j >= 0 && tieneMasPrioridad(actual, ordenados[j])) {
                ordenados[j + 1] = ordenados[j];
                j--;
            }
            ordenados[j + 1] = actual;
        }
        System.out.printf("%-3s | %-10s | %-13s | %-25s | %-10s | %-8s | %-12s | %-12s\n",
                "#", "CÓDIGO", "TIPO", "TÍTULO", "PRIORIDAD", "PUNTAJE", "ESTADO", "FECHA LÍMITE");
        System.out.println("--------------------------------------------------------------------------------------------------------------");
        for (int i = 0; i < n; i++) {
            Ticket t = ordenados[i];
            String tipo = (t instanceof TicketIncidencia) ? "Incidencia" : "Requerimiento";
            System.out.printf("%-3d | %-10s | %-13s | %-25s | %-10s | %-8d | %-12s | %-12s\n",
                    (i + 1),
                    t.getCodigoTicket(),
                    tipo,
                    recortar(t.getTitulo(), 25),
                    t.getNivelPrioridad(),
                    t.getPuntajePrioridad(),
                    t.getEstado(),
                    formatearFecha(t.getFechaLimiteResolucion()));
        }
        System.out.println("\nTotal en cola: " + n);
    }
    private void atenderSiguienteTicket() {
        System.out.println("\n--- ATENDER SIGUIENTE TICKET ---");
        if (!(this.usuarioLogueado instanceof Asignatario)) {
            System.out.println("Error: No hay una sesión de asignatario activa.");
            return;
        }
        Asignatario tecnico = (Asignatario) this.usuarioLogueado;
        ListaEnlazada<Ticket> cola = tecnico.getTicketeraPersonal();
        if (cola == null || cola.estaVacia()) {
            System.out.println("No tienes tickets en tu cola.");
            return;
        }
        Ticket siguiente = null;
        for (int i = 0; i < cola.getTamanio(); i++) {
            Ticket t = cola.obtener(i);
            if (t.getEstado() == EstadoTicket.EN_PROGRESO) {
                System.out.println("Ya estás atendiendo el ticket " + t.getCodigoTicket()
                        + ". Resuélvelo antes de tomar otro.");
                return;
            }
            if (t.getEstado() == EstadoTicket.ASIGNADO
                    && (siguiente == null || tieneMasPrioridad(t, siguiente))) {
                siguiente = t;
            }
        }
        if (siguiente == null) {
            System.out.println("No hay tickets pendientes por atender.");
            return;
        }
        siguiente.cambiarEstado(EstadoTicket.EN_PROGRESO);
        String solicitante = (siguiente.getSolicitante() != null)
                ? siguiente.getSolicitante().getNombreCompleto()
                : "Anónimo";
        String tipo = (siguiente instanceof TicketIncidencia) ? "INCIDENCIA TÉCNICA" : "REQUERIMIENTO DE SERVICIO";
        System.out.println("Has tomado el siguiente ticket:");
        System.out.println("====================================================");
        System.out.println(" CÓDIGO       : " + siguiente.getCodigoTicket());
        System.out.println(" TIPO         : " + tipo);
        System.out.println(" TÍTULO       : " + siguiente.getTitulo());
        System.out.println(" DETALLE      : " + siguiente.getDescripcion());
        System.out.println(" SOLICITANTE  : " + solicitante);
        System.out.println(" PRIORIDAD    : " + siguiente.getNivelPrioridad()
                + " (puntaje " + siguiente.getPuntajePrioridad() + ")");
        System.out.println(" FECHA LÍMITE : " + formatearFecha(siguiente.getFechaLimiteResolucion()));
        System.out.println("====================================================");
    }
    private void resolverTicketEnAtencion() {
    System.out.println("\n--- RESOLVER TICKET EN ATENCIÓN ---");
    ListaEnlazada<Ticket> cola = ((Asignatario) usuarioLogueado).getTicketeraPersonal();
    for (int i = 0; i < cola.getTamanio(); i++) {
        Ticket t = cola.obtener(i);
        if (t.getEstado() == EstadoTicket.EN_PROGRESO) {
            t.cambiarEstado(EstadoTicket.RESUELTO);
            cola.eliminar(i);
            gestor.getHistorialTicketsResueltos().agregar(t);
            System.out.println("Ticket " + t.getCodigoTicket() + " resuelto correctamente.");
            return;
        }
    }
    System.out.println("No tienes ningún ticket en atención.");
}

    private void verDetalleTicket() {
        System.out.println("\n--- DETALLE DE TICKET ---");
        System.out.print("Ingrese el CÓDIGO del ticket: ");
        String codigo = scanner.nextLine().trim();
        ListaEnlazada<Ticket> cola = ((Asignatario) usuarioLogueado).getTicketeraPersonal();
        for (int i = 0; i < cola.getTamanio(); i++) {
            Ticket t = cola.obtener(i);
            if (t.getCodigoTicket().equalsIgnoreCase(codigo)) {
                System.out.println("====================================================");
                System.out.println(" CÓDIGO       : " + t.getCodigoTicket());
                System.out.println(" TÍTULO       : " + t.getTitulo());
                System.out.println(" DETALLE      : " + t.getDescripcion());
                System.out.println(" SOLICITANTE  : " + t.getSolicitante().getNombreCompleto());
                System.out.println(" PRIORIDAD    : " + t.getNivelPrioridad() + " (puntaje " + t.getPuntajePrioridad() + ")");
                System.out.println(" ESTADO       : " + t.getEstado());
                System.out.println(" FECHA LÍMITE : " + formatearFecha(t.getFechaLimiteResolucion()));
                System.out.println("--- COMENTARIOS ---");
                if (t.getHistorialComentarios().estaVacia()) {
                    System.out.println(" (Sin comentarios)");
                }
                for (Comentario c : t.getHistorialComentarios()) {
                    System.out.println(" [" + formatearFecha(c.getFechaCreacion()) + "] "
                            + c.getIdAutor() + ": " + c.getTexto());
                }
                System.out.println("====================================================");
                return;
            }
        }
        System.out.println("No se encontró ese ticket en tu cola.");
    }

    private void agregarComentarioTicket() {
        System.out.println("\n--- AGREGAR COMENTARIO ---");
        System.out.print("Ingrese el CÓDIGO del ticket: ");
        String codigo = scanner.nextLine().trim();
        ListaEnlazada<Ticket> cola = ((Asignatario) usuarioLogueado).getTicketeraPersonal();
        for (int i = 0; i < cola.getTamanio(); i++) {
            Ticket t = cola.obtener(i);
            if (t.getCodigoTicket().equalsIgnoreCase(codigo)) {
                System.out.print("Escriba su comentario: ");
                String texto = scanner.nextLine().trim();
                if (texto.isEmpty()) {
                    System.out.println("El comentario no puede estar vacío.");
                    return;
                }
                t.getHistorialComentarios().agregar(new Comentario(usuarioLogueado.getIdUsuario(), texto));
                System.out.println("Comentario agregado.");
                return;
            }
        }
        System.out.println("No se encontró ese ticket en tu cola.");
    }

    private void gestionarAprobacionRequerimientos() {
        System.out.println("\n--- REQUERIMIENTOS PENDIENTES DE APROBACIÓN ---");
        ListaEnlazada<Ticket> cola = ((Asignatario) usuarioLogueado).getTicketeraPersonal();
        boolean hayPendientes = false;
        for (int i = 0; i < cola.getTamanio(); i++) {
            Ticket t = cola.obtener(i);
            if (t instanceof TicketRequerimiento req && !req.isEstaAprobado()) {
                hayPendientes = true;
                System.out.println(t.getCodigoTicket() + " | " + t.getTitulo()
                        + " | " + req.getTipoServicio().getNombreServicio());
            }
        }
        if (!hayPendientes) {
            System.out.println("No tienes requerimientos pendientes de aprobación.");
            return;
        }
        System.out.print("Ingrese el CÓDIGO a gestionar: ");
        String codigo = scanner.nextLine().trim();
        for (int i = 0; i < cola.getTamanio(); i++) {
            Ticket t = cola.obtener(i);
            if (t instanceof TicketRequerimiento req && !req.isEstaAprobado()
                    && t.getCodigoTicket().equalsIgnoreCase(codigo)) {
                System.out.print("1. Aprobar | 2. Rechazar: ");
                String op = scanner.nextLine().trim();
                if (op.equals("1")) {
                    req.aprobarRequerimiento();
                    System.out.println("Requerimiento aprobado.");
                } else if (op.equals("2")) {
                    t.setEstado(EstadoTicket.CANCELADO);
                    cola.eliminar(i);
                    System.out.println("Requerimiento rechazado y retirado de tu cola.");
                } else {
                    System.out.println("Opción no válida.");
                }
                return;
            }
        }
        System.out.println("No se encontró un requerimiento pendiente con ese código.");
    }

    private void mostrarMiHistorialResueltos() {
        System.out.println("\n--- MI HISTORIAL DE TICKETS RESUELTOS ---");
        ListaEnlazada<Ticket> resueltos = gestor.getHistorialTicketsResueltos();
        int contador = 0;
        for (int i = 0; i < resueltos.getTamanio(); i++) {
            Ticket t = resueltos.obtener(i);
            if (t.getAsignatario() != null
                    && t.getAsignatario().getIdUsuario().equals(usuarioLogueado.getIdUsuario())) {
                System.out.println(t.getCodigoTicket() + " | " + recortar(t.getTitulo(), 25)
                        + " | " + t.getSolicitante().getNombreCompleto()
                        + " | Resuelto: " + formatearFecha(t.getFechaResolucion()));
                contador++;
            }
        }
        if (contador == 0) {
            System.out.println("Aún no has resuelto tickets.");
        } else {
            System.out.println("\nTotal resueltos: " + contador);
        }
    }
    //Metodos para el menu solicitante
    private void crearNuevoTicket() {
        int subOpcion = 0;
        System.out.println("\n---CREAR NUEVO TICKET ---");
        System.out.println("1. Reportar Incidencia (Falla técnica, error de sistema, equipo dañado)");
        System.out.println("2. Solicitar Requerimiento (Acceso a sistemas, nuevo equipo, insumos)");
        System.out.println("0. Volver al menú de solicitante");
        System.out.print("Seleccione el tipo de ticket: ");
        try {
            subOpcion = Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            subOpcion = 0;
        }
        switch (subOpcion) {
            case 1:
                registrarIncidencia();
                break;
            case 2:
                registrarRequerimiento();
                break;
            case 0:
                System.out.println("ℹ️ Operación cancelada. Regresando...");
                break;
            default:
                System.out.println("⚠️ Opción no válida.");
                break;
        }
    }
    private void registrarIncidencia() {
        System.out.println("\n---REGISTRAR INCIDENCIA ---");
        //idEquipo
        System.out.print("Ingrese el id del equipo: ");
        String idEquipo = scanner.nextLine().trim();
        if (idEquipo.isEmpty()) {
            idEquipo = "N/A";
        }
        //titulo del ticket
        System.out.print("Ingrese un título breve del problema: ");
        String titulo = scanner.nextLine().trim();
        //descripcion del ticket
        System.out.print("Ingrese la descripción detallada de la falla: ");
        String descripcion = scanner.nextLine().trim();
        //aseguramos que el ticket contenga titulo y descripcion
        if (titulo.isEmpty() || descripcion.isEmpty()) {
            System.out.println("El título y la descripción son obligatorios.");
            return;
        }
        //alcance de la afectacion
        System.out.println("\n¿A cuántos usuarios o puestos afecta la falla?");
        System.out.println("1. Solo a mí (Alcance Individual - 1)");
        System.out.println("2. A mi departamento/área (Alcance Grupal - 2)");
        System.out.println("3. A toda la agencia / red bancaria (Alcance Masivo - 3)");
        System.out.print("Seleccione alcance (1-3): ");
        int alcance = leerOpcionRango(1, 3);
        //Bloqueo de la afectacion
        System.out.println("\n¿Qué tanto interrumpe su trabajo diario?");
        System.out.println("1. Leve (Puedo seguir trabajando en otras tareas - 1)");
        System.out.println("2. Moderado (Trabajo parcialmente detenido - 2)");
        System.out.println("3. Total (Imposible trabajar / Operación paralizada - 3)");
        System.out.print("Seleccione nivel de bloqueo (1-3): ");
        int bloqueo = leerOpcionRango(1, 3);
        //categoria del problema
        System.out.println("\nSeleccione la categoría del problema:");
        System.out.println("1. Falla de Hardware (Impresora, Monitor, Lector Biométrico)");
        System.out.println("2. Falla de Software (Sistema de caja, Core Bancario, Correo)");
        System.out.println("3. Conectividad / Red (Sin internet, VPN caída)");
        System.out.print("Opción: ");
        int catOp = 0;
        try {
            catOp = Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            catOp = 1;
        }
        TipoProblema tipoProblema;
        switch (catOp) {
            case 1:
                tipoProblema = new TipoProblema("CAT-HW", "Falla de Hardware", 24);
                break;
            case 2:
                tipoProblema = new TipoProblema("CAT-SW", "Falla de Software", 12);
                break;
            case 3:
                tipoProblema = new TipoProblema("CAT-RED", "Conectividad/Red", 6);
                break;
            default:
                tipoProblema = new TipoProblema("CAT-GEN", "Falla General", 48);
                break;
        }
        //buscamos el solicitante
        Solicitante solicitanteActual = (Solicitante) this.usuarioLogueado;
        //agregamos id al ticket
        String idTicket = gestor.generarNuevoIdTicket();
        //buscamos grado ed criticidad
        int criticidadDepto = 2;
        if (solicitanteActual.getDepartamento() != null) {
            criticidadDepto = solicitanteActual.getDepartamento().getCriticidadDepartamento();
        } else {
            System.out.print("Ingrese la criticidad operativa de su área (1: Baja, 2: Media, 3: Alta): ");
            criticidadDepto = leerOpcionRango(1, 3);
        }
        TicketIncidencia nuevaIncidencia = new TicketIncidencia(
                idTicket,
                solicitanteActual,
                titulo,
                descripcion,
                idEquipo,
                tipoProblema,
                alcance,
                bloqueo,
                criticidadDepto
        );
        boolean exito = gestor.registrarYAsignarTicket(nuevaIncidencia);
        if (exito) {
            System.out.println("Incidencia registrada con éxito. CÓDIGO: " + idTicket);
        } else {
            System.out.println("Error al registrar la incidencia.");
        }
    }
    private void registrarRequerimiento() {
        System.out.println("\n---SOLICITAR REQUERIMIENTO ---");
        System.out.print("Ingrese el título del requerimiento: ");
        String titulo = scanner.nextLine().trim();
        System.out.print("Ingrese los detalles de la solicitud: ");
        String descripcion = scanner.nextLine().trim();
        if (titulo.isEmpty() || descripcion.isEmpty()) {
            System.out.println("Todos los campos son obligatorios.");
            return;
        }
        System.out.println("\nSeleccione el tipo de servicio requerido:");
        System.out.println("1. Creación / Modificación de Permisos en Sistemas");
        System.out.println("2. Solicitud de Insumos o Consumibles (Tóner, Papel)");
        System.out.println("3. Asignación de Periféricos o Mobiliario");
        System.out.print("Opción: ");
        int catOp = 0;
        try {
            catOp = Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            catOp = 1;
        }
        TipoServicio tipoServicio;
        int prioridad = 1;
        switch (catOp) {
            case 1:
                tipoServicio = new TipoServicio("SERV-001", "Permisos de Acceso a Sistemas", true, 24);
                break;
            case 2:
                tipoServicio = new TipoServicio("SERV-002", "Suministros e Insumos de Oficina", false, 48);
                break;
            case 3:
                tipoServicio = new TipoServicio("SERV-003", "Asignación de Equipamiento de Cómputo", true, 72);
                break;
            default:
                tipoServicio = new TipoServicio("SERV-GEN", "Solicitud de Servicio General", false, 48);
                break;
        }
        Solicitante solicitanteActual = (Solicitante) this.usuarioLogueado;
        String idTicket = gestor.generarNuevoIdTicket();
        // Instanciación de Requerimiento
        TicketRequerimiento nuevoRequerimiento = new TicketRequerimiento(
                idTicket,
                solicitanteActual,
                titulo,
                descripcion,
                tipoServicio
        );
        boolean exito = gestor.registrarYAsignarTicket(nuevoRequerimiento);
        if (exito) {
            System.out.println("Requerimiento registrado con éxito. CÓDIGO: " + idTicket);
        } else {
            System.out.println("Error al registrar el requerimiento.");
        }
    }
    private int leerOpcionRango(int min, int max) {
        int valor = min;
        try {
            valor = Integer.parseInt(scanner.nextLine().trim());
            if (valor < min || valor > max) {
                System.out.println("Opción fuera de rango. Se asignará por defecto: " + min);
                valor = min;
            }
        } catch (NumberFormatException e) {
            System.out.println("Entrada inválida. Se asignará por defecto: " + min);
            valor = min;
        }
        return valor;
    }
    private void mostrarMisTicketsActivos() {
        System.out.println("\n--- MIS TICKETS ACTIVOS ---");
        if (this.usuarioLogueado == null) {
            System.out.println("Error: No hay una sesión activa.");
            return;
        }
        ListaEnlazada<Ticket> misTickets = gestor.obtenerTicketsActivosPorSolicitante(
                this.usuarioLogueado.getIdUsuario()
        );
        if (misTickets.estaVacia()) {
            System.out.println("No tienes tickets pendientes o en atención actualmente.");
            return;
        }
        System.out.println("Se encontraron " + misTickets.getTamanio() + " ticket(s) activo(s):\n");
        for (int i = 0; i < misTickets.getTamanio(); i++) {
            Ticket t = misTickets.obtener(i);
            System.out.println("====================================================");
            System.out.println(" CÓDIGO    : " + t.getCodigoTicket());
            System.out.println(" TÍTULO    : " + t.getTitulo());
            System.out.println(" DETALLE   : " + t.getDescripcion());
            System.out.println(" PRIORIDAD : " + t.getNivelPrioridad());
            System.out.println(" ESTADO    : " + t.getEstado());
            if (t.getAsignatario() != null) {
                System.out.println(" TÉCNICO   : " + t.getAsignatario());
            } else {
                System.out.println(" TÉCNICO   : Sin asignar (En Cola de Espera Global)");
            }
            if (t instanceof TicketIncidencia) {
                TicketIncidencia inc = (TicketIncidencia) t;
                System.out.println(" TIPO      : INCIDENCIA TÉCNICA");
                System.out.println(" EQUIPO    : " + inc.getIdEquipoAfectado());
                if (inc.getTipoProblema() != null) {
                    System.out.println("CATEGORÍA : " + inc.getTipoProblema().getCodigoCategoria());
                }
            } else if (t instanceof TicketRequerimiento) {
                TicketRequerimiento req = (TicketRequerimiento) t;
                System.out.println(" TIPO      : REQUERIMIENTO DE SERVICIO");
                if (req.getTipoServicio() != null) {
                    System.out.println("SERVICIO  : " + req.getTipoServicio().getNombreServicio());
                }
                System.out.println("APROBADO  : " + (req.isEstaAprobado() ? "SÍ" : "PENDIENTE DE REVISIÓN"));
            }
            System.out.println("====================================================");
        }
    }
    private void mostrarMisTicketsResueltos() {
        System.out.println("\n---HISTORIAL DE MIS TICKETS RESUELTOS ---");
        if (this.usuarioLogueado == null) {
            System.out.println("Error: No hay una sesión activa.");
            return;
        }
        ListaEnlazada<Ticket> resueltos = gestor.obtenerTicketsResueltosPorSolicitante(
                this.usuarioLogueado.getIdUsuario()
        );
        if (resueltos.estaVacia()) {
            System.out.println("No tienes solicitudes en tu historial de resueltos.");
            return;
        }
        System.out.println("Se encontraron " + resueltos.getTamanio() + " ticket(s) en tu historial:\n");
        for (int i = 0; i < resueltos.getTamanio(); i++) {
            Ticket t = resueltos.obtener(i);

            System.out.println("====================================================");
            System.out.println(" CÓDIGO      : " + t.getCodigoTicket());
            System.out.println(" TÍTULO      : " + t.getTitulo());
            System.out.println(" DETALLE     : " + t.getDescripcion());
            System.out.println(" ESTADO      : " + t.getEstado()); // RESUELTO / CERRADO

            if (t.getAsignatario() != null) {
                System.out.println("ATENDIDO POR: " + t.getAsignatario().getNombreCompleto());
            }

            if (t instanceof TicketIncidencia) {
                TicketIncidencia inc = (TicketIncidencia) t;
                System.out.println(" TIPO        : INCIDENCIA TÉCNICA");
                System.out.println(" EQUIPO      : " + inc.getIdEquipoAfectado());
                if (inc.getTipoProblema() != null) {
                    System.out.println("CATEGORÍA   : " + inc.getTipoProblema().getCodigoCategoria());
                }
            } else if (t instanceof TicketRequerimiento) {
                TicketRequerimiento req = (TicketRequerimiento) t;
                System.out.println(" TIPO        : REQUERIMIENTO DE SERVICIO");
                if (req.getTipoServicio() != null) {
                    System.out.println("SERVICIO    : " + req.getTipoServicio().getNombreServicio());
                }
            }
            System.out.println("====================================================");
        }
    }
    private void cancelarTicketPendiente() {
        System.out.println("\n---CANCELAR TICKET PENDIENTE ---");
        if (this.usuarioLogueado == null) {
            System.out.println("Error: No hay una sesión activa.");
            return;
        }
        // Mostramos primero los tickets activos para ayudar al usuario a ver sus códigos
        mostrarMisTicketsActivos();
        System.out.print("\nIngrese el CÓDIGO del ticket que desea cancelar (ej: TCK-001) o '0' para salir: ");
        String codigoTicket = scanner.nextLine().trim();
        if (codigoTicket.equals("0") || codigoTicket.isEmpty()) {
            System.out.println("Operación cancelada.");
            return;
        }
        int resultado = gestor.cancelarTicketPendiente(codigoTicket, this.usuarioLogueado.getIdUsuario());
        switch (resultado) {
            case 1:
                System.out.println("El ticket " + codigoTicket.toUpperCase() + " ha sido cancelado exitosamente.");
                break;
            case -1:
                System.out.println("No se puede cancelar el ticket " + codigoTicket.toUpperCase()
                        + " porque ya fue asignado a un técnico y está 'EN ATENCIÓN'.");
                break;
            case -2:
                System.out.println("Acceso denegado: El ticket " + codigoTicket.toUpperCase()
                        + " fue creado por otro usuario.");
                break;
            default:
                System.out.println("No se encontró ningún ticket con el código '" + codigoTicket + "' en la cola de espera.");
                break;
        }
    }
    private void mostrarMiPerfil() {
        System.out.println("\n--- 👤 MI PERFIL DE SOLICITANTE ---");

        if (this.usuarioLogueado == null) {
            System.out.println("⚠️ Error: No hay una sesión activa.");
            return;
        }

        // Casteo seguro de la sesión actual al tipo Solicitante
        Solicitante solicitante = (Solicitante) this.usuarioLogueado;

        System.out.println("====================================================");
        System.out.println("ID USUARIO    : " + solicitante.getIdUsuario());
        System.out.println("NOMBRE        : " + solicitante.getNombreCompleto());
        System.out.println("CÉDULA        : " + solicitante.getCedula());
        //System.out.println("CORREO        : " + solicitante.getCorreoElectronico());

        // Información referencial del Departamento
        if (solicitante.getDepartamento() != null) {
            Departamento depto = solicitante.getDepartamento();
            System.out.println("DEPARTAMENTO  : " + depto.getNombreArea()
                    + " [" + depto.getIdDepartamento() + "]");
            System.out.println("CRITICIDAD    : Nivel " + depto.getCriticidadDepartamento()
                    + " (Impacta en la prioridad de incidencias)");
        } else {
            System.out.println("DEPARTAMENTO  : Sin departamento asignado");
        }
        System.out.println("====================================================");
    }
    //Metodos auxiliares
    private String recortar(String texto, int max) {
        if (texto == null) {
            return "";
        }
        return (texto.length() <= max) ? texto : texto.substring(0, max - 3) + "...";
    }
    private boolean tieneMasPrioridad(Ticket a, Ticket b) {
        if (a.getPuntajePrioridad() != b.getPuntajePrioridad()) {
            return a.getPuntajePrioridad() > b.getPuntajePrioridad();
        }
        if (a.getFechaCreacion() == null || b.getFechaCreacion() == null) {
            return false;
        }
        return a.getFechaCreacion().isBefore(b.getFechaCreacion());
    }
    private String formatearFecha(LocalDateTime fecha) {
        if (fecha == null) {
            return "N/A";
        }
        return fecha.format(DateTimeFormatter.ofPattern("dd/MM HH:mm"));
    }
}
