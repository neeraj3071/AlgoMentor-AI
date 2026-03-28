# Architecture Design

## System Architecture

```
┌─────────────────────────────────────────────────────────────────┐
│                      Client Layer                               │
│                   (React + Vite Frontend)                        │
│  ┌──────────────┬──────────────┬──────────────────────────────┐ │
│  │ Visualizer   │ Code Editor  │ Problem Explorer │ Dashboard │ │
│  └──────────────┴──────────────┴──────────────────┴────────────┘ │
└─────────────────────────────────────────────────────────────────┘
                              ↓
┌─────────────────────────────────────────────────────────────────┐
│                    API Gateway Layer                            │
│              Spring Boot REST + WebSocket                        │
└─────────────────────────────────────────────────────────────────┘
                              ↓
        ┌─────────────────────┬──────────────────┬─────────────────┐
        ↓                     ↓                  ↓                 ↓
┌──────────────┐   ┌──────────────┐  ┌─────────────────┐  ┌──────────────┐
│Auth Service  │   │Problem Svc   │  │Execution Svc    │  │AI Tutor Svc  │
│(JWT)         │   │(CRUD)        │  │(Code Compile)   │  │(Gemini API)  │
└──────────────┘   └──────────────┘  └─────────────────┘  └──────────────┘
        ↓                     ↓                  ↓                 ↓
        └─────────────────────┬──────────────────┬─────────────────┘
                              ↓
        ┌─────────────────────┬──────────────────┐
        ↓                     ↓                  ↓
   ┌─────────────┐      ┌──────────┐      ┌──────────┐
   │ PostgreSQL  │      │  Redis   │      │ Gemini   │
   │  (Primary   │      │ (Cache & │      │   API    │
   │   Data)     │      │ Sessions)│      │          │
   └─────────────┘      └──────────┘      └──────────┘
```

## Microservices

1. **Auth Service**
   - User registration & login
   - JWT token generation
   - Role-based access control

2. **Problem Service**
   - CRUD operations for DSA problems
   - Category & difficulty filtering
   - Problem metadata

3. **Execution Service**
   - Code compilation & execution
   - Docker sandboxing
   - Resource limiting

4. **AI Service**
   - Gemini API integration
   - Code explanations
   - Hint generation
   - Complexity analysis

## Technology Stack Details

### Frontend
- **React 18**: Component-based UI
- **Vite**: Fast module bundler
- **Tailwind CSS**: Utility-first styling
- **D3.js**: Algorithm visualizations
- **Monaco Editor**: Code editing
- **Zustand**: State management
- **Axios**: HTTP client
- **Socket.io**: Real-time communication

### Backend
- **Spring Boot 3.1**: REST API framework
- **Spring Security**: Authentication & authorization
- **Spring Data JPA**: ORM
- **Spring WebSocket**: Real-time updates
- **PostgreSQL**: Primary database
- **Redis**: Caching & session management
- **Docker Java**: Code execution sandboxing

### DevOps
- **Docker**: Containerization
- **Docker Compose**: Multi-container orchestration
- **Nginx**: Reverse proxy & static hosting

## Data Models

### User
- id, username, email, password
- fullName, profilePicture, bio
- roles (USER, ADMIN)
- problemsSolved, averageAccuracy
- createdAt, updatedAt

### Problem
- id, title, description
- category, difficulty
- examples, constraints
- boilerplateCode, solutionCode
- timeLimit, memoryLimit
- submissionsCount, acceptedCount

### Submission
- id, userId, problemId
- code, status
- output, errorMessage
- executionTime, memoryUsed
- submittedAt

## API Endpoints

### Authentication
- `POST /api/auth/signup` - Register
- `POST /api/auth/login` - Login
- `POST /api/auth/refresh` - Refresh token

### Problems
- `GET /api/problems` - List all
- `GET /api/problems/{id}` - Get by ID
- `GET /api/problems/category/{category}` - Filter by category
- `GET /api/problems/difficulty/{difficulty}` - Filter by difficulty
- `POST /api/problems` - Create (admin only)

### Execution
- `POST /api/execute` - Execute code
- `POST /api/submit` - Submit solution

### AI Tutor
- `POST /api/ai/explain` - Explain code
- `POST /api/ai/hint` - Get hint
- `POST /api/ai/complexity` - Analyze complexity

### User
- `GET /api/user/{id}` - Get profile
- `PUT /api/user/{id}` - Update profile
- `GET /api/user/{id}/progress` - Get progress stats

## Security

1. **Authentication**: JWT tokens with 7-day expiration
2. **Authorization**: Role-based access control (RBAC)
3. **Code Execution**: Docker containerization with resource limits
4. **Input Validation**: Server-side validation on all endpoints
5. **Rate Limiting**: To prevent abuse
6. **CORS**: Configured for frontend domain
7. **Password Hashing**: BCrypt with salt

## Performance Optimization

1. **Caching**: Redis for frequently accessed problems
2. **Code Splitting**: React lazy loading
3. **Database Indexing**: On userId, problemId, username, email
4. **Pagination**: For problem lists
5. **WebSocket**: For real-time features
6. **Compression**: Gzip compression on responses

## Scalability Considerations

1. **Horizontal Scaling**: Stateless backend services
2. **Load Balancing**: Multiple backend instances
3. **Database Replication**: Master-slave setup
4. **Cache Distribution**: Redis cluster
5. **Async Processing**: Future Kafka integration

## Deployment Architecture

```
┌──────────────────────────────────────────────────────────────┐
│                    AWS Cloud Environment                     │
│                                                               │
│  ┌──────────────────────────────────────────────────────┐   │
│  │        ECS Cluster (Docker Containers)              │   │
│  │  ┌─────────────┐  ┌─────────────┐  ┌─────────────┐ │   │
│  │  │ Frontend    │  │  Backend    │  │  Backend    │ │   │
│  │  │ (Nginx)     │  │  (Spring)   │  │  (Spring)   │ │   │
│  │  └─────────────┘  └─────────────┘  └─────────────┘ │   │
│  └──────────────────────────────────────────────────────┘   │
│  ┌──────────────────────────────────────────────────────┐   │
│  │              RDS PostgreSQL                          │   │
│  │        (Multi-AZ for High Availability)              │   │
│  └──────────────────────────────────────────────────────┘   │
│  ┌──────────────────────────────────────────────────────┐   │
│  │         ElastiCache Redis                            │   │
│  │        (Cluster Mode Enabled)                        │   │
│  └──────────────────────────────────────────────────────┘   │
└──────────────────────────────────────────────────────────────┘
```
