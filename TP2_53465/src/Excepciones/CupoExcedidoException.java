package Excepciones;

public class CupoExcedidoException extends Exception {

    public CupoExcedidoException(String mensaje) {
        // super(mensaje) guarda el mensaje en el objeto Throwable para que
        // luego podamos recuperarlo con e.getMessage() en el catch.
        super(mensaje);
    }
}
