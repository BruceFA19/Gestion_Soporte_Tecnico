package Vista;

import Controlador.GestorSoporteBancario;

// aqui slo es mostrar la informacion y recoger la informacion ingresada por el usuarios
// como usamos MVC, la logica siempre se delega en el controlador

public class VistaPrincipal{
    private GestorSoporteBancario controlador;

    public VistaPrincipal(GestorSoporteBancario controlador) {
        this.controlador = controlador;
    }

    public void mostrarMenuPrincipal() {
       
    }
}