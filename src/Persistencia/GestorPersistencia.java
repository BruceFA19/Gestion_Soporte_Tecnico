package Persistencia;
import Controlador.GestorSoporteBancario;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
public class GestorPersistencia {
private static final String RUTA_ARCHIVO = "datos_sistema.dat";
    public static boolean guardarEstado(GestorSoporteBancario gestor) {
        if (gestor == null) {
            return false;
        }
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(RUTA_ARCHIVO))) {
            oos.writeObject(gestor);
            System.out.println("Datos guardados correctamente en " + RUTA_ARCHIVO);
            return true;
        } catch (IOException e) {
            System.err.println("Error al guardar los datos del sistema: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
    public static GestorSoporteBancario cargarEstado() {
        File archivo = new File(RUTA_ARCHIVO);

        // Si el archivo no existe (primera ejecución), se retorna un Gestor nuevo
        if (!archivo.exists()) {
            System.out.println("ℹ️ No se encontró archivo de datos previo. Creando un sistema nuevo...");
            return new GestorSoporteBancario();
        }

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(archivo))) {
            GestorSoporteBancario gestorCargado = (GestorSoporteBancario) ois.readObject();
            System.out.println("📂 Datos cargados exitosamente desde " + RUTA_ARCHIVO);
            return gestorCargado;
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("⚠️ Error al deserializar el archivo. Se creará una instancia limpia: " + e.getMessage());
            return new GestorSoporteBancario();
        }
    }
}