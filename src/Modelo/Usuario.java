package Modelo;

import java.nio.charset.StandardCharsets;

// Clase base para todos los roles de la entidad bancaria

public abstract class Usuario {

    protected String idUsuario;
    protected String nombre;
    protected String apellido;
    protected String cedula;
    protected String usuarioRedBancaria;
    private String contraseniaHash;
    protected RolUsuario rol;

    //" La contraseña llega ya convertida en hash "/
    public Usuario(String idUsuario, String nombre, String apellido, String cedula,
                   String usuarioRedBancaria, String contraseniaHash, RolUsuario rol) {
        this.idUsuario = idUsuario;
        this.nombre = nombre;
        this.apellido = apellido;
        this.cedula = cedula;
        this.usuarioRedBancaria = usuarioRedBancaria;
        this.contraseniaHash = contraseniaHash;
        this.rol = rol;
    }

    public boolean iniciarSesion(String usuarioRed, String contrasenia) {
        // omparar usuario y hash de la contraseña.
        return false;
    }

    public void cerrarSesion() {
        // 
    }

    public String getNombreCompleto() {
        // nombre + apellido.
        return null;
    }

    public String getIdUsuario() { return idUsuario; }
    public String getNombre() { return nombre; }
    public String getApellido() { return apellido; }
    public String getCedula() { return cedula; }
    public String getUsuarioRedBancaria() { return usuarioRedBancaria; }
    public String getContraseniaHash() { return contraseniaHash; }
    public RolUsuario getRol() { return rol; }
}