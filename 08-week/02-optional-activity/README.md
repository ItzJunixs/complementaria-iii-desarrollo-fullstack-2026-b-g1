# Semana 8 - CRUD REST

Actividad opcional de refuerzo.

Caso: **API de una veterinaria - mascotas**. Implementa el diseño de la semana 6
(`../../06-week/02-optional-activity/`) usando la entity y el repository de la semana 7
(`../../07-week/02-optional-activity/`).

## Tecnologías

- Java 17+ · Spring Boot 3.5 · Maven
- Spring Web (API REST) · Spring Data JPA · H2 (base de datos en memoria)

## Estructura (capas)

```text
veterinaria-api/
├── pom.xml
└── src/main/
    ├── java/co/edu/corhuila/veterinaria/
    │   ├── VeterinariaApiApplication.java      # clase principal
    │   ├── controller/MascotaController.java   # HTTP: rutas y códigos de estado
    │   ├── service/MascotaService.java         # reglas de negocio
    │   ├── repository/MascotaRepository.java   # JpaRepository + consulta por método
    │   ├── entity/Mascota.java                 # @Entity -> tabla mascotas
    │   └── exception/
    │       ├── RecursoNoEncontradoException.java
    │       └── ManejadorErrores.java           # @RestControllerAdvice -> 404 / 400
    └── resources/
        ├── application.properties              # H2 en memoria
        └── data.sql                            # 3 mascotas iniciales (ids 1, 2, 3)
```

Flujo: `Controller → Service → Repository → Entity/BD`. El controller no toca el repository y
el service no conoce HTTP.

## Endpoints

URLs con **sustantivo en plural** (`/mascotas`) y el **método HTTP** indica la acción:

| Acción | Método | URL | Body | Respuesta OK | Error |
| --- | --- | --- | --- | --- | --- |
| Crear | `POST` | `/api/mascotas` | JSON mascota | **201 Created** + `Location` | 400 |
| Listar | `GET` | `/api/mascotas` | — | **200 OK** | — |
| Listar por especie | `GET` | `/api/mascotas?especie=perro` | — | **200 OK** | — |
| Obtener | `GET` | `/api/mascotas/{id}` | — | **200 OK** | 404 |
| Actualizar | `PUT` | `/api/mascotas/{id}` | JSON mascota | **200 OK** | 404 / 400 |
| Borrar | `DELETE` | `/api/mascotas/{id}` | — | **204 No Content** | 404 |

Ejemplo de body:

```json
{
  "nombre": "Toby",
  "especie": "Perro",
  "raza": "Beagle",
  "edad": 2,
  "nombreDueno": "Juan Diego Jiménez"
}
```

## Cómo ejecutar

1. Abrir la carpeta `veterinaria-api` en IntelliJ IDEA (abre el `pom.xml` como proyecto Maven).
2. Ejecutar `VeterinariaApiApplication`.
   - O por consola: `mvn spring-boot:run` dentro de `veterinaria-api/`.
3. La API queda en `http://localhost:8080/api/mascotas`.
4. Consola de la base de datos: `http://localhost:8080/h2-console`
   (JDBC URL `jdbc:h2:mem:veterinaria`, usuario `sa`, sin contraseña).

## Pruebas

En `pruebas/`:

- `veterinaria-crud.postman_collection.json` — colección de Postman con el CRUD completo en orden
  (crear → listar → listar por especie → obtener → actualizar → eliminar → obtener eliminada = 404).
  Cada petición tiene *tests* que validan el código de estado y guarda el `id` creado en la
  variable `mascotaId`.
  - Importar en Postman → **Run collection** para ejecutarlas todas.
- `mascotas.http` — las mismas peticiones para el cliente HTTP de IntelliJ.

| # | Petición | Esperado |
| --- | --- | --- |
| 1 | `POST /api/mascotas` | 201 |
| 2 | `GET /api/mascotas` | 200 |
| 3 | `GET /api/mascotas?especie=perro` | 200 |
| 4 | `GET /api/mascotas/{id}` | 200 |
| 5 | `PUT /api/mascotas/{id}` | 200 |
| 6 | `DELETE /api/mascotas/{id}` | 204 |
| 7 | `GET /api/mascotas/{id}` (ya borrada) | 404 |

## Evidencia

Capturas de la ejecución en `evidencias/` (ver `evidencias/README.md`).
