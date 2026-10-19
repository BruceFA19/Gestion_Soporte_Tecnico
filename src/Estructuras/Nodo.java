package Estructuras;

public class Nodo<T> {
    private T dato;
    private Nodo<T> siguiente;
    public Nodo(T dato) {
        this.dato = dato;
        this.siguiente = null; // Al crearse, es el último vagón, no apunta a nada
    }
    public T getDato() { return dato; }
    public void setDato(T dato) { this.dato = dato; }

    public Nodo<T> getSiguiente() { return siguiente; }
    public void setSiguiente(Nodo<T> siguiente) { this.siguiente = siguiente; }
}
