package co.edu.corhuila.biblioteca.dto;

import co.edu.corhuila.biblioteca.entity.Libro;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Datos que la API devuelve al cliente (no se expone la entity directamente).
 */
@Schema(description = "Libro registrado en la biblioteca")
public record LibroResponse(
        @Schema(example = "1") Long id,
        @Schema(example = "Cien años de soledad") String titulo,
        @Schema(example = "Gabriel García Márquez") String autor,
        @Schema(example = "978-0307474728") String isbn,
        @Schema(example = "1967") Integer anioPublicacion,
        @Schema(example = "true") Boolean disponible
) {

    public static LibroResponse desde(Libro libro) {
        return new LibroResponse(
                libro.getId(),
                libro.getTitulo(),
                libro.getAutor(),
                libro.getIsbn(),
                libro.getAnioPublicacion(),
                libro.getDisponible());
    }
}
