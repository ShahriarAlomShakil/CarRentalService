package com.carrent.model;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for Motorcycle model
 * 
 * This test class verifies that the Motorcycle class properly implements
 * OOP principles and all functionality works correctly.
 * 
 * @author Car Rental Team
 * @version 1.0
 */
public class MotorcycleTest {
    
    private Motorcycle motorcycle;
    private Motorcycle sportBike;
    private Motorcycle cruiser;
    
    @BeforeEach
    void setUp() {
        motorcycle = new Motorcycle();
        sportBike = new Motorcycle("M001", "Yamaha", "R1", 150.0, 1000, "Sport");
        cruiser = new Motorcycle("M002", "Harley-Davidson", "Street Glide", 200.0, 
                                1800, "Cruiser", true, 2, false);
    }
    
    @Test
    void testDefaultConstructor() {
        assertNotNull(motorcycle);
        assertTrue(motorcycle.isAvailable());
        assertEquals(2, motorcycle.getPassengerCapacity());
        assertFalse(motorcycle.hasLuggage());
        assertFalse(motorcycle.hasSidecar());
    }
    
    @Test
    void testParameterizedConstructor() {
        assertEquals("M001", sportBike.getId());
        assertEquals("Yamaha", sportBike.getMake());
        assertEquals("R1", sportBike.getModel());
        assertEquals(150.0, sportBike.getDailyRate());
        assertEquals(1000, sportBike.getEngineSize());
        assertEquals("Sport", sportBike.getMotorcycleType());
        assertTrue(sportBike.isAvailable());
    }
    
    @Test
    void testFullConstructor() {
        assertEquals("M002", cruiser.getId());
        assertEquals("Harley-Davidson", cruiser.getMake());
        assertEquals("Street Glide", cruiser.getModel());
        assertEquals(200.0, cruiser.getDailyRate());
        assertEquals(1800, cruiser.getEngineSize());
        assertEquals("Cruiser", cruiser.getMotorcycleType());
        assertTrue(cruiser.hasLuggage());
        assertEquals(2, cruiser.getPassengerCapacity());
        assertFalse(cruiser.hasSidecar());
    }
    
    @Test
    void testInheritance() {
        // Test that Motorcycle is a Vehicle
        assertTrue(sportBike instanceof Vehicle);
        assertTrue(sportBike instanceof Motorcycle);
        
        // Test inherited methods work
        sportBike.rent();
        assertFalse(sportBike.isAvailable());
        assertEquals("Rented", sportBike.getStatusString());
        
        sportBike.returnVehicle();
        assertTrue(sportBike.isAvailable());
        assertEquals("Available", sportBike.getStatusString());
    }
    
    @Test
    void testPolymorphism() {
        // Test overridden getDisplayName method
        String displayName = sportBike.getDisplayName();
        assertEquals("Yamaha R1 (1000cc)", displayName);
        
        // Test that it's different from parent class behavior
        Vehicle vehicle = new Vehicle("V001", "Toyota", "Camry", 80.0);
        assertEquals("Toyota Camry", vehicle.getDisplayName());
    }
    
    @Test
    void testEncapsulation() {
        // Test private fields are accessible only through getters/setters
        motorcycle.setEngineSize(650);
        assertEquals(650, motorcycle.getEngineSize());
        
        motorcycle.setMotorcycleType("Adventure");
        assertEquals("Adventure", motorcycle.getMotorcycleType());
        
        motorcycle.setHasLuggage(true);
        assertTrue(motorcycle.hasLuggage());
        
        motorcycle.setPassengerCapacity(1);
        assertEquals(1, motorcycle.getPassengerCapacity());
    }
    
    @Test
    void testValidation() {
        // Test engine size validation
        assertThrows(IllegalArgumentException.class, () -> {
            motorcycle.setEngineSize(-100);
        });
        
        assertThrows(IllegalArgumentException.class, () -> {
            motorcycle.setEngineSize(0);
        });
        
        // Test passenger capacity validation
        assertThrows(IllegalArgumentException.class, () -> {
            motorcycle.setPassengerCapacity(0);
        });
        
        assertThrows(IllegalArgumentException.class, () -> {
            motorcycle.setPassengerCapacity(4);
        });
        
        // Test daily rate validation (inherited from Vehicle)
        assertThrows(IllegalArgumentException.class, () -> {
            motorcycle.setDailyRate(-50.0);
        });
    }
    
