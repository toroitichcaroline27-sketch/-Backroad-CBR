# Backroad CBR - Spring Boot Application

A modern Spring Boot application for Backroad CBR.

## Prerequisites

- Java 21 or higher
- Maven 3.8.1 or higher (optional - Maven Wrapper included)

## Getting Started

### 1. Build the Project

Using Maven Wrapper (recommended):
```bash
./mvnw clean install
```

Or using Maven directly:
```bash
mvn clean install
```

### 2. Run the Application

Using Maven Wrapper:
```bash
./mvnw spring-boot:run
```

Or using Maven:
```bash
mvn spring-boot:run
```

Or run the JAR file directly:
```bash
java -jar target/backroad-cbr-1.0.0.jar
```

## Accessing the Application

Once running, the application will be available at:
- Main URL: http://localhost:8080/
- API Health: http://localhost:8080/api/health
- H2 Database Console: http://localhost:8080/h2-console

## Project Structure

```
backroad-cbr/
├── src/
│   ├── main/
│   │   ├── java/com/backroad/          # Java source code
│   │   └── resources/                  # Configuration files
│   └── test/
│       └── java/com/backroad/          # Test classes
├── pom.xml                              # Maven configuration
├── .gitignore                           # Git ignore rules
└── README.md                            # This file
```

## Features

- Spring Boot Web Framework
- Spring Data JPA
- H2 In-Memory Database
- Spring DevTools for development
- Spring Boot Testing Framework

## Configuration

Application settings can be modified in `src/main/resources/application.properties`:
- Server port: `server.port=8080`
- Database settings: H2 in-memory database (configurable)
- Logging levels: Adjustable per package

## Build

To create an executable JAR:
```bash
mvn clean package
```

## Testing

Run tests using Maven:
```bash
mvn test
```

## License

MIT License

## Support

For issues and questions, please open an issue in the repository.
