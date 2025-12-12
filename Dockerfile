# Build stage
FROM maven:3.9-eclipse-temurin-21 AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

# Run stage
FROM eclipse-temurin:21-jre
WORKDIR /app

# Install curl for healthcheck
RUN apt-get update && apt-get install -y curl && rm -rf /var/lib/apt/lists/*

# Create data directory for SQLite database
RUN mkdir -p /app/data

# Copy the built JAR file
COPY --from=build /app/target/feature-switcher-1.0.0.jar app.jar

# Expose application port
EXPOSE 8080

# Set environment variable for database location
ENV SPRING_DATASOURCE_URL=jdbc:sqlite:/app/data/featureswitcher.db

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
