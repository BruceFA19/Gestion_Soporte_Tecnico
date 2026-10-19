package Modelo.Users;
import Modelo.Enums.RolUsuario;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
public abstract class Usuario {
    protected String idUsuario;
    protected String nombre;
    protected String apellido;
    protected String cedula;
    protected String usuarioRedBancaria; // Ej: "jrodriguez" (Para el login)
    protected String contraseniaHash;    // Contraseña encriptada
    protected RolUsuario rol;
    // "La contraseña llega ya convertida en hash"
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
    // LÓGICA DE SEGURIDAD (LOGIN)
    public boolean iniciarSesion(String usuarioRed, String contraseniaPlana) {
        // 1. Comparamos el nombre de usuario de red
        if (!this.usuarioRedBancaria.equals(usuarioRed)) {
            return false; // El usuario no existe o está mal escrito
        }
        // 2. Convertimos la contraseña plana ingresada (Ej: "banco123") a Hash
        String hashGeneradoAlInstante = convertirAHash(contraseniaPlana);
        // 3. Comparamos el hash guardado en memoria con el que acabamos de generar
        return this.contraseniaHash.equals(hashGeneradoAlInstante);
    }
    // Herramienta estática para generar Hashes (SHA-256)
    public static String convertirAHash(String textoPlano) {
        try {
            // Usamos el estándar de encriptación SHA-256 de Java
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hashBytes = digest.digest(textoPlano.getBytes());
            // Convertimos los bytes matemáticos a texto hexadecimal legible
            StringBuilder hexString = new StringBuilder();
            for (byte b : hashBytes) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }
            return hexString.toString();

        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error al cargar el algoritmo de encriptación", e);
        }
    }

    public String getIdUsuario() { return idUsuario; }
    public String getNombreCompleto() { return nombre + " " + apellido; }
    public String getCedula() { return cedula; }
    public String getUsuarioRedBancaria() { return usuarioRedBancaria; }
    public String getContraseniaHash() { return contraseniaHash; }
    public RolUsuario getRol() { return rol; }
}