# Car Rental Application

## Phase 1 - Project Setup Complete ✅

A simple desktop Car Rental application built with Java and JavaFX.

### Project Structure

```
CarRent/
├── pom.xml                              # Maven configuration
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/carrent/
│   │   │       ├── model/               # Data classes
│   │   │       │   ├── Vehicle.java
│   │   │       │   ├── Rental.java
│   │   │       │   └── Customer.java
│   │   │       ├── service/             # Business logic
│   │   │       │   ├── VehicleService.java
│   │   │       │   └── RentalService.java
│   │   │       ├── repository/          # Data access
│   │   │       │   ├── VehicleRepository.java
│   │   │       │   └── RentalRepository.java
│   │   │       ├── view/                # UI controllers
│   │   │       │   └── MainViewController.java
│   │   │       └── CarRentApp.java      # Main class
│   │   └── resources/
│   │       ├── fxml/                    # UI layouts
│   │       │   └── MainView.fxml
│   │       └── data/                    # CSV files
│   │           ├── vehicles.csv
│   │           └── rentals.csv
│   └── test/
│       └── java/
│           └── com/carrent/
│               └── model/
│                   └── VehicleTest.java
└── README.md                            # This file
```

### Technology Stack

- **Language**: Java 17+
- **UI Framework**: JavaFX 17+
- **Build Tool**: Maven
- **Data Storage**: CSV files
- **Testing**: JUnit 5

### Phase 1 Deliverables ✅

- ✅ Working development environment setup
- ✅ Project structure with build configuration
- ✅ OOP architecture with proper separation of concerns
- ✅ Model classes with encapsulation (Vehicle, Rental, Customer)
- ✅ Service layer placeholders (VehicleService, RentalService)
- ✅ Repository pattern placeholders (VehicleRepository, RentalRepository)
- ✅ View layer placeholder (MainViewController)
- ✅ Sample data files (vehicles.csv, rentals.csv)
- ✅ Basic unit test structure
- ✅ Maven configuration for JavaFX and JUnit 5

### How to Run - Complete Instructions

#### Prerequisites

