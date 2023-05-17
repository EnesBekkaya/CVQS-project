package com.cvqs.defectsaveservice.service.concretes;

import com.cvqs.defectsaveservice.model.Location;
import com.cvqs.defectsaveservice.repository.LocationRepository;
import com.cvqs.defectsaveservice.service.abstracts.LocationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


/**
 * The LocationManager class is derived from the LocationService interface and
 * manages operations related to locations. This class uses LocationRepository objects for database operations.
 *
 * @author Enes Bekkaya
 * @since  13.02.2023
 */
@Service
@RequiredArgsConstructor
public class LocationManager implements LocationService {
    private final LocationRepository locationRepository;
    /**
     * Finds a location with the given X and Y coordinates. If such a location does not exist, creates and saves a new location.
     * @param x the X coordinate of the location to be searched for
     * @param y the Y coordinate of the location to be searched for
     * @return the found location or the newly created location
    */
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