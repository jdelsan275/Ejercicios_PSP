package modelo;

import java.time.LocalDate;

public class Resultado {
    private int idResultado;
    private int pilIdPiloto;
    private LocalDate fechaCarrera;
    private String granPremio;
    private int posicion;
    private int puntosObtenidos;
    
    // Tabla C (resultado) asociada a un piloto

    public Resultado() {}

    public Resultado(int idResultado, int pilIdPiloto, LocalDate fechaCarrera,
                     String granPremio, int posicion, int puntosObtenidos) {
        this.idResultado = idResultado;
        this.pilIdPiloto = pilIdPiloto;
        this.fechaCarrera = fechaCarrera;
        this.granPremio = granPremio;
        this.posicion = posicion;
        this.puntosObtenidos = puntosObtenidos;
    }

    public int getIdResultado() { return idResultado; }
    public void setIdResultado(int idResultado) { this.idResultado = idResultado; }

    public int getPilIdPiloto() { return pilIdPiloto; }
    public void setPilIdPiloto(int pilIdPiloto) { this.pilIdPiloto = pilIdPiloto; }

    public LocalDate getFechaCarrera() { return fechaCarrera; }
    public void setFechaCarrera(LocalDate fechaCarrera) { this.fechaCarrera = fechaCarrera; }

    public String getGranPremio() { return granPremio; }
    public void setGranPremio(String granPremio) { this.granPremio = granPremio; }

    public int getPosicion() { return posicion; }
    public void setPosicion(int posicion) { this.posicion = posicion; }

    public int getPuntosObtenidos() { return puntosObtenidos; }
    public void setPuntosObtenidos(int puntosObtenidos) { this.puntosObtenidos = puntosObtenidos; }

    @Override
    public String toString() {
        return fechaCarrera + " | " + granPremio + " | Pos " + posicion + " | " + puntosObtenidos + " pts (ID " + idResultado + ")";
    }
}
