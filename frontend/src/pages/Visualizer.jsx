import { useState } from 'react'
import { useParams } from 'react-router-dom'
import { Play, Pause, RotateCcw, ChevronUp, ChevronDown } from 'lucide-react'
import BubbleSortVisualizer from '../visualizers/BubbleSortVisualizer'
import Editor from '@monaco-editor/react'

export default function Visualizer() {
  const { algorithm } = useParams()
  const [isPlaying, setIsPlaying] = useState(false)
  const [speed, setSpeed] = useState(50)
  const [array, setArray] = useState([5, 2, 8, 1, 9, 3, 7])

  const pseudocode = `function bubbleSort(arr) {
  n = arr.length
  for i from 0 to n-1:
    for j from 0 to n-i-1:
      if arr[j] > arr[j+1]:
        swap(arr[j], arr[j+1])
  return arr
}`

  return (
    <div className="flex h-full">
      {/* Left Panel - Visualizer */}
      <div className="flex-1 p-6 border-r border-dark-bg-tertiary overflow-auto">
        <div className="mb-6">
          <h1 className="text-3xl font-bold text-dark-text mb-2 capitalize">
            {algorithm?.replace('-', ' ') || 'Algorithm'} Visualizer
          </h1>
          <p className="text-dark-text-secondary">
            Visualize step-by-step execution of the algorithm
          </p>
        </div>

        {/* Controls */}
        <div className="mb-6 p-4 bg-dark-bg-secondary rounded-lg flex flex-wrap gap-4 items-center">
          <button
            onClick={() => setIsPlaying(!isPlaying)}
            className="flex items-center gap-2 px-4 py-2 bg-primary hover:bg-primary-dark text-white rounded-lg transition-colors"
          >
            {isPlaying ? <Pause size={18} /> : <Play size={18} />}
            {isPlaying ? 'Pause' : 'Play'}
          </button>

          <button
            onClick={() => setArray([5, 2, 8, 1, 9, 3, 7])}
            className="flex items-center gap-2 px-4 py-2 border border-dark-bg-tertiary text-dark-text rounded-lg hover:bg-dark-bg-tertiary transition-colors"
          >
            <RotateCcw size={18} />
            Reset
          </button>

          <div className="flex items-center gap-2">
            <span className="text-sm text-dark-text-secondary">Speed:</span>
            <input
              type="range"
              min="1"
              max="100"
              value={speed}
              onChange={(e) => setSpeed(Number(e.target.value))}
              className="w-24"
            />
            <span className="text-sm text-dark-text">{speed}x</span>
          </div>
        </div>

        {/* Visualizer */}
        <div className="bg-dark-bg-secondary rounded-lg p-6 h-96">
          <BubbleSortVisualizer array={array} speed={speed} />
        </div>

        {/* Algorithm Stats */}
        <div className="mt-6 grid grid-cols-4 gap-4">
          <div className="p-4 bg-dark-bg-secondary rounded-lg">
            <p className="text-dark-text-secondary text-sm">Time Complexity</p>
            <p className="text-xl font-bold text-primary">O(n²)</p>
          </div>
          <div className="p-4 bg-dark-bg-secondary rounded-lg">
            <p className="text-dark-text-secondary text-sm">Space Complexity</p>
            <p className="text-xl font-bold text-primary">O(1)</p>
          </div>
          <div className="p-4 bg-dark-bg-secondary rounded-lg">
            <p className="text-dark-text-secondary text-sm">Comparisons</p>
            <p className="text-xl font-bold text-primary">0 / 21</p>
          </div>
          <div className="p-4 bg-dark-bg-secondary rounded-lg">
            <p className="text-dark-text-secondary text-sm">Swaps</p>
            <p className="text-xl font-bold text-primary">0 / 21</p>
          </div>
        </div>
      </div>

      {/* Right Panel - Pseudocode & Explanation */}
      <div className="w-96 border-l border-dark-bg-tertiary overflow-auto flex flex-col">
        <div className="p-6 border-b border-dark-bg-tertiary">
          <h2 className="text-xl font-bold text-dark-text mb-4">Pseudocode</h2>
          <pre className="bg-dark-bg text-dark-text text-sm overflow-x-auto p-4 rounded-lg font-mono">
            <code>{pseudocode}</code>
          </pre>
        </div>

        <div className="p-6">
          <h2 className="text-xl font-bold text-dark-text mb-4">Description</h2>
          <p className="text-dark-text-secondary text-sm">
            Bubble Sort is a simple sorting algorithm that repeatedly steps through
            the list, compares adjacent elements and swaps them if they are in the
            wrong order. The pass through the list is repeated until the list is sorted.
          </p>

          <h3 className="text-lg font-semibold text-dark-text mt-6 mb-3">Key Points</h3>
          <ul className="text-dark-text-secondary text-sm space-y-2">
            <li>• Simple and easy to understand</li>
            <li>• Inefficient for large datasets</li>
            <li>• Stable sorting algorithm</li>
            <li>• Adaptive to order in input</li>
          </ul>
        </div>
      </div>
    </div>
  )
}