1. **Java Development Kit (JDK) 11 or higher**
   - Download from [Oracle JDK](https://www.oracle.com/java/technologies/javase-downloads.html) or [OpenJDK](https://openjdk.java.net/)
   - **Installation verification**:
     ```bash
     java -version
     javac -version
     ```
   - Should display Java version 11 or higher

2. **Apache Maven 3.6 or higher**
   - Download from [Maven official website](https://maven.apache.org/download.cgi)
   - **Installation verification**:
     ```bash
     mvn -version
     ```
   - Should display Maven version and Java home path

3. **Git (Optional - for cloning repository)**
   - Download from [Git official website](https://git-scm.com/downloads)

#### Setup and Installation

1. **Clone or Download the Project**
   ```bash
   # If using Git
   git clone <repository-url>
   cd CarRent
   
   # Or download and extract ZIP file, then navigate to project directory
   cd CarRent
   ```

2. **Verify Project Structure**
   ```bash
   # Check that you're in the correct directory
   ls -la
   # You should see: pom.xml, src/, README.md, etc.
   ```

#### Running the Application

##### Method 1: Using Maven (Recommended)

1. **Clean and Compile the Project**
   ```bash
   mvn clean compile
   ```

2. **Run Tests (Optional but Recommended)**
   ```bash
   mvn test
   ```

3. **Run the JavaFX Application**
   ```bash
   mvn javafx:run
   ```

##### Method 2: Using VS Code Task (If in VS Code)

1. **Open VS Code in the project directory**
   ```bash
   code .
   ```

2. **Use the configured task**
   - Press `Ctrl+Shift+P` (or `Cmd+Shift+P` on Mac)
   - Type "Tasks: Run Task"
   - Select "Build and Run Car Rental App"

##### Method 3: Package and Run JAR

1. **Package the application**
   ```bash
   mvn clean package
   ```

2. **Run the packaged JAR**
   ```bash
   java --module-path /path/to/javafx/lib \
        --add-modules javafx.controls,javafx.fxml \
        -jar target/car-rental-app-1.0.0.jar
   ```

#### Troubleshooting

##### Common Issues and Solutions

1. **JavaFX Runtime Issues**
   ```bash
   # If you get module path errors, try:
   mvn javafx:run -Djavafx.args="--add-opens java.base/java.lang=ALL-UNNAMED"
   ```

2. **Java Version Mismatch**
   ```bash
   # Check Java version
   java -version
   # Ensure JAVA_HOME is set correctly
   echo $JAVA_HOME
   ```

3. **Maven Dependency Issues**
   ```bash
   # Force refresh dependencies
   mvn clean install -U
   ```

4. **Permission Issues (Linux/Mac)**
   ```bash
   # Make sure you have read/write permissions
   chmod -R 755 src/main/resources/data/
   ```

5. **Port or Display Issues**
   ```bash
   # For Linux systems without display
   export DISPLAY=:0
   # Or run in headless mode for testing
   mvn test -Djava.awt.headless=true
   ```

#### Expected Behavior

When the application runs successfully, you should see:

1. **Console Output**:
   ```
   [INFO] --- javafx-maven-plugin:0.0.8:run (default-cli) @ car-rental-app ---
   [INFO] Running com.carrent.CarRentApp
   ```

2. **JavaFX Window**: A desktop application window should open with the Car Rental System interface

3. **Sample Data**: The application should load with sample vehicles and rental data from CSV files

### Development Commands

```bash
# Full development cycle
mvn clean compile test package

# Run with debug information
mvn javafx:run -Djavafx.args="-Xdebug"

# Generate project reports
mvn site

# Check for dependency updates
mvn versions:display-dependency-updates

# Format code (if formatter plugin is configured)
mvn formatter:format
```

### Phase 1 Features

- Basic project structure following OOP principles
- Model classes with proper encapsulation and validation
- Service layer architecture for business logic separation
- Repository pattern for data access abstraction
- Sample data files with realistic vehicle and rental data
- Unit tests for core model functionality
- JavaFX application structure with placeholder UI

### Application Features (Current Phase)

#### Available Features ✅
- **Vehicle Management**: Basic vehicle entity with proper encapsulation
- **Motorcycle Support**: Specialized motorcycle class extending Vehicle
- **Data Models**: Complete Customer and Rental entities
- **Service Architecture**: Business logic layer structure
- **Repository Pattern**: Data access abstraction layer
- **Sample Data**: Pre-loaded vehicle and rental data
- **Unit Testing**: JUnit 5 test framework with sample tests
- **JavaFX UI**: Basic application window structure

#### Data Files
The application uses CSV files for data storage:
- **`vehicles.csv`**: Contains 5 sample vehicles (Toyota, Honda, Ford, BMW)
- **`rentals.csv`**: Contains 1 sample active rental

#### Current Limitations
- UI is in basic/placeholder state (Phase 3 development pending)
- Business logic implementation is partial (Phase 2 development pending)
- No data persistence operations yet (read/write to CSV files)
- Limited error handling and validation

### Project Status

**Current Phase**: 1 (Project Setup) - ✅ **COMPLETE**  
**Next Phase**: 2 (Business Logic Implementation)  
**Overall Progress**: ~20% complete

### Next Steps (Phase 2)

- Implement business logic in service classes
- Implement CSV file reading/writing in repository classes
- Add data validation and error handling
- Expand unit test coverage
- Create utility classes for file operations

### Build Commands Reference

```bash
# Basic Commands
mvn compile                    # Compile source code
mvn test                      # Run unit tests
mvn javafx:run               # Run the JavaFX application
mvn package                  # Create JAR file
mvn clean                    # Clean build artifacts

# Development Commands
mvn clean compile           # Clean and compile
mvn clean test             # Clean and test
mvn clean package          # Full build cycle
mvn clean install          # Install to local repository

# Specific Operations
mvn compile test           # Compile and test
mvn test -Dtest=VehicleTest # Run specific test class
mvn javafx:run -Djavafx.args="--add-opens java.base/java.lang=ALL-UNNAMED"  # Run with JVM args

# Project Information
mvn dependency:tree        # Show dependency tree
mvn help:effective-pom     # Show effective POM
mvn versions:display-dependency-updates  # Check for updates
```

### Quick Start Guide

1. **One-command setup and run**:
   ```bash
   mvn clean compile javafx:run
   ```

2. **Full validation before running**:
   ```bash
   mvn clean compile test javafx:run
   ```

3. **Package for distribution**:
   ```bash
   mvn clean package
   ```

---

**Version**: 1.0  
**Phase**: 1 Complete  
**Next Phase**: 2 - Business Logic Implementation

### System Requirements

#### Minimum Requirements
- **Operating System**: Windows 10, macOS 10.14, or Linux (Ubuntu 18.04+)
- **RAM**: 4 GB minimum, 8 GB recommended
- **Disk Space**: 500 MB for application + dependencies
- **Display**: 1024x768 minimum resolution

#### Supported Platforms
- **Windows**: Windows 10/11 (x64)
- **macOS**: macOS 10.14 (Mojave) or later
- **Linux**: Ubuntu 18.04+, CentOS 8+, Fedora 30+

#### Java Compatibility
- **Minimum**: Java 11 (LTS)
- **Recommended**: Java 17 (LTS) or Java 21 (LTS)
- **Tested on**: OpenJDK 11, Oracle JDK 11, OpenJDK 17
