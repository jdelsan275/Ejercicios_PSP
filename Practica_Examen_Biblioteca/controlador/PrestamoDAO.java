package controlador;

import modelo.Prestamo;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class PrestamoDAO {
    public List<Prestamo> obtenerPrestamosPorSocio(int idSocio, Boolean soloDevueltos) throws SQLException {
        StringBuilder sb = new StringBuilder(
                "SELECT * FROM prestamo WHERE id_socio = ?"
        );
        if (soloDevueltos != null) {
            if (soloDevueltos) {
                sb.append(" AND fecha_dev IS NOT NULL");
            } else {
                sb.append(" AND fecha_dev IS NULL");
            }
        }
        sb.append(" ORDER BY fecha_sal DESC");

        List<Prestamo> lista = new ArrayList<>();
        try (Connection con = ConexionBD.getConexion();
             PreparedStatement ps = con.prepareStatement(sb.toString())) {
            ps.setInt(1, idSocio);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Prestamo p = new Prestamo();
                    p.setNumero(rs.getInt("numero"));
                    Date fSal = rs.getDate("fecha_sal");
                    Date fDev = rs.getDate("fecha_dev");
                    p.setFechaSal(fSal != null ? fSal.toLocalDate() : null);
                    p.setFechaDev(fDev != null ? fDev.toLocalDate() : null);
                    p.setIdSocio(rs.getInt("id_socio"));
                    p.setIdLibro(rs.getInt("id_libro"));
                    lista.add(p);
                }
            }
        }
        return lista;
    }

    public void insertarPrestamo(int idSocio, int idLibro) throws SQLException {
        String sql = "INSERT INTO prestamo (fecha_sal, fecha_dev, id_socio, id_libro) " +
                     "VALUES (?, NULL, ?, ?)";
        try (Connection con = ConexionBD.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setDate(1, Date.valueOf(LocalDate.now()));
            ps.setInt(2, idSocio);
            ps.setInt(3, idLibro);
            ps.executeUpdate();
        }
    }

    public void marcarDevuelto(int numeroPrestamo) throws SQLException {
        String sql = "UPDATE prestamo SET fecha_dev = ? WHERE numero = ?";
        try (Connection con = ConexionBD.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setDate(1, Date.valueOf(LocalDate.now()));
            ps.setInt(2, numeroPrestamo);
            ps.executeUpdate();
        }
    }
}
