package com.cvqs.defectsaveservice.service.concretes;

import com.cvqs.defectsaveservice.dto.LocationDto;
import com.cvqs.defectsaveservice.dto.VehichleDto;
import com.cvqs.defectsaveservice.model.Defect;
import com.cvqs.defectsaveservice.model.Location;
import com.cvqs.defectsaveservice.model.Vehichle;
import com.cvqs.defectsaveservice.repository.LocationRepository;
import junit.framework.TestCase;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.mockito.internal.verification.Times;
import org.modelmapper.ModelMapper;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class LocationManagerTest extends TestCase {
    private LocationManager locationManager;
    private LocationRepository locationRepository;
    private ModelMapper modelMapper;
    @BeforeEach
    public void setUp() {
        locationRepository = Mockito.mock(LocationRepository.class);
        modelMapper = Mockito.mock(ModelMapper.class);
        locationManager = new LocationManager(locationRepository, modelMapper);
    }


    @DisplayName("should save Location and return location dto")
    @Test
    void shouldSaveAndReturnLocationDto() {
        Defect defect = new Defect();
        Location location1 = new Location();
        location1.setX(10);
        location1.setY(20);
        Location location2 = new Location();
        location2.setX(30);
        location2.setY(40);
        Location location3=new Location();
        location3.setX(34);
        location3.setY(67);
        List<Location> locations = Arrays.asList(location1, location2,location3);

        List<Location> expected=Arrays.asList(location2,location3);

        Mockito.when(locationRepository.findLocationByDefects(defect)).thenReturn(locations);

        Mockito.when(locationRepository.existsLocationsByXAndY(ArgumentMatchers.eq(10), ArgumentMatchers.eq(20))).thenReturn(true);
        Mockito.when(locationRepository.existsLocationsByXAndY(ArgumentMatchers.eq(30), ArgumentMatchers.eq(40))).thenReturn(false);
        Mockito.when(locationRepository.existsLocationsByXAndY(ArgumentMatchers.eq(34), ArgumentMatchers.eq(67))).thenReturn(false);

        Mockito.when(locationRepository.saveAll(Mockito.anyIterable())).thenReturn(Arrays.asList(location1, location2));

        List<LocationDto> result = locationManager.save(locations);

        assertEquals(expected.stream().map(location -> modelMapper.map(location,LocationDto.class)).collect(Collectors.toList()),result);
    }

    @AfterEach
    public void tearDown() {

    }
}