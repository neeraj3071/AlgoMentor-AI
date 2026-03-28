# DSA Visualizer - Configuration Guide

## Environment Setup

### Frontend (.env)
```env
VITE_API_URL=http://localhost:8080/api
```

### Backend (application.yml)
Update the `backend/src/main/resources/application.yml` file with your local configuration:

```yaml
spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/dsa_visualizer
    username: postgres
    password: your_password
```

## Database Setup

### PostgreSQL
1. Create database:
```sql
CREATE DATABASE dsa_visualizer;
```

2. Connect and create tables (these will be auto-created by Hibernate with `ddl-auto: update`)

### Redis
Simply run Redis on default port 6379:
```bash
redis-server
```

## Running the Application

### Terminal 1: PostgreSQL & Redis
```bash
# Make sure PostgreSQL and Redis are running
```

### Terminal 2: Frontend
```bash
cd frontend
npm install
npm run dev
```

Frontend runs on: http://localhost:5173

### Terminal 3: Backend
```bash
cd backend
mvn clean install
mvn spring-boot:run
```

Backend runs on: http://localhost:8080

## API Documentation
Once backend is running, visit: http://localhost:8080/api/swagger-ui.html
