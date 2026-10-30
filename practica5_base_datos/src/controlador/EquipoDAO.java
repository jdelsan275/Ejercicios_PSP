package controlador;

import modelo.Equipo;
import java.sql.*;
import java.time.LocalDate;

// DAO de la tabla A (equipo): valida login y gestiona carga/actualización del perfil

public class EquipoDAO {
    // Valida usuario/contraseña contra la BD y devuelve el Equipo o null si no coincide
    public Equipo validarEquipo(String usuario, String password) throws ExcepcionF1 {
        String sql = "SELECT * FROM equipo WHERE usuario = ? AND password = ?";
        try (Connection con = ConexionBD.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, usuario);
            ps.setString(2, password);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapearEquipo(rs);
                }
                return null;
            }
        } catch (SQLException ex) {
            LogErrores.registrar(ex);
            throw new ExcepcionF1(101, ex.getMessage());
        }
    }

    // Obtiene un equipo por su id para recargar el perfil
    public Equipo obtenerPorId(int idEquipo) throws ExcepcionF1 {
        String sql = "SELECT * FROM equipo WHERE id_equipo = ?";
        try (Connection con = ConexionBD.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, idEquipo);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return mapearEquipo(rs);
                return null;
            }
        } catch (SQLException ex) {
            LogErrores.registrar(ex);
            throw new ExcepcionF1(401, ex.getMessage());
        }
    }

    // Actualiza en BD la fecha, el logo (ruta) y la sede del equipo
    public int actualizarFechaLogoYSede(int idEquipo, LocalDate fechaAlta, String logoPath, String sede) throws ExcepcionF1 {
        String sql = "UPDATE equipo SET fecha_alta = ?, logo_path = ?, sede = ? WHERE id_equipo = ?";
        try (Connection con = ConexionBD.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setDate(1, Date.valueOf(fechaAlta));
            ps.setString(2, logoPath);
            ps.setString(3, sede);
            ps.setInt(4, idEquipo);

            return ps.executeUpdate();
        } catch (SQLException ex) {
            LogErrores.registrar(ex);
            throw new ExcepcionF1(402, ex.getMessage());
        }
    }

    // Mapea la fila actual del ResultSet a un objeto Equipo (modelo)
    private Equipo mapearEquipo(ResultSet rs) throws SQLException {
        Equipo e = new Equipo();
        e.setIdEquipo(rs.getInt("id_equipo"));
        e.setUsuario(rs.getString("usuario"));
        e.setPassword(rs.getString("password"));
        e.setNombreEquipo(rs.getString("nombre_equipo"));
        e.setSede(rs.getString("sede"));
        e.setPresupuesto(rs.getDouble("presupuesto"));

        Date f = rs.getDate("fecha_alta");
        if (f != null) e.setFechaAlta(f.toLocalDate());

        e.setLogoPath(rs.getString("logo_path"));
        return e;
    }
}
