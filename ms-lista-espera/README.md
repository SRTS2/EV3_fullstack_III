# ms-lista-espera - Microservicio de Lista de Espera

Gestion de pacientes en lista de espera con prioridades y tiempos estimados.

## Tecnologias

- Spring Boot 3.2.0
- Java 17
- Spring Data JPA
- Bean Validation

## Endpoints

| Metodo | Ruta | Descripcion |
|---|---|---|
| GET | /api/v1/espera | Listar todas las entradas |
| GET | /api/v1/espera/{id} | Obtener por ID |
| GET | /api/v1/espera/paciente/{pacienteId} | Obtener por paciente |
| GET | /api/v1/espera/especialidad/{especialidad} | Filtrar por especialidad |
| POST | /api/v1/espera | Registrar en espera |
| PUT | /api/v1/espera/{id} | Actualizar |
| PATCH | /api/v1/espera/{id}/estado | Actualizar estado |
| DELETE | /api/v1/espera/{id} | Eliminar |

## Tiempos estimados por prioridad

- URGENTE: 1 dia
- ALTA: 3 dias
- MEDIA: 7 dias
- BAJA: 15 dias

## Instalacion y ejecucion

```bash
mvn spring-boot:run
```

## Pruebas

```bash
mvn test
mvn jacoco:report
```
