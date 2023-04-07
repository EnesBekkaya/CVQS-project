package com.cvqs.defectsaveservice.dto;

import com.cvqs.defectsaveservice.model.Location;
import com.cvqs.defectsaveservice.model.Vehicle;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DefectDto {
    private String type;
    private Vehicle vehicle;
    private List<Location> locations;
}
