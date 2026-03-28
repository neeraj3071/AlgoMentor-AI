# Development Guide

## Getting Started

### Prerequisites
- Node.js 18+
- Java 21
- Maven 3.8+
- PostgreSQL 14+
- Redis 7+
- Docker (optional)
- Git

### Quick Start (Local Development)

#### 1. Clone Repository
```bash
git clone <repository-url>
cd "DSA Visualizer & Practice Platform"
```

#### 2. Setup Database
```sql
CREATE DATABASE dsa_visualizer;
```

#### 3. Start Services
```bash
# Terminal 1: Start PostgreSQL & Redis (or use Docker)
docker run -d -p 5432:5432 -e POSTGRES_DB=dsa_visualizer postgres:16
docker run -d -p 6379:6379 redis:7

# Terminal 2: Start Backend
cd backend
mvn clean install
mvn spring-boot:run

# Terminal 3: Start Frontend
cd frontend
npm install
npm run dev
```

**Frontend**: http://localhost:5173  
**Backend**: http://localhost:8080  
**API Docs**: http://localhost:8080/api/swagger-ui.html

---

## Project Structure Details

### Frontend Structure
```
frontend/
├── src/
│   ├── components/          # Reusable components
│   │   ├── Layout.jsx      # Main layout wrapper
│   │   ├── Navbar.jsx      # Top navigation
│   │   ├── Sidebar.jsx     # Side menu
│   │   └── ProtectedRoute.jsx
│   ├── pages/              # Page components
│   │   ├── Home.jsx
│   │   ├── Login.jsx
│   │   ├── Signup.jsx
│   │   ├── Visualizer.jsx
│   │   ├── PracticeProblems.jsx
│   │   ├── ProblemDetail.jsx
│   │   └── Dashboard.jsx
│   ├── visualizers/        # D3.js visualizations
│   │   └── BubbleSortVisualizer.jsx
│   ├── services/           # API calls
│   │   └── api.js
│   ├── hooks/             # Custom hooks
│   │   └── useApi.js
│   ├── store/             # State management (Zustand)
│   │   └── index.js
│   ├── utils/             # Utility functions
│   ├── App.jsx
│   └── main.jsx
├── package.json
├── vite.config.js
├── tailwind.config.js
└── postcss.config.js
```

### Backend Structure
```
backend/
├── src/
│   ├── main/
│   │   ├── java/com/dsavisualizer/
│   │   │   ├── controller/      # REST endpoints
│   │   │   ├── service/         # Business logic
│   │   │   ├── repository/      # Database access
│   │   │   ├── model/           # Entity classes
│   │   │   ├── dto/             # Data transfer objects
│   │   │   ├── security/        # JWT & auth
│   │   │   ├── config/          # Spring configurations
│   │   │   ├── exception/       # Custom exceptions
│   │   │   ├── utils/           # Utilities
│   │   │   ├── websocket/       # WebSocket handlers
│   │   │   └── DsaVisualizerApplication.java
│   │   └── resources/
│   │       └── application.yml
│   └── test/
└── pom.xml
```

---

## API Development Workflow

### Adding a New API Endpoint

1. **Create DTO** (if needed)
   ```java
   @Data
   @Builder
   public class NewRequestDTO {
       private String field1;
       private Long field2;
   }
   ```

2. **Create Service Method**
   ```java
   @Service
   public class NewService {
       public ResponseDTO process(NewRequestDTO request) {
           // Business logic
           return response;
       }
   }
   ```

3. **Create Controller Endpoint**
   ```java
   @RestController
   @RequestMapping("/api/new")
   public class NewController {
       @PostMapping
       public ResponseEntity<ResponseDTO> create(@RequestBody NewRequestDTO request) {
           return ResponseEntity.ok(service.process(request));
       }
   }
   ```

4. **Add Tests**
   ```java
   @SpringBootTest
   public class NewControllerTest {
       @Test
       public void testCreate() {
           // Test implementation
       }
   }
   ```

---

## Frontend Development Workflow

### Creating a New Page

