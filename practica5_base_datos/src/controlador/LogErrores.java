package controlador;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;

public class LogErrores {
    private static final String FICHERO_LOG = "errores_f1.log";

    // Escribe una línea en el log con fecha/hora y el mensaje indicado
    public static void registrar(String mensaje) {
        try (FileWriter fw = new FileWriter(FICHERO_LOG, true)) {
            fw.write(LocalDateTime.now() + " - " + mensaje + System.lineSeparator());
        } catch (IOException e) {
            System.err.println("No se pudo escribir en el log: " + e.getMessage());
        }
    }

    // Registra en el log el tipo de excepción y su mensaje, sin mostrarlo al usuario
    public static void registrar(Exception ex) {
        registrar(ex.getClass().getName() + " - " + ex.getMessage());
    }
}
