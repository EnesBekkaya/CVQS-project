package com.cvqs.defectsaveservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VehichleDto {
    private String brand;
    private String  registrationPlate;
}