1. **Create Page Component**
   ```jsx
   export default function NewPage() {
       return (
           <div className="p-8">
               {/* Content */}
           </div>
       )
   }
   ```

2. **Add Route**
   ```jsx
   <Route path="/new-page" element={<NewPage />} />
   ```

3. **Add Navigation Link**
   ```jsx
   // In sidebar or navbar
   { icon: Icon, label: 'New Page', path: '/new-page' }
   ```

---

## Testing

### Backend Tests
```bash
cd backend
mvn test
```

### Frontend Tests
```bash
cd frontend
npm test
```

---

## Building & Deployment

### Build Frontend
```bash
cd frontend
npm run build
# Output: dist/
```

### Build Backend
```bash
cd backend
mvn clean package
# Output: target/dsa-backend-1.0.0.jar
```

### Docker Build
```bash
docker-compose build
docker-compose up -d
```

---

## Environment Variables

### Frontend (.env)
```env
VITE_API_URL=http://localhost:8080/api
VITE_WS_URL=ws://localhost:8080/ws
```

### Backend (application.yml)
```yaml
spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/dsa_visualizer
    username: postgres
    password: postgres
  redis:
    host: localhost
    port: 6379

jwt:
  secret: your-secret-key
  expiration: 604800000

gemini:
  api-key: your-gemini-api-key
```

---

## Useful Commands

### Frontend
```bash
npm install                    # Install dependencies
npm run dev                    # Start dev server
npm run build                  # Build for production
npm run preview                # Preview production build
npm run lint                   # Run ESLint
npm run format                 # Format code with Prettier
```

### Backend
```bash
mvn clean install              # Build project
mvn spring-boot:run            # Run application
mvn test                       # Run tests
mvn clean package              # Create JAR
```

### Database
```bash
# PostgreSQL shell
psql -U postgres -d dsa_visualizer

# Common queries
CREATE TABLE users (...);
ALTER TABLE users ADD COLUMN new_field TYPE;
DROP TABLE users;
```

---

## Common Issues & Solutions

### Issue: Port already in use
**Solution**:
```bash
# Find process using port
lsof -i :8080

# Kill process
kill -9 <PID>
```

### Issue: Database connection failed
**Solution**: Ensure PostgreSQL is running and credentials match

### Issue: CORS errors
**Solution**: Check `spring.cors.allowed-origins` in application.yml

### Issue: JWT token expired
**Solution**: User needs to login again to get new token

---

## Code Quality Standards

### Backend
- Unit test coverage: >70%
- Follow Spring Boot best practices
- Use proper exception handling
- Document complex logic
- Use `@Slf4j` for logging

### Frontend
- Use functional components & hooks
- Keep components small & focused
- Use prop validation
- Proper error handling
- Consistent naming conventions

---

## Performance Tips

### Frontend
- Use React.memo for expensive components
- Implement code splitting with React.lazy
- Optimize bundle size
- Use Lighthouse for audits

### Backend
- Add database indexes
- Implement caching with Redis
- Use pagination for large datasets
- Monitor with Spring Boot Actuator

---

## Troubleshooting Checklist

- [ ] Are all services running (DB, Redis, Backend, Frontend)?
- [ ] Do port numbers match in configs?
- [ ] Is JWT token valid and not expired?
- [ ] Are CORS headers configured?
- [ ] Are environment variables set correctly?
- [ ] Check browser console for errors
- [ ] Check backend logs for exceptions
- [ ] Clear browser cache and reopen

---

## Contributing Guidelines

1. Create feature branch: `git checkout -b feature/your-feature`
2. Make changes and commit: `git commit -m "Add feature"`
3. Push: `git push origin feature/your-feature`
4. Create Pull Request with description
5. Code review and merge

---

## Resources

- [React Documentation](https://react.dev)
- [Spring Boot Documentation](https://spring.io/projects/spring-boot)
- [PostgreSQL Documentation](https://www.postgresql.org/docs/)
- [Redis Documentation](https://redis.io/documentation)
- [D3.js Documentation](https://d3js.org/)
- [Tailwind CSS Documentation](https://tailwindcss.com)

