import axiosInstance from '../utils/axios.instance';
import { API_CONFIG } from '../config/api.config';

export const authService = {
  // Signup
  signup: async (userData) => {
    try {
      const response = await axiosInstance.post(API_CONFIG.ENDPOINTS.SIGNUP, userData);
      return response.data;
    } catch (error) {
      throw error.response?.data || { message: 'Signup failed' };
    }
  },

  // Login
  login: async (credentials) => {
    try {
      const response = await axiosInstance.post(API_CONFIG.ENDPOINTS.LOGIN, credentials);
      if (response.data.token) {
        localStorage.setItem('authToken', response.data.token);
        // Store user info with email from response
        const user = {
          email: response.data.email
        };
        localStorage.setItem('user', JSON.stringify(user));
      }
      return response.data;
    } catch (error) {
      throw error.response?.data || { message: 'Login failed' };
    }
  },

  // Verify Email
  verifyEmail: async (token) => {
    try {
      const response = await axiosInstance.post(API_CONFIG.ENDPOINTS.VERIFY_EMAIL, { token });
      return response.data;
    } catch (error) {
      throw error.response?.data || { message: 'Email verification failed' };
    }
  },

  // Resend Verification Email
  resendVerification: async (email) => {
    try {
      const response = await axiosInstance.post(API_CONFIG.ENDPOINTS.RESEND_VERIFICATION, { email });
      return response.data;
    } catch (error) {
      throw error.response?.data || { message: 'Failed to resend verification email' };
    }
  },

  // Logout
  logout: () => {
    localStorage.removeItem('authToken');
    localStorage.removeItem('user');
  },

  // Get Current User
  getCurrentUser: () => {
    const userStr = localStorage.getItem('user');
    return userStr ? JSON.parse(userStr) : null;
  },

  // Check if user is authenticated
  isAuthenticated: () => {
    return !!localStorage.getItem('authToken');
  }
};
