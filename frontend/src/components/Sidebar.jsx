import { Link, useLocation } from 'react-router-dom'
import { Home, Eye, Code2, BarChart2, BookOpen, X } from 'lucide-react'

const menuItems = [
  { icon: Home, label: 'Home', path: '/' },
  { icon: Eye, label: 'Visualizer', path: '/visualizer/bubble-sort' },
  { icon: Code2, label: 'Practice', path: '/practice' },
  { icon: BookOpen, label: 'Learn', path: '/learn' },
  { icon: BarChart2, label: 'Dashboard', path: '/dashboard' },
]

export default function Sidebar({ open, setOpen }) {
  const location = useLocation()

  return (
    <>
      {/* Overlay */}
      {open && (
        <div
          className="fixed inset-0 bg-black/50 lg:hidden z-30"
          onClick={() => setOpen(false)}
        />
      )}

      {/* Sidebar */}
      <aside
        className={`fixed lg:relative w-64 h-screen bg-dark-bg-secondary border-r border-dark-bg-tertiary transform transition-transform duration-300 z-40 flex flex-col ${
          open ? 'translate-x-0' : '-translate-x-full lg:translate-x-0'
        }`}
      >
        {/* Header */}
        <div className="flex items-center justify-between p-6 border-b border-dark-bg-tertiary">
          <h1 className="text-lg font-bold text-dark-text">DSA</h1>
          <button
            onClick={() => setOpen(false)}
            className="lg:hidden p-2 hover:bg-dark-bg-tertiary rounded-lg"
          >
            <X size={20} className="text-dark-text" />
          </button>
        </div>

        {/* Menu Items */}
        <nav className="flex-1 overflow-y-auto p-4 space-y-2">
          {menuItems.map((item) => {
            const Icon = item.icon
            const isActive = location.pathname === item.path
            return (
              <Link
                key={item.path}
                to={item.path}
                onClick={() => setOpen(false)}
                className={`flex items-center gap-3 px-4 py-3 rounded-lg transition-colors ${
                  isActive
                    ? 'bg-primary text-white'
                    : 'text-dark-text hover:bg-dark-bg-tertiary'
                }`}
              >
                <Icon size={20} />
                <span>{item.label}</span>
              </Link>
            )
          })}
        </nav>

        {/* Footer */}
        <div className="border-t border-dark-bg-tertiary p-4">
          <p className="text-xs text-dark-text-secondary">v1.0.0</p>
        </div>
      </aside>
    </>
  )
}
