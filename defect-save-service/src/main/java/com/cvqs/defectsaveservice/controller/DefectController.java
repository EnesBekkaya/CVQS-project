package com.cvqs.defectsaveservice.controller;

import com.cvqs.defectsaveservice.dto.DefectDto;
import com.cvqs.defectsaveservice.service.abstracts.DefectService;
import com.cvqs.defectsaveservice.service.concretes.DefectManager;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/defects")
@RequiredArgsConstructor
public class DefectController {
    private final DefectService defectService;
    private static final Logger LOGGER= LoggerFactory.getLogger(DefectManager.class);

    @GetMapping("/getAll")
    public ResponseEntity<List<DefectDto>> getAllDefects(){

        LOGGER.info("Incoming request for /defects/getAll");
        return ResponseEntity.ok(defectService.getAll());
    }
    @GetMapping("/getByPlate")
    public ResponseEntity<List<DefectDto>> getDefectsByPlate(@RequestParam String registrationPlate){
        LOGGER.info("Incoming request for /defects/getByPlate");
        return ResponseEntity.ok(defectService.findByRegistrationPlate(registrationPlate));
    }

    @PostMapping("/save")
    public ResponseEntity<DefectDto> saveDefect(@RequestBody DefectDto defectDto){
        LOGGER.info("Incoming request for /defects/save");
        return ResponseEntity.ok(defectService.save(defectDto));
    }
}
