package modelo;

import java.time.LocalDate;

public class Prestamo {
    private int numero;
    private LocalDate fechaSal;
    private LocalDate fechaDev;
    private int idSocio;
    private int idLibro;

    public Prestamo() { }

    public Prestamo(int numero, LocalDate fechaSal, LocalDate fechaDev,
                    int idSocio, int idLibro) {
        this.numero = numero;
        this.fechaSal = fechaSal;
        this.fechaDev = fechaDev;
        this.idSocio = idSocio;
        this.idLibro = idLibro;
    }

    public int getNumero() {
    return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public java.time.LocalDate getFechaSal() {
        return fechaSal;
    }

    public void setFechaSal(java.time.LocalDate fechaSal) {
        this.fechaSal = fechaSal;
    }

    public java.time.LocalDate getFechaDev() {
        return fechaDev;
    }

    public void setFechaDev(java.time.LocalDate fechaDev) {
        this.fechaDev = fechaDev;
    }

    public int getIdSocio() {
        return idSocio;
    }

    public void setIdSocio(int idSocio) {
        this.idSocio = idSocio;
    }

    public int getIdLibro() {
        return idLibro;
    }

    public void setIdLibro(int idLibro) {
        this.idLibro = idLibro;
    }
}
