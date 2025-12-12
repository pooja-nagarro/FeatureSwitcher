# FeatureSwitcher

A Spring Boot web application for managing feature toggles with a complete MVC architecture.

## Features

- ✅ **Feature Management** - Create, update, toggle, and delete feature flags
- ✅ **User Management** - Manage application users with roles
- ✅ **MVC Architecture** - Model-View-Controller pattern with proper separation
- ✅ **SQLite Database** - Lightweight, file-based persistence
- ✅ **Docker Support** - Containerized deployment with Docker Compose
- ✅ **REST API** - RESTful endpoints for programmatic access
- ✅ **Thymeleaf Templates** - Server-side rendering with modern UI

## Tech Stack

- **Java 21 LTS** - Latest long-term support version
- **Spring Boot 3.2.0** - Modern Spring framework
- **SQLite** - Embedded database
- **Thymeleaf** - Template engine
- **JPA/Hibernate** - ORM framework
- **Docker** - Containerization

## Quick Start

### Using Docker (Recommended)

```bash
# Build and start
docker-compose up -d --build

# View logs
docker-compose logs -f

# Stop
docker-compose down
```

Access at: **http://localhost:8080**

### Local Development

```bash
# Build
mvn clean install

# Run
java -jar target/feature-switcher-1.0.0.jar

# Or with Maven
mvn spring-boot:run
```

## Application URLs

- **Home:** http://localhost:8080/
- **Features:** http://localhost:8080/features
- **Users:** http://localhost:8080/users
- **About:** http://localhost:8080/about
- **API Status:** http://localhost:8080/api/status

## Project Structure

```
src/
├── main/
│   ├── java/com/example/featureswitcher/
│   │   ├── controller/         # MVC Controllers
│   │   ├── model/              # JPA Entities
│   │   ├── repository/         # Data repositories
│   │   ├── service/            # Business logic
│   │   └── FeatureSwitcherApplication.java
│   └── resources/
│       ├── static/css/         # Stylesheets
│       ├── templates/          # Thymeleaf views
│       └── application.properties
```

## Database

See [DATABASE_SETUP.md](DATABASE_SETUP.md) for detailed database information.

- **Type:** SQLite
- **Location (Docker):** `/app/data/featureswitcher.db`
- **Location (Local):** `./featureswitcher.db`

## API Endpoints

### REST API
- `GET /api/status` - Application status
- `GET /api/hello` - Hello endpoint

### Feature Management
- `GET /features` - List all features
- `GET /features/new` - New feature form
- `POST /features` - Create feature
- `GET /features/{id}` - View feature
- `GET /features/{id}/edit` - Edit feature form
- `POST /features/{id}` - Update feature
- `POST /features/{id}/toggle` - Toggle feature
- `POST /features/{id}/delete` - Delete feature

### User Management
- `GET /users` - List all users
- `GET /users/new` - New user form
- `POST /users` - Create user
- `GET /users/{id}` - View user
- `GET /users/{id}/edit` - Edit user form
- `POST /users/{id}` - Update user
- `POST /users/{id}/delete` - Delete user

## Development

### Prerequisites
- Java 21
- Maven 3.9+
- Docker (optional)

### Build
```bash
mvn clean install
```

### Run Tests
```bash
mvn test
```

## Docker Commands

```bash
# Build image
docker-compose build

# Start in background
docker-compose up -d

# View logs
docker-compose logs -f

# Stop containers
docker-compose down

# Remove volumes
docker-compose down -v

# Access container
docker exec -it featureswitcher-app bash
```

## License

MIT License

## Author

Built with Spring Boot 3.2.0 and Java 21 LTS
