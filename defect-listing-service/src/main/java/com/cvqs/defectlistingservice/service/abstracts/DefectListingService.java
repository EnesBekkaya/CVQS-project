package com.cvqs.defectlistingservice.service.abstracts;

import com.cvqs.defectlistingservice.dto.DefectDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.sql.SQLException;
import java.util.List;

public interface DefectListingService {
    List<DefectDto> getAllDefects();
    List<DefectDto>  findDefectByPlate(String registrationPlate);
    byte[] getDefectImage(String registrationPlate, String defectType) throws SQLException;
     List<DefectDto> getDefectSorted( Integer pageNo,  Integer pageSize,  String sortBy);
}
