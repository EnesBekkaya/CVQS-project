package com.cvqs.defectsaveservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import com.cvqs.defectsaveservice.model.Defect;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LocationDto {
    private int x;
    private int y;
    private Defect defect;
}
