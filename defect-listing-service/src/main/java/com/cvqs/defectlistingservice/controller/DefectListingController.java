package com.cvqs.defectlistingservice.controller;

import com.cvqs.defectlistingservice.client.DefectListingServiceClient;
import com.cvqs.defectlistingservice.dto.DefectDto;
import com.cvqs.defectlistingservice.service.abstracts.DefectListingService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLException;
import java.util.List;

@RestController
    @RequestMapping("/defectListing")
@RequiredArgsConstructor
public class DefectListingController {
    private static final Logger LOGGER= LoggerFactory.getLogger(DefectListingController.class);
    private final DefectListingService defectListingService;
    private final DefectListingServiceClient defectListingServiceClient;
    @GetMapping("/getDefects")
    public ResponseEntity<List<DefectDto>> getDefect(){
        LOGGER.info("Incoming request for /defectListing/getDefects");
        return ResponseEntity.ok(defectListingService.getAllDefects());
    }

    @GetMapping("/getByPlate")
    public ResponseEntity<List<DefectDto> > getDefect(@RequestParam String registrationPlate){
        LOGGER.info("Incoming request for /defectListing/getByPlate");
        return ResponseEntity.ok(defectListingService.findDefectByPlate(registrationPlate));
    }

    @GetMapping("/getImage")
    @Transactional
    ResponseEntity<byte[]> getDefectImage(@RequestParam String registrationPlate, @RequestParam String defectType) throws SQLException{
        LOGGER.info("Incoming request for /defectListing/getImage");
        return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(defectListingService.getDefectImage(registrationPlate,defectType));

    }

    @GetMapping("/sort")
    ResponseEntity<List<DefectDto>> getDefectSorted(@RequestParam Integer pageNo, @RequestParam Integer pageSize, @RequestParam String sortBy){
        LOGGER.info("Incoming request for /defectListing/sort");
        return ResponseEntity.ok(defectListingService.getDefectSorted(pageNo,pageSize,sortBy));
    }
}
