package com.cvqs.defectsaveservice.service.concretes;

import com.cvqs.defectsaveservice.model.Location;
import com.cvqs.defectsaveservice.repository.LocationRepository;

import org.junit.jupiter.api.*;
import org.mockito.Mockito;
public class LocationManagerTest {
    private LocationManager locationManager;
    private LocationRepository locationRepository;
    @BeforeEach
    public void setUp() {
        locationRepository = Mockito.mock(LocationRepository.class);
        locationManager = new LocationManager(locationRepository);
    }

    @DisplayName("should Find Location By X And Y And Return Location")
    @Test
    void shouldFindLocationByXAndYAndReturnLocation() {
        int x=100;
        int y=13;
        Location location1 = new Location();
        location1.setX(10);
        location1.setY(20);

        Location expectedResult=location1;

        Mockito.when(locationRepository.findLocationByXAndY(x,y)).thenReturn(location1);

        Location result=locationManager.findLocationByXAndY(x,y);

        Assertions.assertEquals(expectedResult,result);
        Mockito.verify(locationRepository).findLocationByXAndY(x,y);
    }

    @AfterEach
    public void tearDown() {

    }
}