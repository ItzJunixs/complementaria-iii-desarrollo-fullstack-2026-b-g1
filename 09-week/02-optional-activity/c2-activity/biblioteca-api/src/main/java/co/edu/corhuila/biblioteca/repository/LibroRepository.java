package co.edu.corhuila.biblioteca.repository;

import co.edu.corhuila.biblioteca.entity.Libro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository: acceso a datos de la tabla "libros".
 * JpaRepository ya aporta save, findAll, findById, existsById y deleteById.
 */
@Repository
public interface LibroRepository extends JpaRepository<Libro, Long> {

    // SELECT * FROM libros WHERE LOWER(autor) LIKE LOWER('%texto%')
    List<Libro> findByAutorContainingIgnoreCase(String autor);

    // Para no repetir ISBN al crear
    boolean existsByIsbn(String isbn);

    // Para no repetir ISBN al actualizar (ignorando el mismo libro)
    boolean existsByIsbnAndIdNot(String isbn, Long id);
}
