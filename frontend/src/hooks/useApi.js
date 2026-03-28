// Custom hook for API calls
import { useState, useCallback } from 'react'
import axios from 'axios'

export function useFetch(url, immediate = true) {
  const [data, setData] = useState(null)
  const [loading, setLoading] = useState(false)
  const [error, setError] = useState(null)

  const execute = useCallback(async () => {
    try {
      setLoading(true)
      setError(null)
      const response = await axios.get(url, {
        headers: { Authorization: `Bearer ${localStorage.getItem('token')}` }
      })
      setData(response.data)
    } catch (err) {
      setError(err.message)
    } finally {
      setLoading(false)
    }
  }, [url])

  return { data, loading, error, execute }
}

// Custom hook for WebSocket
export function useWebSocket(url) {
  const [connected, setConnected] = useState(false)
  const [messages, setMessages] = useState([])

  const connect = useCallback(() => {
    const ws = new WebSocket(url)
    ws.onopen = () => setConnected(true)
    ws.onmessage = (event) => setMessages(prev => [...prev, event.data])
    ws.onerror = () => setConnected(false)
    return ws
  }, [url])

  return { connected, messages, connect }
}
