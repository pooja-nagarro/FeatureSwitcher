# Feature Switcher - SQLite Database with Docker

This project uses SQLite for lightweight, file-based data persistence.

## Prerequisites
- **For Docker:** Docker and Docker Compose
- **For Local:** Java 21 and Maven 3.9+

## Running with Docker (Recommended)

### 1. Build and Start the Application
```bash
docker-compose up -d --build
```

This will:
- Build the Docker image with Java 21
- Create a persistent volume for SQLite database
- Start the application on port 8080

### 2. View Logs
```bash
docker-compose logs -f
```

### 3. Stop the Application
```bash
docker-compose down
```

### 4. Stop and Remove Data
```bash
docker-compose down -v
```

### 5. Access the Application
Open: http://localhost:8080

## Running Locally (Without Docker)

```bash
mvn clean install
java -jar target/feature-switcher-1.0.0.jar
```

Or with Maven:
```bash
mvn spring-boot:run
```

## Database Access

### Inside Docker Container
```bash
docker exec -it featureswitcher-app sqlite3 /app/data/featureswitcher.db
.tables
SELECT * FROM features;
.exit
```

### Local SQLite CLI
```bash
sqlite3 featureswitcher.db
.tables
SELECT * FROM features;
.exit
```

### GUI Tool
Use [DB Browser for SQLite](https://sqlitebrowser.org/)

## Initial Data

Auto-seeded on first startup:
- 5 sample features
- 3 sample users (admin, developer, tester)

## Database Locations

- **Docker:** `/app/data/featureswitcher.db` (persisted in volume `sqlite-data`)
- **Local:** `./featureswitcher.db` in project root

## Data Persistence

The Docker setup uses a named volume (`sqlite-data`) to persist the database across container restarts. Your data is safe even if you:
- Stop the container
- Rebuild the image
- Restart Docker

## Healthcheck

The application includes a healthcheck that monitors:
- Endpoint: `http://localhost:8080/api/status`
- Interval: 30 seconds
- Timeout: 10 seconds
- Start period: 40 seconds

Check health status:
```bash
docker ps
```

## Troubleshooting

### View container status
```bash
docker ps -a
```

### View application logs
```bash
docker-compose logs featureswitcher-app
```

### Access container shell
```bash
docker exec -it featureswitcher-app bash
```

### Reset database
```bash
docker-compose down -v
docker-compose up -d
```

### Port already in use
If port 8080 is occupied, change it in `docker-compose.yml`:
```yaml
ports:
  - "8081:8080"  # Changed to 8081
```
