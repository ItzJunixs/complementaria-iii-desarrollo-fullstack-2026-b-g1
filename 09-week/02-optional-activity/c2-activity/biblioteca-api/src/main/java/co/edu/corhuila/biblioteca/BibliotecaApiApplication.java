package co.edu.corhuila.biblioteca;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(info = @Info(
        title = "Biblioteca API",
        version = "1.0.0",
        description = "API REST para gestionar los libros de una biblioteca. "
                + "Actividad calificable Corte 2 - Desarrollo Fullstack CORHUILA 2026-B.",
        contact = @Contact(name = "Juan Diego Jiménez Horta")))
public class BibliotecaApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(BibliotecaApiApplication.class, args);
    }
}
