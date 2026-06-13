'use client';

import Link from 'next/link';
import { useAuth } from './AuthProvider';

export default function Navbar() {
  const { user, logout } = useAuth();

  return (
    <nav className="navbar">
      <div>
        <span className="brand">RedNorte</span>
        <Link href="/dashboard">Dashboard</Link>
        <Link href="/lista-espera">Lista de Espera</Link>
      </div>
      <div>
        <span style={{ marginRight: 16 }}>{user?.nombre || 'Usuario'}</span>
        <button className="btn btn-danger" onClick={logout} style={{ padding: '6px 12px' }}>
          Cerrar Sesion
        </button>
      </div>
    </nav>
  );
}
