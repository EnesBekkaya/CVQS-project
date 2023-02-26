package com.cvqs.defectsaveservice.dto;

import com.cvqs.defectsaveservice.model.Location;
import com.cvqs.defectsaveservice.model.Vehichle;
import lombok.Data;
import java.util.List;
@Data
public class DefectDto {
    private String type;
    private Vehichle vehichle;
    private List<Location> location;
}
