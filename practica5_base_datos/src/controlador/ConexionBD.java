package controlador;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

// Abre, reutiliza y cierra la conexión JDBC a PostgreSQL

public class ConexionBD {
    private static Connection conexion;

    private static final String URL  = "jdbc:postgresql://localhost:5432/f1";
    private static final String USER = "postgres";
    private static final String PASS = "postgres";

    private ConexionBD() {}

    // Abre la conexión si no existe o está cerrada, y lanza ExcepcionF1 si falla
    public static void abrirConexion() throws ExcepcionF1 {
        try {
            if (conexion == null || conexion.isClosed()) {
                conexion = DriverManager.getConnection(URL, USER, PASS);
            }
        } catch (SQLException ex) {
            LogErrores.registrar(ex);
            throw new ExcepcionF1(101, ex.getMessage());
        }
    }

    // Devuelve la conexión activa
    public static Connection getConexion() throws ExcepcionF1 {
        try {
            if (conexion == null || conexion.isClosed()) {
                abrirConexion();
            }
            return conexion;
        } catch (SQLException ex) {
            LogErrores.registrar(ex);
            throw new ExcepcionF1(101, ex.getMessage());
        }
    }

    // Cierra la conexión si está abierta
    public static void cerrarConexion() throws ExcepcionF1 {
        try {
            if (conexion != null && !conexion.isClosed()) {
                conexion.close();
            }
        } catch (SQLException ex) {
            LogErrores.registrar(ex);
            throw new ExcepcionF1(101, ex.getMessage());
        }
    }
}
