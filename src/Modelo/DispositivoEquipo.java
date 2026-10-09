package Modelo;

import java.time.Year;

public class DispositivoEquipo{
    private String idActivoFijo;
    private TipoEquipo tipoEquipo;
    private String marca;
    private String modelo;
    private int anioFabricacion;
    private int nivelVulnerabilidadSeguridad;

    public DispositivoEquipo(String idActivoFijo, TipoEquipo tipoEquipo, String marca, String modelo,
                             int anioFabricacion, int nivelVulnerabilidadSeguridad) {
        this.idActivoFijo = idActivoFijo;
        this.tipoEquipo = tipoEquipo;
        this.marca = marca;
        this.modelo = modelo;
        this.anioFabricacion = anioFabricacion;
        this.nivelVulnerabilidadSeguridad = nivelVulnerabilidadSeguridad;
    }

    public int calcularAntiguedadAnios() {
        // quien le tq
        return 0;
    }

    public String obtenerFichaTecnica() {
        // quien le tq
        return null;
    }

    public String getIdActivoFijo() { return idActivoFijo; }
    public TipoEquipo getTipoEquipo() { return tipoEquipo; }
    public String getMarca() { return marca; }
    public String getModelo() { return modelo; }
    public int getAnioFabricacion() { return anioFabricacion; }
    public int getNivelVulnerabilidadSeguridad() { return nivelVulnerabilidadSeguridad; }
}