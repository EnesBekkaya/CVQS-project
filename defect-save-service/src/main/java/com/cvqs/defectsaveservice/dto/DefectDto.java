package com.cvqs.defectsaveservice.dto;

import com.cvqs.defectsaveservice.model.Location;
import com.cvqs.defectsaveservice.model.Vehicle;
import lombok.Data;

import java.util.List;

@Data
public class DefectDto {
    private String type;

    private Vehicle vehicle;

    private List<Location> location;
}
