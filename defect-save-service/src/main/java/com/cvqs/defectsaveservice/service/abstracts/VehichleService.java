package com.cvqs.defectsaveservice.service.abstracts;

import com.cvqs.defectsaveservice.dto.VehichleDto;
import com.cvqs.defectsaveservice.model.Vehichle;

import java.util.List;

public interface VehichleService {

    VehichleDto save(VehichleDto vehichleDto);
    public List<VehichleDto> getAll();
    public Vehichle findVehichleByRegistrationPlate(String registrationPlate);
}
