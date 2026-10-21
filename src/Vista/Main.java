package Vista;

import Controlador.GestorSoporteBancario;
import Modelo.Enums.RolUsuario;
import Modelo.Users.Administrador;
import Modelo.Users.Asignatario;
import Modelo.Users.Departamento;
import Modelo.Users.Solicitante;

public class Main {
    static void main() {
        GestorSoporteBancario gestor = new GestorSoporteBancario();
        cargarSeed(gestor);
        VistaConsola vista = new VistaConsola(gestor);
        vista.iniciar();
    }
    public static void cargarSeed(GestorSoporteBancario gestor){
        //Seed
        System.out.println("Cargando base de datos en memoria...");
        
        Departamento deptoCajas = new Departamento("DEP-001","Cajas",1);
        Departamento deptoRRHH = new Departamento("DEP-002","Recursos Humanos",3);
        Solicitante empleado1 = new Solicitante(
                "EMP-001", "Ana", "Rojas", "0701234567", "arojas", "1234", RolUsuario.SOLICITANTE, deptoCajas
        );
        Solicitante empleado2 = new Solicitante(
                "EMP-002", "Luis", "Perez", "0707654321", "lperez", "1234", RolUsuario.SOLICITANTE, deptoRRHH
        );

        Asignatario tecnico1 = new Asignatario(
                "TEC-001", "Carlos", "Hardware", "0901112223", "chardware", "1234", RolUsuario.ASIGNATARIO
        );
        Asignatario tecnico2 = new Asignatario(
                "TEC-002", "Maria", "Redes", "0904445556", "mredes", "1234", RolUsuario.ASIGNATARIO
        );

        Administrador admin1 = new Administrador(
                "ADM-001", "Roberto", "Gerente", "0709998887", "rgerente", "1234", RolUsuario.ADMINISTRADOR
        );
        //carga de seed de usuarios
        gestor.registrarUsuario(empleado1);
        gestor.registrarUsuario(empleado2);
        gestor.registrarUsuario(tecnico1);
        gestor.registrarUsuario(tecnico2);
        gestor.registrarUsuario(admin1);
        //carga de asignatarios acitvos
        gestor.registrarAsignatarioActivo(tecnico1);
        gestor.registrarAsignatarioActivo(tecnico2);
    }
}