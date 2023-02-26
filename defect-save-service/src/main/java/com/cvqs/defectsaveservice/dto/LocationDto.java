package com.cvqs.defectsaveservice.dto;

import lombok.Data;
import com.cvqs.defectsaveservice.model.Defect;

@Data
public class LocationDto {
    private int x;
    private int y;
    private Defect defect;
}
