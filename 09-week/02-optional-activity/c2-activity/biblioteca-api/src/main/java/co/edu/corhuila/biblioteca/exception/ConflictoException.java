package co.edu.corhuila.biblioteca.exception;

/** Se traduce a 409 Conflict (por ejemplo, ISBN repetido). */
public class ConflictoException extends RuntimeException {

    public ConflictoException(String mensaje) {
        super(mensaje);
    }
}
