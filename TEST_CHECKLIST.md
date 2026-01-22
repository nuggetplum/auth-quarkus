# ✅ Integration Testing Checklist

## Before Testing
- [ ] PostgreSQL running on port 5432
- [ ] Email service running on port 5000  
- [ ] Backend running on port 8080
- [ ] Frontend running on port 3000

## Test 1: User Signup
- [ ] Navigate to http://localhost:3000/signup
- [ ] Enter email: test@example.com
- [ ] Enter password: Test123
- [ ] Confirm password: Test123
- [ ] Click "Sign Up"
- [ ] Success toast appears
- [ ] Redirected to login page
- [ ] Email service console shows verification email sent

## Test 2: Email Verification
- [ ] Copy verification token from email service console
- [ ] Visit: http://localhost:3000/verify-email?token=<YOUR_TOKEN>
- [ ] "Email Verified!" message appears
- [ ] Green checkmark icon visible
- [ ] "Go to Login" button works

## Test 3: Login
- [ ] Navigate to http://localhost:3000/login
- [ ] Enter email: test@example.com
- [ ] Enter password: Test123
- [ ] Click "Login"
- [ ] Success toast appears
- [ ] Redirected to /dashboard
- [ ] Email displayed on dashboard

## Test 4: Protected Routes
- [ ] Open new incognito/private window
- [ ] Try to access http://localhost:3000/dashboard
- [ ] Automatically redirected to /login

## Test 5: LocalStorage
- [ ] Open Browser DevTools → Application → LocalStorage
- [ ] After login, verify `authToken` exists
- [ ] Verify `user` object with email exists
- [ ] Click Logout
- [ ] Verify both items cleared

## Test 6: Resend Verification
- [ ] Signup with new email (don't verify)
- [ ] Navigate to http://localhost:3000/resend-verification
- [ ] Enter the email address
- [ ] Click "Resend Verification Email"
- [ ] Success message appears
- [ ] Email service console shows new verification sent

## Test 7: Error Handling
- [ ] Try signup with existing email → Conflict error shown
- [ ] Try login with wrong password → Unauthorized error shown
- [ ] Try login before verification → Appropriate error shown
- [ ] All errors show as toast notifications

## Test 8: Form Validation
- [ ] Signup form: Try empty fields → Validation errors shown
- [ ] Signup form: Invalid email format → Error shown
- [ ] Signup form: Password < 6 chars → Error shown
- [ ] Signup form: Passwords don't match → Error shown
- [ ] Login form: Empty fields → Validation errors shown

## Test 9: Network Requests (DevTools)
- [ ] Open Network tab
- [ ] POST /signup returns 201 Created
- [ ] POST /login returns 200 OK
- [ ] Response contains token
- [ ] Authorization header present after login
- [ ] No CORS errors

## Test 10: User Experience
- [ ] Password visibility toggle works
- [ ] Loading spinners show during API calls
- [ ] Forms disable during submission
- [ ] Navigation links work correctly
- [ ] UI is responsive
- [ ] No console errors

---

## Expected Results Summary

✅ **Signup**: Creates user, sends verification email  
✅ **Verification**: Token validates, email marked as verified  
✅ **Login**: Returns JWT token, stores in localStorage  
✅ **Dashboard**: Shows user email, protected route  
✅ **Logout**: Clears token, redirects to login  
✅ **Resend**: Sends new verification email  
✅ **Errors**: User-friendly error messages  
✅ **Validation**: Real-time form validation  
✅ **Security**: Protected routes, token-based auth  

---

## If Something Fails

1. **Check backend logs** in Quarkus terminal
2. **Check browser console** for JavaScript errors
3. **Check network tab** for failed requests
4. **Verify CORS** configuration
5. **Check database** - PostgreSQL running?
6. **Check email service** - Python app running?
7. **Clear localStorage** and try again
8. **Restart all services** and test again

---

**Date Tested**: _______________  
**Tested By**: _______________  
**Status**: _______________  
**Notes**: _______________
