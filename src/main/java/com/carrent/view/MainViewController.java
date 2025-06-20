package com.carrent.view;

import com.carrent.model.Vehicle;
import com.carrent.model.Motorcycle;
import com.carrent.model.Rental;
import com.carrent.service.VehicleService;
import com.carrent.service.RentalService;

import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * Main View Controller for the Car Rental Application - Phase 3 Implementation
 * 
 * This controller manages the complete UI functionality including:
 * - Vehicle display and selection
 * - Rental operations (rent/return)
 * - Real-time cost calculations
 * - Data refresh and form management
 * 
 * @author Car Rental Team
 * @version 3.0
 */
public class MainViewController {
    
    // Services
    private VehicleService vehicleService;
    private RentalService rentalService;
    
    // Vehicle Table Components
    @FXML private TableView<Vehicle> vehicleTable;
    @FXML private TableColumn<Vehicle, String> idColumn;
    @FXML private TableColumn<Vehicle, String> makeColumn;
    @FXML private TableColumn<Vehicle, String> modelColumn;
    @FXML private TableColumn<Vehicle, String> dailyRateColumn;
    @FXML private TableColumn<Vehicle, String> statusColumn;
    
    // Active Rentals Table Components
    @FXML private TableView<Rental> activeRentalsTable;
    @FXML private TableColumn<Rental, String> rentalIdColumn;
    @FXML private TableColumn<Rental, String> rentalVehicleColumn;
    @FXML private TableColumn<Rental, String> rentalCustomerColumn;
    @FXML private TableColumn<Rental, String> rentalStartColumn;
    @FXML private TableColumn<Rental, String> rentalEndColumn;
    @FXML private TableColumn<Rental, String> rentalCostColumn;
    
    // Form Components
    @FXML private TextField customerNameField;
    @FXML private TextField customerPhoneField;
    @FXML private DatePicker startDatePicker;
    @FXML private DatePicker endDatePicker;
    @FXML private Label costLabel;
    
    // Button Components
    @FXML private Button rentButton;
    @FXML private Button returnButton;
    @FXML private Button refreshButton;
    @FXML private Button clearButton;
    @FXML private Button addVehicleButton;
    
    // Status Components
    @FXML private Label statusLabel;
    @FXML private Label vehicleCountLabel;
    
    // Data
    private ObservableList<Vehicle> vehicleList = FXCollections.observableArrayList();
    private ObservableList<Rental> activeRentalsList = FXCollections.observableArrayList();
    
    /**
     * Initialize method called by JavaFX after loading FXML
     */
    @FXML
    private void initialize() {
        // Initialize services
        this.vehicleService = new VehicleService();
        this.rentalService = new RentalService();
        
        // Setup table columns
        setupVehicleTable();
        setupActiveRentalsTable();
        
        // Setup event handlers
        setupEventHandlers();
        
        // Load initial data
        loadData();
        
        // Set initial state
        updateUI();
        
        updateStatus("Application initialized successfully");
    }
    
    /**
     * Setup vehicle table columns and data binding
     */
    private void setupVehicleTable() {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        makeColumn.setCellValueFactory(new PropertyValueFactory<>("make"));
        modelColumn.setCellValueFactory(new PropertyValueFactory<>("model"));
        
        // Custom cell value factory for daily rate formatting
        dailyRateColumn.setCellValueFactory(cellData -> 
            new SimpleStringProperty(String.format("$%.2f", cellData.getValue().getDailyRate())));
        
        // Custom cell value factory for status
        statusColumn.setCellValueFactory(cellData -> 
            new SimpleStringProperty(cellData.getValue().isAvailable() ? "Available" : "Rented"));
        
        vehicleTable.setItems(vehicleList);
    }
    
    /**
     * Setup active rentals table columns and data binding
     */
    private void setupActiveRentalsTable() {
        rentalIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        rentalVehicleColumn.setCellValueFactory(new PropertyValueFactory<>("vehicleId"));
        rentalCustomerColumn.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        
        // Custom cell value factories for date formatting
        rentalStartColumn.setCellValueFactory(cellData -> 
            new SimpleStringProperty(cellData.getValue().getStartDate().toString()));
        rentalEndColumn.setCellValueFactory(cellData -> 
            new SimpleStringProperty(cellData.getValue().getEndDate().toString()));
        rentalCostColumn.setCellValueFactory(cellData -> 
            new SimpleStringProperty(String.format("$%.2f", cellData.getValue().getTotalCost())));
        
        activeRentalsTable.setItems(activeRentalsList);
    }
    
