package Modelo.Auditoria;

import java.io.Serializable;

public class AccionAuditoria implements Serializable {
    private String tipoAccion;
    private String codigoTicket;
    private String detalle;
    public AccionAuditoria(String tipoAccion, String codigoTicket, String detalle) {
        this.tipoAccion = tipoAccion;
        this.codigoTicket = codigoTicket;
        this.detalle = detalle;
    }
    public String getTipoAccion() { return tipoAccion; }
    public String getCodigoTicket() { return codigoTicket; }
    public String getDetalle() { return detalle; }
}