package com.cvqs.defectsaveservice.controller;

import com.cvqs.defectsaveservice.dto.DefectDto;
import com.cvqs.defectsaveservice.service.abstracts.DefectService;
import com.cvqs.defectsaveservice.service.concretes.DefectManager;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.sql.SQLException;
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
    public ResponseEntity<DefectDto> saveDefect(@RequestPart("defect") DefectDto defectDto,@RequestPart("file") MultipartFile file) throws IOException, SQLException {
        LOGGER.info("Incoming request for /defects/save");
        return ResponseEntity.ok(defectService.save(defectDto,file));
    }
    @GetMapping("/getImage")
    @Transactional
    public ResponseEntity<byte[]> getDefectImage(@RequestParam String registrationPlate,@RequestParam String defectType) throws SQLException {
        LOGGER.info("Incoming request for /defects/getImage");
        return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(defectService.getDefectImage(registrationPlate,defectType));
    }
}
