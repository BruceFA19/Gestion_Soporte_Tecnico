package Modelo.Users;
import Modelo.Enums.RolUsuario;

public class Administrador extends Usuario {
    public Administrador(String idUsuario, String nombre, String apellido, String cedula,
                          String contraseniaHash, RolUsuario rol) {
        super(idUsuario, nombre, apellido, cedula, contraseniaHash, rol);
    }
}