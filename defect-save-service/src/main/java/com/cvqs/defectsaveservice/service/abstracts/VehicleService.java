package com.cvqs.defectsaveservice.service.abstracts;

import com.cvqs.defectsaveservice.dto.VehicleDto;
import com.cvqs.defectsaveservice.model.Vehicle;

import java.util.List;
/**
 * VehicleService arayüzü, Vehicle nesneleriyle ilgili işlemleri gerçekleştirir
 *
 * @author Enes Bekkaya
 * @since  12.02.2023
 */
public interface VehicleService {
    /**
     * Verilen VehicleDto nesnesini kaydeder
     * @param vehicleDto kaydedilecek araç bilgileri nesnesi
     * @return kaydedilen vehicleDto nesnesi
     */
    VehicleDto save(VehicleDto vehicleDto);

    /**
     * Bütün araçların bir listesini döndürür.
     * @return araçların listesi  VehicleDto
     */
     List<VehicleDto> getAll();

    /**
     * Belirtilen plakaya sahip aracı bulur ve döndürür
     * @param registrationPlate aracın plakası
     * @return belirtilen plakaya sahip araç Vehicle
     */
     Vehicle findVehicleByRegistrationPlate(String registrationPlate);
}
