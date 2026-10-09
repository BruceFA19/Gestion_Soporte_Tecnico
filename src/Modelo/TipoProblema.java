package Modelo;

// Definicion generica de cualquier tipo de incidente en el banco

public abstract class TipoProblema {

    protected String idTipoProblema;
    protected String codigoCategoria;
    protected String descripcionGeneral;
    protected int nivelComplejidadBase;
    protected int tiempoEstimadoMinutos;

    public TipoProblema(String idTipoProblema, String codigoCategoria, String descripcionGeneral,
                        int nivelComplejidadBase, int tiempoEstimadoMinutos) {
        this.idTipoProblema = idTipoProblema;
        this.codigoCategoria = codigoCategoria;
        this.descripcionGeneral = descripcionGeneral;
        this.nivelComplejidadBase = nivelComplejidadBase;
        this.tiempoEstimadoMinutos = tiempoEstimadoMinutos;
    }

    public String getIdTipoProblema() { return idTipoProblema; }
    public String getCodigoCategoria() { return codigoCategoria; }
    public String getDescripcionGeneral() { return descripcionGeneral; }
    public int getNivelComplejidadBase() { return nivelComplejidadBase; }
    public int getTiempoEstimadoMinutos() { return tiempoEstimadoMinutos; }
}