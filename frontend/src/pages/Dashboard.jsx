import { useEffect, useState } from 'react'
import apiClient from '../services/api'

export default function Dashboard() {
  const [user, setUser] = useState(null)
  const [stats, setStats] = useState(null)
  const [loading, setLoading] = useState(true)

  useEffect(() => {
    fetchUserData()
  }, [])

  const fetchUserData = async () => {
    try {
      const userData = JSON.parse(localStorage.getItem('user') || '{}')
      setUser(userData)

      if (userData.id) {
        const response = await apiClient.get(`/user/${userData.id}`)
        setStats(response.data)
      }
    } catch (error) {
      console.error('Error fetching user data:', error)
    } finally {
      setLoading(false)
    }
  }

  if (loading) return <div className="p-8 text-dark-text">Loading...</div>

  return (
    <div className="p-8">
      <h1 className="text-4xl font-bold text-dark-text mb-8">Dashboard</h1>

      {/* User Stats */}
      <div className="grid grid-cols-1 md:grid-cols-4 gap-6 mb-12">
        <div className="p-6 bg-dark-bg-secondary rounded-lg border border-dark-bg-tertiary">
          <p className="text-dark-text-secondary text-sm mb-2">Problems Solved</p>
          <p className="text-4xl font-bold text-primary">{stats?.problemsSolved ?? user?.problemsSolved ?? 0}</p>
        </div>
        <div className="p-6 bg-dark-bg-secondary rounded-lg border border-dark-bg-tertiary">
          <p className="text-dark-text-secondary text-sm mb-2">Accuracy</p>
          <p className="text-4xl font-bold text-primary">{(stats?.averageAccuracy ?? user?.averageAccuracy ?? 0).toFixed(1)}%</p>
        </div>
        <div className="p-6 bg-dark-bg-secondary rounded-lg border border-dark-bg-tertiary">
          <p className="text-dark-text-secondary text-sm mb-2">Streak</p>
          <p className="text-4xl font-bold text-primary">0</p>
        </div>
        <div className="p-6 bg-dark-bg-secondary rounded-lg border border-dark-bg-tertiary">
          <p className="text-dark-text-secondary text-sm mb-2">Rank</p>
          <p className="text-4xl font-bold text-primary">—</p>
        </div>
      </div>

      {/* Recent Activity */}
      <div className="grid grid-cols-1 lg:grid-cols-3 gap-6">
        <div className="lg:col-span-2 p-6 bg-dark-bg-secondary rounded-lg border border-dark-bg-tertiary">
          <h2 className="text-2xl font-semibold text-dark-text mb-6">Recent Submissions</h2>
          <div className="text-dark-text-secondary text-center py-8">No recent submissions</div>
        </div>

        <div className="p-6 bg-dark-bg-secondary rounded-lg border border-dark-bg-tertiary">
          <h2 className="text-2xl font-semibold text-dark-text mb-6">Category Stats</h2>
          <div className="space-y-3">
            {['Arrays', 'Strings', 'Trees', 'Graphs'].map(cat => (
              <div key={cat} className="flex justify-between text-dark-text-secondary">
                <span>{cat}</span>
                <span>0/10</span>
              </div>
            ))}
          </div>
        </div>
      </div>
    </div>
  )
}
