package com.cvqs.defectsaveservice.repository;

import com.cvqs.defectsaveservice.model.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;
/**
 * VehicleRepository, Vehicle nesnelerinin veritabanı işlemlerini yapmak için kullanılan arayüz.
 *
 * @author Enes Bekkaya
 * @since  12.02.2023
 */
public interface VehicleRepository extends JpaRepository<Vehicle,String> {
    /**
     * Belirtilen plakaya sahip aracı veritabanından bulur.
     * @param registrationPlate araç plakası
     * @return bulunan vehicle
     */
    Vehicle findVehicleByRegistrationPlate(String registrationPlate);
}
