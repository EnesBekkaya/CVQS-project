package com.cvqs.defectlistingservice.client;

import com.cvqs.defectlistingservice.dto.DefectDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
@FeignClient(name = "defect-save-service",path = "/defects")
public interface DefectListingServiceClient {
        @RequestMapping("/getAll")
         ResponseEntity<List<DefectDto>> getAllDefects();
        @RequestMapping("/getByPlate")
         ResponseEntity<List<DefectDto>> getDefectsByPlate(@RequestParam String registrationPlate);


}
