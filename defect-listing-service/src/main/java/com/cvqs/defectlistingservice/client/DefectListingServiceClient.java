package com.cvqs.defectlistingservice.client;

import com.cvqs.defectlistingservice.dto.DefectDto;
import jakarta.transaction.Transactional;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.sql.SQLException;
import java.util.List;

@FeignClient(name = "defect-save-service", path = "/defects")
public interface DefectListingServiceClient {
    @RequestMapping("/getAll")
    ResponseEntity<List<DefectDto>> getAllDefects();

    @RequestMapping("/getByPlate")
    ResponseEntity<List<DefectDto>> getDefectsByPlate(@RequestParam String registrationPlate);

    @GetMapping("/getImage")
    @Transactional
    ResponseEntity<byte[]> getDefectImage(@RequestParam String registrationPlate, @RequestParam String defectType) throws SQLException;

    @GetMapping("/sort")
     ResponseEntity<List<DefectDto>> getDefectSorted(@RequestParam Integer pageNo, @RequestParam Integer pageSize, @RequestParam String sortBy);

}
