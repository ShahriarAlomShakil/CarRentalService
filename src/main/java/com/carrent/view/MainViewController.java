package com.carrent.view;

import com.carrent.model.Vehicle;
import com.carrent.model.Rental;
import com.carrent.service.VehicleService;
import com.carrent.service.RentalService;

import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

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
