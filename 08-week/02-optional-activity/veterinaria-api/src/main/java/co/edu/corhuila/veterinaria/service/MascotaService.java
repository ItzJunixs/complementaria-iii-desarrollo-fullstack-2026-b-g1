package co.edu.corhuila.veterinaria.service;

import co.edu.corhuila.veterinaria.entity.Mascota;
import co.edu.corhuila.veterinaria.exception.RecursoNoEncontradoException;
import co.edu.corhuila.veterinaria.repository.MascotaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service: reglas de negocio de las mascotas.
 * No sabe nada de HTTP; solo usa el repository y lanza excepciones.
 */
@Service
public class MascotaService {

    private final MascotaRepository mascotaRepository;

    // Inyección por constructor
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
        validar(mascota);
        mascota.setId(null); // el id siempre lo genera la base de datos
        return mascotaRepository.save(mascota);
    }

    public Mascota actualizar(Long id, Mascota datos) {
        validar(datos);
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

    // Reglas de negocio mínimas
    private void validar(Mascota mascota) {
        if (mascota.getNombre() == null || mascota.getNombre().isBlank()) {
            throw new IllegalArgumentException("El nombre es obligatorio");
        }
        if (mascota.getEspecie() == null || mascota.getEspecie().isBlank()) {
            throw new IllegalArgumentException("La especie es obligatoria");
        }
        if (mascota.getNombreDueno() == null || mascota.getNombreDueno().isBlank()) {
            throw new IllegalArgumentException("El nombre del dueño es obligatorio");
        }
        if (mascota.getEdad() != null && mascota.getEdad() < 0) {
            throw new IllegalArgumentException("La edad no puede ser negativa");
        }
    }
}
