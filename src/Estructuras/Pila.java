package Estructuras;

import java.io.Serializable;

public class Pila<T> implements Serializable {
    private static final long serialVersionUID = 1L;
    private Nodo<T> cima;
    private int tamanio;
    public Pila() {
        this.cima = null;
        this.tamanio = 0;
    }
    public void apilar(T dato) {
        Nodo<T> nuevoNodo = new Nodo<>(dato);
        nuevoNodo.setSiguiente(cima);
        cima = nuevoNodo;
        tamanio++;
    }
    public T desapilar() {
        if (estaVacia()) {
            return null;
        }
        T datoSaliente = cima.getDato();
        cima = cima.getSiguiente();
        tamanio--;
        return datoSaliente;
    }
    public T obtenerCima() {
        if (estaVacia()) {
            return null;
        }
        return cima.getDato();
    }
    public boolean estaVacia() {
        return cima == null;
    }
    public int getTamanio() {
        return tamanio;
    }
}
