import { useAuth } from '../context/AuthContext';

const Dashboard = () => {
  const { user, logout } = useAuth();

  return (
    <div className="min-h-screen bg-gradient-to-br from-blue-50 to-indigo-100 py-12 px-4">
      <div className="max-w-4xl mx-auto">
        <div className="bg-white rounded-xl shadow-2xl p-8">
          <div className="text-center mb-8">
            <div className="text-6xl mb-4">🎉</div>
            <h1 className="text-4xl font-bold text-gray-800">Welcome to Dashboard</h1>
            <p className="text-gray-600 mt-2">You have successfully logged in!</p>
          </div>

          <div className="bg-blue-50 rounded-lg p-6 mb-8">
            <h2 className="text-xl font-semibold text-gray-800 mb-4">Account Information</h2>
            <div className="space-y-2">
              <p className="text-gray-700">
                <span className="font-semibold">Email:</span> {user?.email}
              </p>
              <p className="text-gray-700">
                <span className="font-semibold">Status:</span>{' '}
                <span className="text-green-600 font-semibold">✓ Verified</span>
              </p>
            </div>
          </div>

          <div className="grid md:grid-cols-3 gap-4 mb-8">
            <div className="bg-gradient-to-br from-blue-500 to-blue-600 text-white rounded-lg p-6 text-center">
              <div className="text-3xl mb-2">🔐</div>
              <h3 className="font-semibold mb-1">Secure</h3>
              <p className="text-sm text-blue-100">Password encrypted with BCrypt</p>
            </div>
            <div className="bg-gradient-to-br from-green-500 to-green-600 text-white rounded-lg p-6 text-center">
              <div className="text-3xl mb-2">✉️</div>
              <h3 className="font-semibold mb-1">Verified</h3>
              <p className="text-sm text-green-100">Email verified successfully</p>
            </div>
            <div className="bg-gradient-to-br from-purple-500 to-purple-600 text-white rounded-lg p-6 text-center">
              <div className="text-3xl mb-2">🎯</div>
              <h3 className="font-semibold mb-1">JWT Auth</h3>
              <p className="text-sm text-purple-100">Token-based authentication</p>
            </div>
          </div>

          <div className="text-center">
            <button
              onClick={logout}
              className="px-8 py-3 bg-red-500 text-white rounded-lg hover:bg-red-600 transition font-semibold"
            >
              Logout
            </button>
          </div>
        </div>
      </div>
    </div>
  );
};

export default Dashboard;
