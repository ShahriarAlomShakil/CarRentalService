package com.carrent.demo;

import com.carrent.model.Motorcycle;
import com.carrent.model.Vehicle;

/**
 * Demo class showing the usage of Motorcycle class and OOP principles
 * 
 * This class demonstrates:
 * - Object creation and initialization
 * - Inheritance (Motorcycle extends Vehicle)
 * - Polymorphism (method overriding and runtime behavior)
 * - Encapsulation (using getters/setters)
 * - Method overloading and business logic
 * 
 * @author Car Rental Team
 * @version 1.0
 */
public class MotorcycleDemo {
    
    public static void main(String[] args) {
        System.out.println("=== Car Rental System - Motorcycle Demo ===\n");
        
        // Demonstrate object creation and constructors
        demonstrateObjectCreation();
        
        // Demonstrate inheritance
        demonstrateInheritance();
        
        // Demonstrate polymorphism
        demonstratePolymorphism();
        
        // Demonstrate encapsulation
        demonstrateEncapsulation();
        
        // Demonstrate business logic
        demonstrateBusinessLogic();
    }
    
    private static void demonstrateObjectCreation() {
        System.out.println("1. OBJECT CREATION AND CONSTRUCTORS");
        System.out.println("=====================================");
        
        // Default constructor
        Motorcycle defaultBike = new Motorcycle();
        System.out.println("Default motorcycle: " + defaultBike);
        
        // Parameterized constructor
        Motorcycle sportBike = new Motorcycle("M001", "Yamaha", "R1", 150.0, 1000, "Sport");
        System.out.println("Sport bike: " + sportBike.getDisplayName());
        
        // Full constructor
        Motorcycle touringBike = new Motorcycle("M002", "BMW", "R1250GS", 180.0, 
                                               1250, "Touring", true, 2, false);
        System.out.println("Touring bike: " + touringBike.getDisplayName());
        System.out.println();
    }
    
    private static void demonstrateInheritance() {
        System.out.println("2. INHERITANCE");
        System.out.println("==============");
        
        Motorcycle motorcycle = new Motorcycle("M003", "Honda", "CBR600RR", 120.0, 600, "Sport");
        
        // Motorcycle inherits all Vehicle properties and methods
        System.out.println("ID: " + motorcycle.getId());
        System.out.println("Make: " + motorcycle.getMake());
        System.out.println("Model: " + motorcycle.getModel());
        System.out.println("Daily Rate: $" + motorcycle.getDailyRate());
        System.out.println("Available: " + motorcycle.isAvailable());
        
        // Motorcycle also has its own specific properties
        System.out.println("Engine Size: " + motorcycle.getEngineSize() + "cc");
        System.out.println("Type: " + motorcycle.getMotorcycleType());
        System.out.println("Passenger Capacity: " + motorcycle.getPassengerCapacity());
        System.out.println();
    }
    
    private static void demonstratePolymorphism() {
        System.out.println("3. POLYMORPHISM");
        System.out.println("===============");
        
        // Create different types of vehicles
        Vehicle regularVehicle = new Vehicle("V001", "Toyota", "Camry", 80.0);
        Vehicle motorcycleAsVehicle = new Motorcycle("M004", "Ducati", "Panigale", 200.0, 1100, "Sport");
        
        // Polymorphic behavior - same method call, different implementations
        System.out.println("Regular vehicle display name: " + regularVehicle.getDisplayName());
        System.out.println("Motorcycle display name: " + motorcycleAsVehicle.getDisplayName());
        
        // Runtime type checking
        if (motorcycleAsVehicle instanceof Motorcycle) {
            Motorcycle bike = (Motorcycle) motorcycleAsVehicle;
            System.out.println("Motorcycle-specific info: " + bike.getLicenseRequirement());
        }
        System.out.println();
    }
    
    private static void demonstrateEncapsulation() {
        System.out.println("4. ENCAPSULATION");
        System.out.println("================");
        
        Motorcycle motorcycle = new Motorcycle();
        
        // Using setters with validation
        try {
            motorcycle.setEngineSize(750);
            motorcycle.setMotorcycleType("Adventure");
            motorcycle.setHasLuggage(true);
            motorcycle.setPassengerCapacity(2);
            motorcycle.setHasSidecar(false);
            
            System.out.println("Successfully set all properties:");
            System.out.println("- Engine Size: " + motorcycle.getEngineSize() + "cc");
            System.out.println("- Type: " + motorcycle.getMotorcycleType());
            System.out.println("- Has Luggage: " + motorcycle.hasLuggage());
            System.out.println("- Passenger Capacity: " + motorcycle.getPassengerCapacity());
            System.out.println("- Has Sidecar: " + motorcycle.hasSidecar());
            
        } catch (IllegalArgumentException e) {
            System.out.println("Validation error: " + e.getMessage());
        }
        
        // Demonstrate validation
        try {
            motorcycle.setEngineSize(-100); // This should fail
        } catch (IllegalArgumentException e) {
            System.out.println("Validation working: " + e.getMessage());
        }
        System.out.println();
    }
    
    private static void demonstrateBusinessLogic() {
        System.out.println("5. BUSINESS LOGIC");
        System.out.println("=================");
        
        // Create different types of motorcycles
        Motorcycle sportBike = new Motorcycle("M005", "Kawasaki", "Ninja ZX-10R", 160.0, 998, "Sport");
        Motorcycle cruiser = new Motorcycle("M006", "Harley-Davidson", "Road King", 180.0, 
                                          1746, "Cruiser", true, 2, false);
        Motorcycle smallBike = new Motorcycle("M007", "Honda", "CB125R", 40.0, 125, "Naked");
        
        System.out.println("Sport Bike Analysis:");
        System.out.println("- Insurance Rate: $" + sportBike.calculateInsuranceRate() + "/day");
        System.out.println("- License Required: " + sportBike.getLicenseRequirement());
        System.out.println("- Suitable for Touring: " + sportBike.isSuitableForTouring());
        
        System.out.println("\nCruiser Analysis:");
        System.out.println("- Insurance Rate: $" + cruiser.calculateInsuranceRate() + "/day");
        System.out.println("- License Required: " + cruiser.getLicenseRequirement());
        System.out.println("- Suitable for Touring: " + cruiser.isSuitableForTouring());
        
        System.out.println("\nSmall Bike Analysis:");
        System.out.println("- Insurance Rate: $" + smallBike.calculateInsuranceRate() + "/day");
        System.out.println("- License Required: " + smallBike.getLicenseRequirement());
        System.out.println("- Suitable for Touring: " + smallBike.isSuitableForTouring());
        
        // Demonstrate rental process
        System.out.println("\nRental Process:");
        System.out.println("Initial status: " + sportBike.getStatusString());
        sportBike.rent();
        System.out.println("After rental: " + sportBike.getStatusString());
        sportBike.returnVehicle();
        System.out.println("After return: " + sportBike.getStatusString());
        
        // Show detailed information
        System.out.println("\nDetailed Information for " + cruiser.getDisplayName() + ":");
        System.out.println(cruiser.getDetailedInfo());
    }
}
