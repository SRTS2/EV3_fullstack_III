'use client';

import { useState } from 'react';
import { useRouter } from 'next/navigation';
import Link from 'next/link';

export default function RegisterPage() {
  const [form, setForm] = useState({ rut: '', nombre: '', email: '', contrasena: '' });
  const [error, setError] = useState('');
  const [success, setSuccess] = useState('');
  const [loading, setLoading] = useState(false);
  const router = useRouter();

  const handleChange = (e) => {
    setForm({ ...form, [e.target.name]: e.target.value });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    setError('');
    setSuccess('');
    setLoading(true);
    try {
      const res = await fetch('/api/auth/register', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(form),
      });
      const data = await res.json();
      if (!res.ok) {
        throw new Error(data.message || data.errors?.[Object.keys(data.errors)[0]] || 'Error al registrarse');
      }
      setSuccess('Registro exitoso! Redirigiendo al login...');
      setTimeout(() => router.push('/login'), 2000);
    } catch (err) {
      setError(err.message);
    } finally {
      setLoading(false);
    }
  };

  return (
    <div style={{ maxWidth: 400, margin: '80px auto' }}>
      <div className="card">
        <h1 style={{ marginBottom: 24, textAlign: 'center', color: '#1e293b' }}>RedNorte</h1>
        <h2 style={{ marginBottom: 20, textAlign: 'center', color: '#64748b' }}>Registro</h2>
        {error && <div className="error" style={{ marginBottom: 16, textAlign: 'center' }}>{error}</div>}
        {success && <div className="success" style={{ marginBottom: 16, textAlign: 'center' }}>{success}</div>}
        <form onSubmit={handleSubmit}>
          <div className="form-group">
            <label>RUT</label>
            <input type="text" name="rut" value={form.rut} onChange={handleChange} placeholder="11111111-1" required />
          </div>
          <div className="form-group">
            <label>Nombre</label>
            <input type="text" name="nombre" value={form.nombre} onChange={handleChange} placeholder="Nombre completo" required />
          </div>
          <div className="form-group">
            <label>Email</label>
            <input type="email" name="email" value={form.email} onChange={handleChange} placeholder="correo@ejemplo.com" required />
          </div>
          <div className="form-group">
            <label>Contrasena</label>
            <input type="password" name="contrasena" value={form.contrasena} onChange={handleChange} placeholder="Minimo 6 caracteres" required />
          </div>
          <button type="submit" className="btn btn-primary" style={{ width: '100%', marginTop: 8 }} disabled={loading}>
            {loading ? 'Registrando...' : 'Registrarse'}
          </button>
        </form>
        <p style={{ marginTop: 16, textAlign: 'center', color: '#64748b' }}>
          Ya tienes cuenta? <Link href="/login" style={{ color: '#2563eb' }}>Inicia sesion</Link>
        </p>
      </div>
    </div>
  );
}
