package com.carrent.model;

/**
 * Motorcycle model class extending Vehicle
 * 
 * This class represents a motorcycle in the car rental system.
 * It inherits basic vehicle properties and behavior from the Vehicle class
 * and adds motorcycle-specific attributes and functionality.
 * 
 * Demonstrates OOP principles:
 * - Inheritance: Extends Vehicle class
 * - Encapsulation: Private fields with public getters/setters
 * - Polymorphism: Overrides methods from parent class
 * 
 * @author Car Rental Team
 * @version 1.0
 */
public class Motorcycle extends Vehicle {
    
    private int engineSize; // Engine size in CC
    private String motorcycleType; // Sport, Cruiser, Touring, etc.
    private boolean hasLuggage; // Whether it has luggage capacity
    private int passengerCapacity; // Number of passengers (usually 1-2)
    private boolean hasSidecar; // Whether it has a sidecar attached
    
    /**
     * Default constructor
     */
    public Motorcycle() {
        super();
        this.passengerCapacity = 2; // Default capacity for most motorcycles
        this.hasLuggage = false;
        this.hasSidecar = false;
    }
    
    /**
     * Parameterized constructor
     * 
     * @param id unique identifier for the motorcycle
     * @param make manufacturer of the motorcycle
     * @param model model name of the motorcycle
     * @param dailyRate daily rental rate in dollars
     * @param engineSize engine size in CC
     * @param motorcycleType type of motorcycle (Sport, Cruiser, etc.)
     */
    public Motorcycle(String id, String make, String model, double dailyRate, 
                     int engineSize, String motorcycleType) {
        super(id, make, model, dailyRate);
        this.engineSize = engineSize;
        this.motorcycleType = motorcycleType;
        this.passengerCapacity = 2;
        this.hasLuggage = false;
        this.hasSidecar = false;
    }
    
    /**
     * Full parameterized constructor
     * 
     * @param id unique identifier for the motorcycle
     * @param make manufacturer of the motorcycle
     * @param model model name of the motorcycle
     * @param dailyRate daily rental rate in dollars
     * @param engineSize engine size in CC
     * @param motorcycleType type of motorcycle
     * @param hasLuggage whether it has luggage capacity
     * @param passengerCapacity number of passengers
     * @param hasSidecar whether it has a sidecar
     */
    public Motorcycle(String id, String make, String model, double dailyRate,
                     int engineSize, String motorcycleType, boolean hasLuggage,
                     int passengerCapacity, boolean hasSidecar) {
        super(id, make, model, dailyRate);
        this.engineSize = engineSize;
        this.motorcycleType = motorcycleType;
        this.hasLuggage = hasLuggage;
        this.passengerCapacity = passengerCapacity;
        this.hasSidecar = hasSidecar;
    }
    
    // Getters and Setters with validation
    
    public int getEngineSize() {
        return engineSize;
    }
    
    public void setEngineSize(int engineSize) {
        if (engineSize <= 0) {
            throw new IllegalArgumentException("Engine size must be positive");
        }
        this.engineSize = engineSize;
    }
    
    public String getMotorcycleType() {
        return motorcycleType;
    }
    
    public void setMotorcycleType(String motorcycleType) {
        this.motorcycleType = motorcycleType;
    }
    
    public boolean hasLuggage() {
        return hasLuggage;
    }
    
    public void setHasLuggage(boolean hasLuggage) {
        this.hasLuggage = hasLuggage;
    }
    
    public int getPassengerCapacity() {
        return passengerCapacity;
    }
    
    public void setPassengerCapacity(int passengerCapacity) {
        if (passengerCapacity < 1 || passengerCapacity > 3) {
            throw new IllegalArgumentException("Passenger capacity must be between 1 and 3");
        }
        this.passengerCapacity = passengerCapacity;
    }
    
    public boolean hasSidecar() {
        return hasSidecar;
    }
    
