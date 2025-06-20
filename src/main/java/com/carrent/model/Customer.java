package com.carrent.model;

import java.util.Objects;

/**
 * Customer model class representing a rental customer
 * 
 * This class encapsulates basic customer information for the car rental system.
 * Kept simple as per PRD requirements (basic info only).
 * 
 * @author Car Rental Team
 * @version 1.0
 */
public class Customer {
    
    private String name;
    private String phone;
    
    /**
     * Default constructor
     */
    public Customer() {
    }
    
    /**
     * Parameterized constructor
     * 
     * @param name customer's full name
     * @param phone customer's phone number
     */
    public Customer(String name, String phone) {
        this.name = name;
        this.phone = phone;
    }
    
    // Getters and Setters with basic validation
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Customer name cannot be empty");
        }
        this.name = name.trim();
    }
    
    public String getPhone() {
        return phone;
    }
    
    public void setPhone(String phone) {
        if (phone == null || phone.trim().isEmpty()) {
            throw new IllegalArgumentException("Phone number cannot be empty");
        }
        this.phone = phone.trim();
    }
    
    /**
     * Validate customer information
     * 
     * @return true if customer has valid name and phone
     */
    public boolean isValid() {
        return name != null && !name.trim().isEmpty() && 
               phone != null && !phone.trim().isEmpty();
    }
    
    /**
     * Get formatted display name for UI
     * 
     * @return formatted customer name with phone
     */
    public String getDisplayName() {
        return name + " (" + phone + ")";
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Customer customer = (Customer) o;
        return Objects.equals(name, customer.name) && Objects.equals(phone, customer.phone);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(name, phone);
    }
    
    @Override
    public String toString() {
        return String.format("Customer{name='%s', phone='%s'}", name, phone);
    }
}
