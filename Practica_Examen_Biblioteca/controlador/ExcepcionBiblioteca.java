package controlador;

public class ExcepcionBiblioteca extends Exception {
    private final int codigoError;

    public ExcepcionBiblioteca(int codigoError, String mensajeTecnico) {
        super(mensajeTecnico);
        this.codigoError = codigoError;
    }

    public int getCodigoError() {
        return codigoError;
    }
}
