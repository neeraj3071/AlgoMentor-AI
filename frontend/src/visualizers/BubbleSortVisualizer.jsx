import { useEffect, useRef } from 'react'
import * as d3 from 'd3'

export default function BubbleSortVisualizer({ array = [5, 2, 8, 1, 9], speed = 50 }) {
  const svgRef = useRef()

  useEffect(() => {
    if (!array || array.length === 0) return

    const width = 800
    const height = 400
    const margin = { top: 40, right: 40, bottom: 40, left: 40 }

    // Clear previous content
    d3.select(svgRef.current).selectAll('*').remove()

    const svg = d3.select(svgRef.current)
      .attr('width', width)
      .attr('height', height)

    const g = svg.append('g')
      .attr('transform', `translate(${margin.left},${margin.top})`)

    const chartWidth = width - margin.left - margin.right
    const chartHeight = height - margin.top - margin.bottom

    const xScale = d3.scaleBand()
      .domain(d3.range(array.length))
      .range([0, chartWidth])
      .padding(0.1)

    const yScale = d3.scaleLinear()
      .domain([0, Math.max(...array)])
      .range([chartHeight, 0])

    // Draw bars
    g.selectAll('.bar')
      .data(array)
      .enter()
      .append('rect')
      .attr('class', 'bar')
      .attr('x', (d, i) => xScale(i))
      .attr('y', d => yScale(d))
      .attr('width', xScale.bandwidth())
      .attr('height', d => chartHeight - yScale(d))
      .attr('fill', '#3b82f6')
      .attr('rx', 4)

    // Add labels
    g.selectAll('.label')
      .data(array)
      .enter()
      .append('text')
      .attr('class', 'label')
      .attr('x', (d, i) => xScale(i) + xScale.bandwidth() / 2)
      .attr('y', d => yScale(d) - 5)
      .attr('text-anchor', 'middle')
      .attr('fill', '#f1f5f9')
      .text(d => d)

  }, [array])

  return (
    <div className="w-full h-full flex items-center justify-center bg-dark-bg-secondary rounded-lg p-4">
      <svg ref={svgRef} className="w-full h-full"></svg>
    </div>
  )
}
