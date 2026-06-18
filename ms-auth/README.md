# ms-auth - Microservicio de Autenticacion

Microservicio de autenticacion y registro de usuarios con JWT y BCrypt.

## Tecnologias

- Spring Boot 3.2.0
- Java 17
- Spring Data JPA
- Spring Security Crypto (BCrypt)
- JWT (jjwt 0.12.5)

## Endpoints

| Metodo | Ruta | Descripcion |
|---|---|---|
| POST | /api/auth/register | Registrar nuevo usuario |
| POST | /api/auth/login | Iniciar sesion (retorna JWT) |
| GET | /api/auth/usuarios/{id} | Obtener usuario por ID |

## Entidad

`Usuario`: id, rut (unique), nombre, email, contrasena (BCrypt), role, activo, pacienteId

## Instalacion y ejecucion

```bash
mvn spring-boot:run
```

## Configuracion

- `JWT_SECRET`: Secreto para firmar JWT (debe coincidir con BFF y Kong)
