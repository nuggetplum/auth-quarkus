# Email Service - Brevo Integration

Python Flask microservice for sending verification emails using Brevo (formerly Sendinblue).

## Setup Instructions

### 1. Install Python Dependencies

```powershell
cd email-service
pip install -r requirements.txt
```

### 2. Configure Environment Variables

Create a `.env` file (copy from `.env.example`):

```powershell
cp .env.example .env
```

Edit `.env` and add your credentials:

- **BREVO_API_KEY**: Get from https://app.brevo.com/settings/keys/api
- **SENDER_EMAIL**: Your verified sender email in Brevo
- **SENDER_NAME**: Your application name

### 3. Get Brevo API Key

1. Sign up at https://www.brevo.com
2. Go to Account → SMTP & API → API Keys
3. Create a new API key
4. Copy and paste into `.env` file

### 4. Verify Sender Email

In Brevo dashboard:

1. Go to Senders & IP
2. Add and verify your sender email address
3. Update `SENDER_EMAIL` in `.env`

### 5. Run the Service

```powershell
python app.py
```

Service will start on `http://localhost:5000`

## API Endpoints

### Health Check

```
GET /health
```

### Send Verification Email

```
POST /send-verification-email
Content-Type: application/json

{
  "email": "user@example.com",
  "verificationToken": "uuid-token-here"
}
```

Response:

```json
{
  "success": true,
  "message": "Verification email sent successfully",
  "messageId": "brevo-message-id"
}
```

## Testing

Test the email service:

```powershell
# Health check
Invoke-RestMethod -Uri http://localhost:5000/health

# Send test email
$body = @{
    email = "your-email@example.com"
    verificationToken = "test-token-123"
} | ConvertTo-Json

Invoke-RestMethod -Uri http://localhost:5000/send-verification-email `
  -Method Post `
  -ContentType "application/json" `
  -Body $body
```

## Integration with Quarkus

The Quarkus backend will call this service after user signup to send verification emails.
