package com.carrent.service;

import com.carrent.model.Rental;
import com.carrent.model.Vehicle;
import com.carrent.repository.RentalRepository;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Rental Service class for managing rental operations
 * 
 * This service class handles all business logic related to rentals
 * following SOLID principles and containing validation logic.
 * 
 * @author Car Rental Team
 * @version 1.0
 */
public class RentalService {
    
    private final RentalRepository rentalRepository;
    private final VehicleService vehicleService;
    
    /**
     * Constructor with dependency injection
     * 
     * @param rentalRepository the rental repository to use
     * @param vehicleService the vehicle service to use
     */
    public RentalService(RentalRepository rentalRepository, VehicleService vehicleService) {
        this.rentalRepository = rentalRepository;
        this.vehicleService = vehicleService;
    }
    
    /**
     * Default constructor - creates its own dependencies
     */
    public RentalService() {
        this.rentalRepository = new RentalRepository();
        this.vehicleService = new VehicleService();
    }
    
    /**
     * Create a new rental
     * 
     * @param vehicleId ID of the vehicle to rent
     * @param customerName name of the customer
     * @param customerPhone customer's phone number
     * @param startDate rental start date
     * @param endDate rental end date
     * @return the created rental if successful, null otherwise
     */
    public Rental createRental(String vehicleId, String customerName, String customerPhone, 
                              LocalDate startDate, LocalDate endDate) {
        
        // Validate input parameters
        if (vehicleId == null || vehicleId.trim().isEmpty() ||
            customerName == null || customerName.trim().isEmpty() ||
            customerPhone == null || customerPhone.trim().isEmpty() ||
            startDate == null || endDate == null) {
            return null;
        }
        
        // Validate dates
        if (startDate.isBefore(LocalDate.now()) || endDate.isBefore(startDate)) {
            return null;
        }
        
        // Check if vehicle is available
        if (!vehicleService.isVehicleAvailable(vehicleId)) {
            return null;
        }
        
        // Calculate total cost
        double totalCost = calculateRentalCost(vehicleId, startDate, endDate);
        if (totalCost <= 0) {
            return null;
        }
        
        // Generate rental ID
        String rentalId = rentalRepository.generateNextId();
        
        // Create rental
        Rental rental = new Rental(rentalId, vehicleId, customerName.trim(), customerPhone.trim(), 
                                  startDate, endDate);
        rental.setTotalCost(totalCost);
        rental.setActive(true);
        
        // Save rental and mark vehicle as rented
        if (rentalRepository.save(rental) && vehicleService.rentVehicle(vehicleId)) {
            return rental;
        }
        
        return null;
    }
    
    /**
     * Complete a rental (return the vehicle)
     * 
     * @param rentalId ID of the rental to complete
     * @return true if successfully completed, false otherwise
     */
    public boolean completeRental(String rentalId) {
        if (rentalId == null || rentalId.trim().isEmpty()) {
            return false;
        }
        
        Rental rental = rentalRepository.findById(rentalId);
        if (rental == null || !rental.isActive()) {
            return false;
        }
        
        // Mark rental as inactive
        rental.setActive(false);
        
        // Return the vehicle and update rental
        return vehicleService.returnVehicle(rental.getVehicleId()) && 
               rentalRepository.update(rental);
    }
    
    /**
     * Calculate rental cost
     * 
     * @param vehicleId ID of the vehicle
     * @param startDate rental start date
     * @param endDate rental end date
     * @return total rental cost
     */
    public double calculateRentalCost(String vehicleId, LocalDate startDate, LocalDate endDate) {
        if (vehicleId == null || startDate == null || endDate == null || 
            endDate.isBefore(startDate)) {
            return 0.0;
        }
        
        Vehicle vehicle = vehicleService.findVehicleById(vehicleId);
        if (vehicle == null) {
            return 0.0;
        }
        
        long days = ChronoUnit.DAYS.between(startDate, endDate);
        if (days == 0) {
            days = 1; // Minimum 1 day rental
        }
        
        return days * vehicle.getDailyRate();
    }
    
