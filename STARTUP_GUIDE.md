# 🚀 Complete Startup Guide

## Starting the Full Application

Follow these steps in order:

### 1️⃣ Start PostgreSQL Database

Ensure PostgreSQL is running with database `signupdb`

### 2️⃣ Start Python Email Service

```powershell
# Terminal 1
cd "c:\oppex assignment\trial1\email-service"
python app.py
```

✅ Should show: `Flask app running on http://127.0.0.1:5000`

### 3️⃣ Start Quarkus Backend

```powershell
# Terminal 2
cd "c:\oppex assignment\trial1\code-with-quarkus"
mvn quarkus:dev
```

✅ Should show: `Listening on: http://localhost:8080`

### 4️⃣ Start React Frontend

```powershell
# Terminal 3
cd "c:\oppex assignment\trial1\frontend"
npm run dev
```

✅ Should show: `Local: http://localhost:5173/`

---

## 🌐 Access the Application

Open your browser and go to: **http://localhost:5173**

### Available Routes:

- **/** - Home/Landing page
- **/signup** - Create new account
- **/login** - Login to existing account
- **/dashboard** - Protected dashboard (requires login)
- **/resend-verification** - Resend verification email
- **/verify-email/:token** - Email verification (from email link)

---

## ✅ Testing the Complete Flow

### 1. Signup

1. Go to http://localhost:5173/signup
2. Enter email and password (min 6 characters)
3. Click "Sign Up"
4. You'll see success message

### 2. Check Email

1. Open your email inbox
2. Find verification email from the app
3. Click the verification link

### 3. Login

1. Go to http://localhost:5173/login
2. Enter your credentials
3. Click "Login"
4. You'll be redirected to dashboard

### 4. Dashboard

- View your account info
- See verified status
- Logout when done

---

## 🛑 Stopping Everything

Press `Ctrl + C` in each of the 3 terminals to stop:

1. Python email service
2. Quarkus backend
3. React frontend

---

## 📝 Quick Reference

| Service              | Port | URL                   |
| -------------------- | ---- | --------------------- |
| React Frontend       | 5173 | http://localhost:5173 |
| Quarkus Backend      | 8080 | http://localhost:8080 |
| Python Email Service | 5000 | http://localhost:5000 |
| PostgreSQL           | 5432 | localhost:5432        |

---

## 🔧 Troubleshooting

### Frontend not loading?

- Check if all dependencies installed: `cd frontend && npm install`
- Check if dev server is running on port 5173

### Backend errors?

- Ensure PostgreSQL is running
- Check database credentials in `application.properties`
- Verify JWT keys exist in `META-INF/resources/`

### Email not sending?

- Verify Brevo API key in `email-service/.env`
- Check Python service is running on port 5000
- Check sender email is verified in Brevo

### CORS errors?

- Verify CORS is configured in `application.properties`
- Check frontend URL matches: `http://localhost:5173`

---

## 🎯 Features Working:

✅ User signup with password encryption  
✅ Email verification via Brevo  
✅ JWT token authentication  
✅ Protected routes  
✅ Resend verification email  
✅ Login prevention for unverified users  
✅ Modern responsive UI with TailwindCSS  
✅ Toast notifications  
✅ Form validation  
✅ Loading states

---

**Enjoy your secure authentication system! 🎉**
