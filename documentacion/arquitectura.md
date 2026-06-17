# Diagrama de Arquitectura - RedNorte

## Esquema General

```mermaid
graph TB
    subgraph "Cliente"
        USR[Usuario Navegador]
    end

    subgraph "API Gateway :8000"
        KONG[Kong API Gateway]
        KONG_RT{{"Ruteo:\n/api/auth -> ms-auth\n/api/* -> BFF"}}
        KONG_JWT{{"Plugin JWT:\nValida token en /api/*"}}
    end

    subgraph "Frontend :3000"
        NGINX[Nginx Proxy]
        NEXT[Next.js App]
    end

    subgraph "Capa BFF :8080"
        BFF[BFF - Backend for Frontend]
        BFF_SEC[Spring Security + JWT Filter]
        BFF_FEIGN[Feign Clients]
        BFF_CB[Resilience4j Circuit Breaker]
        BFF_DASH[DashboardService - Async Aggregation]
    end

    subgraph "Microservicios"
        AUTH[ms-auth :8084]
        PAC[ms-pacientes :8083]
        LET[ms-lista-espera :8081]
        REA[ms-reasignacion :8082]
    end

    subgraph "Persistencia"
        PG_AUTH[(PostgreSQL\nauth_db)]
        PG_PAC[(PostgreSQL\npacientes)]
        PG_LET[(PostgreSQL\nlista_espera)]
        PG_REA[(PostgreSQL\nreasignacion)]
    end

    USR --> NGINX
    NGINX -->|"/*"| NEXT
    NGINX -->|"/api/*"| KONG
    KONG --> KONG_RT
    KONG_RT -->|"/api/auth"| AUTH
    KONG_RT -->|"/api/*"| KONG_JWT
    KONG_JWT -->|"JWT valido"| BFF
    KONG_JWT -->|"JWT invalido"| ERR[401 Unauthorized]

    BFF --> BFF_SEC
    BFF --> BFF_FEIGN
    BFF --> BFF_DASH
    BFF_FEIGN --> PAC
    BFF_FEIGN --> LET
    BFF_FEIGN --> REA
    BFF_DASH --> PAC
    BFF_DASH --> LET
    BFF_DASH --> REA

    AUTH --> PG_AUTH
    PAC --> PG_PAC
    LET --> PG_LET
    REA --> PG_REA
```

## Flujo de Autenticacion

```mermaid
sequenceDiagram
    actor U as Usuario
    participant N as Nginx
    participant K as Kong
    participant A as ms-auth
    participant B as BFF
    participant P as ms-pacientes

    U->>N: POST /api/auth/login {rut, contrasena}
    N->>K: /api/auth/login
    K->>A: /api/auth/login
    A->>A: Validar credenciales (BCrypt)
    A->>A: Generar JWT (iss: rednorte-client)
    A-->>K: {token, role, usuarioId}
    K-->>N: 200 OK
    N-->>U: {token, role, usuarioId}

    U->>N: GET /api/v1/pacientes (Authorization: Bearer JWT)
    N->>K: /api/v1/pacientes
    K->>K: Validar JWT (iss, exp, firma HMAC)
    K->>B: /api/v1/pacientes
    B->>B: Validar JWT (defense in depth)
    B->>P: GET /api/v1/pacientes
    P-->>B: List<PacienteDTO>
    B-->>K: 200 OK
    K-->>N: 200 OK
    N-->>U: List<PacienteDTO>
```

## Flujo de Dashboard

```mermaid
sequenceDiagram
    actor U as Usuario
    participant N as Nginx
    participant K as Kong
    participant B as BFF
    participant P as ms-pacientes
    participant L as ms-lista-espera
    participant R as ms-reasignacion

    U->>N: GET /api/v1/dashboard/paciente/1
    N->>K: /api/v1/dashboard/paciente/1
    K->>K: Validar JWT
    K->>B: GET /api/v1/dashboard/paciente/1
    activate B
    B->>B: DashboardService.getDashboardPaciente(1)
    par Llamadas asincronas
        B->>P: GET /api/v1/pacientes/1
        B->>L: GET /api/v1/espera/paciente/1
        B->>R: GET /api/v1/reasignaciones/historial/paciente/1
    end
    P-->>B: PacienteDTO
    L-->>B: List<ListaEsperaDTO>
    R-->>B: List<ReasignacionDTO>
    B->>B: Agregar datos en PacienteDashboardDTO
    deactivate B
    B-->>K: PacienteDashboardDTO
    K-->>N: 200 OK
    N-->>U: Dashboard agregado
```

## Tecnologias

| Componente | Tecnologia | Version |
|---|---|---|
| Frontend | Next.js | 14.2 |
| API Gateway | Kong | 3.7 |
| BFF | Spring Boot | 3.2.5 |
| Microservicios | Spring Boot | 3.2.0 |
| Lenguaje | Java | 17 |
| Persistencia | Spring Data JPA / PostgreSQL | 15 |
| Autenticacion | JWT (jjwt) | 0.12.5 |
| Documentacion API | OpenAPI / Swagger | 3.0 |
| Pruebas | JUnit 5 + Mockito + JaCoCo | - |
| Contenedores | Docker + Docker Compose | - |
