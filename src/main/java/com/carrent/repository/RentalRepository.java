package com.carrent.repository;

import com.carrent.model.Rental;
import java.io.*;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Rental Repository class for data access operations
 * 
 * This repository class handles all data access operations for rentals
 * using the Repository pattern and CSV file storage.
 * 
 * @author Car Rental Team
 * @version 1.0
 */
public class RentalRepository {
    
    private static final String RENTALS_FILE = "data/rentals.csv";
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private final List<Rental> rentals;
    
    /**
     * Constructor - loads rentals from CSV file
     */
    public RentalRepository() {
        this.rentals = new ArrayList<>();
        loadRentalsFromFile();
    }
    
    /**
     * Load rentals from CSV file
     */
    private void loadRentalsFromFile() {
        try {
            URL resource = getClass().getClassLoader().getResource(RENTALS_FILE);
            if (resource == null) {
                System.err.println("Could not find " + RENTALS_FILE + " in resources");
                return;
            }
            
            Path path = Paths.get(resource.toURI());
            List<String> lines = Files.readAllLines(path);
            
            // Skip header line
            for (int i = 1; i < lines.size(); i++) {
                String line = lines.get(i).trim();
                if (!line.isEmpty()) {
                    Rental rental = parseRentalFromCsv(line);
                    if (rental != null) {
                        rentals.add(rental);
                    }
                }
            }
            
            System.out.println("Loaded " + rentals.size() + " rentals from file");
            
        } catch (IOException | URISyntaxException e) {
            System.err.println("Error loading rentals from file: " + e.getMessage());
        }
    }    /**
     * Parse a rental from CSV line
     */
    private Rental parseRentalFromCsv(String csvLine) {
        try {
            String[] parts = csvLine.split(",");
            if (parts.length >= 7) {
                String id = parts[0].trim();
                String vehicleId = parts[1].trim();
                String customerName = parts[2].trim();
                String customerPhone = parts[3].trim();
                LocalDate startDate = LocalDate.parse(parts[4].trim(), DATE_FORMATTER);
                LocalDate endDate = LocalDate.parse(parts[5].trim(), DATE_FORMATTER);
                double totalCost = Double.parseDouble(parts[6].trim());
                
                // Use constructor that doesn't validate for new rentals when loading existing data
                Rental rental = new Rental(id, vehicleId, customerName, customerPhone, startDate, endDate, false);
                rental.setTotalCost(totalCost);
                
                // Check if IsActive column exists (8th column)
                if (parts.length >= 8) {
                    boolean isActive = Boolean.parseBoolean(parts[7].trim());
                    rental.setActive(isActive);
                } else {
                    // For backward compatibility - check if rental is still active based on end date
                    rental.setActive(!endDate.isBefore(LocalDate.now()));
                }
                
                return rental;
            }
        } catch (DateTimeParseException | NumberFormatException e) {
            System.err.println("Error parsing rental line: " + csvLine + " - " + e.getMessage());
        }
        return null;
    }
    
    /**
     * Save rentals to CSV file
     */
    public void saveRentalsToFile() {
        try {
            URL resource = getClass().getClassLoader().getResource(RENTALS_FILE);
            if (resource == null) {
                System.err.println("Could not find " + RENTALS_FILE + " in resources");
                return;
            }
            
            Path path = Paths.get(resource.toURI());
              List<String> lines = new ArrayList<>();
            lines.add("ID,VehicleID,CustomerName,CustomerPhone,StartDate,EndDate,TotalCost,IsActive");
            
            for (Rental rental : rentals) {
                String line = String.format("%s,%s,%s,%s,%s,%s,%.2f,%s",
                    rental.getId(),
                    rental.getVehicleId(),
                    rental.getCustomerName(),
                    rental.getCustomerPhone(),
                    rental.getStartDate().format(DATE_FORMATTER),
                    rental.getEndDate().format(DATE_FORMATTER),
                    rental.getTotalCost(),
                    rental.isActive()
                );
                lines.add(line);
            }
              Files.write(path, lines);
            System.out.println("Saved " + rentals.size() + " rentals to file: " + path);
            
        } catch (IOException | URISyntaxException e) {
            System.err.println("Error saving rentals to file: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    /**
     * Find all rentals
     * 
     * @return list of all rentals
     */
    public List<Rental> findAll() {
        return new ArrayList<>(rentals);
    }
    
    /**
     * Find rental by ID
     * 
     * @param id rental ID to search for
     * @return rental if found, null otherwise
     */
    public Rental findById(String id) {
        return rentals.stream()
                .filter(rental -> rental.getId().equals(id))
                .findFirst()
                .orElse(null);
    }
    
    /**
     * Find all active rentals
     * 
     * @return list of active rentals
     */
    public List<Rental> findActiveRentals() {
        return rentals.stream()
                .filter(Rental::isActive)
                .collect(Collectors.toList());
    }
    
    /**
     * Find rentals by vehicle ID
     * 
     * @param vehicleId vehicle ID to search for
     * @return list of rentals for the vehicle
     */
    public List<Rental> findByVehicleId(String vehicleId) {
        return rentals.stream()
                .filter(rental -> rental.getVehicleId().equals(vehicleId))
                .collect(Collectors.toList());
    }
    
    /**
     * Find active rental by vehicle ID
     * 
     * @param vehicleId vehicle ID to search for
     * @return active rental if found, null otherwise
     */
    public Rental findActiveRentalByVehicleId(String vehicleId) {
        return rentals.stream()
                .filter(rental -> rental.getVehicleId().equals(vehicleId) && rental.isActive())
                .findFirst()
                .orElse(null);
    }
    
    /**
     * Save a new rental
     * 
     * @param rental rental to save
     * @return true if saved successfully
     */
    public boolean save(Rental rental) {
        if (rental == null || findById(rental.getId()) != null) {
            return false; // Rental already exists or is null
        }
        
        rentals.add(rental);
        saveRentalsToFile();
        return true;
    }
    
    /**
     * Update an existing rental
     * 
     * @param rental rental to update
     * @return true if updated successfully
     */
    public boolean update(Rental rental) {
        if (rental == null) {
            return false;
        }
        
        for (int i = 0; i < rentals.size(); i++) {
            if (rentals.get(i).getId().equals(rental.getId())) {
                rentals.set(i, rental);
                saveRentalsToFile();
                return true;
            }
        }
        return false;
    }
    
    /**
     * Delete a rental by ID
     * 
     * @param id rental ID to delete
     * @return true if deleted successfully
     */
    public boolean deleteById(String id) {
        boolean removed = rentals.removeIf(rental -> rental.getId().equals(id));
        if (removed) {
            saveRentalsToFile();
        }
        return removed;
    }
    
    /**
     * Get total number of rentals
     * 
     * @return total count of rentals
     */
    public int count() {
        return rentals.size();
    }
    
    /**
     * Generate next rental ID
     * 
     * @return next available rental ID
     */
    public String generateNextId() {
        int maxId = rentals.stream()
                .mapToInt(rental -> {
                    try {
                        return Integer.parseInt(rental.getId().substring(1));
                    } catch (NumberFormatException e) {
                        return 0;
                    }
                })
                .max()
                .orElse(0);
        return String.format("R%03d", maxId + 1);
    }
}
