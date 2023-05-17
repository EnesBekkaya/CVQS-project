package com.cvqs.defectsaveservice.controller;

import com.cvqs.defectsaveservice.dto.VehicleDto;
import com.cvqs.defectsaveservice.service.abstracts.VehicleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
/**
 * The VehicleController class is used to handle the requests of the Vehicle service.
 * @author Enes Bekkaya
 * @since  12.02.2023
 */
@RestController
@RequestMapping("/vehicle")
@RequiredArgsConstructor
public class VehicleController {

    private final VehicleService vehicleService;
    /**
     * Used to handle vehicle save requests.
     * @param vehicleDto Information of the vehicle to be added.
     * @return Information of the saved vehicle.
     */
    @PostMapping("/save")
    public ResponseEntity<VehicleDto> saveVehicle(@RequestBody @Valid VehicleDto vehicleDto){
        return ResponseEntity.ok(vehicleService.save(vehicleDto));
    }
    /**
     * Used to handle requests for returning the list of all vehicles.
     * @return List of vehicles
     */
    @RequestMapping("/getAll")
    public ResponseEntity<List<VehicleDto>> getAllVehicle(){
        return ResponseEntity.ok(vehicleService.getAll());
    }
}
