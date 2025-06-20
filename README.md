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

### How to Run (Phase 1)

1. **Prerequisites**:
   - Java JDK 17 or higher
   - Maven 3.6 or higher

2. **Compile the project**:
   ```bash
   mvn compile
   ```

3. **Run tests**:
   ```bash
   mvn test
   ```

4. **Run the application**:
   ```bash
   mvn javafx:run
   ```

### Phase 1 Features

- Basic project structure following OOP principles
- Model classes with proper encapsulation and validation
- Service layer architecture for business logic separation
- Repository pattern for data access abstraction
- Sample data files with realistic vehicle and rental data
- Unit tests for core model functionality
- JavaFX application structure with placeholder UI

### Next Steps (Phase 2)

- Implement business logic in service classes
- Implement CSV file reading/writing in repository classes
- Add data validation and error handling
- Expand unit test coverage
- Create utility classes for file operations

### Build Commands

```bash
# Compile the project
mvn compile

# Run tests
mvn test

# Run the application
mvn javafx:run

# Package as JAR
mvn package

# Clean build artifacts
mvn clean
```

### Sample Data

The application includes sample data files:

**vehicles.csv**: 5 sample vehicles including Toyota, Honda, Ford, and BMW models
**rentals.csv**: 1 sample active rental

### Architecture Patterns Used

- **Model-View-Controller (MVC)**: Clear separation between data, UI, and business logic
- **Repository Pattern**: Abstraction layer for data access
- **Service Layer Pattern**: Business logic encapsulation
- **SOLID Principles**: Single responsibility, dependency inversion

---

**Version**: 1.0  
**Phase**: 1 Complete  
**Next Phase**: 2 - Business Logic Implementation
