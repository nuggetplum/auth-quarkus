import axios from 'axios';

const API_BASE_URL = 'http://localhost:8080';

const api = axios.create({
  baseURL: API_BASE_URL,
  headers: {
    'Content-Type': 'application/json',
  },
});

// Add JWT token to requests if available
api.interceptors.request.use((config) => {
  const token = localStorage.getItem('token');
  if (token) {
    config.headers.Authorization = `Bearer ${token}`;
  }
  return config;
});

// Auth API
export const authAPI = {
  signup: (email, password) => 
    api.post('/signup', { email, password }),
  
  login: (email, password) => 
    api.post('/login', { email, password }),
  
  verifyEmail: (token) => 
    api.post('/verify-email', { token }),
  
  verifyEmailByLink: (token) => 
    api.get(`/verify-email/${token}`),
  
  resendVerification: (email) => 
    api.post('/verify-email/resend', { email }),
};

export default api;
