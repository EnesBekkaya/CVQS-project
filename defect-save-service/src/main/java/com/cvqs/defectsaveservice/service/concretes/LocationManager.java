package com.cvqs.defectsaveservice.service.concretes;

import com.cvqs.defectsaveservice.dto.LocationDto;
import com.cvqs.defectsaveservice.model.Defect;
import com.cvqs.defectsaveservice.model.Location;
import com.cvqs.defectsaveservice.repository.LocationRepository;
import com.cvqs.defectsaveservice.service.abstracts.LocationService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class LocationManager implements LocationService {
    private final LocationRepository locationRepository;
    private final ModelMapper modelMapper;

    @Override
    public LocationDto save(List<Location> locations, Defect defect) {
        Location newLocation=new Location();
        List< Location >newLocations=new ArrayList<>();
        List<Location> savedLocation=locationRepository.findLocationByDefect(defect);
        locations.forEach(location -> {
            if(savedLocation!=null&&locationRepository.existsLocationsByXAndY(location.getX(), location.getY())) {
                //eklenmek istenen lokasyonun aynısı aynı hata için mevcut bu yüzden ekleme yapılmıyor.
            }
            else{
                newLocation.setDefect(defect);
                newLocation.setX(location.getX());
                newLocation.setY(location.getY());
                newLocations.add(newLocation);
            }
        });
            return modelMapper.map(locationRepository.saveAll(newLocations),LocationDto.class);
    }

}