package Estructuras;

// A quien le toque manipular esta parte, reemplazar nomas los valores para loque vaya hacer esta estructura

import java.io.Serializable;

public class TablaHash<K, V> implements Serializable {
    private static final long serialVersionUID = 1L;
    private NodoHash<K, V>[] arreglo;
    private int capacidad;
    private int tamanio;
    @SuppressWarnings("unchecked")
    public TablaHash(int capacidadInicial) {
        this.capacidad = capacidadInicial;
        this.tamanio = 0;
        // En Java, crear arreglos de genéricos requiere este casteo
        this.arreglo = (NodoHash<K, V>[]) new NodoHash[capacidad];
    }
    public TablaHash() {
        this(100);
    }
    private int calcularIndice(K clave) {
        return Math.abs(clave.hashCode() % capacidad);
    }
    public void insertar(K clave, V valor) {
        int indice = calcularIndice(clave);
        NodoHash<K, V> nuevoNodo = new NodoHash<>(clave, valor);

        // Si la "cubeta" está vacía, entra directo
        if (arreglo[indice] == null) {
            arreglo[indice] = nuevoNodo;
            tamanio++;
        } else {
            // Manejo de Colisiones: Recorremos la lista enlazada de esa cubeta
            NodoHash<K, V> actual = arreglo[indice];
            while (true) {
                // Si la clave ya existe, actualizamos el valor (sobrescribimos)
                if (actual.clave.equals(clave)) {
                    actual.valor = valor;
                    return;
                }
                // Si llegamos al final de la cadena de colisión, lo agregamos ahí
                if (actual.siguiente == null) {
                    actual.siguiente = nuevoNodo;
                    tamanio++;
                    break;
                }
                actual = actual.siguiente;
            }
        }
    }
    public V obtener(K clave) {
        int indice = calcularIndice(clave);
        NodoHash<K, V> actual = arreglo[indice];
        while (actual != null) {
            if (actual.clave.equals(clave)) {
                return actual.valor;
            }
            actual = actual.siguiente;
        }
        return null;
    }
    public boolean eliminar(K clave) {
        int indice = calcularIndice(clave);
        NodoHash<K, V> actual = arreglo[indice];
        NodoHash<K, V> anterior = null;
        while (actual != null) {
            if (actual.clave.equals(clave)) {
                if (anterior == null) {
                    arreglo[indice] = actual.siguiente;
                } else {
                    anterior.siguiente = actual.siguiente;
                }
                tamanio--;
                return true;
            }
            anterior = actual;
            actual = actual.siguiente;
        }
        return false;
    }
    public int getTamanio() {
        return tamanio;
    }
    public ListaEnlazada<V> obtenerValores() {
        ListaEnlazada<V> listaValores = new ListaEnlazada<>();
        if (arreglo == null || tamanio == 0) {
            return listaValores;
        }
        for (int i = 0; i < arreglo.length; i++) {
            NodoHash<K, V> actual = arreglo[i];

            while (actual != null) {
                listaValores.agregar(actual.getValor());
                actual = actual.getSiguiente();
            }
        }
        return listaValores;
    }
    public boolean esVacia() {
        return this.tamanio == 0;
    }
}
