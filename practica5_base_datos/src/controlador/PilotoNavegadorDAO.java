package controlador;

import modelo.Piloto;
import java.sql.*;

// DAO “navegador” de la tabla B (piloto): permite recorrer el ResultSet uno a uno (primero/anterior/siguiente/último)

public class PilotoNavegadorDAO {
    private ResultSet rs;
    private Statement st;

    // Ejecuta un SELECT y prepara un ResultSet scrollable para navegar por los pilotos
    public void iniciar(String sql) throws ExcepcionF1 {
        try {
            Connection con = ConexionBD.getConexion();
            st = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
            rs = st.executeQuery(sql);
        } catch (SQLException ex) {
            LogErrores.registrar(ex);
            throw new ExcepcionF1(201, ex.getMessage());
        }
    }

    public boolean avanzar() throws ExcepcionF1 {
        try {
            return rs != null && rs.next();
        } catch (SQLException ex) {
            LogErrores.registrar(ex);
            throw new ExcepcionF1(201, ex.getMessage());
        }
    }

    public boolean retroceder() throws ExcepcionF1 {
        try {
            return rs != null && rs.previous();
        } catch (SQLException ex) {
            LogErrores.registrar(ex);
            throw new ExcepcionF1(201, ex.getMessage());
        }
    }

    public boolean irPrimero() throws ExcepcionF1 {
        try {
            return rs != null && rs.first();
        } catch (SQLException ex) {
            LogErrores.registrar(ex);
            throw new ExcepcionF1(201, ex.getMessage());
        }
    }

    public boolean irUltimo() throws ExcepcionF1 {
        try {
            return rs != null && rs.last();
        } catch (SQLException ex) {
            LogErrores.registrar(ex);
            throw new ExcepcionF1(201, ex.getMessage());
        }
    }
    
    // Indica si está en la primera posición para deshabilitar botones
    public boolean esPrimero() throws ExcepcionF1 {
        try {
            return rs != null && rs.isFirst();
        } catch (SQLException ex) {
            LogErrores.registrar(ex);
            throw new ExcepcionF1(201, ex.getMessage());
        }
    }

    // Indica si está en la última posición para deshabilitar botones
    public boolean esUltimo() throws ExcepcionF1 {
        try {
            return rs != null && rs.isLast();
        } catch (SQLException ex) {
            LogErrores.registrar(ex);
            throw new ExcepcionF1(201, ex.getMessage());
        }
    }

    // Lee la fila actual del ResultSet y la convierte en un objeto Piloto
    public Piloto leer() throws ExcepcionF1 {
        try {
            if (rs == null || rs.isBeforeFirst() || rs.isAfterLast()) return null;

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
        } catch (SQLException ex) {
            LogErrores.registrar(ex);
            throw new ExcepcionF1(201, ex.getMessage());
        }
    }

    // Cierra el ResultSet/Statement
    public void finalizar() throws ExcepcionF1 {
        try {
            if (rs != null) rs.close();
            if (st != null) st.close();
        } catch (SQLException ex) {
            LogErrores.registrar(ex);
            throw new ExcepcionF1(201, ex.getMessage());
        }
    }
}
