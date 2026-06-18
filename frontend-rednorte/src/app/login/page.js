'use client';

import { useState } from 'react';
import { useRouter } from 'next/navigation';
import Link from 'next/link';
import { useAuth } from '@/components/AuthProvider';

export default function LoginPage() {
  const [rut, setRut] = useState('');
  const [contrasena, setContrasena] = useState('');
  const [error, setError] = useState('');
  const [loading, setLoading] = useState(false);
  const { login } = useAuth();
  const router = useRouter();

  const handleSubmit = async (e) => {
    e.preventDefault();
    setError('');
    setLoading(true);
    try {
      const data = await login(rut, contrasena);
      if (data.pacienteId) {
        router.push('/dashboard');
      } else {
        router.push('/dashboard');
      }
    } catch (err) {
      setError(err.message || 'Credenciales invalidas');
    } finally {
      setLoading(false);
    }
  };

  return (
    <div style={{ maxWidth: 400, margin: '80px auto' }}>
      <div className="card">
        <h1 style={{ marginBottom: 24, textAlign: 'center', color: '#1e293b' }}>RedNorte</h1>
        <h2 style={{ marginBottom: 20, textAlign: 'center', color: '#64748b' }}>Iniciar Sesion</h2>
        {error && <div className="error" style={{ marginBottom: 16, textAlign: 'center' }}>{error}</div>}
        <form onSubmit={handleSubmit}>
          <div className="form-group">
            <label>RUT</label>
            <input type="text" value={rut} onChange={(e) => setRut(e.target.value)} placeholder="11111111-1" required />
          </div>
          <div className="form-group">
            <label>Contrasena</label>
            <input type="password" value={contrasena} onChange={(e) => setContrasena(e.target.value)} placeholder="Ingrese su contrasena" required />
          </div>
          <button type="submit" className="btn btn-primary" style={{ width: '100%', marginTop: 8 }} disabled={loading}>
            {loading ? 'Ingresando...' : 'Ingresar'}
          </button>
        </form>
        <p style={{ marginTop: 16, textAlign: 'center', color: '#64748b' }}>
          No tienes cuenta? <Link href="/register" style={{ color: '#2563eb' }}>Registrate</Link>
        </p>
      </div>
    </div>
  );
}
