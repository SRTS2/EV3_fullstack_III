'use client';

import { useState, useEffect } from 'react';
import PrivateRoute from '@/components/PrivateRoute';
import { useAuth } from '@/components/AuthProvider';
import api from '@/services/api';

function ListaEsperaContent() {
  const { user } = useAuth();
  const [entries, setEntries] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState('');
  const [form, setForm] = useState({ especialidad: '', prioridad: 'MEDIA', observaciones: '' });

  const loadEntries = async () => {
    setLoading(true);
    try {
      const res = await api.get('/api/v1/espera');
      setEntries(res.data);
    } catch (err) {
      setError('Error al cargar lista de espera');
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => { loadEntries(); }, []);

  const handleSubmit = async (e) => {
    e.preventDefault();
    setError('');
    try {
      await api.post('/api/v1/espera', {
        pacienteId: user?.pacienteId,
        nombrePaciente: user?.nombre,
        rutPaciente: user?.rut,
        especialidad: form.especialidad,
        prioridad: form.prioridad,
        observaciones: form.observaciones,
      });
      setForm({ especialidad: '', prioridad: 'MEDIA', observaciones: '' });
      loadEntries();
    } catch (err) {
      setError(err.response?.data?.message || 'Error al registrarse en lista de espera');
    }
  };

  const getBadgeClass = (val) => `badge-${val?.toLowerCase?.() || ''}`;

  return (
    <div>
      <h1 style={{ marginBottom: 24 }}>Lista de Espera</h1>
      <div className="card">
        <h2 style={{ marginBottom: 16 }}>Registrarse en Lista de Espera</h2>
        {error && <div className="error" style={{ marginBottom: 12 }}>{error}</div>}
        <form onSubmit={handleSubmit}>
          <div className="form-group">
            <label>Especialidad</label>
            <input type="text" value={form.especialidad} onChange={(e) => setForm({ ...form, especialidad: e.target.value })} placeholder="Ej: Cardiologia" required />
          </div>
          <div className="form-group">
            <label>Prioridad</label>
            <select value={form.prioridad} onChange={(e) => setForm({ ...form, prioridad: e.target.value })}>
              <option value="BAJA">Baja</option>
              <option value="MEDIA">Media</option>
              <option value="ALTA">Alta</option>
              <option value="URGENTE">Urgente</option>
            </select>
          </div>
          <div className="form-group">
            <label>Observaciones</label>
            <input type="text" value={form.observaciones} onChange={(e) => setForm({ ...form, observaciones: e.target.value })} placeholder="Opcional" />
          </div>
          <button type="submit" className="btn btn-primary">Registrarse</button>
        </form>
      </div>
      <div className="card">
        <h2 style={{ marginBottom: 16 }}>Pacientes en Espera</h2>
        {loading && <p>Cargando...</p>}
        {!loading && entries.length === 0 && <p>No hay pacientes en lista de espera.</p>}
        {entries.length > 0 && (
          <table className="table">
            <thead>
              <tr>
                <th>Paciente</th>
                <th>RUT</th>
                <th>Especialidad</th>
                <th>Prioridad</th>
                <th>Estado</th>
                <th>Ingreso</th>
                <th>Tiempo Est.</th>
              </tr>
            </thead>
            <tbody>
              {entries.map((entry) => (
                <tr key={entry.id}>
                  <td>{entry.nombrePaciente}</td>
                  <td>{entry.rutPaciente}</td>
                  <td>{entry.especialidad}</td>
                  <td><span className={`badge ${getBadgeClass(entry.prioridad)}`}>{entry.prioridad}</span></td>
                  <td><span className={`badge ${getBadgeClass(entry.estado)}`}>{entry.estado}</span></td>
                  <td>{entry.fechaIngreso ? new Date(entry.fechaIngreso).toLocaleDateString() : '-'}</td>
                  <td>{entry.tiempoEstimadoDias ? `${entry.tiempoEstimadoDias} dias` : '-'}</td>
                </tr>
              ))}
            </tbody>
          </table>
        )}
      </div>
    </div>
  );
}

export default function ListaEsperaPage() {
  return (
    <PrivateRoute>
      <ListaEsperaContent />
    </PrivateRoute>
  );
}
