package com.cvqs.defectsaveservice.service.abstracts;

import com.cvqs.defectsaveservice.dto.VehichleDto;
import com.cvqs.defectsaveservice.model.Vehicle;

import java.util.List;
/**
 * VehichleService arayüzü, Vehicle nesneleriyle ilgili işlemleri gerçekleştirir
 *
 * @author Enes Bekkaya
 * @since  12.02.2023
 */
public interface VehicleService {
    /**
     * Verilen VehichleDto nesnesini kaydeder
     * @param vehichleDto kaydedilecek araç bilgileri nesnesi
     * @return kaydedilen vehichleDto nesnesi
     */
    VehichleDto save(VehichleDto vehichleDto);

    /**
     * Bütün araçların bir listesini döndürür.
     * @return araçların listesi  VehichleDto
     */
     List<VehichleDto> getAll();

    /**
     * Belirtilen plakaya sahip aracı bulur ve döndürür
     * @param registrationPlate aracın plakası
     * @return belirtilen plakaya sahip araç Vehicle
     */
     Vehicle findVehichleByRegistrationPlate(String registrationPlate);
}
