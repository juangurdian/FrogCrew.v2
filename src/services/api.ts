import axios from 'axios';
import store from '@/store';

// With proxy config, we can use relative URLs
const API_URL = '/api';

// Create axios instance
const api = axios.create({
  baseURL: API_URL,
  headers: {
    'Content-Type': 'application/json',
  },
});

// Add request interceptor to add auth token
api.interceptors.request.use(
  (config: any) => {
    const token = localStorage.getItem('token');
    if (token && config.headers) {
      config.headers.Authorization = `Bearer ${token}`;
    }
    return config;
  },
  (error: any) => {
    return Promise.reject(error);
  }
);

// Add response interceptor to handle auth errors
api.interceptors.response.use(
  (response: any) => {
    return response;
  },
  (error: any) => {
    if (error.response && error.response.status === 401) {
      // Logout user and redirect to login page
      store.dispatch('logout');
      window.location.href = '/login';
    }
    return Promise.reject(error);
  }
);

// Define interfaces for type safety
interface Credentials {
  email: string;
  password: string;
}

interface UserData {
  id?: number;
  email: string;
  firstName: string;
  lastName: string;
  password?: string;
  role: string;
}

// Auth services
export const authService = {
  login(credentials: Credentials) {
    return api.post('/auth/login', credentials);
  },
  register(user: UserData) {
    return api.post('/auth/register', user);
  },
};

// User services
export const userService = {
  getCurrentUser() {
    return api.get('/users/me');
  },
  getAllUsers() {
    return api.get('/users');
  },
  getUserById(id: number) {
    return api.get(`/users/${id}`);
  },
  updateUser(id: number, user: UserData) {
    return api.put(`/users/${id}`, user);
  },
  deleteUser(id: number) {
    return api.delete(`/users/${id}`);
  },
};

export default api; 