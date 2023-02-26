package com.cvqs.defectsaveservice.service.abstracts;

import com.cvqs.defectsaveservice.dto.LocationDto;
import com.cvqs.defectsaveservice.model.Defect;
import com.cvqs.defectsaveservice.model.Location;

import java.util.List;

public interface LocationService {

    LocationDto save(List<Location> locations, Defect defect);
}
