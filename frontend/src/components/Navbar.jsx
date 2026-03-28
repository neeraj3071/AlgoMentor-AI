import { Link, useNavigate } from 'react-router-dom'
import { Menu, LogOut, User, BarChart3 } from 'lucide-react'
import { useState } from 'react'

export default function Navbar({ sidebarOpen, setSidebarOpen }) {
  const navigate = useNavigate()
  const [showUserMenu, setShowUserMenu] = useState(false)

  const handleLogout = () => {
    localStorage.removeItem('token')
    navigate('/login')
  }

  return (
    <nav className="bg-dark-bg-secondary border-b border-dark-bg-tertiary h-16 flex items-center px-6 sticky top-0 z-40">
      <button
        onClick={() => setSidebarOpen(!sidebarOpen)}
        className="p-2 hover:bg-dark-bg-tertiary rounded-lg transition-colors"
      >
        <Menu size={20} className="text-dark-text" />
      </button>

      <div className="flex-1 ml-4">
        <Link to="/" className="flex items-center gap-2">
          <BarChart3 size={24} className="text-primary" />
          <span className="text-xl font-bold text-dark-text">DSA Visualizer</span>
        </Link>
      </div>

      {/* User Menu */}
      <div className="relative">
        <button
          onClick={() => setShowUserMenu(!showUserMenu)}
          className="flex items-center gap-2 px-3 py-2 hover:bg-dark-bg-tertiary rounded-lg transition-colors"
        >
          <User size={20} className="text-primary" />
          <span className="text-darktext">Profile</span>
        </button>

        {showUserMenu && (
          <div className="absolute right-0 mt-2 w-48 bg-dark-bg-secondary rounded-lg shadow-lg border border-dark-bg-tertiary">
            <button
              onClick={() => navigate('/dashboard')}
              className="w-full text-left px-4 py-2 hover:bg-dark-bg-tertiary text-dark-text"
            >
              Dashboard
            </button>
            <button
              onClick={handleLogout}
              className="w-full text-left px-4 py-2 hover:bg-dark-bg-tertiary text-red-500 flex items-center gap-2"
            >
              <LogOut size={16} /> Logout
            </button>
          </div>
        )}
      </div>
    </nav>
  )
}
