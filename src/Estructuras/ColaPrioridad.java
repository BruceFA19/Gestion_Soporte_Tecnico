package Estructuras;
import java.io.Serializable;
public class ColaPrioridad < T extends Comparable<T>> implements Serializable {
    private static final long serialVersionUID = 1L;
    private Nodo<T> frente;
    private int tamanio;
    public ColaPrioridad() {
        this.frente = null;
        this.tamanio = 0;
    }
    public void encolar(T dato) {
        Nodo<T> nuevoNodo = new Nodo<>(dato);
        if (estaVacia() || dato.compareTo(frente.getDato()) > 0) {
            nuevoNodo.setSiguiente(frente);
            frente = nuevoNodo;
        }
        else {
            Nodo<T> actual = frente;
            while (actual.getSiguiente() != null &&
                    dato.compareTo(actual.getSiguiente().getDato()) <= 0) {
                actual = actual.getSiguiente();
            }
            nuevoNodo.setSiguiente(actual.getSiguiente());
            actual.setSiguiente(nuevoNodo);
        }
        tamanio++;
    }
    public T desencolar() {
        if (estaVacia()) {
            return null;
        }
        T datoSaliente = frente.getDato();
        frente = frente.getSiguiente();
        tamanio--;

        return datoSaliente;
    }
    public boolean estaVacia() {
        return frente == null;
    }
    public int getTamanio() {
        return tamanio;
    }
    public ListaEnlazada<T> toListaEnlazada() {
        ListaEnlazada<T> lista = new ListaEnlazada<>();
        Nodo<T> actual = frente;
        while (actual != null) {
            lista.agregar(actual.getDato());
            actual = actual.getSiguiente();
        }
        return lista;
    }
}
