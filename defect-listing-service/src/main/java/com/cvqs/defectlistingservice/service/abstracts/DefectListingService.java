package com.cvqs.defectlistingservice.service.abstracts;

import com.cvqs.defectlistingservice.dto.DefectDto;

import java.util.List;

public interface DefectListingService {
    List<DefectDto> getAllDefects();
    List<DefectDto>  findDefectByPlate(String registrationPlate);
}
