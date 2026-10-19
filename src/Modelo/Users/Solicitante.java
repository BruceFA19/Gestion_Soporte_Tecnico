package Modelo.Users;

import Modelo.Enums.RolUsuario;

// Empleado bancario que acude a soporte tecnicco presencial

public class Solicitante extends Usuario {
    private Departamento departamento;
    public Solicitante(String idUsuario, String nombre, String apellido, String cedula,
                       String usuarioRedBancaria, String contraseniaHash, RolUsuario rol,
                       Departamento departamento) { // Se pide el departamento al construirlo

        // El "super" envía los datos generales a la clase padre (Usuario)
        super(idUsuario, nombre, apellido, cedula, usuarioRedBancaria, contraseniaHash, rol);

        this.departamento = departamento;
    }
    public Departamento getDepartamento() {
        return departamento;
    }
}