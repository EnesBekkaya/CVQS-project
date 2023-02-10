package com.cvqs.defectsaveservice.repository;

import com.cvqs.defectsaveservice.model.Defect;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DefectRepository extends JpaRepository<Defect,String> {
}
