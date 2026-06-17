import { render, screen, fireEvent, waitFor } from '@testing-library/react';
import { AuthProvider, useAuth } from '../src/components/AuthProvider';

const mockRouter = { push: jest.fn() };

jest.mock('next/navigation', () => ({
  useRouter: () => mockRouter,
}));

function TestComponent() {
  const { user, token, login, register, logout, loading } = useAuth();
  return (
    <div>
      <span data-testid="loading">{loading ? 'true' : 'false'}</span>
      <span data-testid="user">{user ? JSON.stringify(user) : 'null'}</span>
      <span data-testid="token">{token || 'null'}</span>
      <button data-testid="login-btn" onClick={() => login('12345678-9', 'pass123')}>
        Login
      </button>
      <button data-testid="register-btn" onClick={() => register({ rut: '12345678-9', nombre: 'Test', contrasena: 'pass123' })}>
        Register
      </button>
      <button data-testid="logout-btn" onClick={logout}>
        Logout
      </button>
    </div>
  );
}

describe('AuthProvider', () => {
  beforeEach(() => {
    localStorage.clear();
    jest.clearAllMocks();
  });

  it('renderiza con estado inicial', () => {
    render(
      <AuthProvider>
        <TestComponent />
      </AuthProvider>
    );
    expect(screen.getByTestId('loading').textContent).toBe('true');
  });

  it('restaura sesion desde localStorage', () => {
    localStorage.setItem('rednorte_user', JSON.stringify({ nombre: 'Juan' }));
    localStorage.setItem('rednorte_token', 'token123');

    render(
      <AuthProvider>
        <TestComponent />
      </AuthProvider>
    );

    expect(screen.getByTestId('user').textContent).toContain('Juan');
    expect(screen.getByTestId('token').textContent).toBe('token123');
  });

  it('login exitoso almacena datos', async () => {
    global.fetch = jest.fn(() =>
      Promise.resolve({
        ok: true,
        json: () => Promise.resolve({ token: 'jwt-token', role: 'PACIENTE', usuarioId: 1, nombre: 'Juan' }),
      })
    );

    render(
      <AuthProvider>
        <TestComponent />
      </AuthProvider>
    );

    fireEvent.click(screen.getByTestId('login-btn'));

    await waitFor(() => {
      expect(screen.getByTestId('token').textContent).toBe('jwt-token');
    });
  });

  it('register hace POST a /api/auth/register', async () => {
    const mockFetch = jest.fn(() =>
      Promise.resolve({
        ok: true,
        json: () => Promise.resolve({ id: 1, rut: '12345678-9', nombre: 'Test' }),
      })
    );
    global.fetch = mockFetch;

    render(
      <AuthProvider>
        <TestComponent />
      </AuthProvider>
    );

    fireEvent.click(screen.getByTestId('register-btn'));

    await waitFor(() => {
      expect(mockFetch).toHaveBeenCalledWith('/api/auth/register', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ rut: '12345678-9', nombre: 'Test', contrasena: 'pass123' }),
      });
    });
  });

  it('logout limpia estado y redirige', () => {
    localStorage.setItem('rednorte_user', JSON.stringify({ nombre: 'Juan' }));
    localStorage.setItem('rednorte_token', 'token123');

    render(
      <AuthProvider>
        <TestComponent />
      </AuthProvider>
    );

    fireEvent.click(screen.getByTestId('logout-btn'));

    expect(localStorage.getItem('rednorte_user')).toBeNull();
    expect(localStorage.getItem('rednorte_token')).toBeNull();
    expect(mockRouter.push).toHaveBeenCalledWith('/login');
  });
});
