import { render, screen } from '@testing-library/react';
import PrivateRoute from '../src/components/PrivateRoute';

const mockRouter = { push: jest.fn() };

jest.mock('next/navigation', () => ({
  useRouter: () => mockRouter,
}));

jest.mock('@/components/AuthProvider', () => ({
  useAuth: jest.fn(),
}));

import { useAuth } from '@/components/AuthProvider';

describe('PrivateRoute', () => {
  beforeEach(() => {
    jest.clearAllMocks();
  });

  it('muestra loading mientras carga', () => {
    useAuth.mockReturnValue({ user: null, loading: true });
    render(<PrivateRoute><div>Contenido privado</div></PrivateRoute>);
    expect(screen.getByText('Cargando...')).toBeInTheDocument();
  });

  it('redirige a /login si no autenticado', () => {
    useAuth.mockReturnValue({ user: null, loading: false });
    render(<PrivateRoute><div>Contenido privado</div></PrivateRoute>);
    expect(mockRouter.push).toHaveBeenCalledWith('/login');
  });

  it('muestra children si autenticado', () => {
    useAuth.mockReturnValue({ user: { nombre: 'Juan' }, loading: false });
    render(<PrivateRoute><div>Contenido privado</div></PrivateRoute>);
    expect(screen.getByText('Contenido privado')).toBeInTheDocument();
  });
});
