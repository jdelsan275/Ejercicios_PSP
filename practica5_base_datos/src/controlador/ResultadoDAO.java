package controlador;

import modelo.Resultado;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

// DAO de la tabla C (resultado): consultas/altas/bajas de resultados asociados a un piloto

public class ResultadoDAO {
    // Devuelve la lista de resultados (tabla C) de un piloto, ordenados por fecha descendente
    public List<Resultado> obtenerResultadosPorPiloto(int idPiloto) throws ExcepcionF1 {
        String sql = "SELECT * FROM resultado WHERE pilId_piloto = ? ORDER BY fecha_carrera DESC";
        List<Resultado> lista = new ArrayList<>();

        try (Connection con = ConexionBD.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, idPiloto);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    lista.add(mapearResultado(rs));
                }
            }
            return lista;
        } catch (SQLException ex) {
            LogErrores.registrar(ex);
            throw new ExcepcionF1(202, ex.getMessage());
        }
    }

    // Inserta un resultado en BD y devuelve cuántas filas se han insertado
    public int insertarResultado(int idPiloto, LocalDate fechaCarrera, String granPremio, int posicion, int puntos) throws ExcepcionF1 {
        String sql = "INSERT INTO resultado (pilId_piloto, fecha_carrera, gran_premio, posicion, puntos_obtenidos) VALUES (?, ?, ?, ?, ?)";
        try (Connection con = ConexionBD.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, idPiloto);
            ps.setDate(2, Date.valueOf(fechaCarrera));
            ps.setString(3, granPremio);
            ps.setInt(4, posicion);
            ps.setInt(5, puntos);

            return ps.executeUpdate();
        } catch (SQLException ex) {
            LogErrores.registrar(ex);
            throw new ExcepcionF1(301, ex.getMessage());
        }
    }

    // Borra un resultado por id
    public int borrarResultado(int idResultado) throws ExcepcionF1 {
        String sql = "DELETE FROM resultado WHERE id_resultado = ?";
        try (Connection con = ConexionBD.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, idResultado);
            return ps.executeUpdate();
        } catch (SQLException ex) {
            LogErrores.registrar(ex);
            throw new ExcepcionF1(302, ex.getMessage());
        }
    }

    // Convierte la fila actual del ResultSet en un objeto Resultado (modelo)
    private Resultado mapearResultado(ResultSet rs) throws SQLException {
        Resultado r = new Resultado();
        r.setIdResultado(rs.getInt("id_resultado"));
        r.setPilIdPiloto(rs.getInt("pilId_piloto"));

        Date fc = rs.getDate("fecha_carrera");
        if (fc != null) r.setFechaCarrera(fc.toLocalDate());

        r.setGranPremio(rs.getString("gran_premio"));
        r.setPosicion(rs.getInt("posicion"));
        r.setPuntosObtenidos(rs.getInt("puntos_obtenidos"));
        return r;
    }
}
