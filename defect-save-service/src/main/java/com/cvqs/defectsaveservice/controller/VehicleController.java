package com.cvqs.defectsaveservice.controller;

import com.cvqs.defectsaveservice.dto.VehicleDto;
import com.cvqs.defectsaveservice.service.abstracts.VehicleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
/**
 * VehicleController sınıfı,VehicleController servisinin isteklerini karşılamak için kullanılır.
 *
 * @author Enes Bekkaya
 * @since  12.02.2023
 */
@RestController
@RequestMapping("/vehicle")
@RequiredArgsConstructor
public class VehicleController {

    private final VehicleService vehicleService;
    /**
     * Araç ekleme isteklerini  karşılamak için kullanılır.
     *
     * @param vehicleDto Eklenecek araç bilgileri
     * @return Eklenen aracın bilgileri
     */
    @PostMapping("/save")
    public ResponseEntity<VehicleDto> saveVehicle(@RequestBody VehicleDto vehicleDto){
        return ResponseEntity.ok(vehicleService.save(vehicleDto));
    }
    /**
     * Tüm araçların listesini döndürme isteklerini karşılamak için kullanılır.
     * @return Araçların listesi
     */
    @RequestMapping("/getAll")
    public ResponseEntity<List<VehicleDto>> getAllVehicle(){
        return ResponseEntity.ok(vehicleService.getAll());
    }
}
