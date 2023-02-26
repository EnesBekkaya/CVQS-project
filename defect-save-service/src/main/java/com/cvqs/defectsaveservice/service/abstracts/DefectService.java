package com.cvqs.defectsaveservice.service.abstracts;

import com.cvqs.defectsaveservice.dto.DefectDto;

import java.util.List;

public interface DefectService {
    DefectDto save(DefectDto defectDto);
   List< DefectDto> getAll();
   List<DefectDto> findByRegistrationPlate(String registrationPlate);
}
