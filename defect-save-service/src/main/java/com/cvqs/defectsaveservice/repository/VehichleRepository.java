package com.cvqs.defectsaveservice.repository;

import com.cvqs.defectsaveservice.model.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;
/**
 * VehichleRepository, Vehicle nesnelerinin veritabanı işlemlerini yapmak için kullanılan arayüz.
 *
 * @author Enes Bekkaya
 * @since  12.02.2023
 */
public interface VehichleRepository extends JpaRepository<Vehicle,String> {
    /**
     * Belirtilen plakaya sahip aracı veritabanından bulur.
     * @param registrationPlate araç plakası
     * @return bulunan vehicle
     */
    Vehicle findVehichleByRegistrationPlate(String registrationPlate);
}
