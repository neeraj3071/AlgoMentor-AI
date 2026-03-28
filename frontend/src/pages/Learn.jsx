import { useNavigate } from 'react-router-dom'
import { BookOpen, Eye, Code2, ArrowRight } from 'lucide-react'

const topics = [
  {
    title: 'Sorting Fundamentals',
    description: 'Learn how comparison-based sorting works and when to use each approach.',
    route: '/visualizer/bubble-sort',
    cta: 'Open Visualizer'
  },
  {
    title: 'Searching Patterns',
    description: 'Understand linear vs binary search and the preconditions for each strategy.',
    route: '/visualizer/binary-search',
    cta: 'Start Exploring'
  },
  {
    title: 'Practice Track',
    description: 'Apply concepts by solving curated DSA problems with code execution support.',
    route: '/practice',
    cta: 'Solve Problems'
  }
]

export default function Learn() {
  const navigate = useNavigate()

  return (
    <div className="p-8 animate-fadeIn">
      <section className="mb-10">
        <div className="inline-flex items-center gap-2 px-3 py-1 rounded-full bg-primary/15 text-primary text-sm font-medium mb-4">
          <BookOpen size={16} />
          Guided Learning
        </div>
        <h1 className="text-4xl font-bold text-dark-text mb-3">Learn DSA Step by Step</h1>
        <p className="text-dark-text-secondary max-w-3xl">
          Choose a topic, watch the algorithm in action, then reinforce your understanding with coding practice.
        </p>
      </section>

      <section className="grid grid-cols-1 md:grid-cols-3 gap-6 mb-10">
        <div className="bg-dark-bg-secondary border border-dark-bg-tertiary rounded-xl p-6">
          <Eye className="text-primary mb-3" size={22} />
          <h2 className="text-lg font-semibold text-dark-text mb-2">Visual Learning</h2>
          <p className="text-dark-text-secondary text-sm">See data movement and decisions at each algorithm step.</p>
        </div>
        <div className="bg-dark-bg-secondary border border-dark-bg-tertiary rounded-xl p-6">
          <Code2 className="text-primary mb-3" size={22} />
          <h2 className="text-lg font-semibold text-dark-text mb-2">Hands-On Coding</h2>
          <p className="text-dark-text-secondary text-sm">Run and submit Java solutions directly in the platform.</p>
        </div>
        <div className="bg-dark-bg-secondary border border-dark-bg-tertiary rounded-xl p-6">
          <BookOpen className="text-primary mb-3" size={22} />
          <h2 className="text-lg font-semibold text-dark-text mb-2">Structured Path</h2>
          <p className="text-dark-text-secondary text-sm">Build concepts from fundamentals to advanced patterns.</p>
        </div>
      </section>

      <section>
        <h2 className="text-2xl font-bold text-dark-text mb-5">Pick Your Next Topic</h2>
        <div className="grid grid-cols-1 lg:grid-cols-3 gap-5">
          {topics.map((topic) => (
            <article
              key={topic.title}
              className="bg-dark-bg-secondary border border-dark-bg-tertiary rounded-xl p-6 hover:border-primary transition-colors"
            >
              <h3 className="text-xl font-semibold text-dark-text mb-2">{topic.title}</h3>
              <p className="text-dark-text-secondary mb-4">{topic.description}</p>
              <button
                onClick={() => navigate(topic.route)}
                className="inline-flex items-center gap-2 text-primary hover:text-primary-light font-medium transition-colors"
              >
                {topic.cta}
                <ArrowRight size={16} />
              </button>
            </article>
          ))}
        </div>
      </section>
    </div>
  )
}
