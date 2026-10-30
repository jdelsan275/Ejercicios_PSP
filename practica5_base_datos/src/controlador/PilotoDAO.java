package controlador;

import modelo.Piloto;
import java.sql.*;

// DAO de la tabla B (piloto): consultas y recalculo del campo puntos_totales

public class PilotoDAO {
    // Busca un piloto por id y lo devuelve como objeto de modelo
    public Piloto obtenerPorId(int idPiloto) throws ExcepcionF1 {
        String sql = "SELECT * FROM piloto WHERE id_piloto = ?";
        try (Connection con = ConexionBD.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, idPiloto);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return mapearPiloto(rs);
                return null;
            }
        } catch (SQLException ex) {
            LogErrores.registrar(ex);
            throw new ExcepcionF1(201, ex.getMessage());
        }
    }

    // Recalcula puntos_totales en BD (suma de resultados)
    public int recalcularPuntosPiloto(int idPiloto) throws ExcepcionF1 {
        String sql = "UPDATE piloto p " +
                     "SET puntos_totales = COALESCE((SELECT SUM(r.puntos_obtenidos) FROM resultado r WHERE r.pilId_piloto = p.id_piloto), 0) " +
                     "WHERE p.id_piloto = ?";
        try (Connection con = ConexionBD.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, idPiloto);
            return ps.executeUpdate();
        } catch (SQLException ex) {
            LogErrores.registrar(ex);
            throw new ExcepcionF1(204, ex.getMessage());
        }
    }

    // Convierte la fila actual del ResultSet en un objeto Piloto (modelo)
    private Piloto mapearPiloto(ResultSet rs) throws SQLException {
        Piloto p = new Piloto();
        p.setIdPiloto(rs.getInt("id_piloto"));
        p.setEqpIdEquipo(rs.getInt("eqpId_equipo"));
        p.setNombre(rs.getString("nombre"));
        p.setNacionalidad(rs.getString("nacionalidad"));

        Date fn = rs.getDate("fecha_nac");
        if (fn != null) p.setFechaNac(fn.toLocalDate());

        int nc = rs.getInt("numero_coche");
        if (rs.wasNull()) p.setNumeroCoche(null);
        else p.setNumeroCoche(nc);

        p.setPuntosObjetivo(rs.getInt("puntos_objetivo"));
        p.setPuntosTotales(rs.getInt("puntos_totales"));

        int mentor = rs.getInt("pilId_piloto_mentor");
        if (rs.wasNull()) p.setPilIdPilotoMentor(null);
        else p.setPilIdPilotoMentor(mentor);

        return p;
    }
}
