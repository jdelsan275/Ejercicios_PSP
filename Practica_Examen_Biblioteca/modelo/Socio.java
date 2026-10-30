package modelo;

import java.time.LocalDate;
import java.time.Period;

public class Socio implements ConEdad {
    private int idSocio;
    private String nombre;
    private String password;
    private String dni;
    private String email;
    private String telefono;
    private LocalDate fechaNac;
    private String direccion;

    public Socio() { }

    public Socio(int idSocio, String nombre, String password, String dni,
                 String email, String telefono, LocalDate fechaNac, String direccion) {
        this.idSocio = idSocio;
        this.nombre = nombre;
        this.password = password;
        this.dni = dni;
        this.email = email;
        this.telefono = telefono;
        this.fechaNac = fechaNac;
        this.direccion = direccion;
    }

    public int getIdSocio() {
    return idSocio;
    }

    public void setIdSocio(int idSocio) {
        this.idSocio = idSocio;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public java.time.LocalDate getFechaNac() {
        return fechaNac;
    }

    public void setFechaNac(java.time.LocalDate fechaNac) {
        this.fechaNac = fechaNac;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    @Override
    public int getEdad() {
        // Calcula la edad en años a partir de fechaNac y la fecha actual
        if (fechaNac == null) {
            return 0;
        }
        return Period.between(fechaNac, LocalDate.now()).getYears(); // uso típico de Period.between
    } // [web:10][web:13]
}
