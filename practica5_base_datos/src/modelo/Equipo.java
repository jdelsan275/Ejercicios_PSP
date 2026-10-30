package modelo;

import java.time.LocalDate;

public class Equipo {
    private int idEquipo;
    private String usuario;
    private String password;
    private String nombreEquipo;
    private String sede;
    private double presupuesto;
    private LocalDate fechaAlta;
    private String logoPath;

    // Tabla A (equipo) para login y perfil
    
    public Equipo() {}

    public Equipo(int idEquipo, String usuario, String password, String nombreEquipo,
                  String sede, double presupuesto, LocalDate fechaAlta, String logoPath) {
        this.idEquipo = idEquipo;
        this.usuario = usuario;
        this.password = password;
        this.nombreEquipo = nombreEquipo;
        this.sede = sede;
        this.presupuesto = presupuesto;
        this.fechaAlta = fechaAlta;
        this.logoPath = logoPath;
    }

    public int getIdEquipo() { return idEquipo; }
    public void setIdEquipo(int idEquipo) { this.idEquipo = idEquipo; }

    public String getUsuario() { return usuario; }
    public void setUsuario(String usuario) { this.usuario = usuario; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getNombreEquipo() { return nombreEquipo; }
    public void setNombreEquipo(String nombreEquipo) { this.nombreEquipo = nombreEquipo; }

    public String getSede() { return sede; }
    public void setSede(String sede) { this.sede = sede; }

    public double getPresupuesto() { return presupuesto; }
    public void setPresupuesto(double presupuesto) { this.presupuesto = presupuesto; }

    public LocalDate getFechaAlta() { return fechaAlta; }
    public void setFechaAlta(LocalDate fechaAlta) { this.fechaAlta = fechaAlta; }

    public String getLogoPath() { return logoPath; }
    public void setLogoPath(String logoPath) { this.logoPath = logoPath; }
}
