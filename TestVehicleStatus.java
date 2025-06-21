import com.carrent.service.VehicleService;
import com.carrent.service.RentalService;
import com.carrent.model.Vehicle;
import com.carrent.model.Rental;
import java.time.LocalDate;

/**
 * Simple test to verify vehicle status changes
 */
public class TestVehicleStatus {
    public static void main(String[] args) {
        System.out.println("Testing Vehicle Status Changes...");
          // Create services - ensure they share the same VehicleService instance
        VehicleService vehicleService = new VehicleService();
        RentalService rentalService = new RentalService(new com.carrent.repository.RentalRepository(), vehicleService);
        
        // Test 1: Check initial status
        System.out.println("\n=== Initial Vehicle Status ===");
        vehicleService.getAllVehicles().forEach(v -> 
            System.out.println(v.getId() + ": " + (v.isAvailable() ? "Available" : "Rented")));
        
        // Test 2: Try to rent an available vehicle
        System.out.println("\n=== Attempting to Rent V001 ===");
        Vehicle v001 = vehicleService.findVehicleById("V001");
        if (v001 != null) {
            System.out.println("Before rental - V001 status: " + (v001.isAvailable() ? "Available" : "Rented"));
            
            Rental rental = rentalService.createRental("V001", "Test User", "555-1234", 
                LocalDate.now(), LocalDate.now().plusDays(3));
            
            if (rental != null) {
                System.out.println("Rental created successfully: " + rental.getId());
                
                // Check status after rental
                Vehicle updatedV001 = vehicleService.findVehicleById("V001");
                System.out.println("After rental - V001 status: " + (updatedV001.isAvailable() ? "Available" : "Rented"));
            } else {
                System.out.println("Failed to create rental");
            }
        }
        
        // Test 3: Check all vehicle statuses again
        System.out.println("\n=== Final Vehicle Status ===");
        vehicleService.getAllVehicles().forEach(v -> 
            System.out.println(v.getId() + ": " + (v.isAvailable() ? "Available" : "Rented")));
            
        System.out.println("\nTest completed.");
    }
}
