import { useState, useEffect } from 'react'
import axios from 'axios'
import { Search, Filter } from 'lucide-react'

export default function PracticeProblems() {
  const [problems, setProblems] = useState([])
  const [loading, setLoading] = useState(true)
  const [searchTerm, setSearchTerm] = useState('')
  const [categoryFilter, setCategoryFilter] = useState('ALL')
  const [difficultyFilter, setDifficultyFilter] = useState('ALL')

  useEffect(() => {
    fetchProblems()
  }, [])

  const fetchProblems = async () => {
    try {
      const response = await axios.get('/api/problems')
      setProblems(response.data)
    } catch (error) {
      console.error('Error fetching problems:', error)
    } finally {
      setLoading(false)
    }
  }

  const categories = ['ALL', 'ARRAYS', 'STRINGS', 'LINKED_LIST', 'TREES', 'GRAPHS', 'DYNAMIC_PROGRAMMING']
  const difficulties = ['ALL', 'EASY', 'MEDIUM', 'HARD', 'EXPERT']

  const getDifficultyColor = (difficulty) => {
    switch (difficulty) {
      case 'EASY': return 'bg-green-500/20 text-green-400'
      case 'MEDIUM': return 'bg-yellow-500/20 text-yellow-400'
      case 'HARD': return 'bg-red-500/20 text-red-400'
      case 'EXPERT': return 'bg-purple-500/20 text-purple-400'
      default: return ''
    }
  }

  return (
    <div className="p-8">
      <h1 className="text-4xl font-bold text-dark-text mb-8">Practice Problems</h1>

      {/* Filters */}
      <div className="mb-8 space-y-4">
        <div className="flex gap-4 flex-wrap">
          {/* Search */}
          <div className="flex-1 min-w-64 relative">
            <Search className="absolute left-3 top-3 text-dark-text-secondary" size={18} />
            <input
              type="text"
              placeholder="Search problems..."
              value={searchTerm}
              onChange={(e) => setSearchTerm(e.target.value)}
              className="w-full pl-10 pr-4 py-2 bg-dark-bg-secondary border border-dark-bg-tertiary rounded-lg text-dark-text placeholder-dark-text-secondary focus:outline-none focus:border-primary transition-colors"
            />
          </div>

          {/* Category Filter */}
          <select
            value={categoryFilter}
            onChange={(e) => setCategoryFilter(e.target.value)}
            className="px-4 py-2 bg-dark-bg-secondary border border-dark-bg-tertiary rounded-lg text-dark-text focus:outline-none focus:border-primary transition-colors"
          >
            {categories.map(cat => (
              <option key={cat} value={cat}>{cat}</option>
            ))}
          </select>

          {/* Difficulty Filter */}
          <select
            value={difficultyFilter}
            onChange={(e) => setDifficultyFilter(e.target.value)}
            className="px-4 py-2 bg-dark-bg-secondary border border-dark-bg-tertiary rounded-lg text-dark-text focus:outline-none focus:border-primary transition-colors"
          >
            {difficulties.map(diff => (
              <option key={diff} value={diff}>{diff}</option>
            ))}
          </select>
        </div>
      </div>

      {/* Problems Table */}
      <div className="overflow-x-auto bg-dark-bg-secondary rounded-lg border border-dark-bg-tertiary">
        <table className="w-full">
          <thead className="bg-dark-bg-tertiary border-b border-dark-bg-tertiary">
            <tr>
              <th className="px-6 py-4 text-left text-dark-text-secondary font-semibold">Title</th>
              <th className="px-6 py-4 text-left text-dark-text-secondary font-semibold">Category</th>
              <th className="px-6 py-4 text-left text-dark-text-secondary font-semibold">Difficulty</th>
              <th className="px-6 py-4 text-left text-dark-text-secondary font-semibold">Acceptance</th>
              <th className="px-6 py-4 text-left text-dark-text-secondary font-semibold">Action</th>
            </tr>
          </thead>
          <tbody>
            {problems.map((problem) => (
              <tr key={problem.id} className="border-b border-dark-bg-tertiary hover:bg-dark-bg-tertiary transition-colors">
                <td className="px-6 py-4 text-dark-text font-medium">{problem.title}</td>
                <td className="px-6 py-4 text-dark-text-secondary">{problem.category}</td>
                <td className="px-6 py-4">
                  <span className={`px-3 py-1 rounded-full text-sm font-medium ${getDifficultyColor(problem.difficulty)}`}>
                    {problem.difficulty}
                  </span>
                </td>
                <td className="px-6 py-4 text-dark-text-secondary">
                  {problem.acceptanceRate?.toFixed(1) || 0}%
                </td>
                <td className="px-6 py-4">
                  <button className="text-primary hover:text-primary-light transition-colors">
                    Solve →
                  </button>
                </td>
              </tr>
            ))}
          </tbody>
        </table>
      </div>
    </div>
  )
}
