package com.cvqs.defectlistingservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DefectDto {
    private String type;
    private Vehicle vehicle;
   private List<Location> locations;
}
