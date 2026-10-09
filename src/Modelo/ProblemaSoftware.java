package Modelo;

// INCIDENCIAS LOGICAS: APLICATIVOS, LICENCIAS, ACCESOS O SISTEMAS

public class ProblemaSoftware extends TipoProblema {

    private String identificadorSistema;
    private String codigoError;
    private boolean requiereElevacionPrivilegios;
    private int nivelImpactoLogico;

    public ProblemaSoftware(String idTipoProblema, String codigoCategoria, String descripcionGeneral,
                            int nivelComplejidadBase, int tiempoEstimadoMinutos,
                            String identificadorSistema, String codigoError,
                            boolean requiereElevacionPrivilegios, int nivelImpactoLogico) {
        super(idTipoProblema, codigoCategoria, descripcionGeneral, nivelComplejidadBase, tiempoEstimadoMinutos);
        this.identificadorSistema = identificadorSistema;
        this.codigoError = codigoError;
        this.requiereElevacionPrivilegios = requiereElevacionPrivilegios;
        this.nivelImpactoLogico = nivelImpactoLogico;
    }

    public String getIdentificadorSistema() { return identificadorSistema; }
    public String getCodigoError() { return codigoError; }
    public boolean getRequiereElevacionPrivilegios() { return requiereElevacionPrivilegios; }
    public int getNivelImpactoLogico() { return nivelImpactoLogico; }
}