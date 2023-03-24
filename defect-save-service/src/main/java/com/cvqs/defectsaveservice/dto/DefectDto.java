package com.cvqs.defectsaveservice.dto;

import com.cvqs.defectsaveservice.model.Location;
import com.cvqs.defectsaveservice.model.Vehichle;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DefectDto {
    private String type;
    private Vehichle vehichle;
    private List<Location> locations;
}
