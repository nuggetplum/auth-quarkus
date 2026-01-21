import { useState, useEffect } from "react";
import { useParams, useNavigate, Link } from "react-router-dom";
import { toast } from "react-toastify";
import { authAPI } from "../services/api";

const EmailVerification = () => {
  const [status, setStatus] = useState("verifying"); // verifying, success, error
  const [message, setMessage] = useState("");
  const { token } = useParams();
  const navigate = useNavigate();

  useEffect(() => {
    if (token) {
      verifyEmail();
    }
  }, [token]);

  const verifyEmail = async () => {
    try {
      const response = await authAPI.verifyEmailByLink(token);
      setStatus("success");
      setMessage(response.data.message || "Email verified successfully!");
      toast.success("Email verified! You can now login.");
      setTimeout(() => navigate("/login"), 3000);
    } catch (error) {
      setStatus("error");
      const errorMsg =
        error.response?.data?.message ||
        error.response?.data ||
        "Verification failed.";
      setMessage(errorMsg);
      toast.error(errorMsg);
    }
  };

  return (
    <div className="min-h-screen bg-gradient-to-br from-blue-50 to-indigo-100 flex items-center justify-center py-12 px-4">
      <div className="max-w-md w-full bg-white rounded-xl shadow-2xl p-8">
        <div className="text-center">
          {status === "verifying" && (
            <>
              <div className="flex justify-center mb-6">
                <svg
                  className="animate-spin h-16 w-16 text-blue-600"
                  viewBox="0 0 24 24"
                >
                  <circle
                    className="opacity-25"
                    cx="12"
                    cy="12"
                    r="10"
                    stroke="currentColor"
                    strokeWidth="4"
                    fill="none"
                  />
                  <path
                    className="opacity-75"
                    fill="currentColor"
                    d="M4 12a8 8 0 018-8V0C5.373 0 0 5.373 0 12h4zm2 5.291A7.962 7.962 0 014 12H0c0 3.042 1.135 5.824 3 7.938l3-2.647z"
                  />
                </svg>
              </div>
              <h2 className="text-2xl font-bold text-gray-800">
                Verifying Email...
              </h2>
              <p className="text-gray-600 mt-2">
                Please wait while we verify your email address.
              </p>
            </>
          )}

          {status === "success" && (
            <>
              <div className="text-6xl mb-6">✅</div>
              <h2 className="text-2xl font-bold text-green-600">
                Email Verified!
              </h2>
              <p className="text-gray-600 mt-2">{message}</p>
              <p className="text-gray-500 mt-4">Redirecting to login...</p>
              <Link
                to="/login"
                className="inline-block mt-6 px-6 py-3 bg-blue-600 text-white rounded-lg hover:bg-blue-700 transition"
              >
                Go to Login
              </Link>
            </>
          )}

          {status === "error" && (
            <>
              <div className="text-6xl mb-6">❌</div>
              <h2 className="text-2xl font-bold text-red-600">
                Verification Failed
              </h2>
              <p className="text-gray-600 mt-2">{message}</p>
              <div className="mt-6 space-y-3">
                <Link
                  to="/resend-verification"
                  className="block px-6 py-3 bg-blue-600 text-white rounded-lg hover:bg-blue-700 transition"
                >
                  Resend Verification Email
                </Link>
                <Link
                  to="/login"
                  className="block px-6 py-3 bg-gray-200 text-gray-700 rounded-lg hover:bg-gray-300 transition"
                >
                  Back to Login
                </Link>
              </div>
            </>
          )}
        </div>
      </div>
    </div>
  );
};

export default EmailVerification;
