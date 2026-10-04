package co.edu.corhuila.veterinaria.controller;

import co.edu.corhuila.veterinaria.entity.Mascota;
import co.edu.corhuila.veterinaria.service.MascotaService;
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
 * Controller: recibe las peticiones HTTP y devuelve el código de estado correcto.
 * URL con sustantivo en plural: /api/mascotas
 */
@RestController
@RequestMapping("/api/mascotas")
public class MascotaController {

    private final MascotaService mascotaService;

    public MascotaController(MascotaService mascotaService) {
        this.mascotaService = mascotaService;
    }

    // GET /api/mascotas            -> 200 + lista
    // GET /api/mascotas?especie=X  -> 200 + lista filtrada
    @GetMapping
    public ResponseEntity<List<Mascota>> listar(@RequestParam(required = false) String especie) {
        return ResponseEntity.ok(mascotaService.listar(especie));
    }

    // GET /api/mascotas/{id} -> 200 o 404
    @GetMapping("/{id}")
    public ResponseEntity<Mascota> obtener(@PathVariable Long id) {
        return ResponseEntity.ok(mascotaService.obtenerPorId(id));
    }

    // POST /api/mascotas -> 201 Created + header Location
    @PostMapping
    public ResponseEntity<Mascota> crear(@RequestBody Mascota mascota) {
        Mascota creada = mascotaService.crear(mascota);
        return ResponseEntity
                .created(URI.create("/api/mascotas/" + creada.getId()))
                .body(creada);
    }

    // PUT /api/mascotas/{id} -> 200 o 404
    @PutMapping("/{id}")
    public ResponseEntity<Mascota> actualizar(@PathVariable Long id, @RequestBody Mascota mascota) {
        return ResponseEntity.ok(mascotaService.actualizar(id, mascota));
    }

    // DELETE /api/mascotas/{id} -> 204 No Content o 404
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        mascotaService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
