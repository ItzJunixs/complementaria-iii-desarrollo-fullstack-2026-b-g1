package co.edu.corhuila.veterinaria;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(info = @Info(
        title = "Veterinaria API",
        version = "1.0",
        description = "CRUD REST de mascotas - Desarrollo Fullstack CORHUILA 2026-B"))
public class VeterinariaApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(VeterinariaApiApplication.class, args);
    }
}
