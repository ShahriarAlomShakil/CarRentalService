package com.carrent.model;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for Vehicle class
 * 
 * This test class validates the Vehicle model functionality.
 * Basic tests for Phase 1, will be expanded in Phase 5.
 * 
 * @author Car Rental Team
 * @version 1.0
 */
public class VehicleTest {
    
    private Vehicle vehicle;
    
    @BeforeEach
    void setUp() {
        vehicle = new Vehicle("V001", "Toyota", "Camry", 45.0);
    }
    
    @Test
    void testVehicleCreation() {
        assertNotNull(vehicle);
        assertEquals("V001", vehicle.getId());
        assertEquals("Toyota", vehicle.getMake());
        assertEquals("Camry", vehicle.getModel());
        assertEquals(45.0, vehicle.getDailyRate());
        assertTrue(vehicle.isAvailable());
    }
    
    @Test
    void testVehicleRental() {
        assertTrue(vehicle.isAvailable());
        
        vehicle.rent();
        
        assertFalse(vehicle.isAvailable());
        assertEquals("Rented", vehicle.getStatusString());
    }
    
    @Test
    void testVehicleReturn() {
        // First rent the vehicle
        vehicle.rent();
        assertFalse(vehicle.isAvailable());
        
        // Then return it
        vehicle.returnVehicle();
        assertTrue(vehicle.isAvailable());
        assertEquals("Available", vehicle.getStatusString());
    }
    
    @Test
    void testInvalidDailyRate() {
        assertThrows(IllegalArgumentException.class, () -> {
            vehicle.setDailyRate(-10.0);
        });
    }
    
    @Test
    void testDisplayName() {
        assertEquals("Toyota Camry", vehicle.getDisplayName());
    }
    
    @Test
    void testRentAlreadyRentedVehicle() {
        vehicle.rent();
        
        assertThrows(IllegalStateException.class, () -> {
            vehicle.rent();
        });
    }
    
    @Test
    void testReturnAvailableVehicle() {
        assertThrows(IllegalStateException.class, () -> {
            vehicle.returnVehicle();
        });
    }
}
