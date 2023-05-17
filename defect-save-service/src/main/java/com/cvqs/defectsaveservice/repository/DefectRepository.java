package com.cvqs.defectsaveservice.repository;

import com.cvqs.defectsaveservice.model.Defect;
import com.cvqs.defectsaveservice.model.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;
/**
 * DefectRepository is an interface used for performing database operations on Defect objects.
 *
 * @author Enes Bekkaya
 * @since  12.02.2023
 */
public interface DefectRepository extends JpaRepository<Defect,String> {
   /**
    * This method retrieves the defect object of the specified type and belonging to the given vehicle.
    * @param type the type of the defect
    * @param vehicle the vehicle object to which the defect belongs
    * @return the defect object
    */
   Defect getDefectsByTypeAndVehicle(String type, Vehicle vehicle);




}
