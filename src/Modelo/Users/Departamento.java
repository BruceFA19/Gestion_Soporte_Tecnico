package Modelo.Users;

import java.io.Serializable;

public class Departamento implements Serializable {

    private String idDepartamento;
    private String nombreArea;
    private int criticidadDepartamento;

    public Departamento(String idDepartamento, String nombreArea, int criticidadDepartamento) {
        this.idDepartamento = idDepartamento;
        this.nombreArea = nombreArea;
        this.criticidadDepartamento = criticidadDepartamento;
    }
    public String getIdDepartamento() {
        return idDepartamento;
    }
    public String getNombreArea() {
        return nombreArea;
    }
    public int getCriticidadDepartamento() {
        return criticidadDepartamento;
    }
    public void setCriticidadDepartamento(int criticidadDepartamento) {
        this.criticidadDepartamento = criticidadDepartamento;
    }
}
