package co.edu.corhuila.biblioteca.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

/**
 * Datos que el cliente envía en POST y PUT.
 * Las validaciones se revisan con @Valid en el controller; si fallan -> 400 Bad Request.
 */
@Schema(description = "Datos para crear o actualizar un libro")
public record LibroRequest(

        @Schema(example = "Cien años de soledad")
        @NotBlank(message = "El título es obligatorio")
        @Size(max = 150, message = "El título admite máximo 150 caracteres")
        String titulo,

        @Schema(example = "Gabriel García Márquez")
        @NotBlank(message = "El autor es obligatorio")
        @Size(max = 100, message = "El autor admite máximo 100 caracteres")
        String autor,

        @Schema(example = "978-0307474728", description = "ISBN de 10 o 13 dígitos, se permiten guiones")
        @NotBlank(message = "El ISBN es obligatorio")
        @Pattern(regexp = "^[0-9-]{10,17}$", message = "El ISBN solo admite dígitos y guiones (10 a 17 caracteres)")
        String isbn,

        @Schema(example = "1967")
        @Min(value = 1450, message = "El año de publicación no puede ser menor a 1450")
        @Max(value = 2100, message = "El año de publicación no es válido")
        Integer anioPublicacion,

        @Schema(example = "true", description = "Si no se envía, se asume true")
        Boolean disponible
) {
}
