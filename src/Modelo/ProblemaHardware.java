package Modelo;

// Incidencias Fisicas: equipos, componentes o periferifocs

public class ProblemaHardware extends TipoProblema {

    private String codigoComponente;
    private boolean requiereReemplazoPieza;
    private int nivelImpactoFisico;
    private double costoEstimadoReparacion;

    public ProblemaHardware(String idTipoProblema, String codigoCategoria, String descripcionGeneral,
                            int nivelComplejidadBase, int tiempoEstimadoMinutos,
                            String codigoComponente, boolean requiereReemplazoPieza,
                            int nivelImpactoFisico, double costoEstimadoReparacion) {
        super(idTipoProblema, codigoCategoria, descripcionGeneral, nivelComplejidadBase, tiempoEstimadoMinutos);
        this.codigoComponente = codigoComponente;
        this.requiereReemplazoPieza = requiereReemplazoPieza;
        this.nivelImpactoFisico = nivelImpactoFisico;
        this.costoEstimadoReparacion = costoEstimadoReparacion;
    }

    public String getCodigoComponente() { return codigoComponente; }
    public boolean getRequiereReemplazoPieza() { return requiereReemplazoPieza; }
    public int getNivelImpactoFisico() { return nivelImpactoFisico; }
    public double getCostoEstimadoReparacion() { return costoEstimadoReparacion; }
}
