package com.cvqs.defectlistingservice.dto;

import lombok.Data;

import java.util.List;
@Data
public class DefectDto {
    private String type;
    private Vehichle vehichle;
   private List<Location> locations;
}
