package com.carrent.view;

import com.carrent.service.VehicleService;
import com.carrent.service.RentalService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Phase 3 Validation Test - UI Integration
 * 
 * Tests to validate that Phase 3 UI implementation is working correctly
 * and properly integrates with Phase 2 business logic.
 * 
 * @author Car Rental Team
 * @version 3.0
 */
public class Phase3ValidationTest {
    
    private VehicleService vehicleService;
    private RentalService rentalService;
    
    @BeforeEach
    void setUp() {
        vehicleService = new VehicleService();
        rentalService = new RentalService();
    }
    
    @Test
    void testServicesIntegration() {
        // Test that services used by UI are working
        assertNotNull(vehicleService, "VehicleService should be initialized");
        assertNotNull(rentalService, "RentalService should be initialized");
        
        // Test vehicle loading (UI dependency)
        var vehicles = vehicleService.getAllVehicles();
        assertNotNull(vehicles, "Vehicles should be loaded for UI");
        assertTrue(vehicles.size() > 0, "Should have vehicles to display in UI");
        
        // Test rental loading (UI dependency)
        var rentals = rentalService.getActiveRentals();
        assertNotNull(rentals, "Active rentals should be loaded for UI");
        
        System.out.println("✅ Phase 3 UI Services Integration: " + vehicles.size() + " vehicles, " + rentals.size() + " active rentals");
    }
    
    @Test
    void testCostCalculationForUI() {
        // Test cost calculation method used by UI
        var vehicles = vehicleService.getAllVehicles();
        if (!vehicles.isEmpty()) {
            var vehicle = vehicles.get(0);
            
            // Test the method called by UI
            try {
                double cost = rentalService.calculateRentalCost(
                    vehicle.getId(), 
                    java.time.LocalDate.now().plusDays(1), 
                    java.time.LocalDate.now().plusDays(4)
                );
                
                assertTrue(cost > 0, "Cost calculation should return positive value for UI display");
                System.out.println("✅ Phase 3 UI Cost Calculation: $" + String.format("%.2f", cost) + " for " + vehicle.getMake() + " " + vehicle.getModel());
                
            } catch (Exception e) {
                fail("Cost calculation failed: " + e.getMessage());
            }
        }
    }
    
    @Test
    void testRentalWorkflowForUI() {
        // Test complete rental workflow that UI uses
        var availableVehicles = vehicleService.getAvailableVehicles();
        if (!availableVehicles.isEmpty()) {
            var vehicle = availableVehicles.get(0);
            
            try {
                // Test rental creation (called by UI Rent button)
                var rental = rentalService.createRental(
                    vehicle.getId(),
                    "UI Test Customer",
                    "555-TEST",
                    java.time.LocalDate.now().plusDays(1),
                    java.time.LocalDate.now().plusDays(3)
                );
                
                assertNotNull(rental, "Rental creation should succeed for UI");
                assertNotNull(rental.getId(), "Rental should have ID for UI display");
                
                // Test rental completion (called by UI Return button)
                rentalService.completeRental(rental.getId());
                
                System.out.println("✅ Phase 3 UI Rental Workflow: Created and completed rental " + rental.getId());
                
            } catch (Exception e) {
                fail("Rental workflow failed: " + e.getMessage());
            }
        }
    }
    
    @Test
    void testDataRefreshForUI() {
        // Test data refresh functionality used by UI
        try {
            // Get initial counts
            int initialVehicleCount = vehicleService.getAllVehicles().size();
            
            // Test refresh (what UI Refresh button does)
            var refreshedVehicles = vehicleService.getAllVehicles();
            var refreshedRentals = rentalService.getActiveRentals();
            
            assertNotNull(refreshedVehicles, "Refreshed vehicles should not be null");
            assertNotNull(refreshedRentals, "Refreshed rentals should not be null");
            
            assertEquals(initialVehicleCount, refreshedVehicles.size(), "Vehicle count should be consistent after refresh");
            
            System.out.println("✅ Phase 3 UI Data Refresh: " + refreshedVehicles.size() + " vehicles, " + refreshedRentals.size() + " rentals");
            
        } catch (Exception e) {
            fail("Data refresh failed: " + e.getMessage());
        }
    }
    
    @Test
    void testUIValidationData() {
        // Test data validation scenarios that UI needs to handle
        try {
            // Test invalid vehicle ID (UI should handle gracefully)
            double invalidCost = rentalService.calculateRentalCost("INVALID", 
                java.time.LocalDate.now(), 
                java.time.LocalDate.now().plusDays(1));
            assertEquals(0.0, invalidCost, "Invalid vehicle ID should return 0.0 cost");
            
            // Test invalid date range (UI validation)
            var vehicles = vehicleService.getAllVehicles();
            if (!vehicles.isEmpty()) {
                double invalidDateCost = rentalService.calculateRentalCost(vehicles.get(0).getId(), 
                    java.time.LocalDate.now(), 
                    java.time.LocalDate.now().minusDays(1)); // End before start
                assertEquals(0.0, invalidDateCost, "Invalid date range should return 0.0 cost");
            }
            
            System.out.println("✅ Phase 3 UI Validation: Error handling working correctly");
            
        } catch (Exception e) {
            fail("Validation testing failed: " + e.getMessage());
        }
    }
}
