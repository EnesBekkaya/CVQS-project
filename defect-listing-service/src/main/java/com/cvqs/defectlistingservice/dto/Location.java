package com.cvqs.defectlistingservice.dto;

import lombok.Data;

@Data
public class Location {
    private int x;
    private int y;
    private DefectDto defect;
}
