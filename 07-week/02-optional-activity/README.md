# Semana 7 - Entity y repository (JPA)

Actividad opcional de refuerzo.

Caso: **API de una veterinaria - mascotas** (el mismo diseñado en `../../06-week/02-optional-activity/`).

## Archivos

| Archivo | Qué contiene |
| --- | --- |
| `entity/Mascota.java` | Entity mapeada a la tabla `mascotas`. |
| `repository/MascotaRepository.java` | Repository que extiende `JpaRepository` + consultas por método. |

Estos archivos se integran en el proyecto completo de la semana 8
(`../../08-week/02-optional-activity/veterinaria-api/`).

## 1. Entity `Mascota`

| Atributo Java | Anotaciones | Columna en la tabla `mascotas` |
| --- | --- | --- |
| `Long id` | `@Id` `@GeneratedValue(strategy = IDENTITY)` | `id` (PK, autoincremental) |
| `String nombre` | `@Column(nullable = false, length = 60)` | `nombre` |
| `String especie` | `@Column(nullable = false, length = 30)` | `especie` |
| `String raza` | `@Column(length = 60)` | `raza` |
| `Integer edad` | — | `edad` |
| `String nombreDueno` | `@Column(name = "nombre_dueno", nullable = false, length = 100)` | `nombre_dueno` |

- `@Entity` le dice a JPA que esta clase es una tabla.
- `@Table(name = "mascotas")` define el nombre de la tabla.
- `@Id` marca la llave primaria y `@GeneratedValue` deja que la base de datos genere el valor.
- JPA exige un **constructor vacío**; también se agregan getters y setters.

Tabla resultante (aproximada):

```sql
CREATE TABLE mascotas (
    id           BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre       VARCHAR(60)  NOT NULL,
    especie      VARCHAR(30)  NOT NULL,
    raza         VARCHAR(60),
    edad         INTEGER,
    nombre_dueno VARCHAR(100) NOT NULL
);
```

## 2. Repository `MascotaRepository`

```java
public interface MascotaRepository extends JpaRepository<Mascota, Long> {
    List<Mascota> findByEspecieIgnoreCase(String especie);
    List<Mascota> findByNombreDuenoContainingIgnoreCase(String nombreDueno);
}
```

- `JpaRepository<Mascota, Long>`: el primer tipo es la entity, el segundo el tipo de su `@Id`.
- No hay que implementar nada: Spring Data crea la implementación en tiempo de ejecución.

**Consulta por método:** `findByEspecieIgnoreCase("perro")`. Spring lee el nombre del método
(`findBy` + `Especie` + `IgnoreCase`) y genera la consulta:

```sql
SELECT * FROM mascotas WHERE LOWER(especie) = LOWER('perro');
```

Sirve para filtrar el listado, por ejemplo `GET /api/mascotas?especie=Perro`.

## 3. Operaciones CRUD que usaría y para qué

| CRUD | Método del repository | Para qué en la veterinaria |
| --- | --- | --- |
| **Create** | `save(mascota)` (sin id) | Registrar una mascota nueva cuando llega por primera vez a la clínica. |
| **Read** (todas) | `findAll()` | Mostrar el listado de mascotas registradas. |
| **Read** (una) | `findById(id)` | Ver la ficha de una mascota antes de atenderla. Devuelve `Optional`, así se puede responder 404 si no existe. |
| **Read** (filtro) | `findByEspecieIgnoreCase(especie)` | Ver solo perros, solo gatos, etc. |
| **Update** | `findById(id)` + `save(mascota)` (con id) | Corregir datos o actualizar la edad / el dueño. `save` hace `UPDATE` cuando la entity ya tiene id. |
| **Delete** | `existsById(id)` + `deleteById(id)` | Dar de baja una mascota registrada por error. Primero se verifica que exista. |
