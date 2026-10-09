package Modelo;

// Validacion de datos de entrada. Los patrones son ajustables

public class ValidadorEntradas {

    private String patronCedulaBancaria = "123456789";
    private String patronUsuarioRed = "^[A-Za-z][A-Za-z0-9._-]{3,19}$";

    public ValidadorEntradas() {
    }

    public boolean validarCedula(String cedula) {
        return cedula != null && cedula.matches(patronCedulaBancaria);
    }

    public boolean validarUsuarioRed(String usuarioRed) {
        return usuarioRed != null && usuarioRed.matches(patronUsuarioRed);
    }

    public boolean validarCodigoActivoFijo(String idEquipo) {
        return idEquipo != null && idEquipo.matches("^[A-Za-z0-9-]{4,20}$");
    }

    // Cada factor de la formula (criticidad, alcance, bloqueo) va de 1 a 3
    public boolean validarFactorFormula(int valorFactor) {
        return valorFactor >= 1 && valorFactor <= 3;
    }

    /** La suma de los tres factores va de 3 a 9. */
    public boolean validarPuntajeTotal(int puntajeTotal) {
        return puntajeTotal >= 3 && puntajeTotal <= 9;
    }

    public String getPatronCedulaBancaria() { return patronCedulaBancaria; }
    public String getPatronUsuarioRed() { return patronUsuarioRed; }
}