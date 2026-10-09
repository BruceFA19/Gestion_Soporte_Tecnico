package Modelo;

//AREA ORGANIZACIONAL DEL BANCO

public class Departamento {

    private String idDepartamento;
    private String nombreArea;
    private int pisoUbicacion;
    private int criticidadDepartamento; // 1 a 3 segun impacto operativo

    public Departamento(String idDepartamento, String nombreArea, int pisoUbicacion, int criticidadDepartamento) {
        this.idDepartamento = idDepartamento;
        this.nombreArea = nombreArea;
        this.pisoUbicacion = pisoUbicacion;
        this.criticidadDepartamento = criticidadDepartamento;
    }

    public String getIdDepartamento() { return idDepartamento; }
    public String getNombreArea() { return nombreArea; }
    public int getPisoUbicacion() { return pisoUbicacion; }
    public int getCriticidadDepartamento() { return criticidadDepartamento; }
    public void setCriticidadDepartamento(int criticidadDepartamento) {
        this.criticidadDepartamento = criticidadDepartamento;
    }
    
}