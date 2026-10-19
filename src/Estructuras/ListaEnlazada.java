package Estructuras;
import java.util.Iterator;
public class ListaEnlazada<T> implements Iterable<T> {
    private Nodo<T> cabeza; // El primer elemento de la lista
    private Nodo<T> cola;   // El último elemento (nos ahorra recorrer toda la lista para agregar)
    private int tamanio;    // Contador rápido de elementos
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
            // Viajamos hasta un vagón ANTES del que queremos eliminar
            for (int i = 0; i < indice - 1; i++) {
                anterior = anterior.getSiguiente();
            }

            Nodo<T> aEliminar = anterior.getSiguiente();
            datoEliminado = aEliminar.getDato();

            // "Saltamos" el vagón a eliminar, conectando el anterior con el siguiente
            anterior.setSiguiente(aEliminar.getSiguiente());

            // Si el que eliminamos era el último, actualizamos la cola
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
            // Usamos .equals() para comparar el contenido de los objetos
            if (actual.getDato().equals(dato)) {
                return indice;
            }
            actual = actual.getSiguiente();
            indice++;
        }

        return -1; // No se encontró
    }
    @Override
    public Iterator<T> iterator() {
        return new Iterator<T>() {
            private Nodo<T> actual = cabeza;

            @Override
            public boolean hasNext() {
                return actual != null; // ¿Hay un siguiente vagón?
            }

            @Override
            public T next() {
                T dato = actual.getDato();
                actual = actual.getSiguiente(); // Avanzamos al siguiente
                return dato;
            }
        };
    }
}
