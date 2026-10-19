package Vista;

import Controlador.GestorSoporteBancario;
import Modelo.Enums.RolUsuario;
import Modelo.Users.*;

import java.util.Scanner;

public class VistaConsola {
    private GestorSoporteBancario gestor;
    private Scanner scanner;

    public VistaConsola(GestorSoporteBancario gestor) {
        this.gestor = gestor;
        this.scanner = new Scanner(System.in);
    }

    //metodo APROBADO
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

    private void procesarLogin() {
        System.out.println("\n--- INICIO DE SESIÓN ---");
        System.out.print("Ingrese su ID de Usuario: ");
        String id = scanner.nextLine();

        System.out.print("Ingrese su Contraseña: ");
        String pass = scanner.nextLine();

        Usuario usuarioLogueado = gestor.validarAcceso(id, pass);

        if (usuarioLogueado == null) {
            System.out.println("❌ Credenciales incorrectas o usuario no encontrado.");
            return; // Lo devuelve al menú principal
        }

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
            System.out.println("\n========================================");
            System.out.println("         PANEL DE ADMINISTRADOR");
            System.out.println("========================================");
            System.out.println("1. Agregar nuevo usuario (Solicitante/Asignatario)");
            System.out.println("2. Ver lista de Asignatarios activos");
            System.out.println("3. Ver lista de Solicitantes registrados");
            System.out.println("4. Ver todos los tickets activos (En espera y en atención)");
            System.out.println("5. Ver tickets resueltos (Historial)");
            System.out.println("6. Eliminar usuario (Solicitante/Asignatario)");
            System.out.println("7. Cerrar Sesión");
            System.out.print("Seleccione una opción: ");
            try {
                opcion = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                opcion = 0; // Para evitar errores si digitan letras
            }
            switch (opcion) {
                case 1:
                    crearNuevoUsuario();
                    break;
                case 2:
                    System.out.println("\n[ LISTA DE ASIGNATARIOS ]");
                    // Aquí recorreremos la lista de técnicos
                    break;
                case 3:
                    System.out.println("\n[ LISTA DE SOLICITANTES ]");
                    // Aquí recorreremos la Tabla Hash filtrando por Solicitantes
                    break;
                case 4:
                    System.out.println("\n[ TICKETS ACTIVOS ]");
                    // Aquí veremos la ColaGlobal y las ticketeras de los asignatarios
                    break;
                case 5:
                    System.out.println("\n[ TICKETS RESUELTOS ]");
                    // Aquí usaremos la ListaEnlazada del Historial de Tickets
                    break;
                case 6:
                    System.out.println("Cerrando sesión de administrador...");
                    break;
                case 7:
                    System.out.println("\n[ ELIMINAR USUARIO ]");
                    break;
                default:
                    System.out.println("Opción no válida. Intente nuevamente.");
            }
        } while (opcion != 7);
    }

    private void crearNuevoUsuario() {
        System.out.println("\n--- 📝 REGISTRO DE NUEVO USUARIO ---");
        System.out.println("¿Qué tipo de rol tendrá el nuevo usuario?");
        System.out.println("1. Solicitante (Empleado Regular)");
        System.out.println("2. Asignatario (Técnico de Soporte)");
        System.out.print("Seleccione (1 o 2): ");

        int tipoRol = 0;
        try {
            tipoRol = Integer.parseInt(scanner.nextLine());
        } catch (Exception e) {
            System.out.println("❌ Entrada inválida. Operación cancelada.");
            return;
        }

        if (tipoRol != 1 && tipoRol != 2) {
            System.out.println("❌ Opción no válida. Operación cancelada.");
            return;
        }

        // Pedimos los datos comunes que comparten TODOS los Usuarios
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

        // Bifurcación dependiendo de la clase hija
        if (tipoRol == 1) {
            // Es un Solicitante: Necesitamos crear su Departamento
            System.out.println("\n--- Datos del Departamento ---");
            System.out.print("Nombre del Depto (Ej. Contabilidad): ");
            String nombreDepto = scanner.nextLine();
            System.out.print("Nivel de criticidad (1 al 10): ");
            int criticidad = Integer.parseInt(scanner.nextLine());

            // Creamos el departamento (Generando un ID genérico para el prototipo)
            Departamento nuevoDepto = new Departamento("DEP-NEW", nombreDepto, criticidad);

            // Instanciamos al Solicitante
            Solicitante nuevoSolicitante = new Solicitante(
                    idUsuario, nombre, apellido, cedula, red, pass, RolUsuario.SOLICITANTE, nuevoDepto
            );

            // Magia del polimorfismo: Guardamos al Solicitante
            gestor.registrarUsuario(nuevoSolicitante);
            System.out.println("✅ ¡Solicitante registrado exitosamente en la Tabla Hash!");

        } else if (tipoRol == 2) {
            // Es un Asignatario
            Asignatario nuevoAsignatario = new Asignatario(
                    idUsuario, nombre, apellido, cedula, red, pass, RolUsuario.ASIGNATARIO
            );

            // Lo guardamos en la Tabla Hash para que pueda loguearse
            gestor.registrarUsuario(nuevoAsignatario);

            // Y de paso, lo agregamos a la lista de activos para que el Router ya le pueda enviar tickets
            gestor.registrarAsignatarioActivo(nuevoAsignatario);

            System.out.println("✅ ¡Asignatario técnico registrado y activado para recibir tickets!");
        }
    }
}
