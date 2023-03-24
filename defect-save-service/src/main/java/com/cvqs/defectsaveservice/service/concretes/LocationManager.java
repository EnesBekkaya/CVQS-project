package com.cvqs.defectsaveservice.service.concretes;

import com.cvqs.defectsaveservice.dto.LocationDto;
import com.cvqs.defectsaveservice.model.Location;
import com.cvqs.defectsaveservice.repository.LocationRepository;
import com.cvqs.defectsaveservice.service.abstracts.LocationService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LocationManager implements LocationService {
    private final LocationRepository locationRepository;
    private final ModelMapper modelMapper;

    @Override
    public List<LocationDto> save(List<Location> locations) {
        List<Location> locations1 = locationRepository.saveAll(locations);
        return locations1.stream().map(location -> modelMapper.map(location, LocationDto.class)).collect(Collectors.toList());

    }

    @Override
    public Location findLocationByXAndY(int x, int y) {
        Location location = locationRepository.findLocationByXAndY(x, y);
        Location newLocation = new Location();

        if (location == null) {
            newLocation.setX(x);
            newLocation.setY(y);
            return locationRepository.save(newLocation);

        }
        else return location;

    }




}