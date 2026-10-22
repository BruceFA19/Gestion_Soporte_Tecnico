package Estructuras;
import java.io.Serializable;
import java.util.Iterator;
public class ListaEnlazada<T> implements Iterable<T>, Serializable {
    private static final long serialVersionUID = 1L;
    private Nodo<T> cabeza;
    private Nodo<T> cola;
    private int tamanio;
    public ListaEnlazada() {
        this.cabeza = null;
        this.cola = null;
        this.tamanio = 0;
    }
    public void agregar(T dato) {
        Nodo<T> nuevoNodo = new Nodo<>(dato);
        if (estaVacia()) {
            cabeza = nuevoNodo;
            cola = nuevoNodo;
        } else {
            cola.setSiguiente(nuevoNodo); // El último actual apunta al nuevo
            cola = nuevoNodo;             // El nuevo se convierte en el último
        }
        tamanio++;
    }
    public T obtener(int indice) {
        if (indice < 0 || indice >= tamanio) {
            throw new IndexOutOfBoundsException("Índice fuera de rango: " + indice);
        }
        Nodo<T> actual = cabeza;
        for (int i = 0; i < indice; i++) {
            actual = actual.getSiguiente();
        }
        return actual.getDato();
    }
    public boolean estaVacia() {
        return cabeza == null;
    }
    public int getTamanio() {
        return tamanio;
    }
    public T eliminar(int indice) {
        if (indice < 0 || indice >= tamanio) {
            throw new IndexOutOfBoundsException("Índice fuera de rango: " + indice);
        }
        T datoEliminado;
        // Caso A: Eliminar el primer elemento (cabeza)
        if (indice == 0) {
            datoEliminado = cabeza.getDato();
            cabeza = cabeza.getSiguiente();
            if (cabeza == null) { // Si la lista quedó vacía
                cola = null;
            }
        }
        // Caso B: Eliminar en el medio o al final
        else {
            Nodo<T> anterior = cabeza;
            for (int i = 0; i < indice - 1; i++) {
                anterior = anterior.getSiguiente();
            }
            Nodo<T> aEliminar = anterior.getSiguiente();
            datoEliminado = aEliminar.getDato();
            anterior.setSiguiente(aEliminar.getSiguiente());
            if (aEliminar == cola) {
                cola = anterior;
            }
        }
        tamanio--;
        return datoEliminado;
    }
    public int buscarIndice(T dato) {
        Nodo<T> actual = cabeza;
        int indice = 0;
        while (actual != null) {
            if (actual.getDato().equals(dato)) {
                return indice;
            }
            actual = actual.getSiguiente();
            indice++;
        }
        return -1;
    }
    @Override
    public Iterator<T> iterator() {
        return new Iterator<T>() {
            private Nodo<T> actual = cabeza;
            @Override
            public boolean hasNext() {
                return actual != null;
            }
            @Override
            public T next() {
                T dato = actual.getDato();
                actual = actual.getSiguiente();
                return dato;
            }
        };
    }
    public boolean eliminarEnPosicion(int indice) {
        if (indice < 0 || indice >= tamanio || cabeza == null) {
            return false;
        }
        if (indice == 0) {
            cabeza = cabeza.getSiguiente();
            tamanio--;
            return true;
        }
        Nodo<T> actual = cabeza;
        for (int i = 0; i < indice - 1; i++) {
            actual = actual.getSiguiente();
        }
        Nodo<T> nodoAEliminar = actual.getSiguiente();
        actual.setSiguiente(nodoAEliminar.getSiguiente());
        tamanio--;
        return true;
    }
    public Nodo<T> getCabeza() {
        return this.cabeza;
    }
}
