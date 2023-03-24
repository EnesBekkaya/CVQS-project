package com.cvqs.defectlistingservice.service.abstracts;

import com.cvqs.defectlistingservice.dto.DefectDto;

import java.sql.SQLException;
import java.util.List;

public interface DefectListingService {
    List<DefectDto> getAllDefects();
    List<DefectDto>  findDefectByPlate(String registrationPlate);
    byte[] getDefectImage(String registrationPlate, String defectType) throws SQLException;
}
