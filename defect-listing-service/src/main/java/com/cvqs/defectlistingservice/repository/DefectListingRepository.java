package com.cvqs.defectlistingservice.repository;

import com.cvqs.defectlistingservice.model.Defect;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface DefectListingRepository extends JpaRepository<Defect,String> {
    /**
     * Retrieves the defects of the vehicle with the specified registration plate.
     * @param registrationPlate The registration plate of the vehicle.
     * @return A list of defect objects.
     */
    @Query("SELECT t FROM Defect t WHERE t.vehicle.registrationPlate=:registrationPlate")
    List<Defect> findByRegistrationPlate(String registrationPlate);

    /**
     * Gets the defect object with the specified registration plate and type.
     *
     * @param registrationPlate the registration plate of the vehicle
     * @param type the type of the defect
     * @return The defect object
     */
    @Query("SELECT t FROM Defect t WHERE t.vehicle.registrationPlate=:registrationPlate and t.type=:type")
    Defect findDefectByregistrationPlateAndType(String registrationPlate,String type);
}
