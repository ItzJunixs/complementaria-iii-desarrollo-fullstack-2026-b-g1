package co.edu.corhuila.biblioteca.controller;

import co.edu.corhuila.biblioteca.dto.LibroRequest;
import co.edu.corhuila.biblioteca.dto.LibroResponse;
import co.edu.corhuila.biblioteca.service.LibroService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.List;

/**
 * Controller: expone el recurso /api/libros y devuelve el código HTTP adecuado.
 */
@RestController
@RequestMapping("/api/libros")
@Tag(name = "Libros", description = "CRUD de libros de la biblioteca")
public class LibroController {

    private final LibroService libroService;

    public LibroController(LibroService libroService) {
        this.libroService = libroService;
    }

    @Operation(summary = "Listar libros", description = "Devuelve todos los libros; opcionalmente filtra por autor.")
    @ApiResponse(responseCode = "200", description = "Lista de libros")
    @GetMapping
    public ResponseEntity<List<LibroResponse>> listar(
            @Parameter(description = "Filtro opcional: parte del nombre del autor", example = "garcía")
            @RequestParam(required = false) String autor) {
        return ResponseEntity.ok(libroService.listar(autor));
    }

    @Operation(summary = "Obtener un libro por id")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Libro encontrado"),
            @ApiResponse(responseCode = "400", description = "Id con formato inválido"),
            @ApiResponse(responseCode = "404", description = "El libro no existe")
    })
    @GetMapping("/{id}")
    public ResponseEntity<LibroResponse> obtener(@PathVariable Long id) {
        return ResponseEntity.ok(libroService.obtener(id));
    }

    @Operation(summary = "Crear un libro")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Libro creado"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos"),
            @ApiResponse(responseCode = "409", description = "El ISBN ya existe")
    })
    @PostMapping
    public ResponseEntity<LibroResponse> crear(@Valid @RequestBody LibroRequest datos) {
        LibroResponse creado = libroService.crear(datos);
        return ResponseEntity.created(URI.create("/api/libros/" + creado.id())).body(creado);
    }

    @Operation(summary = "Actualizar un libro")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Libro actualizado"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos"),
            @ApiResponse(responseCode = "404", description = "El libro no existe"),
            @ApiResponse(responseCode = "409", description = "El ISBN pertenece a otro libro")
    })
    @PutMapping("/{id}")
    public ResponseEntity<LibroResponse> actualizar(@PathVariable Long id,
                                                    @Valid @RequestBody LibroRequest datos) {
        return ResponseEntity.ok(libroService.actualizar(id, datos));
    }

    @Operation(summary = "Eliminar un libro")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Libro eliminado"),
            @ApiResponse(responseCode = "404", description = "El libro no existe")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        libroService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
