package com.cvqs.defectsaveservice.repository;

import com.cvqs.defectsaveservice.model.Vehichle;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VehichleRepository extends JpaRepository<Vehichle,String> {
    Vehichle findVehichleByRegistrationPlate(String registrationPlate);
}
