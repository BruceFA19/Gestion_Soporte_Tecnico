package Modelo;

// Calcula el puntaje total (criticidad + alcance + bloqueo, CADA UNO DE 1 A 3) y lo clasifica en un nuvel de priordad
// >= 8 -> CRITICA | 6-7 -> ALTA | 4-5 -> MEDIA | 3 -> BAJA

public class EvaluadorPrioridadEquipo {

    private int umbralPrioridadCritica;
    private int umbralPrioridadAltaMin;
    private int umbralPrioridadAltaMax;
    private int umbralPrioridadMediaMin;
    private int umbralPrioridadMediaMax;

    public EvaluadorPrioridadEquipo() {
        this.umbralPrioridadCritica = 8;
        this.umbralPrioridadAltaMin = 6;
        this.umbralPrioridadAltaMax = 7;
        this.umbralPrioridadMediaMin = 4;
        this.umbralPrioridadMediaMax = 5;
    }

    public int calcularPuntajeTotal(int criticidadDepartamento, int alcanceUsuarios, int gradoBloqueo) {
        // TODO: sumar los tres factores.
        return 0;
    }

    public NivelPrioridad clasificarPrioridad(int puntajeTotal) {
        // TODO: devolver el nivel segun los umbrales.
        return null;
    }

    public boolean esPrioridadCritica(int puntajeTotal) {
        // TODO: puntajeTotal frente al umbral critico.
        return false;
    }

    public int getUmbralPrioridadCritica() { return umbralPrioridadCritica; }
    public int getUmbralPrioridadAltaMin() { return umbralPrioridadAltaMin; }
    public int getUmbralPrioridadAltaMax() { return umbralPrioridadAltaMax; }
    public int getUmbralPrioridadMediaMin() { return umbralPrioridadMediaMin; }
    public int getUmbralPrioridadMediaMax() { return umbralPrioridadMediaMax; }
}