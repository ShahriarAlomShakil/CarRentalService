package com.carrent.model;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

/**
 * Rental model class representing a vehicle rental transaction
 * 
 * This class encapsulates all the properties and behavior of a rental
 * in the car rental system. It handles rental calculations and validation.
 * 
 * @author Car Rental Team
 * @version 1.0
 */
public class Rental {
    
    private String id;
    private String vehicleId;
    private String customerName;
    private String customerPhone;
    private LocalDate startDate;
    private LocalDate endDate;
    private double totalCost;
    private boolean isActive;
    
    /**
     * Default constructor
     */
    public Rental() {
        this.isActive = true; // New rentals are active by default
    }
    
    /**
     * Parameterized constructor
     * 
     * @param id unique identifier for the rental
     * @param vehicleId ID of the rented vehicle
     * @param customerName name of the customer
     * @param customerPhone phone number of the customer
     * @param startDate rental start date
     * @param endDate rental end date
     */
    public Rental(String id, String vehicleId, String customerName, String customerPhone, 
                  LocalDate startDate, LocalDate endDate) {
        this.id = id;
        this.vehicleId = vehicleId;
        this.customerName = customerName;
        this.customerPhone = customerPhone;
        this.startDate = startDate;
        this.endDate = endDate;
        this.isActive = true;
        
        // Validate dates
        validateDates();
    }
    
    // Getters and Setters with proper validation
    
    public String getId() {
        return id;
    }
    
    public void setId(String id) {
        this.id = id;
    }
    
    public String getVehicleId() {
        return vehicleId;
    }
    
    public void setVehicleId(String vehicleId) {
        this.vehicleId = vehicleId;
    }
    
    public String getCustomerName() {
        return customerName;
    }
    
    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }
    
    public String getCustomerPhone() {
        return customerPhone;
    }
    
    public void setCustomerPhone(String customerPhone) {
        this.customerPhone = customerPhone;
    }
    
    public LocalDate getStartDate() {
        return startDate;
    }
    
    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
        validateDates();
    }
    
    public LocalDate getEndDate() {
        return endDate;
    }
    
    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
        validateDates();
    }
    
    public double getTotalCost() {
        return totalCost;
    }
    
    public void setTotalCost(double totalCost) {
        if (totalCost < 0) {
            throw new IllegalArgumentException("Total cost cannot be negative");
        }
        this.totalCost = totalCost;
    }
    
    public boolean isActive() {
        return isActive;
    }
    
    public void setActive(boolean active) {
        this.isActive = active;
    }
    
    /**
     * Calculate the number of rental days
     * 
     * @return number of days between start and end date (minimum 1)
     */
    public long getRentalDays() {
        if (startDate == null || endDate == null) {
            return 0;
        }
        long days = ChronoUnit.DAYS.between(startDate, endDate);
        return Math.max(days, 1); // Minimum 1 day rental
    }
    
    /**
     * Calculate total cost based on daily rate and rental days
     * 
     * @param dailyRate the daily rate of the vehicle
     * @return calculated total cost
     */
    public double calculateTotalCost(double dailyRate) {
        double cost = dailyRate * getRentalDays();
        setTotalCost(cost);
        return cost;
    }
    
    /**
     * Complete the rental (mark as inactive)
     */
    public void completeRental() {
        if (!isActive) {
            throw new IllegalStateException("Rental is already completed");
        }
        this.isActive = false;
    }
    
    /**
     * Validate rental dates
     */
    private void validateDates() {
        if (startDate != null && endDate != null) {
            if (endDate.isBefore(startDate)) {
                throw new IllegalArgumentException("End date cannot be before start date");
            }
            if (startDate.isBefore(LocalDate.now())) {
                throw new IllegalArgumentException("Start date cannot be in the past");
            }
        }
    }
    
    /**
     * Get rental period as formatted string
     * 
     * @return formatted string showing rental period
     */
    public String getRentalPeriodString() {
        if (startDate == null || endDate == null) {
            return "Dates not set";
        }
        return startDate.toString() + " to " + endDate.toString() + " (" + getRentalDays() + " days)";
    }
    
    /**
     * Get rental status as string
     * 
     * @return "Active" or "Completed"
     */
    public String getStatusString() {
        return isActive ? "Active" : "Completed";
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Rental rental = (Rental) o;
        return Objects.equals(id, rental.id);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
    
    @Override
    public String toString() {
        return String.format("Rental{id='%s', vehicleId='%s', customer='%s', period='%s', cost=%.2f, active=%s}",
                id, vehicleId, customerName, getRentalPeriodString(), totalCost, isActive);
    }
}
