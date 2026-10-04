package co.edu.corhuila.veterinaria.controller;

import co.edu.corhuila.veterinaria.entity.Mascota;
import co.edu.corhuila.veterinaria.service.MascotaService;
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
 * Controller documentado con anotaciones de OpenAPI (Swagger).
 */
@RestController
@RequestMapping("/api/mascotas")
@Tag(name = "Mascotas", description = "CRUD de mascotas de la veterinaria")
public class MascotaController {

    private final MascotaService mascotaService;

    public MascotaController(MascotaService mascotaService) {
        this.mascotaService = mascotaService;
    }

    @Operation(summary = "Listar mascotas", description = "Lista todas las mascotas o filtra por especie.")
    @ApiResponse(responseCode = "200", description = "Lista de mascotas")
    @GetMapping
    public ResponseEntity<List<Mascota>> listar(
            @Parameter(description = "Filtro opcional por especie", example = "Perro")
            @RequestParam(required = false) String especie) {
        return ResponseEntity.ok(mascotaService.listar(especie));
    }

    @Operation(summary = "Obtener una mascota por id")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Mascota encontrada"),
            @ApiResponse(responseCode = "400", description = "Id con formato inválido"),
            @ApiResponse(responseCode = "404", description = "La mascota no existe")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Mascota> obtener(@PathVariable Long id) {
        return ResponseEntity.ok(mascotaService.obtenerPorId(id));
    }

    @Operation(summary = "Crear una mascota")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Mascota creada"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos")
    })
    @PostMapping
    public ResponseEntity<Mascota> crear(@Valid @RequestBody Mascota mascota) {
        Mascota creada = mascotaService.crear(mascota);
        return ResponseEntity
                .created(URI.create("/api/mascotas/" + creada.getId()))
                .body(creada);
    }

    @Operation(summary = "Actualizar una mascota")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Mascota actualizada"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos"),
            @ApiResponse(responseCode = "404", description = "La mascota no existe")
    })
    @PutMapping("/{id}")
    public ResponseEntity<Mascota> actualizar(@PathVariable Long id, @Valid @RequestBody Mascota mascota) {
        return ResponseEntity.ok(mascotaService.actualizar(id, mascota));
    }

    @Operation(summary = "Eliminar una mascota")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Mascota eliminada"),
            @ApiResponse(responseCode = "404", description = "La mascota no existe")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        mascotaService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
