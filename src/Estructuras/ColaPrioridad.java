package Estructuras;

// A quien le toque manipular esta parte, reemplazar nomas los valores para loque vaya hacer esta estructura
public class ColaPrioridad<T extends Comparable<T>> {

    private Nodo<T> frente; // El primero de la fila (el que tiene mayor prioridad)
    private int tamanio;

    public ColaPrioridad() {
        this.frente = null;
        this.tamanio = 0;
    }

    // ==========================================
    // OPERACIONES DE LA COLA
    // ==========================================

    // Encolar: Inserta el elemento respetando su orden de prioridad
    public void encolar(T dato) {
        Nodo<T> nuevoNodo = new Nodo<>(dato);

        // Caso 1: La cola está vacía, o el nuevo dato es MÁS urgente que el que está al frente
        // (dato.compareTo > 0 significa que el nuevo tiene mayor prioridad)
        if (estaVacia() || dato.compareTo(frente.getDato()) > 0) {
            nuevoNodo.setSiguiente(frente);
            frente = nuevoNodo;
        }
        // Caso 2: Hay que buscar su lugar en la fila
        else {
            Nodo<T> actual = frente;

            // Avanzamos mientras el siguiente nodo exista y tenga MAYOR O IGUAL prioridad que el nuestro
            // (Si tienen la misma prioridad, el nuevo se pone detrás del viejo, respetando el orden de llegada)
            while (actual.getSiguiente() != null &&
                    dato.compareTo(actual.getSiguiente().getDato()) <= 0) {
                actual = actual.getSiguiente();
            }

            // Lo insertamos en la posición encontrada
            nuevoNodo.setSiguiente(actual.getSiguiente());
            actual.setSiguiente(nuevoNodo);
        }
        tamanio++;
    }

    // Desencolar: Saca y devuelve al primer elemento de la fila
    public T desencolar() {
        if (estaVacia()) {
            return null; // O podrías lanzar una excepción
        }
        T datoSaliente = frente.getDato();
        frente = frente.getSiguiente(); // El segundo pasa a ser el primero
        tamanio--;

        return datoSaliente;
    }

    // ==========================================
    // UTILIDADES
    // ==========================================

    public boolean estaVacia() {
        return frente == null;
    }

    public int getTamanio() {
        return tamanio;
    }
}
