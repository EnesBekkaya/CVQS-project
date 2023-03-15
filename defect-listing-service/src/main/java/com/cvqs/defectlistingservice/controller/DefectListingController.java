package com.cvqs.defectlistingservice.controller;

import com.cvqs.defectlistingservice.dto.DefectDto;
import com.cvqs.defectlistingservice.service.abstracts.DefectListingService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/defectListing")
@RequiredArgsConstructor
public class DefectListingController {
    private static final Logger LOGGER= LoggerFactory.getLogger(DefectListingController.class);
    private final DefectListingService defectListingService;
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
}
