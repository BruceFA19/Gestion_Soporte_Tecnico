package Vista;

import Controlador.GestorSoporteBancario;
import Modelo.Enums.RolUsuario;
import Modelo.Users.Administrador;
import Modelo.Users.Asignatario;
import Modelo.Users.Departamento;
import Modelo.Users.Solicitante;
import Persistencia.GestorPersistencia;

public class Main {
    static void main() {
        GestorSoporteBancario gestor = GestorPersistencia.cargarEstado();
        if (gestor.esSistemaNuevo()) {
            cargarSeed(gestor);
            GestorPersistencia.guardarEstado(gestor);
        }
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.out.println("\n[Shutdown Hook] Ejecutando guardado de emergencia...");
            GestorPersistencia.guardarEstado(gestor);
        }));
        VistaConsola vista = new VistaConsola(gestor);
        vista.iniciar();
        GestorPersistencia.guardarEstado(gestor);
    }
    private static void cargarSeed(GestorSoporteBancario gestor) {
        System.out.println("Cargando base de datos inicial en memoria (Seed)...");

        Departamento deptoCajas = new Departamento("DEP-001", "Cajas", 1);
        Departamento deptoRRHH = new Departamento("DEP-002", "Recursos Humanos", 3);

        Solicitante empleado1 = new Solicitante(
                gestor.generarNuevoIdUsuario("SOLICITANTE"),
                "Ana", "Rojas", "0701234567", "1234",
                RolUsuario.SOLICITANTE, deptoCajas
        );

        Solicitante empleado2 = new Solicitante(
                gestor.generarNuevoIdUsuario("SOLICITANTE"),
                "Luis", "Perez", "0707654321", "lperez",
                RolUsuario.SOLICITANTE, deptoRRHH
        );

        Asignatario tecnico1 = new Asignatario(
                gestor.generarNuevoIdUsuario("ASIGNATARIO"),
                "Carlos", "Hardware", "0901112223", "1234",
                RolUsuario.ASIGNATARIO
        );

        Asignatario tecnico2 = new Asignatario(
                gestor.generarNuevoIdUsuario("ASIGNATARIO"),
                "Maria", "Redes", "0904445556", "1234",
                RolUsuario.ASIGNATARIO
        );

        Administrador admin1 = new Administrador(
                gestor.generarNuevoIdUsuario("ADMINISTRADOR"),
                "Roberto", "Gerente", "0709998887", "1234",
                RolUsuario.ADMINISTRADOR
        );

        gestor.registrarDepartamento(deptoCajas);
        gestor.registrarDepartamento(deptoRRHH);

        gestor.registrarUsuario(empleado1);
        gestor.registrarUsuario(empleado2);
        gestor.registrarUsuario(tecnico1);
        gestor.registrarUsuario(tecnico2);
        gestor.registrarUsuario(admin1);

        gestor.registrarAsignatarioActivo(tecnico1);
        gestor.registrarAsignatarioActivo(tecnico2);

        System.out.println("Base de datos inicial cargada con éxito.");
    }
}