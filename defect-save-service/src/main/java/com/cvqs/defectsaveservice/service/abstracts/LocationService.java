package com.cvqs.defectsaveservice.service.abstracts;

import com.cvqs.defectsaveservice.model.Location;

/**
 * LocationService arayüzü, Location nesneleriyle ilgili işlemleri gerçekleştirir
 *
 * @author Enes Bekkaya
 * @since  12.02.2023
 */
public interface LocationService {
   /**
    * X ve Y koordinatlarına göre bir  Location arar ve bulursa döndürür
    * @param x aranan konumun X koordinatı
    * @param y aranan konumun Y koordinatı
    * @return belirtilen koordinatlara sahip Location varlığı
    */
   Location findLocationByXAndY(int x,int y);
}
