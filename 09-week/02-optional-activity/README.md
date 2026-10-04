# Semana 9 - Documentar y probar la API

Actividad opcional de refuerzo.

Caso: **API de una veterinaria - mascotas**. Parte del CRUD de la semana 8
(`../../08-week/02-optional-activity/veterinaria-api/`) y le agrega documentación con Swagger,
validaciones y manejo de errores 400 / 404.

## Cambios respecto a la semana 8

| Archivo | Cambio |
| --- | --- |
| `pom.xml` | + `springdoc-openapi-starter-webmvc-ui` (Swagger) y `spring-boot-starter-validation`. |
| `application.properties` | Rutas de Swagger: `/swagger-ui.html` y `/v3/api-docs`. |
| `VeterinariaApiApplication.java` | `@OpenAPIDefinition` con título, versión y descripción de la API. |
| `entity/Mascota.java` | Validaciones `@NotBlank`, `@Size`, `@Min`, `@Max` + `@Schema` con ejemplos. |
| `controller/MascotaController.java` | `@Valid` en POST/PUT, `@Tag`, `@Operation` y `@ApiResponses` por endpoint. |
| `exception/ManejadorErrores.java` | 400 por validación (lista los campos con error), JSON mal formado e id inválido; 404 por recurso inexistente. |
| `service/MascotaService.java` | Se quita la validación manual (ahora la hace Bean Validation). |

## 1. Swagger

1. Abrir `veterinaria-api` en IntelliJ y ejecutar `VeterinariaApiApplication`
   (o `mvn spring-boot:run`).
2. Abrir **http://localhost:8080/swagger-ui.html** — redirige a `/swagger-ui/index.html` y muestra
   el grupo **Mascotas** con los 5 endpoints, sus parámetros, el esquema `Mascota` y los códigos
   de respuesta documentados.
3. La especificación OpenAPI en JSON queda en **http://localhost:8080/v3/api-docs**.

Desde Swagger también se puede probar cada endpoint con el botón **Try it out**.

## 2. Pruebas con Postman

Colección: `pruebas/veterinaria-semana09.postman_collection.json`
(Postman → **Import** → elegir el archivo → **Run collection**).
Variable `baseUrl = http://localhost:8080`. Cada petición tiene *tests* que comprueban el código.

Se prueban **3 endpoints** (POST, GET lista y GET por id) y **2 casos de error** (404 y 400):

| # | Petición | Body / dato | Código esperado |
| --- | --- | --- | --- |
| 1 | `POST /api/mascotas` | Mascota válida ("Toby") | **201 Created** |
| 2 | `GET /api/mascotas` | — | **200 OK** |
| 3 | `GET /api/mascotas/{id}` | id de la mascota creada | **200 OK** |
| 4 | `GET /api/mascotas/9999` | id que no existe | **404 Not Found** |
| 5 | `POST /api/mascotas` | `nombre` vacío, `edad: -2`, sin dueño | **400 Bad Request** |

Respuesta esperada del caso 4 (404):

```json
{
  "fecha": "2026-10-05T06:30:00",
  "estado": 404,
  "error": "Not Found",
  "mensaje": "No existe una mascota con id 9999"
}
```

Respuesta esperada del caso 5 (400):

```json
{
  "fecha": "2026-10-05T06:31:00",
  "estado": 400,
  "error": "Bad Request",
  "mensaje": "Datos inválidos",
  "campos": {
    "nombre": "El nombre es obligatorio",
    "edad": "La edad no puede ser negativa",
    "nombreDueno": "El nombre del dueño es obligatorio"
  }
}
```

(El orden de los campos puede variar.)

## 3. Reporte de códigos de estado

| Código | Dónde apareció | Interpretación |
| --- | --- | --- |
| **200 OK** | GET lista y GET por id | La petición se procesó bien y la respuesta trae los datos en el body. |
| **201 Created** | POST válido | Se creó un recurso nuevo. La respuesta trae la mascota con su `id` y el header `Location: /api/mascotas/{id}` indica dónde consultarla. |
| **400 Bad Request** | POST con datos inválidos | El error es **del cliente**: el body no cumple las reglas (`@NotBlank`, `@Min`). La API no guarda nada y explica qué campo corregir. |
| **404 Not Found** | GET de un id inexistente | La URL es correcta, pero **el recurso no existe**. Lo decide el service al no encontrar la mascota con `findById`. |

Otros códigos que la API puede devolver: **204 No Content** al eliminar (éxito sin body) y
**500 Internal Server Error** si falla algo inesperado en el servidor (no debería ocurrir en
estas pruebas).

**Conclusión:** los códigos 2xx indican éxito y los 4xx indican que el cliente debe corregir la
petición. Diferenciar 400 (datos mal enviados) de 404 (recurso inexistente) permite que el
frontend muestre el mensaje correcto al usuario.

## Evidencia

Capturas en `evidencias/` (ver `evidencias/README.md`).
