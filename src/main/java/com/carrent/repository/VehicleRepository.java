package com.carrent.repository;

import com.carrent.model.Vehicle;
import java.io.*;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Vehicle Repository class for data access operations
 * 
 * This repository class handles all data access operations for vehicles
 * using the Repository pattern and CSV file storage.
 * 
 * @author Car Rental Team
 * @version 1.0
 */
public class VehicleRepository {
    
    private static final String VEHICLES_FILE = "data/vehicles.csv";
    private final List<Vehicle> vehicles;
    
    /**
     * Constructor - loads vehicles from CSV file
     */
    public VehicleRepository() {
        this.vehicles = new ArrayList<>();
        loadVehiclesFromFile();
    }
    
    /**
     * Load vehicles from CSV file
     */
    private void loadVehiclesFromFile() {
        try {
            URL resource = getClass().getClassLoader().getResource(VEHICLES_FILE);
            if (resource == null) {
                System.err.println("Could not find " + VEHICLES_FILE + " in resources");
                return;
            }
            
            Path path = Paths.get(resource.toURI());
            List<String> lines = Files.readAllLines(path);
            
            // Skip header line
            for (int i = 1; i < lines.size(); i++) {
                String line = lines.get(i).trim();
                if (!line.isEmpty()) {
                    Vehicle vehicle = parseVehicleFromCsv(line);
                    if (vehicle != null) {
                        vehicles.add(vehicle);
                    }
                }
            }
            
            System.out.println("Loaded " + vehicles.size() + " vehicles from file");
            
        } catch (IOException | URISyntaxException e) {
            System.err.println("Error loading vehicles from file: " + e.getMessage());
        }
    }
    
    /**
     * Parse a vehicle from CSV line
     */
    private Vehicle parseVehicleFromCsv(String csvLine) {
        try {
            String[] parts = csvLine.split(",");
            if (parts.length >= 5) {
                String id = parts[0].trim();
                String make = parts[1].trim();
                String model = parts[2].trim();
                double dailyRate = Double.parseDouble(parts[3].trim());
                boolean isAvailable = Boolean.parseBoolean(parts[4].trim());
                
                Vehicle vehicle = new Vehicle(id, make, model, dailyRate);
                vehicle.setAvailable(isAvailable);
                return vehicle;
            }
        } catch (NumberFormatException e) {
            System.err.println("Error parsing vehicle line: " + csvLine);
        }
        return null;
    }
      /**
     * Save vehicles to CSV file
     */
    public void saveVehiclesToFile() {
        try {
            URL resource = getClass().getClassLoader().getResource(VEHICLES_FILE);
            if (resource == null) {
                System.err.println("Could not find " + VEHICLES_FILE + " in resources");
                return;
            }
            
            Path path = Paths.get(resource.toURI());
            
            List<String> lines = new ArrayList<>();
            lines.add("ID,Make,Model,DailyRate,IsAvailable");
            
            for (Vehicle vehicle : vehicles) {
                String line = String.format("%s,%s,%s,%.2f,%s",
                    vehicle.getId(),
                    vehicle.getMake(),
                    vehicle.getModel(),
                    vehicle.getDailyRate(),
                    vehicle.isAvailable()
                );
                lines.add(line);
            }
              Files.write(path, lines);
            System.out.println("Saved " + vehicles.size() + " vehicles to file: " + path);
            
        } catch (IOException | URISyntaxException e) {
            System.err.println("Error saving vehicles to file: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    /**
     * Find all vehicles
     * 
     * @return list of all vehicles
     */
    public List<Vehicle> findAll() {
        return new ArrayList<>(vehicles);
    }
    
    /**
     * Find vehicle by ID
     * 
     * @param id vehicle ID to search for
     * @return vehicle if found, null otherwise
     */
    public Vehicle findById(String id) {
        return vehicles.stream()
                .filter(vehicle -> vehicle.getId().equals(id))
                .findFirst()
                .orElse(null);
    }
    
    /**
     * Save a new vehicle
     * 
     * @param vehicle vehicle to save
     * @return true if saved successfully
     */
    public boolean save(Vehicle vehicle) {
        if (vehicle == null || findById(vehicle.getId()) != null) {
            return false; // Vehicle already exists or is null
        }
        
        vehicles.add(vehicle);
        saveVehiclesToFile();
        return true;
    }
      /**
     * Update an existing vehicle
     * 
     * @param vehicle vehicle to update
     * @return true if updated successfully
     */
    public boolean update(Vehicle vehicle) {
        if (vehicle == null) {
            System.out.println("VehicleRepository: Cannot update null vehicle");
            return false;
        }
        
        System.out.println("VehicleRepository: Updating vehicle: " + vehicle.getId() + " - Available: " + vehicle.isAvailable());
        
        for (int i = 0; i < vehicles.size(); i++) {
            if (vehicles.get(i).getId().equals(vehicle.getId())) {
                vehicles.set(i, vehicle);
                System.out.println("VehicleRepository: Vehicle found and updated in memory");
                saveVehiclesToFile();
                System.out.println("VehicleRepository: Saved vehicles to file");
                return true;
            }
        }
        System.out.println("VehicleRepository: Vehicle not found for update: " + vehicle.getId());
        return false;
    }
    
    /**
     * Find all available vehicles
     * 
     * @return list of available vehicles
     */
    public List<Vehicle> findAvailable() {
        return vehicles.stream()
                .filter(Vehicle::isAvailable)
                .collect(Collectors.toList());
    }
    
    /**
     * Delete a vehicle by ID
     * 
     * @param id vehicle ID to delete
     * @return true if deleted successfully
     */
    public boolean deleteById(String id) {
        boolean removed = vehicles.removeIf(vehicle -> vehicle.getId().equals(id));
        if (removed) {
            saveVehiclesToFile();
        }
        return removed;
    }
    
    /**
     * Get total number of vehicles
     * 
     * @return total count of vehicles
     */
    public int count() {
        return vehicles.size();
    }
}
