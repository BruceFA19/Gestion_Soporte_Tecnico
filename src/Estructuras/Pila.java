package Estructuras;

import java.io.Serializable;

public class Pila<T> implements Serializable {
    private static final long serialVersionUID = 1L;
    private Nodo<T> cima; // El "plato" que está hasta arriba
    private int tamanio;

    public Pila() {
        this.cima = null;
        this.tamanio = 0;
    }

    // ==========================================
    // OPERACIONES DE LA PILA
    // ==========================================

    // Push: Poner un elemento en la cima
    public void apilar(T dato) {
        Nodo<T> nuevoNodo = new Nodo<>(dato);

        // El nuevo nodo apunta al que antes era la cima (lo aplasta hacia abajo)
        nuevoNodo.setSiguiente(cima);
        // Ahora el nuevo nodo es oficialmente la cima
        cima = nuevoNodo;

        tamanio++;
    }

    // Pop: Sacar y devolver el elemento de la cima
    public T desapilar() {
        if (estaVacia()) {
            return null; // O lanzar EmptyStackException
        }

        T datoSaliente = cima.getDato();
        // La nueva cima pasa a ser el elemento que estaba justo debajo
        cima = cima.getSiguiente();

        tamanio--;
        return datoSaliente;
    }

    // Peek: Solo mirar qué hay en la cima sin sacarlo
    public T obtenerCima() {
        if (estaVacia()) {
            return null;
        }
        return cima.getDato();
    }

    // ==========================================
    // UTILIDADES
    // ==========================================

    public boolean estaVacia() {
        return cima == null;
    }

    public int getTamanio() {
        return tamanio;
    }
}
