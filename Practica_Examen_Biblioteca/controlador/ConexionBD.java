package controlador;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionBD {
    private static Connection conexion;

    private static final String URL  = "jdbc:postgresql://localhost:5432/postgres"; // BD biblio
    private static final String USER = "postgres";
    private static final String PASS = "postgres";

    private ConexionBD() { }

    public static void abrirConexion() throws SQLException {
        if (conexion == null || conexion.isClosed()) {
            // DriverManager usará el driver de PostgreSQL en el classpath
            conexion = DriverManager.getConnection(URL, USER, PASS);
        }
    }

    public static Connection getConexion() throws SQLException {
        if (conexion == null || conexion.isClosed()) {
            abrirConexion();
        }
        return conexion;
    }

    public static void cerrarConexion() throws SQLException {
        if (conexion != null && !conexion.isClosed()) {
            conexion.close();
        }
    }
}
