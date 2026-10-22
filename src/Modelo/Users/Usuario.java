package Modelo.Users;
import Modelo.Enums.RolUsuario;

import java.io.Serializable;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
public abstract class Usuario implements Serializable {
    protected String idUsuario;
    protected String nombre;
    protected String apellido;
    protected String cedula;
    protected String contraseniaHash;    // Contraseña encriptada
    protected RolUsuario rol;
    // "La contraseña llega ya convertida en hash"
    public Usuario(String idUsuario, String nombre, String apellido, String cedula,
                    String contraseniaHash, RolUsuario rol) {
        this.idUsuario = idUsuario;
        this.nombre = nombre;
        this.apellido = apellido;
        this.cedula = cedula;
        this.contraseniaHash = contraseniaHash;
        this.rol = rol;
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
    public String getContraseniaHash() { return contraseniaHash; }
    public RolUsuario getRol() { return rol; }
}