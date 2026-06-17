import { useState } from 'react';

const API_URL = '';

export default function Home() {
  const [rut, setRut] = useState('');
  const [contrasena, setContrasena] = useState('');
  const [token, setToken] = useState('');
  const [error, setError] = useState('');
  const [dashboard, setDashboard] = useState(null);

  async function handleLogin(e) {
    e.preventDefault();
    setError('');
    setDashboard(null);
    try {
      const res = await fetch(`${API_URL}/api/auth/login`, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ rut, contrasena }),
      });
      if (!res.ok) {
        const err = await res.json();
        throw new Error(err.error || 'Error al iniciar sesion');
      }
      const data = await res.json();
      setToken(data.token);
    } catch (err) {
      setError(err.message);
    }
  }

  async function loadDashboard() {
    setError('');
    try {
      const res = await fetch(`${API_URL}/api/v1/dashboard/paciente/1`, {
        headers: { Authorization: `Bearer ${token}` },
      });
      if (!res.ok) throw new Error('Error al cargar dashboard');
      const data = await res.json();
      setDashboard(data);
    } catch (err) {
      setError(err.message);
    }
  }

  return (
    <div style={{ padding: '2rem', fontFamily: 'sans-serif', maxWidth: 600, margin: '0 auto' }}>
      <h1>RedNorte - Portal Paciente</h1>

      {!token ? (
        <form onSubmit={handleLogin} style={{ display: 'flex', flexDirection: 'column', gap: '0.5rem' }}>
          <h2>Iniciar Sesion</h2>
          <input placeholder="RUT" value={rut} onChange={e => setRut(e.target.value)} />
          <input type="password" placeholder="Contrasena" value={contrasena} onChange={e => setContrasena(e.target.value)} />
          <button type="submit">Ingresar</button>
        </form>
      ) : (
        <div>
          <p style={{ color: 'green' }}>Sesion iniciada correctamente</p>
          <p>Token: <code>{token.substring(0, 50)}...</code></p>
          <button onClick={loadDashboard}>Cargar Dashboard</button>

          {dashboard && (
            <div style={{ marginTop: '1rem', border: '1px solid #ccc', padding: '1rem' }}>
              <h2>Dashboard</h2>
              <pre>{JSON.stringify(dashboard, null, 2)}</pre>
            </div>
          )}
        </div>
      )}

      {error && <p style={{ color: 'red' }}>{error}</p>}
    </div>
  );
}
