package co.edu.corhuila.biblioteca.service;

import co.edu.corhuila.biblioteca.dto.LibroRequest;
import co.edu.corhuila.biblioteca.dto.LibroResponse;
import co.edu.corhuila.biblioteca.entity.Libro;
import co.edu.corhuila.biblioteca.exception.ConflictoException;
import co.edu.corhuila.biblioteca.exception.RecursoNoEncontradoException;
import co.edu.corhuila.biblioteca.repository.LibroRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Service: reglas de negocio. No conoce HTTP; usa el repository y lanza excepciones.
 */
@Service
public class LibroService {

    private final LibroRepository libroRepository;

    public LibroService(LibroRepository libroRepository) {
        this.libroRepository = libroRepository;
    }

    @Transactional(readOnly = true)
    public List<LibroResponse> listar(String autor) {
        List<Libro> libros = (autor == null || autor.isBlank())
                ? libroRepository.findAll()
                : libroRepository.findByAutorContainingIgnoreCase(autor.trim());
        return libros.stream().map(LibroResponse::desde).toList();
    }

    @Transactional(readOnly = true)
    public LibroResponse obtener(Long id) {
        return LibroResponse.desde(buscarPorId(id));
    }

    @Transactional
    public LibroResponse crear(LibroRequest datos) {
        String isbn = datos.isbn().trim();
        if (libroRepository.existsByIsbn(isbn)) {
            throw new ConflictoException("Ya existe un libro con ISBN " + isbn);
        }
        Libro libro = new Libro();
        copiar(datos, libro);
        return LibroResponse.desde(libroRepository.save(libro));
    }

    @Transactional
    public LibroResponse actualizar(Long id, LibroRequest datos) {
        Libro libro = buscarPorId(id); // 404 si no existe
        String isbn = datos.isbn().trim();
        if (libroRepository.existsByIsbnAndIdNot(isbn, id)) {
            throw new ConflictoException("Ya existe otro libro con ISBN " + isbn);
        }
        copiar(datos, libro);
        return LibroResponse.desde(libroRepository.save(libro));
    }

    @Transactional
    public void eliminar(Long id) {
        if (!libroRepository.existsById(id)) {
            throw new RecursoNoEncontradoException("No existe un libro con id " + id);
        }
        libroRepository.deleteById(id);
    }

    private Libro buscarPorId(Long id) {
        return libroRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("No existe un libro con id " + id));
    }

    private void copiar(LibroRequest datos, Libro libro) {
        libro.setTitulo(datos.titulo().trim());
        libro.setAutor(datos.autor().trim());
        libro.setIsbn(datos.isbn().trim());
        libro.setAnioPublicacion(datos.anioPublicacion());
        libro.setDisponible(datos.disponible() == null ? Boolean.TRUE : datos.disponible());
    }
}
