package com.cvqs.defectsaveservice.service.abstracts;

import com.cvqs.defectsaveservice.dto.LocationDto;
import com.cvqs.defectsaveservice.model.Location;

import java.util.List;
/**
 * LocationService arayüzü, Location nesneleriyle ilgili işlemleri gerçekleştirir
 *
 * @author Enes Bekkaya
 * @since  12.02.2023
 */
public interface LocationService {
   /**
    * Belirtilen konumların listesini kaydeder.
    * @param locations kaydedilecek  Location listesi.
    * @return kaydedilen LocationDto listesi.
    */
   List< LocationDto> save(List<Location> locations);

   /**
    * X ve Y koordinatlarına göre bir  Location arar ve bulursa döndürür
    * @param x aranan konumun X koordinatı
    * @param y aranan konumun Y koordinatı
    * @return belirtilen koordinatlara sahip Location varlığı
    */
   Location findLocationByXAndY(int x,int y);
}
