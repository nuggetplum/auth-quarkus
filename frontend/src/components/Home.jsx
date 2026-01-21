import { Link } from "react-router-dom";

const Home = () => {
  return (
    <div className="min-h-screen bg-gradient-to-br from-blue-50 to-indigo-100">
      <div className="container mx-auto px-4 py-16">
        <div className="max-w-4xl mx-auto text-center">
          <div className="text-7xl mb-8">🔐</div>
          <h1 className="text-5xl font-bold text-gray-800 mb-6">
            Secure Authentication System
          </h1>
          <p className="text-xl text-gray-600 mb-12">
            Complete signup and login system with email verification powered by
            Quarkus & React
          </p>

          <div className="flex justify-center gap-4 mb-16">
            <Link
              to="/signup"
              className="px-8 py-4 bg-gradient-to-r from-blue-600 to-blue-700 text-white rounded-lg font-semibold text-lg hover:from-blue-700 hover:to-blue-800 transition shadow-lg"
            >
              Get Started
            </Link>
            <Link
              to="/login"
              className="px-8 py-4 bg-white text-blue-600 rounded-lg font-semibold text-lg hover:bg-gray-50 transition shadow-lg border-2 border-blue-600"
            >
              Login
            </Link>
          </div>

          <div className="grid md:grid-cols-3 gap-8 mt-16">
            <div className="bg-white rounded-xl shadow-lg p-8">
              <div className="text-4xl mb-4">🔒</div>
              <h3 className="text-xl font-bold text-gray-800 mb-2">Secure</h3>
              <p className="text-gray-600">
                Passwords encrypted with BCrypt. JWT token authentication with
                24-hour expiry.
              </p>
            </div>
            <div className="bg-white rounded-xl shadow-lg p-8">
              <div className="text-4xl mb-4">📧</div>
              <h3 className="text-xl font-bold text-gray-800 mb-2">
                Email Verified
              </h3>
              <p className="text-gray-600">
                Email verification system using Brevo. Unverified users cannot
                login.
              </p>
            </div>
            <div className="bg-white rounded-xl shadow-lg p-8">
              <div className="text-4xl mb-4">⚡</div>
              <h3 className="text-xl font-bold text-gray-800 mb-2">
                Fast & Modern
              </h3>
              <p className="text-gray-600">
                Built with Quarkus backend and React frontend with TailwindCSS.
              </p>
            </div>
          </div>

          <div className="mt-16 bg-white rounded-xl shadow-lg p-8">
            <h2 className="text-2xl font-bold text-gray-800 mb-4">Features</h2>
            <div className="grid md:grid-cols-2 gap-4 text-left">
              <div className="flex items-start">
                <span className="text-green-500 mr-2">✓</span>
                <span className="text-gray-700">
                  User registration with email validation
                </span>
              </div>
              <div className="flex items-start">
                <span className="text-green-500 mr-2">✓</span>
                <span className="text-gray-700">
                  Secure password hashing (BCrypt)
                </span>
              </div>
              <div className="flex items-start">
                <span className="text-green-500 mr-2">✓</span>
                <span className="text-gray-700">
                  JWT token-based authentication
                </span>
              </div>
              <div className="flex items-start">
                <span className="text-green-500 mr-2">✓</span>
                <span className="text-gray-700">
                  Email verification with Brevo
                </span>
              </div>
              <div className="flex items-start">
                <span className="text-green-500 mr-2">✓</span>
                <span className="text-gray-700">Resend verification email</span>
              </div>
              <div className="flex items-start">
                <span className="text-green-500 mr-2">✓</span>
                <span className="text-gray-700">
                  Protected routes & dashboard
                </span>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
};

export default Home;
