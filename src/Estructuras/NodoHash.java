package Estructuras;
import java.io.Serializable;
public class NodoHash<K, V> implements Serializable {
    private static final long serialVersionUID = 1L;
    K clave;
    V valor;
    NodoHash<K, V> siguiente;
    public K getClave() {
        return clave;
    }
    public V getValor() {
        return valor;
    }
    public NodoHash<K, V> getSiguiente() {
        return siguiente;
    }
    public NodoHash(K clave, V valor) {
        this.clave = clave;
        this.valor = valor;
        this.siguiente = null;
    }
}
