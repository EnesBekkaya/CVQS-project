package com.cvqs.defectsaveservice.controller;

import com.cvqs.defectsaveservice.dto.VehichleDto;
import com.cvqs.defectsaveservice.service.abstracts.VehicleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
/**
 * VehichleController sınıfı,VehichleController servisinin isteklerini karşılamak için kullanılır.
 *
 * @author Enes Bekkaya
 * @since  12.02.2023
 */
@RestController
@RequestMapping("/vehichle")
@RequiredArgsConstructor
public class VehichleController {

    private final VehicleService vehicleService;
    /**
     * Araç ekleme isteklerini  karşılamak için kullanılır.
     *
     * @param vehichleDto Eklenecek araç bilgileri
     * @return Eklenen aracın bilgileri
     */
    @PostMapping("/save")
    public ResponseEntity<VehichleDto> saveVehicle(@RequestBody VehichleDto vehichleDto){
        return ResponseEntity.ok(vehicleService.save(vehichleDto));
    }
    /**
     * Tüm araçların listesini döndürme isteklerini karşılamak için kullanılır.
     * @return Araçların listesi
     */
    @RequestMapping("/getAll")
    public ResponseEntity<List<VehichleDto>> getAllVehichle(){
        return ResponseEntity.ok(vehicleService.getAll());
    }
}
