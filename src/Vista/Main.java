package Vista;

import Controlador.GestorSoporteBancario;

// Punto de entrada: conecta el controlador con la vista
public class Main {

    private GestorSoporteBancario gestorSistema;
    private VistaPrincipal vista;

    public Main() {
        this.gestorSistema = new GestorSoporteBancario();
        this.vista = new VistaPrincipal(gestorSistema);
    }

    public static void main(String[] args) {
        new Main().vista.mostrarMenuPrincipal();
    }
}