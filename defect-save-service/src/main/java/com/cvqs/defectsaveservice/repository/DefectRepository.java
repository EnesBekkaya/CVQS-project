package com.cvqs.defectsaveservice.repository;

import com.cvqs.defectsaveservice.model.Defect;
import com.cvqs.defectsaveservice.model.Vehichle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface DefectRepository extends JpaRepository<Defect,String> {
   Defect getDefectsByTypeAndVehichle(String type,Vehichle vehichle);
   @Query("SELECT t FROM Defect t WHERE t.vehichle.registrationPlate=:registrationPlate")
   List<Defect> findByRegistrationPlate(String registrationPlate);
}
