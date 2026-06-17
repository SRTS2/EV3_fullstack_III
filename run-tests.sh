#!/bin/bash
echo "========================================"
echo " RedNorte - Ejecucion de Pruebas"
echo "========================================"
echo ""

echo "[1/6] Ejecutando pruebas de bff-rednorte..."
cd bff-rednorte && mvn clean verify || exit 1
cd ..

echo "[2/6] Ejecutando pruebas de ms-auth..."
cd ms-auth && mvn clean verify || exit 1
cd ..

echo "[3/6] Ejecutando pruebas de ms-pacientes..."
cd ms-pacientes && mvn clean verify || exit 1
cd ..

echo "[4/6] Ejecutando pruebas de ms-lista-espera..."
cd ms-lista-espera && mvn clean verify || exit 1
cd ..

echo "[5/6] Ejecutando pruebas de ms-reasignacion..."
cd ms-reasignacion && mvn clean verify || exit 1
cd ..

echo "[6/6] Ejecutando pruebas de frontend-rednorte..."
cd frontend-rednorte && npm install && npm test || exit 1
cd ..

echo ""
echo "========================================"
echo " Todas las pruebas completadas exitosamente"
echo "========================================"
