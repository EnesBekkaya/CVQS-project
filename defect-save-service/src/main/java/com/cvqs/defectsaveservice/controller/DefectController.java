package com.cvqs.defectsaveservice.controller;

import com.cvqs.defectsaveservice.dto.DefectDto;
import com.cvqs.defectsaveservice.service.abstracts.DefectService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/defects")
@RequiredArgsConstructor
public class DefectController {
    private final DefectService defectService;

    @GetMapping("/getAll")
    public ResponseEntity<List<DefectDto>> getAllDefects(){
        return ResponseEntity.ok(defectService.getAll());
    }
    @GetMapping("/getByPlate")
    public ResponseEntity<List<DefectDto>> getDefectsByPlate(@RequestParam String registrationPlate){
        return ResponseEntity.ok(defectService.findByRegistrationPlate(registrationPlate));
    }

    @PostMapping("/save")
    public ResponseEntity<DefectDto> saveDefect(@RequestBody DefectDto defectDto){

        return ResponseEntity.ok(defectService.save(defectDto));
    }
}
