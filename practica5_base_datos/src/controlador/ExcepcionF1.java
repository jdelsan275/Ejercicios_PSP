package controlador;

public class ExcepcionF1 extends Exception {
    private final int codigoError;

    // Crea una excepción con código (para usuario) y mensaje (para log)
    public ExcepcionF1(int codigoError, String mensajeTecnico) {
        super(mensajeTecnico);
        this.codigoError = codigoError;
    }

    // Devuelve el código de error asociado para convertirlo en mensaje de usuario
    public int getCodigoError() {
        return codigoError;
    }
}
