package controlador;

import java.util.HashMap;
import java.util.Map;

public class GestorErrores {
    private static final Map<Integer, String> mapaErrores = new HashMap<>();

    static {
        mapaErrores.put(1, "Error de conexión con la base de datos.");
        mapaErrores.put(2, "Usuario o contraseña incorrectos.");
        mapaErrores.put(3, "No se ha podido cargar la lista de préstamos.");
        mapaErrores.put(4, "No se ha podido registrar el préstamo.");
        mapaErrores.put(5, "No se ha podido marcar el préstamo como devuelto.");
        mapaErrores.put(6, "No se ha podido cargar la lista de libros.");
        // Añade más códigos según necesidades
    }

    public static String getMensajeError(int codigo) {
        return mapaErrores.getOrDefault(codigo, "Se ha producido un error desconocido.");
    }
}