    /**
     * Setup event handlers for UI interactions
     */
    private void setupEventHandlers() {
        // Vehicle selection handler
        vehicleTable.getSelectionModel().selectedItemProperty().addListener(
            (observable, oldValue, newValue) -> handleVehicleSelection(newValue));
        
        // Date picker change handlers for cost calculation
        startDatePicker.valueProperty().addListener((observable, oldValue, newValue) -> calculateCost());
        endDatePicker.valueProperty().addListener((observable, oldValue, newValue) -> calculateCost());
        
        // Active rental selection handler
        activeRentalsTable.getSelectionModel().selectedItemProperty().addListener(
            (observable, oldValue, newValue) -> handleActiveRentalSelection(newValue));
    }
    
    /**
     * Load data from services
     */
    private void loadData() {
        try {
            // Load vehicles
            List<Vehicle> vehicles = vehicleService.getAllVehicles();
            vehicleList.clear();
            vehicleList.addAll(vehicles);
            
            // Load active rentals
            List<Rental> activeRentals = rentalService.getActiveRentals();
            activeRentalsList.clear();
            activeRentalsList.addAll(activeRentals);
            
            updateVehicleCount();
            updateStatus("Data loaded successfully");
        } catch (Exception e) {
            showError("Error loading data", e.getMessage());
        }
    }
    
    /**
     * Handle vehicle selection in the table
     */
    private void handleVehicleSelection(Vehicle selectedVehicle) {
        if (selectedVehicle != null) {
            if (selectedVehicle.isAvailable()) {
                updateStatus("Selected vehicle: " + selectedVehicle.getMake() + " " + selectedVehicle.getModel());
                rentButton.setDisable(false);
            } else {
                updateStatus("Selected vehicle is not available for rent");
                rentButton.setDisable(true);
            }
        } else {
            rentButton.setDisable(true);
        }
        calculateCost();
    }
    
    /**
     * Handle active rental selection
     */
    private void handleActiveRentalSelection(Rental selectedRental) {
        if (selectedRental != null) {
            returnButton.setDisable(false);
            updateStatus("Selected rental: " + selectedRental.getId() + " for " + selectedRental.getCustomerName());
        } else {
            returnButton.setDisable(true);
        }
    }
    
    /**
     * Calculate and display rental cost
     */
    private void calculateCost() {
        Vehicle selectedVehicle = vehicleTable.getSelectionModel().getSelectedItem();
        LocalDate startDate = startDatePicker.getValue();
        LocalDate endDate = endDatePicker.getValue();
        
        if (selectedVehicle != null && startDate != null && endDate != null) {
            if (endDate.isAfter(startDate)) {
                try {
                    double cost = rentalService.calculateRentalCost(selectedVehicle.getId(), startDate, endDate);
                    costLabel.setText(String.format("Total Cost: $%.2f", cost));
                    costLabel.setStyle("-fx-text-fill: #27ae60; -fx-font-weight: bold;");
                } catch (Exception e) {
                    costLabel.setText("Error calculating cost");
                    costLabel.setStyle("-fx-text-fill: #e74c3c; -fx-font-weight: bold;");
                }
            } else {
                costLabel.setText("End date must be after start date");
                costLabel.setStyle("-fx-text-fill: #e74c3c; -fx-font-weight: bold;");
            }
        } else {
            costLabel.setText("Total Cost: $0.00");
            costLabel.setStyle("-fx-text-fill: #27ae60; -fx-font-weight: bold;");
        }
    }
    