    @Test
    void testSidecarFunctionality() {
        motorcycle.setHasSidecar(true);
        assertTrue(motorcycle.hasSidecar());
        assertEquals(3, motorcycle.getPassengerCapacity()); // Should auto-adjust
        
        motorcycle.setHasSidecar(false);
        assertFalse(motorcycle.hasSidecar());
        // Passenger capacity should remain 3 (not auto-reduced)
    }
    
    @Test
    void testInsuranceCalculation() {
        // Test small engine motorcycle
        Motorcycle smallBike = new Motorcycle("M003", "Honda", "CBR125", 50.0, 125, "Sport");
        double smallInsurance = smallBike.calculateInsuranceRate();
        assertEquals(30.0, smallInsurance); // Base(10) + Sport(20)
        
        // Test large engine cruiser
        double cruiserInsurance = cruiser.calculateInsuranceRate();
        assertEquals(30.0, cruiserInsurance); // Base(10) + Large engine(15) + Cruiser(5)
        
        // Test sport bike insurance
        double sportInsurance = sportBike.calculateInsuranceRate();
        assertEquals(45.0, sportInsurance); // Base(10) + Large engine(15) + Sport(20)
    }
    
    @Test
    void testTouringSuitability() {
        // Cruiser with luggage should be suitable
        assertTrue(cruiser.isSuitableForTouring());
        
        // Sport bike without luggage but large engine
        assertFalse(sportBike.isSuitableForTouring());
        
        // Create touring motorcycle
        Motorcycle touringBike = new Motorcycle("M004", "BMW", "R1250GS", 180.0, 1250, "Touring");
        assertTrue(touringBike.isSuitableForTouring());
        
        // Medium bike with luggage
        Motorcycle adventureBike = new Motorcycle("M005", "Kawasaki", "Versys", 120.0, 
                                                 650, "Adventure", true, 2, false);
        assertTrue(adventureBike.isSuitableForTouring());
    }
    
    @Test
    void testLicenseRequirement() {
        // Test A1 license (125cc and below)
        Motorcycle smallBike = new Motorcycle("M006", "Honda", "CBR125", 40.0, 125, "Sport");
        assertEquals("A1 License (Light Motorcycle)", smallBike.getLicenseRequirement());
        
        // Test A2 license (126-400cc)
        Motorcycle mediumBike = new Motorcycle("M007", "Yamaha", "MT-03", 70.0, 321, "Naked");
        assertEquals("A2 License (Medium Motorcycle)", mediumBike.getLicenseRequirement());
        
        // Test A license (over 400cc)
        assertEquals("A License (Full Motorcycle)", sportBike.getLicenseRequirement());
        assertEquals("A License (Full Motorcycle)", cruiser.getLicenseRequirement());
    }
    
    @Test
    void testRentingBehavior() {
        assertTrue(sportBike.isAvailable());
        
        // Test successful rental
        sportBike.rent();
        assertFalse(sportBike.isAvailable());
        
        // Test attempting to rent already rented motorcycle
        assertThrows(IllegalStateException.class, () -> {
            sportBike.rent();
        });
        
        // Test returning motorcycle
        sportBike.returnVehicle();
        assertTrue(sportBike.isAvailable());
        
        // Test attempting to return available motorcycle
        assertThrows(IllegalStateException.class, () -> {
            sportBike.returnVehicle();
        });
    }
    
    @Test
    void testDetailedInfo() {
        String info = sportBike.getDetailedInfo();
        
        assertTrue(info.contains("Yamaha R1 (1000cc)"));
        assertTrue(info.contains("Type: Sport"));
        assertTrue(info.contains("Engine Size: 1000cc"));
        assertTrue(info.contains("Passenger Capacity: 2"));
        assertTrue(info.contains("Daily Rate: $150.00"));
        assertTrue(info.contains("License Required: A License"));
        assertTrue(info.contains("Status: Available"));
    }
    
    @Test
    void testToString() {
        String str = sportBike.toString();
        
        assertTrue(str.contains("Motorcycle"));
        assertTrue(str.contains("id='M001'"));
        assertTrue(str.contains("make='Yamaha'"));
        assertTrue(str.contains("model='R1'"));
        assertTrue(str.contains("engineSize=1000"));
        assertTrue(str.contains("type='Sport'"));
        assertTrue(str.contains("dailyRate=150.00"));
        assertTrue(str.contains("isAvailable=true"));
    }
}
