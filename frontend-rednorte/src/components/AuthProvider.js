'use client';

import { createContext, useContext, useState, useEffect } from 'react';
import { useRouter } from 'next/navigation';

const AuthContext = createContext();

export function useAuth() {
  return useContext(AuthContext);
}

export function AuthProvider({ children }) {
  const [user, setUser] = useState(null);
  const [token, setToken] = useState(null);
  const [loading, setLoading] = useState(true);
  const router = useRouter();

  useEffect(() => {
    const stored = localStorage.getItem('rednorte_user');
    const storedToken = localStorage.getItem('rednorte_token');
    if (stored && storedToken) {
      setUser(JSON.parse(stored));
      setToken(storedToken);
    }
    setLoading(false);
  }, []);

  const login = async (rut, contrasena) => {
    try {
      const res = await fetch('/api/auth/login', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ rut, contrasena }),
      });
      if (!res.ok) {
        const err = await res.json();
        throw new Error(err.message || 'Error al iniciar sesion');
      }
      const data = await res.json();
      setUser({ rut: data.rut, nombre: data.nombre, role: data.role, usuarioId: data.usuarioId, pacienteId: data.pacienteId });
      setToken(data.token);
      localStorage.setItem('rednorte_user', JSON.stringify({ rut: data.rut, nombre: data.nombre, role: data.role, usuarioId: data.usuarioId, pacienteId: data.pacienteId }));
      localStorage.setItem('rednorte_token', data.token);
      return data;
    } catch (err) {
      throw err;
    }
  };

  const register = async (userData) => {
    try {
      const res = await fetch('/api/auth/register', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(userData),
      });
      if (!res.ok) {
        const err = await res.json();
        throw new Error(err.message || 'Error al registrarse');
      }
      return await res.json();
    } catch (err) {
      throw err;
    }
  };

  const logout = () => {
    setUser(null);
    setToken(null);
    localStorage.removeItem('rednorte_user');
    localStorage.removeItem('rednorte_token');
    router.push('/login');
  };

  const value = { user, token, loading, login, register, logout };

  return (
    <AuthContext.Provider value={value}>
      {children}
    </AuthContext.Provider>
  );
}
