package com.cvqs.defectsaveservice.repository;

import com.cvqs.defectsaveservice.model.Location;
import org.springframework.data.jpa.repository.JpaRepository;
/**
 * LocationRepository is the interface used to perform database operations of Location objects.
 *
 * @author Enes Bekkaya
 * @since  12.02.2023
 */
public interface LocationRepository extends JpaRepository<Location,String> {
  /**
   * Gets a Location record with the given x and y coordinates.
   * @param x The x coordinate
   * @param y The y coordinate
   * @return The Location record with the given coordinates if it exists, otherwise null.
   */
  Location findLocationByXAndY(int x,int y);
}
