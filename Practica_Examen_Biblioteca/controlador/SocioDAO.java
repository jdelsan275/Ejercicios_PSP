package controlador;

import modelo.Socio;
import java.sql.*;
import java.time.LocalDate;

public class SocioDAO {
    public Socio validarSocio(String nombre, String password) throws SQLException {
        String sql = "SELECT * FROM socio WHERE nombre = ? AND password = ?";
        try (Connection con = ConexionBD.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, nombre);
            ps.setString(2, password);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Socio s = new Socio();
                    s.setIdSocio(rs.getInt("id_socio"));
                    s.setNombre(rs.getString("nombre"));
                    s.setPassword(rs.getString("password"));
                    s.setDni(rs.getString("dni"));
                    s.setEmail(rs.getString("email"));
                    s.setTelefono(rs.getString("telefono"));
                    Date fecha = rs.getDate("fecha_nac");
                    if (fecha != null) {
                        s.setFechaNac(fecha.toLocalDate());
                    }
                    s.setDireccion(rs.getString("direccion"));
                    return s;
                } else {
                    return null;
                }
            }
        }
    }
}
