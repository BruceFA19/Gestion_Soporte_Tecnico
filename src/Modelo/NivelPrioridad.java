package Modelo;

// NIVEL DE PRIORIDAD DE UN TICKET (1 a 4)

public enum NivelPrioridad {
    BAJA(1),
    MEDIA(2),
    ALTA(3),
    CRITICA(4);

    private final int valor;

    NivelPrioridad(int valor) {
        this.valor = valor;
    }

    public int getValor() {
        return valor;
    }
}