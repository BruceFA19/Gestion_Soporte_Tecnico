package Vista;
import Controlador.GestorSoporteBancario;
import Estructuras.ListaEnlazada;
import Modelo.Enums.RolUsuario;
import Modelo.Tickets.Incidencia.TicketIncidencia;
import Modelo.Tickets.Requerimiento.TicketRequerimiento;
import Modelo.Tickets.Ticket;
import Modelo.Users.*;
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
        if (usuarioLogueado == null) {
            System.out.println("Credenciales incorrectas o usuario no encontrado.");
            return;
        }
        System.out.println("¡Bienvenido, " + usuarioLogueado.getNombreCompleto() + "!");
        this.usuarioLogueado = usuarioAutenticado;
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
        System.out.println("\n(Entrando al panel de Asignatario...)");
        // Aquí pondremos las opciones de ver tickets, atender siguiente, etc.
    }
    private void mostrarMenuSolicitante(Solicitante solicitante) {
        System.out.println("\n(Entrando al panel de Solicitante...)");
        // Aquí pondremos la opción de crear ticket, ver mis reportes, etc.
    }
    private void mostrarMenuAdministrador(Administrador administrador) {
        int opcion = 0;
        do {
            System.out.println("         PANEL DE ADMINISTRADOR");
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
        System.out.println("\n--- 📝 REGISTRO DE NUEVO USUARIO ---");
        System.out.println("¿Qué tipo de rol tendrá el nuevo usuario?");
        System.out.println("1. Solicitante (Empleado Regular)");
        System.out.println("2. Asignatario (Técnico de Soporte)");
        System.out.println("3. Cancelar");
        System.out.print("Seleccione (1-3): ");

        int tipoRol = 0;
        try {
            tipoRol = Integer.parseInt(scanner.nextLine());
        } catch (Exception e) {
            System.out.println("Entrada inválida. Operación cancelada.");
            return;
        }

        if (tipoRol != 1 && tipoRol != 2) {
            System.out.println("Opción no válida. Operación cancelada.");
            return;
        }

        System.out.print("ID del Usuario (Ej. EMP-099): ");
        String idUsuario = scanner.nextLine();
        System.out.print("Nombre: ");
        String nombre = scanner.nextLine();
        System.out.print("Apellido: ");
        String apellido = scanner.nextLine();
        System.out.print("Cédula: ");
        String cedula = scanner.nextLine();
        System.out.print("Usuario Red Bancaria: ");
        String red = scanner.nextLine();
        System.out.print("Contraseña: ");
        String pass = scanner.nextLine();

        if (tipoRol == 1) {
            System.out.println("\n--- Datos del Departamento ---");
            System.out.print("Nombre del Depto (Ej. Contabilidad): ");
            String nombreDepto = scanner.nextLine();
            System.out.print("Nivel de criticidad (1 al 10): ");
            int criticidad = Integer.parseInt(scanner.nextLine());
            Departamento nuevoDepto = new Departamento("DEP-NEW", nombreDepto, criticidad);
            Solicitante nuevoSolicitante = new Solicitante(
                    idUsuario, nombre, apellido, cedula, red, pass, RolUsuario.SOLICITANTE, nuevoDepto
            );
            gestor.registrarUsuario(nuevoSolicitante);
            System.out.println("Solicitante registrado exitosamente en la Tabla Hash!");

        } else if (tipoRol == 2) {
            Asignatario nuevoAsignatario = new Asignatario(
                    idUsuario, nombre, apellido, cedula, red, pass, RolUsuario.ASIGNATARIO
            );
            gestor.registrarUsuario(nuevoAsignatario);
            gestor.registrarAsignatarioActivo(nuevoAsignatario);

            System.out.println("zAsignatario técnico registrado y activado para recibir tickets!");
        }
    }
    private void mostrarAsignatariosActivos() {

        System.out.println("\n--- 👥 LISTA DE ASIGNATARIOS TÉCNICOS ACTIVOS ---");
        ListaEnlazada<Asignatario> asignatarios = gestor.getAsignatariosActivos();

        // Validamos si la lista está vacía
        if (asignatarios == null || asignatarios.estaVacia()) {
            System.out.println("⚠️ No hay asignatarios técnicos registrados o activos en el sistema.");
            return;
        }

        System.out.printf("%-10s | %-25s | %-15s | %-15s\n", "ID", "NOMBRE COMPLETO", "USUARIO RED", "TICKETS EN COLA");
        System.out.println("------------------------------------------------------------------------------");

        for (int i = 0; i < asignatarios.getTamanio(); i++) {
            Asignatario asig = asignatarios.obtener(i);
            String nombreCompleto = asig.getNombreCompleto();
            int cantTickets = asig.getTicketeraPersonal().getTamanio();

            System.out.printf("%-10s | %-25s | %-15s | %-15d\n",
                    asig.getIdUsuario(),
                    nombreCompleto,
                    asig.getUsuarioRedBancaria(),
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

            System.out.printf("%-10s | %-25s | %-12s | %-20s | %-15s\n",
                    sol.getIdUsuario(),
                    nombreCompleto,
                    sol.getCedula(),
                    nombreDepto,
                    sol.getUsuarioRedBancaria());
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
        System.out.println("\n--- ✅ HISTORIAL DE TICKETS RESUELTOS ---");
        ListaEnlazada<Ticket> resueltos = gestor.getHistorialTicketsResueltos();
        if (resueltos == null || resueltos.estaVacia()) {
            System.out.println("ℹ️ Aún no hay tickets registrados como resueltos en el historial.");
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
            boolean exito = gestor.eliminarUsuario(identificador, usuarioLogueado.getUsuarioRedBancaria());
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
        System.out.println("\n--- 📜 SUBMENÚ: CONSULTA DE LISTAS ---");
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
    //Metodos para el menu solicitante
}
