# Informe de Pruebas Unitarias - RedNorte

## Resumen de Cobertura

| Componente | Tests | Cobertura Min | Framework |
|---|---|---|---|
| bff-rednorte | 4 | 60% line (JaCoCo) | JUnit 5 + Mockito |
| ms-pacientes | 8 | 60% line (JaCoCo) | JUnit 5 + Mockito |
| ms-lista-espera | 7 | 60% line (JaCoCo) | JUnit 5 + Mockito |
| ms-reasignacion | 6 | 60% line (JaCoCo) | JUnit 5 + Mockito |
| ms-auth | 8 | 60% line (JaCoCo) | JUnit 5 + Mockito |
| frontend-rednorte | 12 | 60% line (Jest) | Jest + RTL |
| **Total** | **45** | **60%** | |

## Detalle por Componente

### bff-rednorte (JwtUtilTest.java) - 4 tests

**Cobertura objetivo**: 60% lineas (JaCoCo)

| Test | Resultado |
|---|---|
| generateAndValidateToken | Verifica generacion y validacion de JWT |
| extractUsername | Extrae subject del token |
| extractRole | Extrae claim role del token |
| extractPacienteId | Extrae claim pacienteId del token |

### ms-pacientes (PacienteServiceTest.java) - 8 tests

**Cobertura objetivo**: 60% lineas (JaCoCo)

| Test | Resultado |
|---|---|
| testRegistrar | Registro exitoso de paciente |
| testRegistrar_RutDuplicado | Lanza excepcion si RUT ya existe |
| testAutenticar_Exitoso | Login con credenciales correctas |
| testAutenticar_ContrasenaIncorrecta | Login con contrasena incorrecta |
| testAutenticar_PacienteInactivo | Login de paciente desactivado |
| testObtenerPorId_Existe | Busqueda exitosa por ID |
| testObtenerPorId_NoExiste | Busqueda de ID inexistente |
| testActualizar | Actualizacion parcial de datos |

### ms-lista-espera (ListaEsperaServiceTest.java) - 7 tests

**Cobertura objetivo**: 60% lineas (JaCoCo)

| Test | Resultado |
|---|---|
| testRegistrar | Registro en lista de espera con calculo de tiempo |
| testListarPendientes | Listar solo entradas EN_ESPERA |
| testObtenerPorPaciente | Busqueda por ID de paciente |
| testObtenerPorId_Existe | Busqueda por ID de entrada |
| testObtenerPorId_NoExiste | ID inexistente lanza excepcion |
| testActualizarEstado | Cambio de estado exitoso |
| testEliminar_Existe | Eliminacion exitosa |
| testEliminar_NoExiste | Eliminacion de ID inexistente |

### ms-reasignacion (ReasignacionServiceTest.java) - 6 tests

**Cobertura objetivo**: 60% lineas (JaCoCo)

| Test | Resultado |
|---|---|
| testEjecutarReasignacion | Creacion con estado PENDIENTE y fecha futura |
| testObtenerHistorial | Historial por ID de paciente |
| testListarDisponibles | Listar solo PENDIENTES |
| testConfirmarReasignacion | Cambio a estado CONFIRMADA |
| testCancelarReasignacion | Cambio a estado CANCELADA con motivo |
| testConfirmar_NoExiste | Confirmacion de ID inexistente |

### ms-auth (AuthServiceTest.java) - 8 tests

**Cobertura objetivo**: 60% lineas (JaCoCo)

| Test | Resultado |
|---|---|
| testRegister_Exitoso | Registro exitoso de usuario |
| testRegister_RutDuplicado | RUT duplicado lanza excepcion |
| testLogin_Exitoso | Login con credenciales correctas retorna JWT |
| testLogin_RutInvalido | RUT inexistente lanza excepcion |
| testLogin_UsuarioInactivo | Usuario desactivado lanza excepcion |
| testObtenerPorId_Existe | Busqueda exitosa por ID |
| testObtenerPorId_NoExiste | ID inexistente lanza EntityNotFoundException |
| testActualizarPacienteId | Actualizacion de pacienteId en usuario |

### frontend-rednorte - 12 tests

**Cobertura objetivo**: 60% lineas (Jest)

**api.test.js** (5 tests):
| Test | Resultado |
|---|---|
| crea instancia axios con baseURL /api | Verifica configuracion de axios |
| interceptor request agrega token de localStorage | Token se agrega al header Authorization |
| interceptor request no agrega header si no hay token | Sin token, no hay header |
| interceptor response redirige a /login en 401 | 401 limpia storage y redirige |
| interceptor response rechaza errores no-401 | Errores 500 pasan al catch |

**AuthProvider.test.js** (5 tests):
| Test | Resultado |
|---|---|
| renderiza con estado inicial | loading=true al inicio |
| restaura sesion desde localStorage | Recupera user/token guardados |
| login exitoso almacena datos | Login via API almacena token |
| register hace POST a /api/auth/register | Verifica llamada al endpoint |
| logout limpia estado y redirige | Logout limpia storage y redirige a /login |

**PrivateRoute.test.js** (3 tests):
| Test | Resultado |
|---|---|
| muestra loading mientras carga | Muestra "Cargando..." |
| redirige a /login si no autenticado | router.push a /login |
| muestra children si autenticado | Renderiza contenido protegido |

## Como ejecutar las pruebas

### Backend (microservicios Spring Boot)
```bash
# Requisito: Java 17+ y Maven 3.8+

# Ejecutar pruebas de un microservicio especifico
cd bff-rednorte
mvn clean test

# Con reporte de cobertura JaCoCo (genera HTML en target/site/jacoco/)
mvn clean verify

# Ejecutar todos los microservicios
cd bff-rednorte && mvn clean verify
cd ms-auth && mvn clean verify
cd ms-pacientes && mvn clean verify
cd ms-lista-espera && mvn clean verify
cd ms-reasignacion && mvn clean verify
```

### Frontend (Next.js)
```bash
# Requisito: Node.js 20+ y npm

cd frontend-rednorte
npm install         # Instalar dependencias
npm test            # Ejecutar tests con cobertura
npm run test:watch  # Modo watch para desarrollo
```

### Script automatizado
```bash
# Windows
run-tests.bat

# Linux/Mac
chmod +x run-tests.sh
./run-tests.sh
```

## Herramientas

- **JUnit 5**: Framework de pruebas unitarias (Java)
- **Mockito**: Mocking de dependencias (Java)
- **JaCoCo**: Generacion de reportes de cobertura de codigo (Java)
- **Jest**: Framework de pruebas unitarias (JavaScript)
- **React Testing Library**: Testing de componentes React
- **Maven**: Ejecucion de tests Java via `mvn test`
- **npm**: Ejecucion de tests JavaScript via `npm test`

## Reportes de Cobertura

Para generar los reportes HTML de cobertura:

```bash
# Backend: reporte en target/site/jacoco/index.html
cd ms-pacientes && mvn clean verify

# Frontend: reporte en coverage/lcov-report/index.html
cd frontend-rednorte && npm test
```
