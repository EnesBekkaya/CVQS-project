package com.cvqs.defectsaveservice.service.abstracts;

import com.cvqs.defectsaveservice.dto.VehicleDto;
import com.cvqs.defectsaveservice.model.Vehicle;

import java.util.List;
/**
 * VehicleService interface performs operations on Vehicle objects
 *
 * @author Enes Bekkaya
 * @since  12.02.2023
 */
public interface VehicleService {
    /**
     * Saves the given VehicleDto object.
     * @param vehicleDto the object containing the information of the vehicle to be saved
     * @return the saved VehicleDto object
     */
    VehicleDto save(VehicleDto vehicleDto);

    /**
     * Returns a list of all vehicles.
     * @return a list of vehicles as VehicleDto
     */
     List<VehicleDto> getAll();

    /**
     * Finds and returns the vehicle with the given registration plate.
     * @param registrationPlate the registration plate of the vehicle to find
     * @return the vehicle with the given registration plate
     */
     Vehicle findVehicleByRegistrationPlate(String registrationPlate);
}
