/** @type {import('tailwindcss').Config} */
export default {
  content: [
    "./index.html",
    "./src/**/*.{js,jsx}"
  ],
  theme: {
    extend: {
      colors: {
        dark: {
          bg: '#0f172a',
          'bg-secondary': '#1e293b',
          'bg-tertiary': '#334155',
          text: '#f1f5f9',
          'text-secondary': '#cbd5e1'
        },
        primary: {
          light: '#60a5fa',
          DEFAULT: '#3b82f6',
          dark: '#1e40af'
        },
        accent: {
          purple: '#a78bfa',
          pink: '#ec4899'
        }
      },
      animation: {
        'pulse-glow': 'pulse-glow 2s cubic-bezier(0.4, 0, 0.6, 1) infinite',
      },
      keyframes: {
        'pulse-glow': {
          '0%, 100%': { opacity: '1' },
          '50%': { opacity: '.5' }
        }
      }
    }
  },
  plugins: [],
  darkMode: 'class'
}
