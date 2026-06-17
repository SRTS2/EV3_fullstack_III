# Frontend RedNorte

Frontend desarrollado con Next.js 14 para el sistema de gestion de pacientes RedNorte.

## Tecnologias

- Next.js 14 (App Router)
- React 18
- Axios para peticiones HTTP
- Context API para estado de autenticacion

## Estructura

```
src/
  app/
    login/        - Pagina de inicio de sesion
    register/     - Pagina de registro
    dashboard/    - Dashboard del paciente
    lista-espera/ - Lista de espera
    layout.js     - Layout principal con Navbar
    page.js       - Home (redirige a login)
  components/
    AuthProvider.js - Contexto de autenticacion
    Navbar.js       - Barra de navegacion
    PrivateRoute.js - Ruta protegida
  services/
    api.js          - Cliente Axios con interceptors
  globals.css       - Estilos globales
```

## Instalacion y ejecucion

```bash
npm install
npm run dev     # Desarrollo en http://localhost:3000
npm run build   # Produccion
```

## Variables de entorno

- `NEXT_PUBLIC_KONG_URL`: URL del API Gateway (default: http://localhost:8000)
