package Estructuras;

// A quien le toque manipular esta parte, reemplazar nomas los valores para loque vaya hacer esta estructura

public class TablaHash<K, V> {

    // Un arreglo estático de Nodos Hash
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

    // Constructor por defecto (crea una tabla de 100 espacios)
    public TablaHash() {
        this(100);
    }

    // ==========================================
    // FUNCIÓN HASH (El motor matemático)
    // ==========================================
    private int calcularIndice(K clave) {
        // Obtenemos el código hash nativo, quitamos el signo negativo (abs)
        // y usamos el módulo (%) para que encaje en el tamaño del arreglo
        return Math.abs(clave.hashCode() % capacidad);
    }

    // ==========================================
    // OPERACIONES BÁSICAS
    // ==========================================

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

        // Buscamos dentro de la cubeta correspondiente
        while (actual != null) {
            if (actual.clave.equals(clave)) {
                return actual.valor; // ¡Lo encontramos en O(1)!
            }
            actual = actual.siguiente;
        }

        return null; // El usuario no existe
    }

    public void eliminar(K clave) {
        int indice = calcularIndice(clave);
        NodoHash<K, V> actual = arreglo[indice];
        NodoHash<K, V> anterior = null;

        while (actual != null) {
            if (actual.clave.equals(clave)) {
                if (anterior == null) {
                    // Era el primer elemento de la cubeta
                    arreglo[indice] = actual.siguiente;
                } else {
                    // Estaba en medio de una cadena de colisión
                    anterior.siguiente = actual.siguiente;
                }
                tamanio--;
                return;
            }
            anterior = actual;
            actual = actual.siguiente;
        }
    }

    public int getTamanio() {
        return tamanio;
    }
}
