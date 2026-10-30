package controlador;

import modelo.Libro;
import java.sql.*;

public class LibroResultSetDAO {
    private ResultSet rsLibros;

    /**
     * Carga un ResultSet SENSITIVO y UPDATABLE de todos los libros.
     */
    public void cargarLibros() throws SQLException {
        Connection con = ConexionBD.getConexion();
        Statement st = con.createStatement(
                ResultSet.TYPE_SCROLL_SENSITIVE,
                ResultSet.CONCUR_UPDATABLE
        ); // configuración típica para un ResultSet desplazable y actualizable [web:12]
        String sql = "SELECT * FROM libro ORDER BY id_libro";
        rsLibros = st.executeQuery(sql);
    }

    public boolean moverPrimero() throws SQLException {
        return rsLibros != null && rsLibros.first();
    }

    public boolean moverUltimo() throws SQLException {
        return rsLibros != null && rsLibros.last();
    }

    public boolean moverSiguiente() throws SQLException {
        return rsLibros != null && rsLibros.next();
    }

    public boolean moverAnterior() throws SQLException {
        return rsLibros != null && rsLibros.previous();
    }

    public void irAntesDelPrimero() throws SQLException {
        if (rsLibros != null) {
            rsLibros.beforeFirst();
        }
    }

    public void irDespuesDelUltimo() throws SQLException {
        if (rsLibros != null) {
            rsLibros.afterLast();
        }
    }

    public boolean moverRelativo(int filas) throws SQLException {
        return rsLibros != null && rsLibros.relative(filas);
    }

    public boolean moverAbsoluto(int fila) throws SQLException {
        return rsLibros != null && rsLibros.absolute(fila);
    }

    public Libro getLibroActual() throws SQLException {
        if (rsLibros == null || rsLibros.isAfterLast() || rsLibros.isBeforeFirst()) {
            return null;
        }
        Libro l = new Libro();
        l.setIdLibro(rsLibros.getInt("id_libro"));
        l.setTitulo(rsLibros.getString("titulo"));
        l.setAutor(rsLibros.getString("autor"));
        l.setEditorial(rsLibros.getString("editorial"));
        l.setAnioPublicacion(rsLibros.getInt("anio_publicacion"));
        l.setEjemplaresTotales(rsLibros.getInt("ejemplares_totales"));
        l.setEjemplaresPrestados(rsLibros.getInt("ejemplares_prestados"));
        l.setIsbn(rsLibros.getString("isbn"));
        return l;
    }

    /**
     * Incrementa el campo ejemplares_prestados en 1 en la fila actual.
     */
    public void incrementarPrestadosActual() throws SQLException {
        int prestados = rsLibros.getInt("ejemplares_prestados");
        rsLibros.updateInt("ejemplares_prestados", prestados + 1);
        rsLibros.updateRow();
    }
}
