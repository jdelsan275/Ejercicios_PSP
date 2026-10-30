package modelo;

import java.time.LocalDate;

public class Piloto {
    private int idPiloto;
    private int eqpIdEquipo;
    private String nombre;
    private String nacionalidad;
    private LocalDate fechaNac;
    private Integer numeroCoche;

    private int puntosObjetivo;
    private int puntosTotales;

    private Integer pilIdPilotoMentor;
    
    //  Tabla B (piloto) y sus campos de puntos/mentor

    public Piloto() {}

    public Piloto(int idPiloto, int eqpIdEquipo, String nombre, String nacionalidad,
                  LocalDate fechaNac, Integer numeroCoche,
                  int puntosObjetivo, int puntosTotales, Integer pilIdPilotoMentor) {
        this.idPiloto = idPiloto;
        this.eqpIdEquipo = eqpIdEquipo;
        this.nombre = nombre;
        this.nacionalidad = nacionalidad;
        this.fechaNac = fechaNac;
        this.numeroCoche = numeroCoche;
        this.puntosObjetivo = puntosObjetivo;
        this.puntosTotales = puntosTotales;
        this.pilIdPilotoMentor = pilIdPilotoMentor;
    }

    public int getIdPiloto() { return idPiloto; }
    public void setIdPiloto(int idPiloto) { this.idPiloto = idPiloto; }

    public int getEqpIdEquipo() { return eqpIdEquipo; }
    public void setEqpIdEquipo(int eqpIdEquipo) { this.eqpIdEquipo = eqpIdEquipo; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getNacionalidad() { return nacionalidad; }
    public void setNacionalidad(String nacionalidad) { this.nacionalidad = nacionalidad; }

    public LocalDate getFechaNac() { return fechaNac; }
    public void setFechaNac(LocalDate fechaNac) { this.fechaNac = fechaNac; }

    public Integer getNumeroCoche() { return numeroCoche; }
    public void setNumeroCoche(Integer numeroCoche) { this.numeroCoche = numeroCoche; }

    public int getPuntosObjetivo() { return puntosObjetivo; }
    public void setPuntosObjetivo(int puntosObjetivo) { this.puntosObjetivo = puntosObjetivo; }

    public int getPuntosTotales() { return puntosTotales; }
    public void setPuntosTotales(int puntosTotales) { this.puntosTotales = puntosTotales; }

    public Integer getPilIdPilotoMentor() { return pilIdPilotoMentor; }
    public void setPilIdPilotoMentor(Integer pilIdPilotoMentor) { this.pilIdPilotoMentor = pilIdPilotoMentor; }
}
