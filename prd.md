# Product Requirements Document (PRD)
## Car Rental Application

**Version:** 1.0  
**Date:** June 20, 2025  
**Technology Stack:** Java + JavaFX  

---

## 1. Project Overview

### 1.1 Purpose
Develop a simple desktop Car Rental application th### 4.1 Technology Stack
- **Language**: Java 17+
- **UI Framework**: JavaFX 17+
- **Build Tool**: Maven or Gradle
- **Data Storage**: Simple text files (CSV format)
- **Testing**: JUnit 5ows users to browse available vehicles, rent them, calculate rental costs, and return vehicles. The application focuses on core rental functionality without user authentication complexity.

### 1.2 Target Users
- Car rental service employees
- Customers using self-service kiosks
- Small car rental business owners

### 1.3 Key Objectives
- Provide an intuitive interface for vehicle management
- Simplify the rental process
- Automate cost calculations
- Track vehicle availability status

---

## 2. Core Features (Basic Only)

### 2.1 Vehicle Management
- **Display Vehicle List**: Show all vehicles with basic details (make, model, daily rate, availability)
- **Vehicle Status**: Available or Rented only

### 2.2 Rental Operations
- **Rent Vehicle**: Select an available vehicle and rent it
- **Cost Calculation**: Calculate total cost (daily rate × number of days)
- **Return Vehicle**: Mark vehicle as returned and available

### 2.3 Data Management
- **File Storage**: Save data in simple CSV files
- **Data Persistence**: Load and save vehicle/rental data

---

## 3. Development Phases

## Phase 1: Project Setup and Foundation (Week 1)

### 3.1 Environment Setup
- **Day 1-2**: Setup Development Environment
  - Install Java JDK 17+ 
  - Setup JavaFX SDK
  - Configure IDE (IntelliJ IDEA/Eclipse)
  - Create Maven/Gradle project structure
  - Setup version control (Git)

### 3.2 Project Structure
- **Day 3-4**: Create Simple Project Structure
  ```
  src/
  ├── main/
  │   ├── java/
  │   │   ├── Vehicle.java        # Vehicle data class
  │   │   ├── Rental.java         # Rental data class
  │   │   ├── CarRentService.java # All business logic
  │   │   ├── MainController.java # UI controller
  │   │   └── CarRentApp.java     # Main class
  │   └── resources/
  │       ├── main.fxml          # Single UI layout
  │       └── data/              # CSV files
  ```

### 3.3 Dependencies Setup
- **Day 5**: Configure Build File
  - JavaFX controls and FXML only
  - Unit testing framework (JUnit 5)

**Deliverables:**
- ✅ Working development environment
- ✅ Project structure with build configuration
- ✅ Version control setup

---

## Phase 2: Data Models and Core Logic (Week 2)

### 3.1 Data Models (Simple Classes)
- **Day 1-2**: Create Basic Classes
  - `Vehicle.java`: Properties (id, make, model, dailyRate, isAvailable)
  - `Rental.java`: Properties (vehicleId, customerName, startDate, endDate, totalCost)

### 3.2 Business Logic (Single Service)
- **Day 3-4**: Implement One Service Class
  - `CarRentService.java`: All operations (load vehicles, rent, return, save data)

### 3.3 Data Storage (Simple File Operations)
- **Day 5**: Basic File Handling
  - Direct CSV read/write in service class
  - Simple validation
  - Basic error handling

**Deliverables:**
- ✅ Simple data classes
- ✅ Single service class with all operations
- ✅ Basic file I/O functionality

---

## Phase 3: User Interface Design (Week 3)

### 3.1 Main Application Window
- **Day 1-2**: Single Window Design
  - One window with all functionality
  - Simple layout with basic sections

### 3.2 Vehicle Display
- **Day 3**: Basic Vehicle Table
  - Simple table: Make, Model, Rate, Status
  - Select vehicle button

### 3.3 Rental Interface
- **Day 4-5**: Minimal Forms
  - Customer name field
  - Start and end date fields
  - Rent and Return buttons

**Deliverables:**
- ✅ Single window UI
- ✅ Basic table and forms
- ✅ Minimal styling

---

## Phase 4: Core Functionality Implementation (Week 4)

### 4.1 Vehicle Operations
- **Day 1-2**: Implement Vehicle Features
  - Load and display vehicle list
  - Vehicle search and filtering
  - Vehicle details view
  - Real-time availability updates

### 4.2 Rental Operations
- **Day 3-4**: Implement Rental Features
  - Vehicle selection for rental
  - Date validation and selection
  - Automatic cost calculation
  - Rental confirmation process

### 4.3 Return Operations
- **Day 5**: Implement Return Features
  - Active rental lookup
  - Return date processing
  - Final cost calculation
  - Vehicle status update

**Deliverables:**
- ✅ Fully functional vehicle browsing
- ✅ Complete rental process
- ✅ Working return system

---

## Phase 5: Testing and Deployment (Week 5)

### 5.1 Testing
- **Day 1-3**: Basic Testing
  - Unit tests for model classes
  - Service layer testing
  - Basic integration testing

### 5.2 Bug Fixes
- **Day 4**: Quality Assurance
  - Fix identified issues
  - Code cleanup
  - Basic documentation

### 5.3 Deployment
- **Day 5**: Final Package
  - Create executable JAR
  - Include sample data
  - Basic user guide

**Deliverables:**
- ✅ Tested application
- ✅ Deployment package
- ✅ Simple documentation

---

## Phase 6: Reserved for Future (Optional)

*This phase is reserved for any additional requirements or enhancements that may arise during development.*

---

