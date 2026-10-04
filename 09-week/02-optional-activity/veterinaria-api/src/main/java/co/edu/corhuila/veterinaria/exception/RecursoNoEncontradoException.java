package co.edu.corhuila.veterinaria.exception;

/**
 * Se lanza desde el service cuando no existe el registro buscado.
 * El ManejadorErrores la convierte en una respuesta 404 Not Found.
 */
public class RecursoNoEncontradoException extends RuntimeException {

    public RecursoNoEncontradoException(String mensaje) {
        super(mensaje);
    }
}
