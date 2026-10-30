package controlador;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;

public class LogErrores {
    private static final String FICHERO_LOG = "errores_biblioteca.log";

    public static void registrar(String mensaje) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(FICHERO_LOG, true))) {
            pw.println(LocalDateTime.now() + " - " + mensaje);
        } catch (IOException e) {
            // En un proyecto real se podría manejar de otra forma,
            // pero aquí no volvemos a lanzar excepción para no encadenar errores.
            e.printStackTrace();
        }
    }

    public static void registrar(Exception ex) {
        registrar(ex.getClass().getSimpleName() + ": " + ex.getMessage());
    }
}
