package controlador;

import java.util.HashMap;

// Diccionario de Errores: mensajes para mostrar en la Vista

public class GestorErrores {
    private static final HashMap<Integer, String> mapa = new HashMap<>();

    static {
        mapa.put(101, "Error 101: No hay conexión con la base de datos.");
        mapa.put(102, "Error 102: Usuario o contraseña incorrectos.");
        mapa.put(103, "Error 103: Sesión no iniciada.");

        mapa.put(201, "Error 201: No se pudieron cargar los pilotos.");
        mapa.put(202, "Error 202: No se pudieron cargar los resultados del piloto.");
        mapa.put(203, "Error 203: No se pudo actualizar el piloto.");
        mapa.put(204, "Error 204: No se pudo recalcular puntos del piloto.");

        mapa.put(301, "Error 301: No se pudo insertar el resultado.");
        mapa.put(302, "Error 302: No se pudo borrar el resultado.");
        mapa.put(303, "Error 303: Datos de resultado no válidos.");

        mapa.put(401, "Error 401: No se pudo cargar el perfil del equipo.");
        mapa.put(402, "Error 402: No se pudo actualizar el perfil del equipo.");
        mapa.put(403, "Error 403: Fecha no válida.");
        mapa.put(404, "Error 404: No se pudo copiar la imagen.");
    }

    // Devuelve el mensaje asociado al código
    public static String getMensajeError(int codigo) {
        return mapa.getOrDefault(codigo, "Error " + codigo + ": Error no controlado.");
    }
}