    /**
     * Handle vehicle rental
     */
    @FXML
    private void handleRentVehicle() {
        try {
            // Validate input
            Vehicle selectedVehicle = vehicleTable.getSelectionModel().getSelectedItem();
            String customerName = customerNameField.getText().trim();
            String customerPhone = customerPhoneField.getText().trim();
            LocalDate startDate = startDatePicker.getValue();
            LocalDate endDate = endDatePicker.getValue();
            
            if (selectedVehicle == null) {
                showWarning("No Selection", "Please select a vehicle to rent.");
                return;
            }
            
            if (customerName.isEmpty()) {
                showWarning("Missing Information", "Please enter customer name.");
                return;
            }
            
            if (customerPhone.isEmpty()) {
                showWarning("Missing Information", "Please enter customer phone number.");
                return;
            }
            
            if (startDate == null || endDate == null) {
                showWarning("Missing Dates", "Please select both start and end dates.");
                return;
            }
            
            if (!endDate.isAfter(startDate)) {
                showWarning("Invalid Dates", "End date must be after start date.");
                return;
            }
            
            if (startDate.isBefore(LocalDate.now())) {
                showWarning("Invalid Start Date", "Start date cannot be in the past.");
                return;
            }
            
            // Create rental
            Rental rental = rentalService.createRental(
                selectedVehicle.getId(), customerName, customerPhone, startDate, endDate);
            
            // Show confirmation
            showInfo("Rental Created", 
                String.format("Rental %s created successfully!\nTotal Cost: $%.2f", 
                    rental.getId(), rental.getTotalCost()));
            
            // Refresh data and clear form
            loadData();
            handleClearForm();
            
        } catch (Exception e) {
            showError("Rental Error", "Failed to create rental: " + e.getMessage());
        }
    }
    
    /**
     * Handle vehicle return
     */
    @FXML
    private void handleReturnVehicle() {
        try {
            Rental selectedRental = activeRentalsTable.getSelectionModel().getSelectedItem();
            
            if (selectedRental == null) {
                showWarning("No Selection", "Please select an active rental to return.");
                return;
            }
            
            // Confirm return
            Optional<ButtonType> result = showConfirmation("Confirm Return", 
                String.format("Return vehicle for rental %s?\nCustomer: %s", 
                    selectedRental.getId(), selectedRental.getCustomerName()));
            
            if (result.isPresent() && result.get() == ButtonType.OK) {
                // Complete rental
                rentalService.completeRental(selectedRental.getId());
                
                showInfo("Vehicle Returned", 
                    String.format("Vehicle returned successfully!\nRental %s completed.", 
                        selectedRental.getId()));
                
                // Refresh data
                loadData();
            }
            
        } catch (Exception e) {
            showError("Return Error", "Failed to return vehicle: " + e.getMessage());
        }
    }
    
    /**
     * Handle data refresh
     */
    @FXML
    private void handleRefreshData() {
        loadData();
        updateStatus("Data refreshed successfully");
    }
    
    /**
     * Handle form clearing
     */
    @FXML
    private void handleClearForm() {
        customerNameField.clear();
        customerPhoneField.clear();
        startDatePicker.setValue(null);
        endDatePicker.setValue(null);
        costLabel.setText("Total Cost: $0.00");
        costLabel.setStyle("-fx-text-fill: #27ae60; -fx-font-weight: bold;");
        
        vehicleTable.getSelectionModel().clearSelection();
        activeRentalsTable.getSelectionModel().clearSelection();
        
        updateStatus("Form cleared");
    }
    
