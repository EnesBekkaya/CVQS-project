package com.cvqs.defectlistingservice.service.concretes;

import com.cvqs.defectlistingservice.client.DefectListingServiceClient;
import com.cvqs.defectlistingservice.dto.DefectDto;
import com.cvqs.defectlistingservice.service.abstracts.DefectListingService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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
}
