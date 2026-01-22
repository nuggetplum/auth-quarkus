# Quick Start - Test the Complete System

Write-Host "==================================" -ForegroundColor Cyan
Write-Host "🚀 Starting Full Stack Application" -ForegroundColor Cyan
Write-Host "==================================" -ForegroundColor Cyan
Write-Host ""

# Function to check if a port is in use
function Test-Port {
    param($Port)
    $connection = Test-NetConnection -ComputerName localhost -Port $Port -WarningAction SilentlyContinue -InformationLevel Quiet
    return $connection
}

Write-Host "📋 Pre-flight Checks..." -ForegroundColor Yellow
Write-Host ""

# Check PostgreSQL
Write-Host "  Checking PostgreSQL (port 5432)..." -NoNewline
if (Test-Port 5432) {
    Write-Host " ✅" -ForegroundColor Green
} else {
    Write-Host " ❌ Not running!" -ForegroundColor Red
    Write-Host "  Please start PostgreSQL first" -ForegroundColor Red
}

# Check if ports are available
Write-Host "  Checking port 3000 (Frontend)..." -NoNewline
if (-not (Test-Port 3000)) {
    Write-Host " ✅ Available" -ForegroundColor Green
} else {
    Write-Host " ⚠️  In use" -ForegroundColor Yellow
}

Write-Host "  Checking port 5000 (Email Service)..." -NoNewline
if (-not (Test-Port 5000)) {
    Write-Host " ✅ Available" -ForegroundColor Green
} else {
    Write-Host " ⚠️  In use" -ForegroundColor Yellow
}

Write-Host "  Checking port 8080 (Backend)..." -NoNewline
if (-not (Test-Port 8080)) {
    Write-Host " ✅ Available" -ForegroundColor Green
} else {
    Write-Host " ⚠️  In use" -ForegroundColor Yellow
}

Write-Host ""
Write-Host "==================================" -ForegroundColor Cyan
Write-Host "📚 Quick Reference" -ForegroundColor Cyan
Write-Host "==================================" -ForegroundColor Cyan
Write-Host ""
Write-Host "Frontend:      " -NoNewline; Write-Host "http://localhost:3000" -ForegroundColor Green
Write-Host "Backend:       " -NoNewline; Write-Host "http://localhost:8080" -ForegroundColor Green
Write-Host "Email Service: " -NoNewline; Write-Host "http://localhost:5000" -ForegroundColor Green
Write-Host ""
Write-Host "Routes:" -ForegroundColor Yellow
Write-Host "  • Signup:     /signup"
Write-Host "  • Login:      /login"
Write-Host "  • Verify:     /verify-email"
Write-Host "  • Resend:     /resend-verification"
Write-Host "  • Dashboard:  /dashboard"
Write-Host ""
Write-Host "Test Credentials:" -ForegroundColor Yellow
Write-Host "  Email:    test@example.com"
Write-Host "  Password: Test123"
Write-Host ""
Write-Host "==================================" -ForegroundColor Cyan
Write-Host "🎯 Start Services Manually" -ForegroundColor Cyan
Write-Host "==================================" -ForegroundColor Cyan
Write-Host ""
Write-Host "Terminal 1 - Email Service:" -ForegroundColor Yellow
Write-Host "  cd email-service"
Write-Host "  python app.py"
Write-Host ""
Write-Host "Terminal 2 - Backend:" -ForegroundColor Yellow
Write-Host "  cd code-with-quarkus"
Write-Host "  ./mvnw clean quarkus:dev"
Write-Host ""
Write-Host "Terminal 3 - Frontend:" -ForegroundColor Yellow
Write-Host "  cd frontend"
Write-Host "  npm run dev"
Write-Host ""
Write-Host "==================================" -ForegroundColor Cyan
Write-Host ""

$response = Read-Host "Do you want to start the Frontend now? (y/n)"
if ($response -eq 'y' -or $response -eq 'Y') {
    Write-Host ""
    Write-Host "Starting Frontend..." -ForegroundColor Green
    cd "c:\oppex assignment\trial1_frontend\frontend"
    npm run dev
}
