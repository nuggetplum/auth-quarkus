# Frontend - Authentication System

A modern React-based frontend for user authentication with email verification.

## Features

- ✅ User Signup with validation
- ✅ User Login
- ✅ Email Verification
- ✅ Resend Verification Email
- ✅ Protected Routes
- ✅ JWT Token Authentication
- ✅ Material-UI Components
- ✅ Form Validation with Formik & Yup
- ✅ Toast Notifications

## Tech Stack

- **React 19** - UI Framework
- **Vite** - Build Tool (Lightning fast!)
- **Material-UI (MUI)** - Component Library
- **React Router** - Routing
- **Axios** - HTTP Client
- **Formik + Yup** - Form Management & Validation
- **React Toastify** - Toast Notifications

## Getting Started

### Installation

```bash
npm install
```

### Running the Application

```bash
npm run dev
# or
npm start
```

The app will open at [http://localhost:3000](http://localhost:3000)

## API Integration

The frontend connects to the Quarkus backend at `http://localhost:8080`

Make sure to configure CORS on the backend for `http://localhost:3000`

## Available Routes

- `/signup` - User registration
- `/login` - User login
- `/verify-email?token=xxx` - Email verification
- `/resend-verification` - Resend verification email
- `/dashboard` - Protected dashboard (requires auth)
