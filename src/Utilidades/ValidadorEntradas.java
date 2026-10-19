package Utilidades;
public class ValidadorEntradas {
    // Regex para validar que la cédula tenga exactamente 10 números (formato estándar)
    private String patronCedula = "^\\d{10}$";
    private String patronUsuarioRed = "^[A-Za-z][A-Za-z0-9._-]{3,19}$";
    public ValidadorEntradas() {
    }
    public boolean validarCedula(String cedula) {
        return cedula != null && cedula.matches(patronCedula);
    }
    public boolean validarUsuarioRed(String usuarioRed) {
        return usuarioRed != null && usuarioRed.matches(patronUsuarioRed);
    }
    public boolean validarCodigoActivoFijo(String idEquipo) {
        return idEquipo != null && idEquipo.matches("^[A-Za-z0-9-]{4,20}$");
    }
    // Cada factor de la fórmula (criticidad, alcance, bloqueo, complejidad) va de 1 a 3
    public boolean validarFactorFormula(int valorFactor) {
        return valorFactor >= 1 && valorFactor <= 3;
    }
    // ¡ACTUALIZADO A NUESTRA FÓRMULA DE 4 DIMENSIONES!
    // Mínimo 4 (1+1+1+1), Máximo 12 (3+3+3+3)
    public boolean validarPuntajeTotal(int puntajeTotal) {
        return puntajeTotal >= 4 && puntajeTotal <= 12;
    }
    public String getPatronCedula() { return patronCedula; }
    public String getPatronUsuarioRed() { return patronUsuarioRed; }
}