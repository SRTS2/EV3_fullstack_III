@echo off
echo ========================================
echo  RedNorte - Ejecucion de Pruebas
echo ========================================
echo.

echo [1/6] Ejecutando pruebas de bff-rednorte...
cd bff-rednorte
call mvn clean verify
if %errorlevel% neq 0 ( echo ERROR en bff-rednorte & exit /b %errorlevel% )
cd ..

echo [2/6] Ejecutando pruebas de ms-auth...
cd ms-auth
call mvn clean verify
if %errorlevel% neq 0 ( echo ERROR en ms-auth & exit /b %errorlevel% )
cd ..

echo [3/6] Ejecutando pruebas de ms-pacientes...
cd ms-pacientes
call mvn clean verify
if %errorlevel% neq 0 ( echo ERROR en ms-pacientes & exit /b %errorlevel% )
cd ..

echo [4/6] Ejecutando pruebas de ms-lista-espera...
cd ms-lista-espera
call mvn clean verify
if %errorlevel% neq 0 ( echo ERROR en ms-lista-espera & exit /b %errorlevel% )
cd ..

echo [5/6] Ejecutando pruebas de ms-reasignacion...
cd ms-reasignacion
call mvn clean verify
if %errorlevel% neq 0 ( echo ERROR en ms-reasignacion & exit /b %errorlevel% )
cd ..

echo [6/6] Ejecutando pruebas de frontend-rednorte...
cd frontend-rednorte
call npm install
call npm test
if %errorlevel% neq 0 ( echo ERROR en frontend-rednorte & exit /b %errorlevel% )
cd ..

echo.
echo ========================================
echo  Todas las pruebas completadas exitosamente
echo ========================================
pause
