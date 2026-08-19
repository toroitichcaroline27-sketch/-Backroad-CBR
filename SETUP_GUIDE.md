# Spring Boot Project Setup Guide

## Project Created Successfully! ✅

Your Spring Boot project "Backroad CBR" has been created at:
```
C:\Users\toroi\OneDrive\Desktop\Backroad-CBR\
```

## Project Contents

### Files Created:
- ✓ `pom.xml` - Maven configuration with Spring Boot 3.3.0
- ✓ `src/main/java/com/backroad/BackroadCbrApplication.java` - Main Spring Boot application
- ✓ `src/main/resources/application.properties` - Application configuration
- ✓ `.gitignore` - Git ignore rules
- ✓ `README.md` - Project documentation
- ✓ `build.bat` - Batch script for building (Windows)

## Installation & Setup Steps

### Step 1: Install Maven

Maven hasn't been installed yet. Follow these steps:

#### Option A: Manual Installation (Recommended)
1. Download Maven from: https://maven.apache.org/download.cgi
   - Download: `apache-maven-3.9.6-bin.zip` (or latest stable version)

2. Extract to a permanent location:
   - Extract to: `C:\Apache\maven` or any location you prefer

3. Add Maven to PATH:
   - Right-click "This PC" → Properties
   - Click "Advanced system settings"
   - Click "Environment Variables..."
   - Under "System variables", click "New..."
   - Variable name: `MAVEN_HOME`
   - Variable value: `C:\Apache\maven` (or your extraction path)
   - Click OK
   
4. Create PATH entry:
   - In "System variables", select `Path` and click "Edit..."
   - Click "New" and add: `%MAVEN_HOME%\bin`
   - Click OK on all dialogs
   - **Restart your terminal/PowerShell**

5. Verify installation:
   ```powershell
   mvn --version
   ```

#### Option B: Using Chocolatey (if installed)
```powershell
choco install maven -y
```

#### Option C: Using Windows Package Manager
```powershell
winget install Apache.Maven
```

### Step 2: Build the Project

Once Maven is installed and PATH is updated, build the project:

```powershell
cd C:\Users\toroi\OneDrive\Desktop\Backroad-CBR
mvn clean install
```

This will:
- Download dependencies
- Compile the source code
- Run tests
- Package the application into a JAR file

### Step 3: Run the Application

After successful build, run the application:

```powershell
mvn spring-boot:run
```

Or directly with Java:
```powershell
java -jar target/backroad-cbr-1.0.0.jar
```

### Step 4: Access the Application

Once running, open your browser and visit:

- **Main Page**: http://localhost:8080/
- **Health Check**: http://localhost:8080/api/health
- **H2 Database Console**: http://localhost:8080/h2-console
  - Driver Class: `org.h2.Driver`
  - JDBC URL: `jdbc:h2:mem:backroad_db`
  - User Name: `sa`
  - Password: (leave empty)

## Project Structure

```
Backroad-CBR/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/backroad/
│   │   │       └── BackroadCbrApplication.java
│   │   └── resources/
│   │       └── application.properties
│   └── test/
│       └── java/com/backroad/
├── pom.xml                 # Maven configuration
├── .gitignore              # Git ignore rules  
├── README.md               # Documentation
├── build.bat               # Build script
└── setup_log.txt           # Setup diagnostics
```

## Features Included

✓ Spring Boot 3.3.0 (Latest LTS)
✓ Spring Web (REST API support)
✓ Spring Data JPA (Database access)
✓ H2 In-Memory Database
✓ Spring DevTools (Hot reload)
✓ JUnit 5 Testing
✓ Java 21 compatibility

## Troubleshooting

### Maven command not found
- Ensure Maven is properly extracted
- Check PATH environment variable includes `%MAVEN_HOME%\bin`
- Restart PowerShell/terminal after changing PATH
- Run: `mvn --version` to verify

### Build fails
- Ensure Java 21 is installed: `java -version`
- Delete `target/` folder and try again: `mvn clean install`
- Check internet connection for dependency downloads
- Try: `mvn clean install -U` to force update dependencies

### Port 8080 already in use
- Edit `src/main/resources/application.properties`
- Change: `server.port=8080` to `server.port=8081` (or any available port)

### H2 console not accessible
- Make sure the application is running
- Visit: http://localhost:8080/h2-console
- Connection should auto-fill, just click "Connect"

## Development

### Adding Dependencies
Edit `pom.xml` and add to the `<dependencies>` section, then rebuild:
```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-data-rest</artifactId>
</dependency>
```

### Creating REST Endpoints
Endpoints can be added to `BackroadCbrApplication.java` or separate controller classes:
```java
@GetMapping("/api/users")
public List<String> getUsers() {
    return Arrays.asList("User1", "User2");
}
```

### Database Tables
Tables are auto-created based on JPA entities. Create entities in:
```
src/main/java/com/backroad/entities/
```

## Next Steps

1. ✓ Install Maven (see above)
2. ✓ Build: `mvn clean install`
3. ✓ Run: `mvn spring-boot:run`
4. ✓ Verify at http://localhost:8080/
5. ✓ Start building your application!

## Quick Commands Reference

```powershell
# Navigate to project
cd C:\Users\toroi\OneDrive\Desktop\Backroad-CBR

# Build project
mvn clean install

# Run application
mvn spring-boot:run

# Run tests
mvn test

# Package only (skip tests)
mvn clean package -DskipTests

# View Maven version
mvn --version

# Check Java version
java -version
```

## Support & Resources

- **Spring Boot Docs**: https://spring.io/projects/spring-boot
- **Maven Guide**: https://maven.apache.org/guides/
- **Spring Boot Starter POM**: https://github.com/spring-projects/spring-boot/blob/main/spring-boot-project/spring-boot-starters/
- **H2 Database**: https://h2database.com

---

**Project created on**: 2026-08-18
**Spring Boot Version**: 3.3.0
**Java Version**: 21
**Maven Build Tool**: Ready for installation
