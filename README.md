# Car Rental Application - Setup Guide

A desktop Car Rental application built with Java and JavaFX.

## Prerequisites

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

## Setup and Installation

1. **Clone or Download the Project**
   ```bash
   # If using Git
   git clone https://github.com/ShahriarAlomShakil/CarRentalService.git
   cd CarRentalService
   
   # Or download and extract ZIP file, then navigate to project directory
   cd CarRentalService
   ```

2. **Verify Project Structure**
   ```bash
   # Check that you're in the correct directory
   ls -la
   # You should see: pom.xml, src/, README.md, etc.
   ```

## Running the Application

### Method 1: Using Maven (Recommended)

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

### Method 2: Using VS Code Task (If in VS Code)

1. **Open VS Code in the project directory**
   ```bash
   code .
   ```

2. **Use the configured task**
   - Press `Ctrl+Shift+P` (or `Cmd+Shift+P` on Mac)
   - Type "Tasks: Run Task"
   - Select "Build and Run Car Rental App"

### Method 3: Package and Run JAR

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

## Troubleshooting

### Common Issues and Solutions

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

## Expected Behavior

When the application runs successfully, you should see:

1. **Console Output**:
   ```
   [INFO] --- javafx-maven-plugin:0.0.8:run (default-cli) @ car-rental-app ---
   [INFO] Running com.carrent.CarRentApp
   ```

2. **JavaFX Window**: A desktop application window should open with the Car Rental System interface

3. **Sample Data**: The application should load with sample vehicles and rental data from CSV files

## Quick Start

1. **One-command setup and run**:
   ```bash
   mvn clean compile javafx:run
   ```

2. **With testing**:
   ```bash
   mvn clean compile test javafx:run
   ```

---

For complete project documentation, architecture details, and development information, see [PROJECT_DOCUMENTATION.md](PROJECT_DOCUMENTATION.md)
