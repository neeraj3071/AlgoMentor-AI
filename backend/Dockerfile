FROM eclipse-temurin:21-jdk-alpine

WORKDIR /app

COPY pom.xml .
RUN mvn dependency:go-offline -B 2>/dev/null || true

COPY src ./src
RUN mvn clean package -DskipTests

FROM eclipse-temurin:21-jre-alpine
WORKDIR /app
COPY --from=0 /app/target/*.jar app.jar

EXPOSE 8080
CMD ["java", "-jar", "app.jar"]
