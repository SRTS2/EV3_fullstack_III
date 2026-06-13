'use client';

import { useState, useEffect } from 'react';
import PrivateRoute from '@/components/PrivateRoute';
import { useAuth } from '@/components/AuthProvider';
import api from '@/services/api';

function DashboardContent() {
  const { user } = useAuth();
  const [dashboard, setDashboard] = useState(null);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState('');

  useEffect(() => {
    if (!user?.pacienteId) {
      setLoading(false);
      return;
    }
    api.get(`/api/v1/dashboard/paciente/${user.pacienteId}`)
      .then((res) => setDashboard(res.data))
      .catch((err) => setError('Error al cargar dashboard'))
      .finally(() => setLoading(false));
  }, [user]);

  const getPrioridadClass = (p) => {
    if (!p) return '';
    return `badge-${p.toLowerCase()}`;
  };

  const getEstadoClass = (e) => {
    if (!e) return '';
    return `badge-${e.toLowerCase()}`;
  };

  return (
    <div>
      <h1 style={{ marginBottom: 24 }}>Dashboard</h1>
      {loading && <p>Cargando datos del paciente...</p>}
      {error && <div className="error">{error}</div>}
      {!user?.pacienteId && (
        <div className="card">
          <p>No hay informacion de paciente asociada a tu cuenta.</p>
          <p>Contacta al administrador si esto es un error.</p>
        </div>
      )}
      {dashboard?.paciente && (
        <div className="card">
          <h2>Datos del Paciente</h2>
          <p><strong>RUT:</strong> {dashboard.paciente.rut}</p>
          <p><strong>Nombre:</strong> {dashboard.paciente.nombre}</p>
          <p><strong>Email:</strong> {dashboard.paciente.email}</p>
          <p><strong>Telefono:</strong> {dashboard.paciente.telefono || 'No registrado'}</p>
        </div>
      )}
      {dashboard?.listaEspera && (
        <div className="card">
          <h2>Estado en Lista de Espera</h2>
          <p><strong>Especialidad:</strong> {dashboard.listaEspera.especialidad}</p>
          <p><strong>Prioridad:</strong> <span className={`badge ${getPrioridadClass(dashboard.listaEspera.prioridad)}`}>{dashboard.listaEspera.prioridad}</span></p>
          <p><strong>Estado:</strong> <span className={`badge ${getEstadoClass(dashboard.listaEspera.estado)}`}>{dashboard.listaEspera.estado}</span></p>
          <p><strong>Tiempo estimado:</strong> {dashboard.listaEspera.tiempoEstimado || 'No disponible'}</p>
        </div>
      )}
      {dashboard?.reasignaciones?.length > 0 && (
        <div className="card">
          <h2>Reasignaciones</h2>
          <table className="table">
            <thead>
              <tr>
                <th>Especialidad</th>
                <th>Fecha Original</th>
                <th>Fecha Reasignada</th>
                <th>Estado</th>
              </tr>
            </thead>
            <tbody>
              {dashboard.reasignaciones.map((r) => (
                <tr key={r.id}>
                  <td>{r.especialidad}</td>
                  <td>{r.fechaOriginal ? new Date(r.fechaOriginal).toLocaleDateString() : '-'}</td>
                  <td>{r.fechaReasignada ? new Date(r.fechaReasignada).toLocaleDateString() : '-'}</td>
                  <td><span className={`badge ${getEstadoClass(r.estado)}`}>{r.estado}</span></td>
                </tr>
              ))}
            </tbody>
          </table>
        </div>
      )}
    </div>
  );
}

export default function DashboardPage() {
  return (
    <PrivateRoute>
      <DashboardContent />
    </PrivateRoute>
  );
}
