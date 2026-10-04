package co.edu.corhuila.veterinaria.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * Entity Mascota: cada objeto es una fila de la tabla "mascotas".
 * Semana 9: se agregan validaciones (-> 400 Bad Request) y @Schema para Swagger.
 */
@Entity
@Table(name = "mascotas")
@Schema(description = "Mascota registrada en la veterinaria")
public class Mascota {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Id generado por la base de datos", example = "1",
            accessMode = Schema.AccessMode.READ_ONLY)
    private Long id;

    @NotBlank(message = "El nombre es obligatorio")
    @Size(max = 60, message = "El nombre admite máximo 60 caracteres")
    @Column(nullable = false, length = 60)
    @Schema(example = "Toby")
    private String nombre;

    @NotBlank(message = "La especie es obligatoria")
    @Size(max = 30, message = "La especie admite máximo 30 caracteres")
    @Column(nullable = false, length = 30)
    @Schema(example = "Perro")
    private String especie;

    @Size(max = 60, message = "La raza admite máximo 60 caracteres")
    @Column(length = 60)
    @Schema(example = "Beagle")
    private String raza;

    @Min(value = 0, message = "La edad no puede ser negativa")
    @Max(value = 40, message = "La edad no puede ser mayor a 40")
    @Schema(description = "Edad en años", example = "2")
    private Integer edad;

    @NotBlank(message = "El nombre del dueño es obligatorio")
    @Size(max = 100, message = "El nombre del dueño admite máximo 100 caracteres")
    @Column(name = "nombre_dueno", nullable = false, length = 100)
    @Schema(example = "Juan Diego Jiménez")
    private String nombreDueno;

    public Mascota() {
    }

    public Mascota(String nombre, String especie, String raza, Integer edad, String nombreDueno) {
        this.nombre = nombre;
        this.especie = especie;
        this.raza = raza;
        this.edad = edad;
        this.nombreDueno = nombreDueno;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEspecie() {
        return especie;
    }

    public void setEspecie(String especie) {
        this.especie = especie;
    }

    public String getRaza() {
        return raza;
    }

    public void setRaza(String raza) {
        this.raza = raza;
    }

    public Integer getEdad() {
        return edad;
    }

    public void setEdad(Integer edad) {
        this.edad = edad;
    }

    public String getNombreDueno() {
        return nombreDueno;
    }

    public void setNombreDueno(String nombreDueno) {
        this.nombreDueno = nombreDueno;
    }
}
