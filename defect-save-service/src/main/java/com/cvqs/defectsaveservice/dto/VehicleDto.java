package com.cvqs.defectsaveservice.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VehicleDto {
    @NotNull(message = "brand değeri girilmek zorundadır")
    @NotEmpty(message = "brand değeri boş bırakılamaz")
    private String brand;
    @NotNull(message = "brand değeri girilmek zorundadır")
    @NotEmpty(message = "brand değeri boş bırakılamaz")
    private String  registrationPlate;
}