    /**
     * Get all rentals
     * 
     * @return list of all rentals
     */
    public List<Rental> getAllRentals() {
        return rentalRepository.findAll();
    }
    
    /**
     * Get all active rentals
     * 
     * @return list of active rentals
     */
    public List<Rental> getActiveRentals() {
        return rentalRepository.findActiveRentals();
    }
    
    /**
     * Find rental by ID
     * 
     * @param id rental ID to search for
     * @return rental if found, null otherwise
     */
    public Rental findRentalById(String id) {
        if (id == null || id.trim().isEmpty()) {
            return null;
        }
        return rentalRepository.findById(id);
    }
    
    /**
     * Find rentals by vehicle ID
     * 
     * @param vehicleId vehicle ID to search for
     * @return list of rentals for the vehicle
     */
    public List<Rental> findRentalsByVehicleId(String vehicleId) {
        if (vehicleId == null || vehicleId.trim().isEmpty()) {
            return List.of();
        }
        return rentalRepository.findByVehicleId(vehicleId);
    }
    
    /**
     * Find active rental by vehicle ID
     * 
     * @param vehicleId vehicle ID to search for
     * @return active rental if found, null otherwise
     */
    public Rental findActiveRentalByVehicleId(String vehicleId) {
        if (vehicleId == null || vehicleId.trim().isEmpty()) {
            return null;
        }
        return rentalRepository.findActiveRentalByVehicleId(vehicleId);
    }
    
    /**
     * Find rentals by customer name
     * 
     * @param customerName customer name to search for
     * @return list of rentals for the customer
     */
    public List<Rental> findRentalsByCustomer(String customerName) {
        if (customerName == null || customerName.trim().isEmpty()) {
            return List.of();
        }
        
        return getAllRentals().stream()
                .filter(rental -> rental.getCustomerName().equalsIgnoreCase(customerName.trim()))
                .collect(Collectors.toList());
    }
    
    /**
     * Get rentals within a date range
     * 
     * @param startDate start of date range
     * @param endDate end of date range
     * @return list of rentals within the date range
     */
    public List<Rental> getRentalsInDateRange(LocalDate startDate, LocalDate endDate) {
        if (startDate == null || endDate == null || endDate.isBefore(startDate)) {
            return List.of();
        }
        
        return getAllRentals().stream()
                .filter(rental -> !rental.getStartDate().isAfter(endDate) && 
                                !rental.getEndDate().isBefore(startDate))
                .collect(Collectors.toList());
    }
    
    /**
     * Get overdue rentals (rentals that should have been returned)
     * 
     * @return list of overdue rentals
     */
    public List<Rental> getOverdueRentals() {
        LocalDate today = LocalDate.now();
        return getActiveRentals().stream()
                .filter(rental -> rental.getEndDate().isBefore(today))
                .collect(Collectors.toList());
    }
    
    /**
     * Check if a vehicle is currently rented
     * 
     * @param vehicleId vehicle ID to check
     * @return true if vehicle is currently rented, false otherwise
     */
    public boolean isVehicleCurrentlyRented(String vehicleId) {
        return findActiveRentalByVehicleId(vehicleId) != null;
    }
    
    /**
     * Get total number of rentals
     * 
     * @return total count of rentals
     */
    public int getTotalRentals() {
        return rentalRepository.count();
    }
    
    /**
     * Get number of active rentals
     * 
     * @return count of active rentals
     */
    public int getActiveRentalsCount() {
        return getActiveRentals().size();
    }
    
    /**
     * Calculate total revenue from all completed rentals
     * 
     * @return total revenue amount
     */
    public double getTotalRevenue() {
        return getAllRentals().stream()
                .filter(rental -> !rental.isActive()) // Only completed rentals
                .mapToDouble(Rental::getTotalCost)
                .sum();
    }
    
    /**
     * Calculate potential revenue from active rentals
     * 
     * @return potential revenue amount
     */
    public double getPotentialRevenue() {
        return getActiveRentals().stream()
                .mapToDouble(Rental::getTotalCost)
                .sum();
    }
}
