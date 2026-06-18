# BFF RedNorte - Backend for Frontend

Microservicio BFF que agrega y orquesta las peticiones a los microservicios internos.

## Tecnologias

- Spring Boot 3.2.5
- Java 17
- Spring Cloud OpenFeign
- Spring Security + JWT
- Resilience4j (Circuit Breaker)

## Endpoints

| Metodo | Ruta | Descripcion |
|---|---|---|
| GET | /api/v1/dashboard/paciente/{id} | Dashboard agregado de paciente |
| GET | /api/v1/espera | Listar pacientes en espera |
| POST | /api/v1/espera | Registrar en lista de espera |
| GET | /api/v1/pacientes | Listar pacientes |
| GET | /api/v1/pacientes/{id} | Obtener paciente por ID |
| POST | /api/v1/pacientes | Crear paciente |
| PUT | /api/v1/pacientes/{id} | Actualizar paciente |
| GET | /api/v1/reasignaciones/historial/paciente/{id} | Historial de reasignaciones |
| POST | /api/v1/reasignaciones | Ejecutar reasignacion |

## Instalacion y ejecucion

```bash
mvn spring-boot:run -Dspring-boot.run.profiles=default
```

## Configuracion

Variables de entorno:

- `JWT_SECRET`: Secreto para firmar JWT
- `MS_AUTH_URL`: URL de ms-auth
- `MS_PACIENTES_URL`: URL de ms-pacientes
- `MS_LISTA_ESPERA_URL`: URL de ms-lista-espera
- `MS_REASIGNACION_URL`: URL de ms-reasignacion

## Pruebas

```bash
mvn test
mvn jacoco:report  # Reporte de cobertura en target/site/jacoco/
```
