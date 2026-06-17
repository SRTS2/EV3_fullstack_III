# Descripcion de la Persistencia de Datos - RedNorte

## Estrategia General

Cada microservicio gestiona su propia base de datos independiente, siguiendo el patron **Database per Service**. Esto garantiza el aislamiento de datos entre servicios y evita acoplamiento.

## Tecnologia

- **ORM**: Spring Data JPA (Hibernate)
- **Base de Datos**: PostgreSQL 15 (produccion), H2 in-memory (desarrollo local)
- **DDL**: `ddl-auto: update` - Hibernate genera y actualiza las tablas automaticamente
- **Driver**: PostgreSQL JDBC Driver

## Esquema de Bases de Datos

### 1. auth_db (ms-auth)

**Tabla: `usuarios`**

| Columna | Tipo | Restricciones |
|---|---|---|
| id | BIGINT | PK, AUTO_INCREMENT |
| rut | VARCHAR(255) | UNIQUE, NOT NULL |
| nombre | VARCHAR(255) | NOT NULL |
| email | VARCHAR(255) | |
| contrasena | VARCHAR(255) | NOT NULL (BCrypt) |
| role | VARCHAR(255) | NOT NULL, DEFAULT 'PACIENTE' |
| activo | BOOLEAN | NOT NULL, DEFAULT TRUE |
| paciente_id | BIGINT | |

### 2. pacientes (ms-pacientes)

**Tabla: `pacientes`**

| Columna | Tipo | Restricciones |
|---|---|---|
| id | BIGINT | PK, AUTO_INCREMENT |
| rut | VARCHAR(255) | UNIQUE, NOT NULL |
| nombre | VARCHAR(255) | NOT NULL |
| email | VARCHAR(255) | NOT NULL |
| telefono | VARCHAR(255) | |
| direccion | VARCHAR(255) | |
| fecha_nacimiento | DATE | |
| contrasena | VARCHAR(255) | NOT NULL (BCrypt) |
| role | VARCHAR(255) | NOT NULL, DEFAULT 'PACIENTE' |
| activo | BOOLEAN | NOT NULL, DEFAULT TRUE |

### 3. lista_espera (ms-lista-espera)

**Tabla: `pacientes_espera`**

| Columna | Tipo | Restricciones |
|---|---|---|
| id | BIGINT | PK, AUTO_INCREMENT |
| paciente_id | BIGINT | |
| nombre_paciente | VARCHAR(255) | |
| rut_paciente | VARCHAR(255) | |
| especialidad | VARCHAR(255) | |
| prioridad | VARCHAR(255) | ENUM(BAJA, MEDIA, ALTA, URGENTE) |
| estado | VARCHAR(255) | ENUM(EN_ESPERA, ASIGNADO, CANCELADO) |
| fecha_ingreso | TIMESTAMP | |
| tiempo_estimado_dias | INT | |
| observaciones | VARCHAR(500) | |

### 4. reasignacion (ms-reasignacion)

**Tabla: `reasignaciones`**

| Columna | Tipo | Restricciones |
|---|---|---|
| id | BIGINT | PK, AUTO_INCREMENT |
| paciente_id | BIGINT | |
| nombre_paciente | VARCHAR(255) | |
| especialidad | VARCHAR(255) | |
| fecha_original | TIMESTAMP | |
| fecha_reasignada | TIMESTAMP | |
| estado | VARCHAR(255) | ENUM(PENDIENTE, CONFIRMADA, CANCELADA) |
| motivo_cancelacion | VARCHAR(255) | |

## Perfiles de Ejecucion

### Desarrollo (default)
- H2 en memoria por cada microservicio
- `ddl-auto: update`
- `show-sql: true`
- Sin dependencia externa, ideal para desarrollo local

### Produccion (docker)
- PostgreSQL con credenciales via variables de entorno
- Cada microservicio conecta a su propia BD
- `ddl-auto: update` (puede cambiarse a `validate` en produccion real)

## Inicializacion

El archivo `init-db.sql` crea las bases de datos al iniciar PostgreSQL por primera vez:

```sql
CREATE ROLE rednorte LOGIN PASSWORD 'rednorte_pass_2024';
CREATE DATABASE auth_db OWNER rednorte;
CREATE DATABASE lista_espera OWNER rednorte;
CREATE DATABASE reasignacion OWNER rednorte;
CREATE DATABASE pacientes OWNER rednorte;
```

Luego cada microservicio crea automaticamente sus tablas via `ddl-auto: update`.
