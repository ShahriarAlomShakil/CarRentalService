package com.carrent.model;

import java.util.Objects;

/**
 * Vehicle model class representing a rental vehicle
 * 
 * This class encapsulates all the properties and behavior of a vehicle
 * in the car rental system. It follows OOP principles with proper encapsulation.
 * 
 * @author Car Rental Team
 * @version 1.0
 */
public class Vehicle {
    
    private String id;
    private String make;
    private String model;
    private double dailyRate;
    private boolean isAvailable;
    
    /**
     * Default constructor
     */
    public Vehicle() {
        this.isAvailable = true; // New vehicles are available by default
    }
    
    /**
     * Parameterized constructor
     * 
     * @param id unique identifier for the vehicle
     * @param make manufacturer of the vehicle
     * @param model model name of the vehicle
     * @param dailyRate daily rental rate in dollars
     */
    public Vehicle(String id, String make, String model, double dailyRate) {
        this.id = id;
        this.make = make;
        this.model = model;
        this.dailyRate = dailyRate;
        this.isAvailable = true;
    }
    
    // Getters and Setters with proper encapsulation
    
    public String getId() {
        return id;
    }
    
    public void setId(String id) {
        this.id = id;
    }
    
    public String getMake() {
        return make;
    }
    
    public void setMake(String make) {
        this.make = make;
    }
    
    public String getModel() {
        return model;
    }
    
    public void setModel(String model) {
        this.model = model;
    }
    
    public double getDailyRate() {
        return dailyRate;
    }
    
    public void setDailyRate(double dailyRate) {
        if (dailyRate < 0) {
            throw new IllegalArgumentException("Daily rate cannot be negative");
        }
        this.dailyRate = dailyRate;
    }
    
    public boolean isAvailable() {
        return isAvailable;
    }
    
    public void setAvailable(boolean available) {
        this.isAvailable = available;
    }
    
    /**
     * Business method to rent this vehicle
     */
    public void rent() {
        if (!isAvailable) {
            throw new IllegalStateException("Vehicle is already rented");
        }
        this.isAvailable = false;
    }
    
    /**
     * Business method to return this vehicle
     */
    public void returnVehicle() {
        if (isAvailable) {
            throw new IllegalStateException("Vehicle is not currently rented");
        }
        this.isAvailable = true;
    }
    
    /**
     * Get display name for UI
     * 
     * @return formatted string with make and model
     */
    public String getDisplayName() {
        return make + " " + model;
    }
    
    /**
     * Get status as string for display
     * 
     * @return "Available" or "Rented"
     */
    public String getStatusString() {
        return isAvailable ? "Available" : "Rented";
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vehicle vehicle = (Vehicle) o;
        return Objects.equals(id, vehicle.id);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
    
    @Override
    public String toString() {
        return String.format("Vehicle{id='%s', make='%s', model='%s', dailyRate=%.2f, isAvailable=%s}",
                id, make, model, dailyRate, isAvailable);
    }
}
