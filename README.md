# DSA Visualizer & Practice Platform

## Project Structure

```
├── frontend/                 # React + Vite frontend
│   ├── src/
│   │   ├── components/      # Reusable React components
│   │   ├── pages/          # Page components
│   │   ├── hooks/          # Custom React hooks
│   │   ├── services/       # API calls & external services
│   │   ├── visualizers/    # D3.js algorithm visualizations
│   │   ├── store/          # Zustand state management
│   │   └── utils/          # Utility functions
│   ├── package.json
│   ├── vite.config.js
│   └── tailwind.config.js
├── backend/                 # Spring Boot backend
│   ├── src/
│   │   ├── main/java/com/dsavisualizer/
│   │   │   ├── controller/     # REST API endpoints
│   │   │   ├── service/        # Business logic
│   │   │   ├── repository/     # Database access
│   │   │   ├── model/          # Entity classes
│   │   │   ├── config/         # Configuration classes
│   │   │   ├── security/       # JWT & Security
│   │   │   ├── dto/            # Data Transfer Objects
│   │   │   ├── exception/      # Custom exceptions
│   │   │   └── utils/          # Utility classes
│   │   └── resources/
│   │       └── application.yml
│   └── pom.xml
├── docs/                    # Documentation
└── README.md
```

## Quick Start

### Prerequisites
- Node.js (v18+)
- Java 17
- Maven
- PostgreSQL
- Redis
- Docker

### Frontend Setup

```bash
cd frontend
npm install
npm run dev
```

Frontend runs on `http://localhost:5173`

### Backend Setup

```bash
cd backend
mvn clean install
mvn spring-boot:run
```

Backend runs on `http://localhost:8080`

## Features

### 1. Algorithm Visualizer
- Sorting algorithms (Bubble Sort, Merge Sort, Quick Sort)
- Tree traversals (Inorder, Preorder, Postorder)
- Graph algorithms (BFS, DFS, Dijkstra)
- Dynamic Programming (Fibonacci, Knapsack)

### 2. Coding Practice Platform
- 100+ DSA problems
- Multiple difficulty levels
- Custom test cases
- Real-time code execution

### 3. AI Tutor (Gemini API)
- Code explanation
- Hint generation
- Complexity analysis
- Optimization suggestions

### 4. User Dashboard
- Track progress
- View solved problems
- Analyze weak areas
- Performance metrics

## Environment Variables

### Backend (.env)
```
SPRING_DATASOURCE_URL=jdbc:postgresql://localhost:5432/dsa_visualizer
SPRING_DATASOURCE_USERNAME=postgres
SPRING_DATASOURCE_PASSWORD=postgres
SPRING_REDIS_HOST=localhost
SPRING_REDIS_PORT=6379
JWT_SECRET=your-secret-key
GEMINI_API_KEY=your-gemini-api-key
```

### Frontend (.env)
```
VITE_API_URL=http://localhost:8080/api
```

## Database Setup

```sql
CREATE DATABASE dsa_visualizer;
```

## Docker Compose (Optional)

```yaml
version: '3.8'
services:
  postgres:
    image: postgres:16
    environment:
      POSTGRES_DB: dsa_visualizer
      POSTGRES_PASSWORD: postgres
    ports:
      - "5432:5432"
  
  redis:
    image: redis:7
    ports:
      - "6379:6379"
```

## API Documentation

Swagger UI: `http://localhost:8080/api/swagger-ui.html`

## Architecture

### Microservices
- **Auth Service**: User authentication & JWT token management
- **Problem Service**: DSA problem retrieval & management
- **Execution Service**: Code compilation & execution
- **AI Service**: Gemini API integration
- **Visualization Service**: Algorithm visualization data

## Security

- JWT-based authentication
- Rate limiting on API endpoints
- Input sanitization
- Secure Docker execution environment
- Role-based access control

## Performance Optimization

- Redis caching for frequent queries
- Lazy loading of components
- WebSocket for real-time updates
- Code splitting in React
- Database indexing

## Testing

```bash
# Frontend tests
cd frontend
npm run test

# Backend tests
cd backend
mvn test
```

## Deployment

### Frontend
- Build: `npm run build`
- Deploy to: Vercel, Netlify, or Firebase

### Backend
- Build: `mvn clean package`
- Deploy to: AWS EC2, Docker containers, or Kubernetes

## Repository Strategy

### Branches
- `develop`: active development (dev environment)
- `staging`: pre-production validation
- `main`: production-ready releases

### Promotion Flow
1. Feature branches target `develop`
2. Promote validated changes from `develop` to `staging`
3. Promote release candidates from `staging` to `main`

### CI/CD
- CI workflow runs on PRs and pushes to `develop`, `staging`, and `main`
- Release workflow packages artifacts for `staging` and `main`
- See [docs/repo/branching-strategy.md](docs/repo/branching-strategy.md)

### Environment Files
- Backend dev template: `backend/.env.dev.example`
- Backend staging template: `backend/.env.staging.example`
- Backend production template: `backend/.env.prod.example`

## API Endpoints

### Authentication
- `POST /api/auth/register` - User registration
- `POST /api/auth/login` - User login
- `POST /api/auth/refresh` - Refresh JWT token

### Problems
- `GET /api/problems` - Get all problems
- `GET /api/problems/:id` - Get problem details
- `GET /api/problems?category=arrays` - Filter by category

### Code Execution
- `POST /api/execute` - Execute code
- `POST /api/submit` - Submit solution

### AI Tutor
- `POST /api/ai/explain` - Get code explanation
- `POST /api/ai/hint` - Get hint for problem
- `POST /api/ai/analyze` - Analyze complexity

### User
- `GET /api/user/profile` - Get user profile
- `GET /api/user/progress` - Get progress stats
- `GET /api/user/submissions` - Get user submissions

## Contributing

1. Fork the repository
2. Create a feature branch
3. Commit your changes
4. Push to the branch
5. Create a Pull Request

## License

MIT License

## Author

[Your Name]

## Support

For issues or questions, please open an issue on GitHub.
# AlgoMentor-AI
