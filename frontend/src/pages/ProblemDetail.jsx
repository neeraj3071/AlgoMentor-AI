import { useState, useEffect } from 'react'
import { useParams } from 'react-router-dom'
import Editor from '@monaco-editor/react'
import { Play, RotateCcw, Zap } from 'lucide-react'
import apiClient from '../services/api'

export default function ProblemDetail() {
  const { id } = useParams()
  const [problem, setProblem] = useState(null)
  const [code, setCode] = useState('')
  const [output, setOutput] = useState('')
  const [loading, setLoading] = useState(false)
  const [submitting, setSubmitting] = useState(false)

  useEffect(() => {
    fetchProblem()
  }, [id])

  const fetchProblem = async () => {
    try {
      const response = await apiClient.get(`/problems/${id}`)
      setProblem(response.data)
      setCode(response.data.boilerplateCode || '')
    } catch (error) {
      console.error('Error fetching problem:', error)
    }
  }

  const handleRun = async () => {
    setLoading(true)
    try {
      const response = await apiClient.post('/execute', {
        problemId: Number(id),
        code: code,
        language: 'java'
      })
      setOutput(response.data.output || response.data.errorMessage)
    } catch (error) {
      setOutput(error.response?.data?.message || ('Error executing code: ' + error.message))
    } finally {
      setLoading(false)
    }
  }

  const handleSubmit = async () => {
    setSubmitting(true)
    try {
      const response = await apiClient.post('/execute', {
        problemId: Number(id),
        code: code,
        language: 'java'
      })
      setOutput(`Submission result: ${response.data.status}`)
    } catch (error) {
      setOutput(error.response?.data?.message || ('Submission error: ' + error.message))
    } finally {
      setSubmitting(false)
    }
  }

  if (!problem) return <div className="p-8 text-dark-text">Loading...</div>

  return (
    <div className="flex h-full">
      {/* Left Panel - Problem Details */}
      <div className="w-1/2 p-6 border-r border-dark-bg-tertiary overflow-auto">
        <div className="mb-6">
          <div className="flex items-center gap-3 mb-2">
            <h1 className="text-3xl font-bold text-dark-text">{problem.title}</h1>
            <span className="px-3 py-1 bg-yellow-500/20 text-yellow-400 rounded-full text-sm font-medium">
              {problem.difficulty}
            </span>
          </div>
          <p className="text-dark-text-secondary">Category: {problem.category}</p>
        </div>

        {/* Description */}
        <div className="mb-6">
          <h2 className="text-xl font-semibold text-dark-text mb-3">Description</h2>
          <p className="text-dark-text-secondary">{problem.description}</p>
        </div>

        {/* Examples */}
        <div className="mb-6">
          <h2 className="text-xl font-semibold text-dark-text mb-3">Examples</h2>
          <pre className="bg-dark-bg p-4 rounded-lg text-sm text-dark-text overflow-x-auto">
            {problem.examples}
          </pre>
        </div>

        {/* Constraints */}
        <div className="mb-6">
          <h2 className="text-xl font-semibold text-dark-text mb-3">Constraints</h2>
          <pre className="bg-dark-bg p-4 rounded-lg text-sm text-dark-text-secondary overflow-x-auto">
            {problem.constraints}
          </pre>
        </div>

        {/* Stats */}
        <div className="grid grid-cols-2 gap-4">
          <div className="p-4 bg-dark-bg-secondary rounded-lg">
            <p className="text-dark-text-secondary text-sm">Submissions</p>
            <p className="text-2xl font-bold text-dark-text">{problem.submissionsCount}</p>
          </div>
          <div className="p-4 bg-dark-bg-secondary rounded-lg">
            <p className="text-dark-text-secondary text-sm">Acceptance Rate</p>
            <p className="text-2xl font-bold text-dark-text">{problem.acceptanceRate?.toFixed(1) || 0}%</p>
          </div>
        </div>
      </div>

      {/* Right Panel - Code Editor */}
      <div className="w-1/2 flex flex-col border-l border-dark-bg-tertiary">
        {/* Editor */}
        <div className="flex-1 border-b border-dark-bg-tertiary">
          <Editor
            height="100%"
            defaultLanguage="java"
            theme="vs-dark"
            value={code}
            onChange={(value) => setCode(value || '')}
            options={{
              minimap: { enabled: false },
              fontSize: 14,
              fontFamily: 'Fira Code, monospace',
            }}
          />
        </div>

        {/* Output */}
        <div className="h-32 border-b border-dark-bg-tertiary overflow-auto bg-dark-bg p-4">
          <p className="text-dark-text-secondary text-sm mb-2">Output:</p>
          <pre className="text-dark-text text-sm font-mono">{output}</pre>
        </div>

        {/* Controls */}
        <div className="p-4 flex gap-3 bg-dark-bg-secondary">
          <button
            onClick={handleRun}
            disabled={loading}
            className="flex items-center gap-2 px-4 py-2 bg-dark-bg-tertiary hover:bg-dark-bg-tertiary hover:bg-opacity-70 text-dark-text rounded-lg transition-colors disabled:opacity-50"
          >
            <Play size={16} />
            Run Code
          </button>
          <button
            onClick={handleSubmit}
            disabled={submitting}
            className="flex items-center gap-2 px-4 py-2 bg-primary hover:bg-primary-dark text-white rounded-lg transition-colors disabled:opacity-50"
          >
            <Zap size={16} />
            Submit
          </button>
        </div>
      </div>
    </div>
  )
}
