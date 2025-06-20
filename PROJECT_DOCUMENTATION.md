# Car Rental Application - Complete Project Documentation

## Table of Contents
1. [Project Overview](#project-overview)
2. [Architecture & Design](#architecture--design)
3. [Technology Stack](#technology-stack)
4. [Project Structure](#project-structure)
5. [Core Components](#core-components)
6. [Data Models](#data-models)
7. [Business Logic](#business-logic)
8. [User Interface](#user-interface)
9. [Data Storage](#data-storage)
10. [Testing Strategy](#testing-strategy)
11. [Build & Deployment](#build--deployment)
12. [Development Phases](#development-phases)
13. [How to Run](#how-to-run)
14. [Future Enhancements](#future-enhancements)

---

## Project Overview

The **Car Rental Application** is a desktop-based rental management system built with Java and JavaFX. It provides a comprehensive solution for managing vehicle rentals, customers, and rental transactions in a car rental business.

### Key Features
- **Vehicle Management**: Add, update, and track rental vehicles
- **Customer Management**: Handle customer information and rental history
- **Rental Operations**: Process new rentals, returns, and rental calculations
- **Inventory Tracking**: Real-time availability status of vehicles
- **Data Persistence**: CSV-based data storage for vehicles and rentals
- **Modern UI**: JavaFX-based graphical user interface

### Target Users
- Car rental business operators
- Fleet managers
- Rental counter staff
- Small to medium-sized rental companies

---

## Architecture & Design

### Design Patterns Used

#### 1. Model-View-Controller (MVC)
- **Model**: Data entities (`Vehicle`, `Rental`, `Customer`, `Motorcycle`)
- **View**: JavaFX FXML files and controllers (`MainViewController`)
- **Controller**: Service layer managing business logic

#### 2. Repository Pattern
- Abstract data access through repository interfaces
- Separation of data storage concerns from business logic
- Easy switching between different data sources

#### 3. Service Layer Pattern
- Encapsulation of business rules and validation
- Transaction management and coordination
- Clear API for UI layer interaction

#### 4. SOLID Principles
- **Single Responsibility**: Each class has one clear purpose
- **Open/Closed**: Extensible through inheritance (Vehicle → Motorcycle)
- **Liskov Substitution**: Subtypes can replace parent types
- **Interface Segregation**: Focused, specific interfaces
- **Dependency Inversion**: Depend on abstractions, not concretions

### Component Architecture
```
┌─────────────────┐
│   UI Layer      │ ← JavaFX Controllers & FXML
├─────────────────┤
│ Service Layer   │ ← Business Logic & Validation
├─────────────────┤
│Repository Layer │ ← Data Access Abstraction
├─────────────────┤
│  Data Layer     │ ← CSV Files & File I/O
└─────────────────┘
```

---

## Technology Stack

### Core Technologies
- **Language**: Java 11+
- **UI Framework**: JavaFX 11.0.2
- **Build Tool**: Apache Maven 3.6+
- **Testing**: JUnit 5.9.2
- **Data Format**: CSV (Comma-Separated Values)

### Dependencies
```xml
<!-- JavaFX Dependencies -->
<dependency>
    <groupId>org.openjfx</groupId>
    <artifactId>javafx-controls</artifactId>
    <version>11.0.2</version>
</dependency>

<dependency>
    <groupId>org.openjfx</groupId>
    <artifactId>javafx-fxml</artifactId>
    <version>11.0.2</version>
</dependency>

<!-- Testing Dependencies -->
<dependency>
    <groupId>org.junit.jupiter</groupId>
    <artifactId>junit-jupiter-api</artifactId>
    <version>5.9.2</version>
</dependency>
```

### Development Tools
- **IDE**: Any Java-compatible IDE (IntelliJ IDEA, Eclipse, VS Code)
- **Version Control**: Git
- **Package Manager**: Maven
- **Runtime**: Java Virtual Machine (JVM)

---

## Project Structure

```
CarRent/
├── pom.xml                                 # Maven build configuration
├── README.md                               # Basic project information
├── PROJECT_DOCUMENTATION.md               # This comprehensive documentation
├── src/
│   ├── main/
│   │   ├── java/com/carrent/
│   │   │   ├── CarRentApp.java            # Main application entry point
│   │   │   ├── model/                     # Data model classes
│   │   │   │   ├── Vehicle.java           # Base vehicle entity
│   │   │   │   ├── Motorcycle.java        # Motorcycle specialization
│   │   │   │   ├── Customer.java          # Customer entity
│   │   │   │   └── Rental.java            # Rental transaction entity
│   │   │   ├── service/                   # Business logic layer
│   │   │   │   ├── VehicleService.java    # Vehicle operations
│   │   │   │   └── RentalService.java     # Rental operations
│   │   │   ├── repository/                # Data access layer
│   │   │   │   ├── VehicleRepository.java # Vehicle data operations
│   │   │   │   └── RentalRepository.java  # Rental data operations
│   │   │   ├── view/                      # UI controllers
│   │   │   │   └── MainViewController.java # Main window controller
│   │   │   └── demo/                      # Demo classes
│   │   │       └── MotorcycleDemo.java    # Motorcycle demonstration
│   │   └── resources/
│   │       ├── fxml/                      # JavaFX layout files
│   │       │   └── MainView.fxml          # Main application layout
│   │       └── data/                      # Application data files
│   │           ├── vehicles.csv           # Vehicle inventory data
│   │           └── rentals.csv            # Rental transaction data
│   └── test/
│       └── java/com/carrent/
│           ├── model/                     # Model unit tests
│           │   ├── VehicleTest.java       # Vehicle class tests
│           │   └── MotorcycleTest.java    # Motorcycle class tests
│           ├── service/                   # Service layer tests
│           │   └── Phase2ValidationTest.java # Business logic validation
│           └── view/                      # UI tests
│               └── Phase3ValidationTest.java # UI validation tests
└── target/                                # Maven build output directory
```

---

## Core Components

### 1. Main Application (`CarRentApp.java`)
- **Purpose**: Entry point for the JavaFX application
- **Responsibilities**:
  - Initialize JavaFX application context
  - Load main FXML layout
  - Set up primary stage and scene
  - Handle application lifecycle

### 2. Model Layer
The model layer contains all data entities representing the core business objects.

#### Base Classes
- **`Vehicle.java`**: Abstract base class for all rental vehicles
- **`Customer.java`**: Represents rental customers
- **`Rental.java`**: Represents rental transactions

#### Specialized Classes
- **`Motorcycle.java`**: Specialized vehicle type with motorcycle-specific properties

### 3. Service Layer
Contains business logic and coordinates between UI and data layers.

- **`VehicleService.java`**: Manages vehicle-related operations
- **`RentalService.java`**: Handles rental transactions and business rules

### 4. Repository Layer
Provides data access abstraction and handles persistence operations.

- **`VehicleRepository.java`**: Vehicle data access operations
- **`RentalRepository.java`**: Rental data access operations

### 5. View Layer
JavaFX-based user interface components.

- **`MainViewController.java`**: Main window controller
- **`MainView.fxml`**: Main application layout definition

---

## Data Models

### Vehicle Entity
```java
public class Vehicle {
    private String id;           // Unique identifier (e.g., "V001")
    private String make;         // Manufacturer (e.g., "Toyota")
    private String model;        // Model name (e.g., "Camry")
    private double dailyRate;    // Daily rental rate in dollars
    private boolean isAvailable; // Availability status
}
```

**Key Features:**
- Encapsulation with private fields and public getters/setters
- Input validation for daily rate (must be positive)
- Default availability status (true for new vehicles)
- Immutable ID after creation
- Proper equals() and hashCode() implementation

### Motorcycle Entity (Inherits from Vehicle)
```java
public class Motorcycle extends Vehicle {
    private int engineSize;         // Engine size in CC
    private String motorcycleType;  // Sport, Cruiser, Touring, etc.
    private boolean hasLuggage;     // Luggage capacity availability
    private int passengerCapacity;  // Number of passengers (1-2)
    private boolean hasSidecar;     // Sidecar attachment status
}
```

**Specialized Features:**
- Inheritance from Vehicle base class
- Motorcycle-specific attributes
- Enhanced validation for motorcycle properties
- Specialized toString() method

### Customer Entity
```java
public class Customer {
    private String customerId;    // Unique customer identifier
    private String name;          // Customer full name
    private String phoneNumber;   // Contact phone number
    private String email;         // Email address
    private String driverLicense; // Driver's license number
}
```

### Rental Entity
```java
public class Rental {
    private String rentalId;      // Unique rental identifier
    private String vehicleId;     // Associated vehicle ID
    private String customerName;  // Customer name
    private String customerPhone; // Customer phone
    private LocalDate startDate;  // Rental start date
    private LocalDate endDate;    // Rental end date
    private double totalCost;     // Total rental cost
}
```

---

## Business Logic

### Vehicle Management
- **Add Vehicle**: Validate input and add new vehicles to inventory
- **Update Vehicle**: Modify vehicle properties and rates
- **Delete Vehicle**: Remove vehicles from inventory (if not rented)
- **Search Vehicles**: Find vehicles by make, model, or availability
- **Set Availability**: Toggle vehicle availability status

### Rental Management
- **Create Rental**: Process new rental transactions
- **Calculate Cost**: Compute rental cost based on duration and daily rate
- **Return Vehicle**: Process vehicle returns and update availability
- **Rental History**: Track customer rental history
- **Validate Dates**: Ensure rental dates are logical and available

### Data Validation Rules
- **Vehicle ID**: Must be unique and non-empty
- **Daily Rate**: Must be positive (> 0)
- **Dates**: Start date must be before end date
- **Customer Info**: Required fields validation
- **Phone Numbers**: Format validation
- **Engine Size**: Must be positive for motorcycles

---

## User Interface

### Main Application Window
The JavaFX-based GUI provides an intuitive interface for rental operations:

#### Key UI Components
- **Vehicle Management Panel**: Add, edit, and view vehicles
- **Rental Management Panel**: Process rentals and returns
- **Customer Information Panel**: Manage customer data
- **Search and Filter Panel**: Find specific vehicles or rentals
- **Reports Panel**: View rental statistics and reports

#### UI Features
- **Responsive Design**: Adapts to different window sizes
- **Data Validation**: Real-time input validation with error messages
- **Navigation**: Tab-based navigation between different sections
- **Data Grids**: Table views for vehicles and rentals
- **Form Controls**: Input forms for data entry

### FXML Layout Structure
```xml
<!-- MainView.fxml -->
<BorderPane xmlns="http://javafx.com/javafx/11.0.1">
    <top>
        <!-- Menu bar and toolbar -->
    </top>
    <center>
        <!-- Main content area with tabs -->
        <TabPane>
            <Tab text="Vehicles">
                <!-- Vehicle management interface -->
            </Tab>
            <Tab text="Rentals">
                <!-- Rental management interface -->
            </Tab>
            <Tab text="Customers">
                <!-- Customer management interface -->
            </Tab>
        </TabPane>
    </center>
    <bottom>
        <!-- Status bar -->
    </bottom>
</BorderPane>
```

---

## Data Storage

### CSV File Format

#### vehicles.csv
```csv
ID,Make,Model,DailyRate,IsAvailable
V001,Toyota,Corolla,29.00,true
V002,Honda,Civic,38.00,true
V003,Toyota,Camry,42.00,false
V004,Ford,Explorer,68.00,true
V005,BMW,3Series,95.00,true
```

#### rentals.csv
```csv
ID,VehicleID,CustomerName,CustomerPhone,StartDate,EndDate,TotalCost
R001,V003,John Doe,555-0123,2025-06-20,2025-06-23,126.00
```

### Data Access Pattern
- **Repository Pattern**: Abstract data access operations
- **File I/O**: CSV reading and writing utilities
- **Error Handling**: Graceful handling of file access errors
- **Data Integrity**: Validation during read/write operations

---

## Testing Strategy

### Unit Testing Framework
- **Framework**: JUnit 5.9.2
- **Coverage**: Model classes, service methods, and data validation
- **Test Types**: Unit tests, integration tests, and validation tests

### Test Categories

#### 1. Model Tests (`VehicleTest.java`, `MotorcycleTest.java`)
```java
@Test
void testVehicleCreation() {
    Vehicle vehicle = new Vehicle("V001", "Toyota", "Camry", 45.0);
    assertNotNull(vehicle);
    assertEquals("V001", vehicle.getId());
    assertEquals("Toyota", vehicle.getMake());
    assertTrue(vehicle.isAvailable());
}

@Test
void testInvalidDailyRate() {
    assertThrows(IllegalArgumentException.class, () -> {
        new Vehicle("V001", "Toyota", "Camry", -10.0);
    });
}
```

#### 2. Service Tests (`Phase2ValidationTest.java`)
- Business logic validation
- Service method behavior
- Data consistency checks

#### 3. UI Tests (`Phase3ValidationTest.java`)
- Controller functionality
- UI component behavior
- User interaction validation

### Testing Commands
```bash
# Run all tests
mvn test

# Run specific test class
mvn test -Dtest=VehicleTest

# Run tests with coverage
mvn test jacoco:report
```

---

## Build & Deployment

### Maven Configuration
The project uses Maven for dependency management and build automation.

#### Key Maven Goals
```bash
# Compile source code
mvn compile

# Run unit tests
mvn test

# Package application
mvn package

# Clean build artifacts
mvn clean

# Run JavaFX application
mvn javafx:run

# Complete build cycle
mvn clean compile test package
```

### Build Plugins
- **Maven Compiler Plugin**: Java compilation
- **Maven Surefire Plugin**: Test execution
- **JavaFX Maven Plugin**: JavaFX application execution
- **Maven Resources Plugin**: Resource processing

### Deployment Options
1. **Standalone JAR**: Package as executable JAR file
2. **JavaFX Native**: Create platform-specific executables
3. **Development Mode**: Run directly with Maven

---

## Development Phases

### Phase 1: Project Setup ✅ **COMPLETE**
- [x] Maven project structure
- [x] Basic model classes with encapsulation
- [x] Service and repository placeholders
- [x] Sample data files
- [x] Unit test framework setup
- [x] JavaFX application skeleton

### Phase 2: Business Logic Implementation
- [ ] Complete service layer implementation
- [ ] Repository data access methods
- [ ] CSV file reading/writing
- [ ] Data validation and error handling
- [ ] Expanded unit test coverage

### Phase 3: User Interface Development
- [ ] Complete FXML layout design
- [ ] Controller implementation
- [ ] Data binding and event handling
- [ ] Form validation and user feedback
- [ ] UI testing and validation

### Phase 4: Integration & Testing
- [ ] Integration testing
- [ ] End-to-end workflow testing
- [ ] Performance testing
- [ ] Bug fixes and optimization

### Phase 5: Final Polish & Documentation
- [ ] Code review and refactoring
- [ ] Complete documentation
- [ ] User manual creation
- [ ] Deployment preparation

---

## How to Run

### Prerequisites
1. **Java Development Kit (JDK) 11 or higher**
   - Download from [Oracle](https://www.oracle.com/java/technologies/javase-downloads.html) or [OpenJDK](https://openjdk.java.net/)
   - Verify installation: `java -version`

2. **Apache Maven 3.6 or higher**
   - Download from [Maven website](https://maven.apache.org/download.cgi)
   - Verify installation: `mvn -version`

3. **Git (optional, for version control)**
   - Download from [Git website](https://git-scm.com/downloads)

### Setup Steps
1. **Clone or download the project**
   ```bash
   git clone <repository-url>
   cd CarRent
   ```

2. **Compile the project**
   ```bash
   mvn compile
   ```

3. **Run tests (optional but recommended)**
   ```bash
   mvn test
   ```

4. **Run the application**
   ```bash
   mvn javafx:run
   ```

### Alternative Running Methods
```bash
# Run with specific JVM arguments
mvn javafx:run -Djavafx.args="--add-opens java.base/java.lang=ALL-UNNAMED"

# Package and run JAR
mvn package
java --module-path /path/to/javafx/lib --add-modules javafx.controls,javafx.fxml -jar target/car-rental-app-1.0.0.jar
```

### Troubleshooting
- **JavaFX Module Issues**: Ensure JavaFX modules are properly configured
- **File Access**: Check that CSV files have read/write permissions
- **Port Conflicts**: No network ports used in this desktop application

---

## Future Enhancements

### Planned Features
1. **Database Integration**
   - Replace CSV with SQLite or MySQL
   - Advanced querying capabilities
   - Better data integrity and concurrency

2. **Advanced UI Features**
   - Dashboard with charts and analytics
   - Advanced search and filtering
   - Print receipts and reports
   - Multi-language support

3. **Business Features**
   - Customer loyalty programs
   - Reservation system
   - Payment processing integration
   - Insurance tracking
   - Maintenance scheduling

4. **Technical Improvements**
   - REST API for mobile apps
   - Cloud deployment options
   - Automated backups
   - Performance monitoring
   - Security enhancements

### Extension Points
- **New Vehicle Types**: Add trucks, vans, electric vehicles
- **Pricing Models**: Complex pricing algorithms, seasonal rates
- **Reporting**: Custom reports and analytics
- **Integration**: Third-party service integration
- **Mobile App**: Companion mobile application

---

## Contributing

### Development Guidelines
1. Follow Java coding conventions
2. Write unit tests for new features
3. Update documentation for changes
4. Use meaningful commit messages
5. Follow SOLID principles

### Code Style
- Use descriptive variable and method names
- Add JavaDoc comments for public methods
- Maintain consistent indentation (4 spaces)
- Follow package organization conventions

---

## License & Contact

**Version**: 1.0.0  
**Current Phase**: Phase 1 Complete  
**Next Phase**: Business Logic Implementation  
**Last Updated**: June 20, 2025

For questions, issues, or contributions, please refer to the project repository or contact the development team.

---

*This documentation is maintained alongside the codebase and should be updated with each major change or phase completion.*
