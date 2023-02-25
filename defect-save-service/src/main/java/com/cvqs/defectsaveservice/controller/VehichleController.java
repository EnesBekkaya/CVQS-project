package com.cvqs.defectsaveservice.controller;

import com.cvqs.defectsaveservice.dto.VehichleDto;
import com.cvqs.defectsaveservice.service.abstracts.VehichleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/vehichle")
@RequiredArgsConstructor
public class VehichleController {

    private final VehichleService vehichleService;
    @PostMapping("/save")
    public ResponseEntity<VehichleDto> saveDefect(@RequestBody VehichleDto vehichleDto){
        return ResponseEntity.ok(vehichleService.save(vehichleDto));
    }

    @RequestMapping("/getAll")
    public ResponseEntity<List<VehichleDto>> getAllVehichle(){
        return ResponseEntity.ok(vehichleService.getAll());
    }
}
