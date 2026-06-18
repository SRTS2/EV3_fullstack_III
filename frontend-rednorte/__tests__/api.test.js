import api from '../src/services/api';
import axios from 'axios';

jest.mock('axios');

describe('API Service', () => {
  beforeEach(() => {
    localStorage.clear();
  });

  it('crea instancia axios con baseURL /api', () => {
    expect(axios.create).toHaveBeenCalledWith({
      baseURL: '/api',
      headers: { 'Content-Type': 'application/json' },
    });
  });

  it('interceptor request agrega token de localStorage', () => {
    localStorage.setItem('rednorte_token', 'test-token');

    const onFulfilled = axios.interceptors.request.use.mock.calls[0][0];
    const config = { headers: {} };
    const result = onFulfilled(config);

    expect(result.headers.Authorization).toBe('Bearer test-token');
  });

  it('interceptor request no agrega header si no hay token', () => {
    const onFulfilled = axios.interceptors.request.use.mock.calls[0][0];
    const config = { headers: {} };
    const result = onFulfilled(config);

    expect(result.headers.Authorization).toBeUndefined();
  });

  it('interceptor response redirige a /login en 401', () => {
    localStorage.setItem('rednorte_token', 'test-token');
    localStorage.setItem('rednorte_user', '{"name":"test"}');

    const onRejected = axios.interceptors.response.use.mock.calls[0][1];
    const error = { response: { status: 401 } };

    delete global.window.location;
    global.window.location = { href: '' };

    onRejected(error).catch(() => {
      expect(localStorage.getItem('rednorte_token')).toBeNull();
      expect(global.window.location.href).toBe('/login');
    });
  });

  it('interceptor response rechaza errores no-401', () => {
    const onRejected = axios.interceptors.response.use.mock.calls[0][1];
    const error = { response: { status: 500 } };

    onRejected(error).catch((err) => {
      expect(err).toBe(error);
    });
  });
});
