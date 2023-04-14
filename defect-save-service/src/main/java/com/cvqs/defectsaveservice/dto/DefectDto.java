package com.cvqs.defectsaveservice.dto;

import com.cvqs.defectsaveservice.model.Location;
import com.cvqs.defectsaveservice.model.Vehicle;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DefectDto {
    @NotNull(message = "type değeri girilmek zorundadır")
    @NotEmpty(message = "type değeri boş bırakılamaz")
    private String type;

    @NotNull(message = "vehicle değeri girilmek zorundadır")
    @Valid
    private Vehicle vehicle;

    @NotNull(message = "locations değeri null olamaz")
    @Size(min = 1,message = "en az 1 adet konum girilmedilir.")
    @Valid
    private List<Location> locations;
}
