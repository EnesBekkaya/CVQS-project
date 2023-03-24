package com.cvqs.defectsaveservice.repository;

import com.cvqs.defectsaveservice.model.Location;
import org.springframework.data.jpa.repository.JpaRepository;
import com.cvqs.defectsaveservice.model.Defect;
import java.util.List;

public interface LocationRepository extends JpaRepository<Location,String> {
  List<Location> findLocationByDefects(Defect defect);
  Boolean existsLocationsByXAndY(int x,int y);
  Location findLocationByXAndY(int x,int y);
}
