package com.cvqs.defectlistingservice.controller;

import com.cvqs.defectlistingservice.dto.DefectDto;
import com.cvqs.defectlistingservice.service.abstracts.DefectListingService;
import lombok.RequiredArgsConstructor;
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

    private final DefectListingService defectListingService;
    @GetMapping("/get")
    public ResponseEntity<List<DefectDto>> getDefect(){
        return ResponseEntity.ok(defectListingService.getAllDefects());
    }

    @GetMapping("/getByPlate")
    public ResponseEntity<List<DefectDto> > getDefect(@RequestParam String registrationPlate){
        return ResponseEntity.ok(defectListingService.findDefectByPlate(registrationPlate));
    }
}
