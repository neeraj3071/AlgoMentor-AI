import { useState, useEffect } from 'react'
import axios from 'axios'
import { Play, Pause, RotateCcw, Zap } from 'lucide-react'

export default function Home() {
  const [algorithms, setAlgorithms] = useState([
    { id: 1, name: 'Bubble Sort', category: 'Sorting', difficulty: 'Easy' },
    { id: 2, name: 'Merge Sort', category: 'Sorting', difficulty: 'Medium' },
    { id: 3, name: 'Quick Sort', category: 'Sorting', difficulty: 'Medium' },
    { id: 4, name: 'Binary Search', category: 'Searching', difficulty: 'Easy' },
    { id: 5, name: 'BFS', category: 'Graphs', difficulty: 'Medium' },
    { id: 6, name: 'DFS', category: 'Graphs', difficulty: 'Medium' },
  ])

  return (
    <div className="p-8">
      {/* Hero Section */}
      <section className="mb-16">
        <h1 className="text-5xl font-bold text-dark-text mb-4">
          Master Data Structures & Algorithms
        </h1>
        <p className="text-xl text-dark-text-secondary mb-8">
          Learn DSA through interactive visualizations, practice problems, and AI-powered explanations
        </p>
        <div className="flex gap-4">
          <button className="px-8 py-3 bg-primary hover:bg-primary-dark text-white rounded-lg font-semibold transition-colors">
            Start Learning
          </button>
          <button className="px-8 py-3 border border-primary text-primary hover:bg-primary/10 rounded-lg font-semibold transition-colors">
            View Problems
          </button>
        </div>
      </section>

      {/* Features Section */}
      <section className="mb-16">
        <h2 className="text-3xl font-bold text-dark-text mb-8">Features</h2>
        <div className="grid grid-cols-1 md:grid-cols-3 gap-6">
          {[
            { icon: '📊', title: 'Visualizations', desc: 'See algorithms execute step-by-step' },
            { icon: '💻', title: 'Code Practice', desc: 'Solve 100+ DSA problems' },
            { icon: '🤖', title: 'AI Tutor', desc: 'Get hints and explanations powered by Gemini' },
          ].map((feature, i) => (
            <div key={i} className="p-6 bg-dark-bg-secondary rounded-lg border border-dark-bg-tertiary hover:border-primary transition-colors">
              <div className="text-3xl mb-3">{feature.icon}</div>
              <h3 className="text-lg font-semibold text-dark-text mb-2">{feature.title}</h3>
              <p className="text-dark-text-secondary">{feature.desc}</p>
            </div>
          ))}
        </div>
      </section>

      {/* Algorithms Section */}
      <section>
        <h2 className="text-3xl font-bold text-dark-text mb-8">Popular Algorithms</h2>
        <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-4">
          {algorithms.map((algo) => (
            <div
              key={algo.id}
              className="p-6 bg-dark-bg-secondary rounded-lg border border-dark-bg-tertiary hover:border-primary cursor-pointer transition-colors group"
            >
              <h3 className="text-lg font-semibold text-dark-text group-hover:text-primary transition-colors">
                {algo.name}
              </h3>
              <div className="flex justify-between mt-2 text-sm text-dark-text-secondary">
                <span>{algo.category}</span>
                <span className={`px-2 py-1 rounded ${
                  algo.difficulty === 'Easy' ? 'bg-green-500/20 text-green-400' :
                  algo.difficulty === 'Medium' ? 'bg-yellow-500/20 text-yellow-400' :
                  'bg-red-500/20 text-red-400'
                }`}>
                  {algo.difficulty}
                </span>
              </div>
            </div>
          ))}
        </div>
      </section>
    </div>
  )
}
