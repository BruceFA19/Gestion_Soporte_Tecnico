package Estructuras;

public class NodoHash<K, V> {
    K clave;
    V valor;
    NodoHash<K, V> siguiente; // Para manejar colisiones
    public NodoHash(K clave, V valor) {
        this.clave = clave;
        this.valor = valor;
        this.siguiente = null;
    }
}
