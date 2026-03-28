import { LineChart, Line, XAxis, YAxis, CartesianGrid, Tooltip, Legend, ResponsiveContainer } from 'recharts'

export default function PerformanceChart({ data }) {
  return (
    <ResponsiveContainer width="100%" height={300}>
      <LineChart data={data}>
        <CartesianGrid stroke="#334155" />
        <XAxis stroke="#cbd5e1" />
        <YAxis stroke="#cbd5e1" />
        <Tooltip contentStyle={{ backgroundColor: '#0f172a', border: '1px solid #334155' }} />
        <Legend />
        <Line 
          type="monotone" 
          dataKey="runtime" 
          stroke="#3b82f6" 
          dot={{ fill: '#3b82f6' }}
          name="Runtime (ms)"
        />
      </LineChart>
    </ResponsiveContainer>
  )
}
