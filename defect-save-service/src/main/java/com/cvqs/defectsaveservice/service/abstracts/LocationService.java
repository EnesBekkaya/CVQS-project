package com.cvqs.defectsaveservice.service.abstracts;

import com.cvqs.defectsaveservice.dto.LocationDto;
import com.cvqs.defectsaveservice.model.Location;

import java.util.List;

public interface LocationService {

   List< LocationDto> save(List<Location> locations);
   Location findLocationByXAndY(int x,int y);
}
