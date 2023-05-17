package com.cvqs.defectsaveservice.service.abstracts;

import com.cvqs.defectsaveservice.model.Location;

/**
 * LocationService interface performs operations on Location objects
 *
 * @author Enes Bekkaya
 * @since  12.02.2023
 */
public interface LocationService {
   /**
    * Searches for a Location based on its X and Y coordinates, and returns it if found.
    * @param x X coordinate of the location being searched for
    * @param y Y coordinate of the location being searched for
    * @return The Location entity with the specified coordinates, or null if not found.
    */
   Location findLocationByXAndY(int x,int y);
}
