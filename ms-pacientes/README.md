# ms-pacientes - Microservicio de Pacientes

CRUD completo de pacientes con persistencia JPA y validacion de datos.

## Tecnologias

- Spring Boot 3.2.0
- Java 17
- Spring Data JPA
- Bean Validation
- Spring Security Crypto (BCrypt)

## Endpoints

| Metodo | Ruta | Descripcion |
|---|---|---|
| POST | /api/v1/pacientes | Registrar paciente |
| GET | /api/v1/pacientes | Listar todos |
| GET | /api/v1/pacientes/{id} | Obtener por ID |
| GET | /api/v1/pacientes/rut/{rut} | Obtener por RUT |
| PUT | /api/v1/pacientes/{id} | Actualizar |
| PATCH | /api/v1/pacientes/{id} | Actualizacion parcial |
| DELETE | /api/v1/pacientes/{id} | Desactivar (soft-delete) |

## Entidad

`Paciente`: id, rut (unique), nombre, email, telefono, direccion, fechaNacimiento, contrasena (BCrypt), role, activo

## Instalacion y ejecucion

```bash
mvn spring-boot:run
```

## Pruebas

```bash
mvn test
mvn jacoco:report
```
