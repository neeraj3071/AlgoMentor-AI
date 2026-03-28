import { useState } from 'react'
import { useNavigate } from 'react-router-dom'
import axios from 'axios'

export default function Signup() {
  const [formData, setFormData] = useState({
    username: '',
    email: '',
    password: '',
    fullName: '',
  })
  const [loading, setLoading] = useState(false)
  const [error, setError] = useState('')
  const navigate = useNavigate()

  const handleChange = (e) => {
    setFormData({ ...formData, [e.target.name]: e.target.value })
  }

  const handleSubmit = async (e) => {
    e.preventDefault()
    setLoading(true)
    setError('')

    try {
      await axios.post('/api/auth/signup', formData)
      navigate('/login')
    } catch (err) {
      setError(err.response?.data?.message || 'Signup failed')
    } finally {
      setLoading(false)
    }
  }

  return (
    <div className="min-h-screen bg-dark-bg flex items-center justify-center px-4">
      <div className="w-full max-w-md bg-dark-bg-secondary rounded-lg shadow-lg p-8">
        <h1 className="text-2xl font-bold text-dark-text mb-6 text-center">Sign Up</h1>

        {error && (
          <div className="mb-4 p-3 bg-red-500/20 border border-red-500 rounded-lg text-red-500 text-sm">
            {error}
          </div>
        )}

        <form onSubmit={handleSubmit} className="space-y-4">
          <div>
            <label className="block text-dark-text-secondary text-sm mb-2">
              Full Name
            </label>
            <input
              type="text"
              name="fullName"
              value={formData.fullName}
              onChange={handleChange}
              className="w-full px-4 py-2 bg-dark-bg-tertiary border border-dark-bg-tertiary rounded-lg text-dark-text focus:outline-none focus:border-primary transition-colors"
              placeholder="Enter your full name"
              required
            />
          </div>

          <div>
            <label className="block text-dark-text-secondary text-sm mb-2">
              Username
            </label>
            <input
              type="text"
              name="username"
              value={formData.username}
              onChange={handleChange}
              className="w-full px-4 py-2 bg-dark-bg-tertiary border border-dark-bg-tertiary rounded-lg text-dark-text focus:outline-none focus:border-primary transition-colors"
              placeholder="Choose a username"
              required
            />
          </div>

          <div>
            <label className="block text-dark-text-secondary text-sm mb-2">
              Email
            </label>
            <input
              type="email"
              name="email"
              value={formData.email}
              onChange={handleChange}
              className="w-full px-4 py-2 bg-dark-bg-tertiary border border-dark-bg-tertiary rounded-lg text-dark-text focus:outline-none focus:border-primary transition-colors"
              placeholder="Enter your email"
              required
            />
          </div>

          <div>
            <label className="block text-dark-text-secondary text-sm mb-2">
              Password
            </label>
            <input
              type="password"
              name="password"
              value={formData.password}
              onChange={handleChange}
              className="w-full px-4 py-2 bg-dark-bg-tertiary border border-dark-bg-tertiary rounded-lg text-dark-text focus:outline-none focus:border-primary transition-colors"
              placeholder="Create a password"
              required
            />
          </div>

          <button
            type="submit"
            disabled={loading}
            className="w-full py-2 bg-primary hover:bg-primary-dark text-white rounded-lg font-semibold transition-colors disabled:opacity-50"
          >
            {loading ? 'Creating account...' : 'Sign Up'}
          </button>
        </form>

        <div className="mt-6 text-center text-dark-text-secondary">
          Already have an account?{' '}
          <button
            onClick={() => navigate('/login')}
            className="text-primary hover:underline"
          >
            Log in
          </button>
        </div>
      </div>
    </div>
  )
}
