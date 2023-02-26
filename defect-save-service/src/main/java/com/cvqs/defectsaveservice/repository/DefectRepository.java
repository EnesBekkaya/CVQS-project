package com.cvqs.defectsaveservice.repository;

import com.cvqs.defectsaveservice.model.Defect;
import com.cvqs.defectsaveservice.model.Vehichle;
import org.springframework.data.jpa.repository.JpaRepository;
public interface DefectRepository extends JpaRepository<Defect,String> {
   Defect getDefectsByTypeAndVehichle(String type,Vehichle vehichle);
}
