from flask import Flask, request, jsonify
from flask_cors import CORS
import sib_api_v3_sdk
from sib_api_v3_sdk.rest import ApiException
import os
from dotenv import load_dotenv

# Load environment variables
load_dotenv()

app = Flask(__name__)
CORS(app)

# Configure Brevo API
configuration = sib_api_v3_sdk.Configuration()
configuration.api_key['api-key'] = os.getenv('BREVO_API_KEY')
api_instance = sib_api_v3_sdk.TransactionalEmailsApi(sib_api_v3_sdk.ApiClient(configuration))

# Configuration
SENDER_EMAIL = os.getenv('SENDER_EMAIL')
SENDER_NAME = os.getenv('SENDER_NAME', 'Authentication Service')
FRONTEND_URL = os.getenv('FRONTEND_URL', 'http://localhost:8080')


@app.route('/health', methods=['GET'])
def health_check():
    """Health check endpoint"""
    return jsonify({
        'status': 'healthy',
        'service': 'email-service'
    }), 200


@app.route('/send-verification-email', methods=['POST'])
def send_verification_email():
    """
    Send verification email
    Expected JSON body:
    {
        "email": "user@example.com",
        "verificationToken": "uuid-token"
    }
    """
    try:
        data = request.get_json()
        
        # Validate request
        if not data:
            return jsonify({'error': 'No data provided'}), 400
        
        email = data.get('email')
        verification_token = data.get('verificationToken')
        
        if not email or not verification_token:
            return jsonify({'error': 'Email and verificationToken are required'}), 400
        
        # Create verification link
        verification_link = f"{FRONTEND_URL}/verify-email/{verification_token}"
        
        # Prepare email
        sender = {"name": SENDER_NAME, "email": SENDER_EMAIL}
        to = [{"email": email}]
        
        # Email content
        subject = "Verify Your Email Address"
        html_content = f"""
        <!DOCTYPE html>
        <html>
        <head>
            <style>
                body {{
                    font-family: Arial, sans-serif;
                    line-height: 1.6;
                    color: #333;
                }}
                .container {{
                    max-width: 600px;
                    margin: 0 auto;
                    padding: 20px;
                }}
                .header {{
                    background-color: #4CAF50;
                    color: white;
                    padding: 20px;
                    text-align: center;
                }}
                .content {{
                    padding: 20px;
                    background-color: #f9f9f9;
                }}
                .button {{
                    display: inline-block;
                    padding: 12px 30px;
                    margin: 20px 0;
                    background-color: #4CAF50;
                    color: white;
                    text-decoration: none;
                    border-radius: 5px;
                }}
                .footer {{
                    padding: 20px;
                    text-align: center;
                    font-size: 12px;
                    color: #777;
                }}
            </style>
        </head>
        <body>
            <div class="container">
                <div class="header">
                    <h1>Welcome!</h1>
                </div>
                <div class="content">
                    <h2>Please verify your email address</h2>
                    <p>Thank you for signing up! To complete your registration, please verify your email address by clicking the button below:</p>
                    <p style="text-align: center;">
                        <a href="{verification_link}" class="button">Verify Email</a>
                    </p>
                    <p>Or copy and paste this link into your browser:</p>
                    <p style="word-break: break-all; color: #4CAF50;">{verification_link}</p>
                    <p><strong>This link will expire in 24 hours.</strong></p>
                    <p>If you didn't create an account, you can safely ignore this email.</p>
                </div>
                <div class="footer">
                    <p>&copy; 2026 Your Application. All rights reserved.</p>
                </div>
            </div>
        </body>
        </html>
        """
        
        # Send email via Brevo
        send_smtp_email = sib_api_v3_sdk.SendSmtpEmail(
            to=to,
            sender=sender,
            subject=subject,
            html_content=html_content
        )
        
        api_response = api_instance.send_transac_email(send_smtp_email)
        
        return jsonify({
            'success': True,
            'message': 'Verification email sent successfully',
            'messageId': api_response.message_id
        }), 200
        
    except ApiException as e:
        print(f"Brevo API Exception: {e}")
        return jsonify({
            'success': False,
            'error': f'Failed to send email: {str(e)}'
        }), 500
    except Exception as e:
        print(f"Error: {e}")
        return jsonify({
            'success': False,
            'error': f'Internal server error: {str(e)}'
        }), 500


if __name__ == '__main__':
    port = int(os.getenv('FLASK_PORT', 5000))
    host = os.getenv('FLASK_HOST', '0.0.0.0')
    
    print(f"Starting Email Service on {host}:{port}")
    print(f"Sender Email: {SENDER_EMAIL}")
    print(f"Frontend URL: {FRONTEND_URL}")
    
    app.run(host=host, port=port, debug=True)
