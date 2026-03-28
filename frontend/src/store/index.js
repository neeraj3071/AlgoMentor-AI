import { create } from 'zustand'

export const useAuthStore = create((set) => ({
  user: JSON.parse(localStorage.getItem('user') || 'null'),
  token: localStorage.getItem('token'),
  
  setAuth: (user, token) => {
    localStorage.setItem('user', JSON.stringify(user))
    localStorage.setItem('token', token)
    set({ user, token })
  },
  
  logout: () => {
    localStorage.removeItem('user')
    localStorage.removeItem('token')
    set({ user: null, token: null })
  },
}))

export const useProblemStore = create((set) => ({
  problems: [],
  currentProblem: null,
  
  setProblems: (problems) => set({ problems }),
  setCurrentProblem: (problem) => set({ currentProblem: problem }),
}))

export const useVisualizerStore = create((set) => ({
  array: [],
  isPlaying: false,
  speed: 50,
  
  setArray: (array) => set({ array }),
  setIsPlaying: (isPlaying) => set({ isPlaying }),
  setSpeed: (speed) => set({ speed }),
}))
