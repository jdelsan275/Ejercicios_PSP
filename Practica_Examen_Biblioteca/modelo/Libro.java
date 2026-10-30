package modelo;

public class Libro implements Disponible {
    private int idLibro;
    private String titulo;
    private String autor;
    private String editorial;
    private int anioPublicacion;
    private int ejemplaresTotales;
    private int ejemplaresPrestados;
    private String isbn;

    public Libro() { }

    public Libro(int idLibro, String titulo, String autor, String editorial,
                 int anioPublicacion, int ejemplaresTotales, int ejemplaresPrestados, String isbn) {
        this.idLibro = idLibro;
        this.titulo = titulo;
        this.autor = autor;
        this.editorial = editorial;
        this.anioPublicacion = anioPublicacion;
        this.ejemplaresTotales = ejemplaresTotales;
        this.ejemplaresPrestados = ejemplaresPrestados;
        this.isbn = isbn;
    }

    public int getIdLibro() {
    return idLibro;
    }

    public void setIdLibro(int idLibro) {
        this.idLibro = idLibro;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public String getEditorial() {
        return editorial;
    }

    public void setEditorial(String editorial) {
        this.editorial = editorial;
    }

    public int getAnioPublicacion() {
        return anioPublicacion;
    }

    public void setAnioPublicacion(int anioPublicacion) {
        this.anioPublicacion = anioPublicacion;
    }

    public int getEjemplaresTotales() {
        return ejemplaresTotales;
    }

    public void setEjemplaresTotales(int ejemplaresTotales) {
        this.ejemplaresTotales = ejemplaresTotales;
    }

    public int getEjemplaresPrestados() {
        return ejemplaresPrestados;
    }

    public void setEjemplaresPrestados(int ejemplaresPrestados) {
        this.ejemplaresPrestados = ejemplaresPrestados;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    @Override
    public boolean estaDisponible() {
        // true si hay ejemplares libres
        return ejemplaresTotales > ejemplaresPrestados;
    }

    // Resto de getters y setters omitidos por brevedad
}
