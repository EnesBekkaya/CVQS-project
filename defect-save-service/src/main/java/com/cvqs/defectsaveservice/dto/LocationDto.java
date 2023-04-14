package com.cvqs.defectsaveservice.dto;

import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Data;
import com.cvqs.defectsaveservice.model.Defect;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LocationDto {
    @Min(value = 0,message = "x değeri 0'dan küçük olamaz")
    private int x;
    @Min(value = 0,message = "y değeri 0'dan küçük olamaz")
    private int y;
    private Defect defect;
}
