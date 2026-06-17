# ms-reasignacion - Microservicio de Reasignacion de Citas

Gestion de reasignacion de citas medicas con estados y cancelacion.

## Tecnologias

- Spring Boot 3.2.0
- Java 17
- Spring Data JPA
- Bean Validation

## Endpoints

| Metodo | Ruta | Descripcion |
|---|---|---|
| GET | /api/v1/reasignaciones | Listar todas |
| GET | /api/v1/reasignaciones/{id} | Obtener por ID |
| POST | /api/v1/reasignaciones | Ejecutar reasignacion |
| PUT | /api/v1/reasignaciones/{id} | Actualizar |
| PUT | /api/v1/reasignaciones/{id}/confirmar | Confirmar reasignacion |
| PUT | /api/v1/reasignaciones/{id}/cancelar | Cancelar reasignacion |
| GET | /api/v1/reasignaciones/historial/paciente/{pacienteId} | Historial por paciente |
| GET | /api/v1/reasignaciones/disponibles | Listar disponibles (PENDIENTE) |
| DELETE | /api/v1/reasignaciones/{id} | Eliminar |

## Estados

- PENDIENTE: Reasignacion creada, pendiente de confirmacion
- CONFIRMADA: Reasignacion aceptada
- CANCELADA: Reasignacion cancelada (requiere motivo)

## Instalacion y ejecucion

```bash
mvn spring-boot:run
```

## Pruebas

```bash
mvn test
mvn jacoco:report
```
