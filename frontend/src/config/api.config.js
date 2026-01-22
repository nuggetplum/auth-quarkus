export const API_CONFIG = {
  BASE_URL: import.meta.env.VITE_API_BASE_URL || 'http://localhost:8080',
  ENDPOINTS: {
    SIGNUP: '/signup',
    LOGIN: '/login',
    VERIFY_EMAIL: '/verify-email',
    RESEND_VERIFICATION: '/verify-email/resend'
  },
  TIMEOUT: 10000
};
