import { BrowserRouter as Router, Routes, Route } from 'react-router-dom'
import Layout from './components/Layout'
import Home from './pages/Home'
import Visualizer from './pages/Visualizer'
import PracticeProblems from './pages/PracticeProblems'
import ProblemDetail from './pages/ProblemDetail'
import Dashboard from './pages/Dashboard'
import Login from './pages/Login'
import Signup from './pages/Signup'
import Learn from './pages/Learn'

function App() {
  return (
    <Router>
      <Routes>
        <Route path="/login" element={<Login />} />
        <Route path="/signup" element={<Signup />} />
        <Route element={<Layout />}>
          <Route path="/" element={<Home />} />
          <Route path="/visualizer/:algorithm" element={<Visualizer />} />
          <Route path="/practice" element={<PracticeProblems />} />
          <Route path="/learn" element={<Learn />} />
          <Route path="/problem/:id" element={<ProblemDetail />} />
          <Route path="/dashboard" element={<Dashboard />} />
        </Route>
      </Routes>
    </Router>
  )
}

export default App
