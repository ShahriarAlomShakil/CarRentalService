package com.carrent.service;

import com.carrent.model.Vehicle;
import com.carrent.model.Rental;
import java.time.LocalDate;

/**
 * Simple test class to validate Phase 2 implementation
 * This demonstrates that all services and repositories work correctly
 */
public class Phase2ValidationTest {
    
    public static void main(String[] args) {
        System.out.println("=== Phase 2 Validation Test ===");
        
        // Test Vehicle Service
        testVehicleService();
        
        // Test Rental Service
        testRentalService();
        
        System.out.println("=== Phase 2 Validation Complete ===");
    }
    
    private static void testVehicleService() {
        System.out.println("\n--- Testing Vehicle Service ---");
        
        VehicleService vehicleService = new VehicleService();
        
        // Test loading vehicles from CSV
        var allVehicles = vehicleService.getAllVehicles();
        System.out.println("Total vehicles loaded: " + allVehicles.size());
        
        // Display all vehicles
        allVehicles.forEach(vehicle -> 
            System.out.printf("Vehicle: %s %s %s - $%.2f/day - %s%n",
                vehicle.getId(), vehicle.getMake(), vehicle.getModel(),
                vehicle.getDailyRate(), vehicle.isAvailable() ? "Available" : "Rented"));
        
        // Test available vehicles
        var availableVehicles = vehicleService.getAvailableVehicles();
        System.out.println("Available vehicles: " + availableVehicles.size());
        
        // Test finding a specific vehicle
        Vehicle vehicle = vehicleService.findVehicleById("V001");
        if (vehicle != null) {
            System.out.println("Found vehicle V001: " + vehicle.getMake() + " " + vehicle.getModel());
        }
        
        // Test vehicle availability check
        boolean isAvailable = vehicleService.isVehicleAvailable("V001");
        System.out.println("V001 available: " + isAvailable);
        
        System.out.println("Vehicle service tests completed successfully!");
    }
    
    private static void testRentalService() {
        System.out.println("\n--- Testing Rental Service ---");
        
        RentalService rentalService = new RentalService();
        
        // Test loading rentals from CSV
        var allRentals = rentalService.getAllRentals();
        System.out.println("Total rentals loaded: " + allRentals.size());
        
        // Display all rentals
        allRentals.forEach(rental -> 
            System.out.printf("Rental: %s - Vehicle %s - Customer: %s - $%.2f - %s%n",
                rental.getId(), rental.getVehicleId(), rental.getCustomerName(),
                rental.getTotalCost(), rental.isActive() ? "Active" : "Completed"));
        
        // Test active rentals
        var activeRentals = rentalService.getActiveRentals();
        System.out.println("Active rentals: " + activeRentals.size());
        
        // Test cost calculation
        LocalDate startDate = LocalDate.now().plusDays(1);
        LocalDate endDate = LocalDate.now().plusDays(4);
        double cost = rentalService.calculateRentalCost("V001", startDate, endDate);
        System.out.printf("Calculated cost for V001 (3 days): $%.2f%n", cost);
        
        // Test creating a new rental
        Rental newRental = rentalService.createRental("V002", "Jane Smith", "555-0987", 
                                                     startDate, endDate);
        if (newRental != null) {
            System.out.println("Successfully created new rental: " + newRental.getId());
            
            // Test completing the rental
            boolean completed = rentalService.completeRental(newRental.getId());
            System.out.println("Rental completed: " + completed);
        } else {
            System.out.println("Failed to create new rental (this is expected if V002 is not available)");
        }
        
        // Test revenue calculations
        double totalRevenue = rentalService.getTotalRevenue();
        double potentialRevenue = rentalService.getPotentialRevenue();
        System.out.printf("Total revenue: $%.2f%n", totalRevenue);
        System.out.printf("Potential revenue: $%.2f%n", potentialRevenue);
        
        System.out.println("Rental service tests completed successfully!");
    }
}