## 4. Technical Specifications

### 4.1 System Requirements
- **Operating System**: Windows 10+, macOS 10.14+, Linux
- **Java Version**: JDK 17 or higher
- **Memory**: Minimum 256MB RAM
- **Storage**: 50MB available space (including data files)

### 4.2 Technology Stack
- **Language**: Java 17+
- **UI Framework**: JavaFX 17+
- **Build Tool**: Maven or Gradle
- **Data Storage**: JSON files
- **Testing**: JUnit 5

### 4.3 Architecture Pattern
- **Pattern**: Simple MVC (Model-View-Controller)
- **Design**: Minimal layers for easy understanding
- **Focus**: Functionality over complex architecture

---

## 5. User Interface Specifications

### 5.1 Main Window Layout (Ultra Simple)
```
┌─────────────────────────────────────────────────┐
│ Car Rental System                    [- □ X]    │
├─────────────────────────────────────────────────┤
│ ┌─────────────────────────────────────────────┐ │
│ │            Vehicle List                     │ │
│ └─────────────────────────────────────────────┘ │
│ ┌─────────────────────────────────────────────┐ │
│ │ Customer: [____] Start: [__] End: [__]      │ │
│ │ [Rent Vehicle] [Return Vehicle]             │ │
│ └─────────────────────────────────────────────┘ │
└─────────────────────────────────────────────────┘
```

### 5.2 Screen Definitions

#### 5.2.1 Single Screen Design
- **Purpose**: All functionality in one screen
- **Components**:
  - Vehicle table (Make, Model, Rate, Status)
  - Customer name text field
  - Start/End date pickers
  - Rent and Return buttons
  - Cost display

---

## 6. Data Specifications

### 6.1 Vehicle Data (CSV)
```
Make,Model,DailyRate,IsAvailable
Toyota,Camry,45.00,true
Honda,Civic,35.00,true
Ford,Explorer,65.00,false
```

### 6.2 Rental Data (CSV)
```
VehicleMake,CustomerName,StartDate,EndDate,TotalCost
Toyota Camry,John Doe,2025-06-20,2025-06-23,135.00
Ford Explorer,Jane Smith,2025-06-21,2025-06-25,260.00
```

### 6.3 File Structure (Minimal)
```
data/
├── vehicles.csv       # Vehicle list
└── rentals.csv        # Rental records
```

### 6.4 Sample Data (Minimal)
**Sample vehicles.csv:**
```
Make,Model,DailyRate,IsAvailable
Toyota,Corolla,29.00,true
Honda,Civic,38.00,true
Ford,Explorer,68.00,true
```

---

## 7. Business Rules

### 7.1 Rental Rules
- Minimum rental period: 1 day
- Maximum rental period: 30 days
- Start date cannot be in the past
- End date must be after start date
- Vehicle must be available for the entire rental period

### 7.2 Cost Calculation (Simple)
- Base cost = Daily Rate × Number of Days
- Cost displayed with 2 decimal places
- No additional fees or taxes

### 7.3 Vehicle Management (Basic)
- Vehicle becomes unavailable when rented
- Vehicle becomes available when returned
- Simple status tracking only

---

## 8. Error Handling (Basic)

### 8.1 Input Validation
- Required field validation
- Date validation (end date after start date)
- Numeric validation for costs

### 8.2 File Operations
- Handle file read/write errors
- Basic error messages to user

---

## 9. Success Criteria

### 9.1 Functional Requirements
- ✅ Users can view all vehicles with current availability
- ✅ Users can successfully rent available vehicles
- ✅ System accurately calculates rental costs
- ✅ Users can return vehicles and update availability
- ✅ All data persists between application sessions

### 9.2 Quality Requirements
- ✅ Application starts within 3 seconds
- ✅ All user actions respond within 1 second
- ✅ No data loss during normal operations
- ✅ Simple, intuitive UI
- ✅ Handles 50+ vehicles and 200+ rental records

### 9.3 Deployment Requirements
- ✅ Single executable JAR file for distribution
- ✅ No external dependencies (pure file-based storage)
- ✅ Works on target operating systems
- ✅ Includes sample CSV data for immediate use

---

## 10. Future Enhancements (Out of Scope)

### 10.1 Phase 2 Features
- User authentication and authorization
- Customer database management
- Online reservations
- Payment processing integration
- Vehicle maintenance tracking

### 10.2 Advanced Features
- Multi-location support
- Fleet management
- Advanced reporting and analytics
- Mobile application
- Integration with external systems

---

## 11. Project Timeline Summary

| Phase | Duration | Key Deliverables |
|-------|----------|------------------|
| Phase 1 | Week 1 | Project setup |
| Phase 2 | Week 2 | Basic classes and file handling |
| Phase 3 | Week 3 | Simple UI |
| Phase 4 | Week 4 | Core functionality |
| Phase 5 | Week 5 | Testing and final package |

**Total Duration**: 5 weeks
**Estimated Effort**: 60-80 hours

---

## 12. Development Notes

### 12.1 Simple Best Practices
- Use clear class and method names
- Keep methods small and focused
- Add basic comments for clarity
- Follow standard Java naming conventions

### 12.2 Basic Architecture
- **Model**: Simple data classes (Vehicle, Rental)
- **Service**: Basic business logic
- **View**: Simple JavaFX controllers
- **File I/O**: Direct CSV read/write

### 12.3 Minimal Requirements
- Basic unit tests for core methods
- Simple error messages
- Clean, readable code
- Working application with core features

---

*This ultra-simplified PRD focuses on the absolute minimum viable car rental application with clean, straightforward code.*
