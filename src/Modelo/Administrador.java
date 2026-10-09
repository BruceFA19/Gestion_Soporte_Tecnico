package Modelo;

// SUPERVISOR CON CONTROL DE LA ATENCION Y DE LAS CONFIGURACION DEL SISTEMA

public class Administrador extends Usuario{

    private String codigoAdministrador;
    private int nivelAcceso;

    public Administrador(String idUsuario, String nombre, String apellido, String cedula,
                         String usuarioRedBancaria, String contraseniaHash,
                         String codigoAdministrador, int nivelAcceso) {
        super(idUsuario, nombre, apellido, cedula, usuarioRedBancaria, contraseniaHash, RolUsuario.ADMINISTRADOR);
        this.codigoAdministrador = codigoAdministrador;
        this.nivelAcceso = nivelAcceso;
    }

    public boolean reasignarTurnoManual(String codigoTicket, String idTecnico) {
        // quien le toque
        return false;
    }

    public ReporteSoporte generarReporteDesempenio() {
        // quien le toque
        return null;
    }

    public String getCodigoAdministrador() { return codigoAdministrador; }
    public int getNivelAcceso() { return nivelAcceso; }
}