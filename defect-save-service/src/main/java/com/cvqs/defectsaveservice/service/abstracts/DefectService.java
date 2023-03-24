package com.cvqs.defectsaveservice.service.abstracts;

import com.cvqs.defectsaveservice.dto.DefectDto;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public interface DefectService {
    DefectDto save(DefectDto defectDto, MultipartFile file) throws IOException, SQLException;
   List< DefectDto> getAll();
   List<DefectDto> findByRegistrationPlate(String registrationPlate);

    byte[] getDefectImage(String registrationPlate,String defectType) throws SQLException;
}
