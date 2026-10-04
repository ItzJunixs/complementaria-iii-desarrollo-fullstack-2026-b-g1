package co.edu.corhuila.veterinaria.service;

import co.edu.corhuila.veterinaria.entity.Mascota;
import co.edu.corhuila.veterinaria.exception.RecursoNoEncontradoException;
import co.edu.corhuila.veterinaria.repository.MascotaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service: reglas de negocio de las mascotas.
 * Semana 9: la validación de campos pasó a la entity (@NotBlank, @Min) con @Valid en el controller.
 */
@Service
public class MascotaService {

    private final MascotaRepository mascotaRepository;

    public MascotaService(MascotaRepository mascotaRepository) {
        this.mascotaRepository = mascotaRepository;
    }

    public List<Mascota> listar(String especie) {
        if (especie == null || especie.isBlank()) {
            return mascotaRepository.findAll();
        }
        return mascotaRepository.findByEspecieIgnoreCase(especie.trim());
    }

    public Mascota obtenerPorId(Long id) {
        return mascotaRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException(
                        "No existe una mascota con id " + id));
    }

    public Mascota crear(Mascota mascota) {
        mascota.setId(null); // el id siempre lo genera la base de datos
        return mascotaRepository.save(mascota);
    }

    public Mascota actualizar(Long id, Mascota datos) {
        Mascota existente = obtenerPorId(id); // 404 si no existe
        existente.setNombre(datos.getNombre());
        existente.setEspecie(datos.getEspecie());
        existente.setRaza(datos.getRaza());
        existente.setEdad(datos.getEdad());
        existente.setNombreDueno(datos.getNombreDueno());
        return mascotaRepository.save(existente);
    }

    public void eliminar(Long id) {
        if (!mascotaRepository.existsById(id)) {
            throw new RecursoNoEncontradoException("No existe una mascota con id " + id);
        }
        mascotaRepository.deleteById(id);
    }
}
