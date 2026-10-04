package co.edu.corhuila.veterinaria.repository;

import co.edu.corhuila.veterinaria.entity.Mascota;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository de Mascota.
 * Al extender JpaRepository<Mascota, Long> ya tenemos el CRUD completo:
 * save, findAll, findById, existsById, deleteById, count...
 */
@Repository
public interface MascotaRepository extends JpaRepository<Mascota, Long> {

    // Consulta por nombre de método (query method):
    // Spring genera -> SELECT * FROM mascotas WHERE LOWER(especie) = LOWER(?)
    List<Mascota> findByEspecieIgnoreCase(String especie);

    // Otra consulta derivada: mascotas de un dueño, buscando por parte del nombre
    // -> SELECT * FROM mascotas WHERE LOWER(nombre_dueno) LIKE LOWER('%?%')
    List<Mascota> findByNombreDuenoContainingIgnoreCase(String nombreDueno);
}
