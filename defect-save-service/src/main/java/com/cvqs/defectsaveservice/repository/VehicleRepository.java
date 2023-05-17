package com.cvqs.defectsaveservice.repository;

import com.cvqs.defectsaveservice.model.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;
/**
 * VehicleRepository is the interface for database operations of Vehicle objects.
 *
 * @author Enes Bekkaya
 * @since  12.02.2023
 */
public interface VehicleRepository extends JpaRepository<Vehicle,String> {
    /**
     * Finds the vehicle with the given registration plate in the database.
     * @param registrationPlate the registration plate of the vehicle
     * @return the found vehicle
     */
    Vehicle findVehicleByRegistrationPlate(String registrationPlate);
}
