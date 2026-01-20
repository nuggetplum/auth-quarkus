# Quarkus Authentication System

A complete authentication system with email verification using Quarkus and Python microservices.

## Features

- User signup with encrypted passwords (BCrypt)
- JWT-based authentication
- Email verification with Brevo integration
- Resend verification email functionality
- Login prevention for unverified users

## Tech Stack

- **Backend**: Quarkus 3.30.6 (Java 21)
- **Database**: PostgreSQL
- **Email Service**: Python Flask + Brevo API
- **Security**: SmallRye JWT, BCrypt password hashing

## Prerequisites

- Java 21
- Maven 3.8+
- PostgreSQL
- Python 3.x
- Brevo API key

## Setup Instructions

### 1. Database Setup

Create PostgreSQL database:

```sql
CREATE DATABASE signupdb;
CREATE USER adi1 WITH PASSWORD 'pass';
GRANT ALL PRIVILEGES ON DATABASE signupdb TO adi1;
```

### 2. Configure Email Service

Create `email-service/.env` file:

```
BREVO_API_KEY=your_brevo_api_key_here
```

### 3. Generate JWT Keys

Navigate to `code-with-quarkus/src/main/resources/META-INF/resources/` and generate keys:

```bash
# Generate private key
openssl genrsa -out privateKey.pem 2048

# Generate public key
openssl rsa -in privateKey.pem -pubout -out publicKey.pem
```

## Running the Application

### Start Python Email Service

```powershell
cd email-service
python app.py
```

### Start Quarkus Application

```powershell
cd code-with-quarkus
mvn quarkus:dev
```

Application runs on: http://localhost:8080

## API Endpoints

### Authentication

- `POST /signup` - Create new user account
- `POST /login` - Login and get JWT token

### Email Verification

- `POST /verify-email` - Verify email with token
- `GET /verify-email/{token}` - Verify via URL link
- `POST /verify-email/resend` - Resend verification email

## Example Usage

### Signup

```powershell
Invoke-RestMethod -Uri http://localhost:8080/signup `
  -Method Post `
  -ContentType "application/json" `
  -Body '{"email":"user@example.com","password":"password123"}'
```

### Login

```powershell
Invoke-RestMethod -Uri http://localhost:8080/login `
  -Method Post `
  -ContentType "application/json" `
  -Body '{"email":"user@example.com","password":"password123"}'
```

### Resend Verification

```powershell
Invoke-RestMethod -Uri http://localhost:8080/verify-email/resend `
  -Method Post `
  -ContentType "application/json" `
  -Body '{"email":"user@example.com"}'
```

## Project Structure

```
trial1/
├── code-with-quarkus/          # Quarkus backend
│   ├── src/main/java/org/acme/
│   │   ├── entity/             # JPA entities
│   │   ├── repository/         # Data access
│   │   ├── service/            # Business logic
│   │   ├── resource/           # REST endpoints
│   │   └── dto/                # Data transfer objects
│   └── src/main/resources/
│       └── application.properties
└── email-service/              # Python email microservice
    ├── app.py
    └── .env
```

## Security Notes

- Passwords are hashed with BCrypt
- JWT tokens expire after 24 hours
- Verification tokens expire after 24 hours
- Unverified users cannot login
- Environment files with API keys are excluded from git

## License

This project is for educational purposes.