    public void setHasSidecar(boolean hasSidecar) {
        this.hasSidecar = hasSidecar;
        // If sidecar is added, increase passenger capacity
        if (hasSidecar && this.passengerCapacity < 3) {
            this.passengerCapacity = 3;
        }
    }
    
    // Business methods specific to motorcycles
    
    /**
     * Calculates insurance rate based on engine size and type
     * Larger engines and sport bikes typically have higher insurance rates
     * 
     * @return daily insurance rate
     */
    public double calculateInsuranceRate() {
        double baseRate = 10.0; // Base insurance rate per day
        
        // Engine size factor
        if (engineSize >= 1000) {
            baseRate += 15.0;
        } else if (engineSize > 600) {
            baseRate += 10.0;
        } else if (engineSize > 300) {
            baseRate += 5.0;
        }
        
        // Type factor
        if ("Sport".equalsIgnoreCase(motorcycleType)) {
            baseRate += 20.0; // Sport bikes have higher insurance
        } else if ("Cruiser".equalsIgnoreCase(motorcycleType)) {
            baseRate += 5.0;
        }
        
        return baseRate;
    }
    
    /**
     * Checks if motorcycle is suitable for long distance travel
     * 
     * @return true if suitable for touring
     */
    public boolean isSuitableForTouring() {
        return "Touring".equalsIgnoreCase(motorcycleType) || 
               (engineSize >= 600 && hasLuggage);
    }
    
    /**
     * Gets the license requirement based on engine size
     * 
     * @return required license type
     */
    public String getLicenseRequirement() {
        if (engineSize <= 125) {
            return "A1 License (Light Motorcycle)";
        } else if (engineSize <= 400) {
            return "A2 License (Medium Motorcycle)";
        } else {
            return "A License (Full Motorcycle)";
        }
    }
    
    // Overridden methods (Polymorphism)
    
    /**
     * Override getDisplayName to include motorcycle-specific info
     * 
     * @return formatted string with make, model, and engine size
     */
    @Override
    public String getDisplayName() {
        return String.format("%s %s (%dcc)", getMake(), getModel(), engineSize);
    }
    
    /**
     * Override rent method to include motorcycle-specific validation
     */
    @Override
    public void rent() {
        if (!isAvailable()) {
            throw new IllegalStateException("Motorcycle is already rented");
        }
        // Additional motorcycle-specific rental checks could go here
        super.rent();
    }
    
    /**
     * Get detailed vehicle information including motorcycle specifics
     * 
     * @return detailed information string
     */
    public String getDetailedInfo() {
        StringBuilder info = new StringBuilder();
        info.append(getDisplayName()).append("\n");
        info.append("Type: ").append(motorcycleType).append("\n");
        info.append("Engine Size: ").append(engineSize).append("cc\n");
        info.append("Passenger Capacity: ").append(passengerCapacity).append("\n");
        info.append("Luggage Capacity: ").append(hasLuggage ? "Yes" : "No").append("\n");
        info.append("Sidecar: ").append(hasSidecar ? "Yes" : "No").append("\n");
        info.append("Daily Rate: $").append(String.format("%.2f", getDailyRate())).append("\n");
        info.append("Insurance Rate: $").append(String.format("%.2f", calculateInsuranceRate())).append("\n");
        info.append("License Required: ").append(getLicenseRequirement()).append("\n");
        info.append("Status: ").append(getStatusString());
        return info.toString();
    }
    
    @Override
    public String toString() {
        return String.format("Motorcycle{id='%s', make='%s', model='%s', engineSize=%d, " +
                           "type='%s', dailyRate=%.2f, passengerCapacity=%d, hasLuggage=%s, " +
                           "hasSidecar=%s, isAvailable=%s}",
                getId(), getMake(), getModel(), engineSize, motorcycleType, 
                getDailyRate(), passengerCapacity, hasLuggage, hasSidecar, isAvailable());
    }
}