    /**
     * Handle adding new vehicle
     */
    @FXML
    private void handleAddNewVehicle() {
        try {
            // Create custom dialog for vehicle input
            Dialog<Vehicle> dialog = new Dialog<>();
            dialog.setTitle("Add New Vehicle");
            dialog.setHeaderText("Enter vehicle details");
            
            // Set the button types
            ButtonType addButtonType = new ButtonType("Add Vehicle", ButtonBar.ButtonData.OK_DONE);
            dialog.getDialogPane().getButtonTypes().addAll(addButtonType, ButtonType.CANCEL);
            
            // Create the form
            GridPane grid = new GridPane();
            grid.setHgap(10);
            grid.setVgap(10);
            grid.setPadding(new Insets(20, 150, 10, 10));
            
            TextField idField = new TextField();
            idField.setPromptText("Vehicle ID (e.g., V001)");
            TextField makeField = new TextField();
            makeField.setPromptText("Make (e.g., Toyota)");
            TextField modelField = new TextField();
            modelField.setPromptText("Model (e.g., Camry)");
            TextField dailyRateField = new TextField();
            dailyRateField.setPromptText("Daily Rate (e.g., 45.99)");
            
            // Vehicle type selection
            ComboBox<String> vehicleTypeCombo = new ComboBox<>();
            vehicleTypeCombo.getItems().addAll("Regular Vehicle", "Motorcycle");
            vehicleTypeCombo.setValue("Regular Vehicle");
            
            // Motorcycle specific fields (initially hidden)
            TextField engineSizeField = new TextField();
            engineSizeField.setPromptText("Engine Size (CC)");
            TextField motorcycleTypeField = new TextField();
            motorcycleTypeField.setPromptText("Type (Sport, Cruiser, etc.)");
            CheckBox hasLuggageBox = new CheckBox("Has Luggage Capacity");
            CheckBox hasSidecarBox = new CheckBox("Has Sidecar");
            TextField passengerCapacityField = new TextField();
            passengerCapacityField.setPromptText("Passenger Capacity");
            passengerCapacityField.setText("2"); // Default for motorcycles
            
            // Initially hide motorcycle fields
            engineSizeField.setVisible(false);
            motorcycleTypeField.setVisible(false);
            hasLuggageBox.setVisible(false);
            hasSidecarBox.setVisible(false);
            passengerCapacityField.setVisible(false);
            
            // Show/hide motorcycle fields based on selection
            vehicleTypeCombo.setOnAction(e -> {
                boolean isMotorcycle = "Motorcycle".equals(vehicleTypeCombo.getValue());
                engineSizeField.setVisible(isMotorcycle);
                motorcycleTypeField.setVisible(isMotorcycle);
                hasLuggageBox.setVisible(isMotorcycle);
                hasSidecarBox.setVisible(isMotorcycle);
                passengerCapacityField.setVisible(isMotorcycle);
                
                // Adjust dialog size
                dialog.getDialogPane().autosize();
            });
            
            grid.add(new Label("Vehicle ID:"), 0, 0);
            grid.add(idField, 1, 0);
            grid.add(new Label("Make:"), 0, 1);
            grid.add(makeField, 1, 1);
            grid.add(new Label("Model:"), 0, 2);
            grid.add(modelField, 1, 2);
            grid.add(new Label("Daily Rate ($):"), 0, 3);
            grid.add(dailyRateField, 1, 3);
            grid.add(new Label("Vehicle Type:"), 0, 4);
            grid.add(vehicleTypeCombo, 1, 4);
            
            // Motorcycle-specific fields
            grid.add(new Label("Engine Size (CC):"), 0, 5);
            grid.add(engineSizeField, 1, 5);
            grid.add(new Label("Motorcycle Type:"), 0, 6);
            grid.add(motorcycleTypeField, 1, 6);
            grid.add(new Label("Passenger Capacity:"), 0, 7);
            grid.add(passengerCapacityField, 1, 7);
            grid.add(hasLuggageBox, 1, 8);
            grid.add(hasSidecarBox, 1, 9);
            
            dialog.getDialogPane().setContent(grid);
            
            // Enable/Disable add button depending on whether required fields are filled
            Node addButton = dialog.getDialogPane().lookupButton(addButtonType);
            addButton.setDisable(true);
            
            // Validation
            Runnable validateForm = () -> {
                boolean validBasic = !idField.getText().trim().isEmpty() && 
                                   !makeField.getText().trim().isEmpty() && 
                                   !modelField.getText().trim().isEmpty() && 
                                   !dailyRateField.getText().trim().isEmpty();
                
                boolean validMotorcycle = true;
                if ("Motorcycle".equals(vehicleTypeCombo.getValue())) {
                    validMotorcycle = !engineSizeField.getText().trim().isEmpty() && 
                                    !motorcycleTypeField.getText().trim().isEmpty() &&
                                    !passengerCapacityField.getText().trim().isEmpty();
                }
                
                addButton.setDisable(!(validBasic && validMotorcycle));
            };
            
            // Add listeners for validation
            idField.textProperty().addListener((observable, oldValue, newValue) -> validateForm.run());
            makeField.textProperty().addListener((observable, oldValue, newValue) -> validateForm.run());
            modelField.textProperty().addListener((observable, oldValue, newValue) -> validateForm.run());
            dailyRateField.textProperty().addListener((observable, oldValue, newValue) -> validateForm.run());
            vehicleTypeCombo.valueProperty().addListener((observable, oldValue, newValue) -> validateForm.run());
            engineSizeField.textProperty().addListener((observable, oldValue, newValue) -> validateForm.run());
            motorcycleTypeField.textProperty().addListener((observable, oldValue, newValue) -> validateForm.run());
            passengerCapacityField.textProperty().addListener((observable, oldValue, newValue) -> validateForm.run());
            
            // Convert the result when the add button is clicked
            dialog.setResultConverter(dialogButton -> {
                if (dialogButton == addButtonType) {
                    try {
                        String id = idField.getText().trim();
                        String make = makeField.getText().trim();
                        String model = modelField.getText().trim();
                        double dailyRate = Double.parseDouble(dailyRateField.getText().trim());
                        
                        // Check if vehicle ID already exists
                        if (vehicleService.findVehicleById(id) != null) {
                            Platform.runLater(() -> showError("Duplicate ID", 
                                "A vehicle with ID '" + id + "' already exists. Please use a different ID."));
                            return null;
                        }
                        
                        if ("Motorcycle".equals(vehicleTypeCombo.getValue())) {
                            // Create motorcycle
                            int engineSize = Integer.parseInt(engineSizeField.getText().trim());
                            String motorcycleType = motorcycleTypeField.getText().trim();
                            int passengerCapacity = Integer.parseInt(passengerCapacityField.getText().trim());
                            
                            Motorcycle motorcycle = new Motorcycle(id, make, model, dailyRate, engineSize, motorcycleType);
                            motorcycle.setHasLuggage(hasLuggageBox.isSelected());
                            motorcycle.setHasSidecar(hasSidecarBox.isSelected());
                            motorcycle.setPassengerCapacity(passengerCapacity);
                            
                            return motorcycle;
                        } else {
                            // Create regular vehicle
                            return new Vehicle(id, make, model, dailyRate);
                        }
                        
                    } catch (NumberFormatException e) {
                        Platform.runLater(() -> showError("Invalid Input", 
                            "Please enter valid numeric values for daily rate, engine size, and passenger capacity."));
                        return null;
                    }
                }
                return null;
            });
            
            Optional<Vehicle> result = dialog.showAndWait();
            
            result.ifPresent(vehicle -> {
                // Add vehicle using service
                if (vehicleService.addVehicle(vehicle)) {
                    showInfo("Vehicle Added", 
                        String.format("Vehicle %s (%s %s) has been added successfully!", 
                            vehicle.getId(), vehicle.getMake(), vehicle.getModel()));
                    
                    // Refresh data
                    loadData();
                    updateStatus("New vehicle added successfully");
                } else {
                    showError("Add Failed", "Failed to add the vehicle. Please try again.");
                }
            });
            
        } catch (Exception e) {
            showError("Error", "An error occurred while adding the vehicle: " + e.getMessage());
        }
    }

    /**
     * Update UI state
     */
    private void updateUI() {
        rentButton.setDisable(true);
        returnButton.setDisable(true);
    }
    
    /**
     * Update vehicle count display
     */
    private void updateVehicleCount() {
        int totalVehicles = vehicleList.size();
        long availableVehicles = vehicleList.stream().mapToLong(v -> v.isAvailable() ? 1 : 0).sum();
        vehicleCountLabel.setText(String.format("Vehicles: %d total, %d available", 
            totalVehicles, availableVehicles));
    }
    
    /**
     * Update status label
     */
    private void updateStatus(String message) {
        Platform.runLater(() -> statusLabel.setText(message));
    }
    
    /**
     * Show information dialog
     */
    private void showInfo(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    
    /**
     * Show warning dialog
     */
    private void showWarning(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    
    /**
     * Show error dialog
     */
    private void showError(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    
    /**
     * Show confirmation dialog
     */
    private Optional<ButtonType> showConfirmation(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        return alert.showAndWait();
    }
}
