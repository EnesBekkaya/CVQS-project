package com.cvqs.defectlistingservice.service.concretes;

import com.cvqs.defectlistingservice.client.DefectListingServiceClient;
import com.cvqs.defectlistingservice.dto.DefectDto;
import com.cvqs.defectlistingservice.service.abstracts.DefectListingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DefectListingManager implements DefectListingService {

    private final DefectListingServiceClient defectSaveServiceClient;

    @Override
    public List<DefectDto> getAllDefects() {
       List<DefectDto> defectDtos= defectSaveServiceClient.getAllDefects().getBody();
       return defectDtos;
    }

    @Override
    public List<DefectDto>  findDefectByPlate(String registrationPlate) {
        List<DefectDto>  defectDto=defectSaveServiceClient.getDefectsByPlate(registrationPlate).getBody();
        return defectDto;
    }

    @Override
    public byte[] getDefectImage(String registrationPlate, String defectType) throws SQLException {
        return defectSaveServiceClient.getDefectImage(registrationPlate,defectType).getBody();
    }

    @Override
    public List<DefectDto> getDefectSorted(Integer pageNo, Integer pageSize, String sortBy) {
        return defectSaveServiceClient.getDefectSorted(pageNo,pageSize,sortBy).getBody();
    }


}
