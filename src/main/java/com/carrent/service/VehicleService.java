package com.carrent.service;

import com.carrent.model.Vehicle;
import com.carrent.repository.VehicleRepository;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Vehicle Service class for managing vehicle operations
 * 
 * This service class handles all business logic related to vehicles
 * following SOLID principles and containing validation logic.
 * 
 * @author Car Rental Team
 * @version 1.0
 */
public class VehicleService {
    
    private final VehicleRepository vehicleRepository;
    
    /**
     * Constructor with dependency injection
     * 
     * @param vehicleRepository the vehicle repository to use
     */
    public VehicleService(VehicleRepository vehicleRepository) {
        this.vehicleRepository = vehicleRepository;
    }
    
    /**
     * Default constructor - creates its own repository
     */
    public VehicleService() {
        this.vehicleRepository = new VehicleRepository();
    }
    
    /**
     * Get all vehicles
     * 
     * @return list of all vehicles
     */
    public List<Vehicle> getAllVehicles() {
        return vehicleRepository.findAll();
    }
    
    /**
     * Get all available vehicles
     * 
     * @return list of available vehicles
     */
    public List<Vehicle> getAvailableVehicles() {
        return vehicleRepository.findAvailable();
    }
    
    /**
     * Find vehicle by ID
     * 
     * @param id vehicle ID to search for
     * @return vehicle if found, null otherwise
     */
    public Vehicle findVehicleById(String id) {
        if (id == null || id.trim().isEmpty()) {
            return null;
        }
        return vehicleRepository.findById(id);
    }
    
    /**
     * Check if vehicle is available for rental
     * 
     * @param vehicleId vehicle ID to check
     * @return true if vehicle is available, false otherwise
     */
    public boolean isVehicleAvailable(String vehicleId) {
        Vehicle vehicle = findVehicleById(vehicleId);
        return vehicle != null && vehicle.isAvailable();
    }
    
    /**
     * Rent a vehicle (mark as not available)
     * 
     * @param vehicleId vehicle ID to rent
     * @return true if successfully rented, false otherwise
     */
    public boolean rentVehicle(String vehicleId) {
        Vehicle vehicle = findVehicleById(vehicleId);
        if (vehicle == null || !vehicle.isAvailable()) {
            return false;
        }
        
        vehicle.setAvailable(false);
        return vehicleRepository.update(vehicle);
    }
    
    /**
     * Return a vehicle (mark as available)
     * 
     * @param vehicleId vehicle ID to return
     * @return true if successfully returned, false otherwise
     */
    public boolean returnVehicle(String vehicleId) {
        Vehicle vehicle = findVehicleById(vehicleId);
        if (vehicle == null) {
            return false;
        }
        
        vehicle.setAvailable(true);
        return vehicleRepository.update(vehicle);
    }
    
    /**
     * Add a new vehicle
     * 
     * @param vehicle vehicle to add
     * @return true if successfully added, false otherwise
     */
    public boolean addVehicle(Vehicle vehicle) {
        if (vehicle == null || vehicle.getId() == null || vehicle.getId().trim().isEmpty()) {
            return false;
        }
        
        // Validate vehicle data
        if (vehicle.getMake() == null || vehicle.getMake().trim().isEmpty() ||
            vehicle.getModel() == null || vehicle.getModel().trim().isEmpty() ||
            vehicle.getDailyRate() <= 0) {
            return false;
        }
        
        return vehicleRepository.save(vehicle);
    }
    
    /**
     * Update an existing vehicle
     * 
     * @param vehicle vehicle to update
     * @return true if successfully updated, false otherwise
     */
    public boolean updateVehicle(Vehicle vehicle) {
        if (vehicle == null || vehicle.getId() == null || vehicle.getId().trim().isEmpty()) {
            return false;
        }
        
        // Validate vehicle data
        if (vehicle.getMake() == null || vehicle.getMake().trim().isEmpty() ||
            vehicle.getModel() == null || vehicle.getModel().trim().isEmpty() ||
            vehicle.getDailyRate() <= 0) {
            return false;
        }
        
        return vehicleRepository.update(vehicle);
    }
    
    /**
     * Delete a vehicle
     * 
     * @param vehicleId vehicle ID to delete
     * @return true if successfully deleted, false otherwise
     */
    public boolean deleteVehicle(String vehicleId) {
        if (vehicleId == null || vehicleId.trim().isEmpty()) {
            return false;
        }
        
        // Check if vehicle exists and is not currently rented
        Vehicle vehicle = findVehicleById(vehicleId);
        if (vehicle == null || !vehicle.isAvailable()) {
            return false; // Cannot delete a rented vehicle
        }
        
        return vehicleRepository.deleteById(vehicleId);
    }
    
    /**
     * Get vehicles filtered by make
     * 
     * @param make vehicle make to filter by
     * @return list of vehicles with the specified make
     */
    public List<Vehicle> getVehiclesByMake(String make) {
        if (make == null || make.trim().isEmpty()) {
            return List.of();
        }
        
        return getAllVehicles().stream()
                .filter(vehicle -> vehicle.getMake().equalsIgnoreCase(make.trim()))
                .collect(Collectors.toList());
    }
    
    /**
     * Get vehicles within a price range
     * 
     * @param minRate minimum daily rate (inclusive)
     * @param maxRate maximum daily rate (inclusive)
     * @return list of vehicles within the price range
     */
    public List<Vehicle> getVehiclesByPriceRange(double minRate, double maxRate) {
        if (minRate < 0 || maxRate < minRate) {
            return List.of();
        }
        
        return getAllVehicles().stream()
                .filter(vehicle -> vehicle.getDailyRate() >= minRate && vehicle.getDailyRate() <= maxRate)
                .collect(Collectors.toList());
    }
    
    /**
     * Get total number of vehicles
     * 
     * @return total count of vehicles
     */
    public int getTotalVehicles() {
        return vehicleRepository.count();
    }
    
    /**
     * Get number of available vehicles
     * 
     * @return count of available vehicles
     */
    public int getAvailableVehiclesCount() {
        return getAvailableVehicles().size();
    }
    
    /**
     * Get number of rented vehicles
     * 
     * @return count of rented vehicles
     */
    public int getRentedVehiclesCount() {
        return getTotalVehicles() - getAvailableVehiclesCount();
    }
}
