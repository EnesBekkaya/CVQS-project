package com.cvqs.defectlistingservice.repository;

import com.cvqs.defectlistingservice.model.Defect;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface DefectListingRepository extends JpaRepository<Defect,String> {
    /**
     * Belirtilen plakaya sahip aracın arızalarını getirir.
     * @param registrationPlate araç plakası
     * @return Arıza nesnelerinin listesi
     */
    @Query("SELECT t FROM Defect t WHERE t.vehicle.registrationPlate=:registrationPlate")
    List<Defect> findByRegistrationPlate(String registrationPlate);

    /**
     * Belirtilen plakaya ve tipte sahip arıza nesnesini getirir.
     * @param registrationPlate araç plakası
     * @param type arıza tipi
     * @return Arıza nesnesi
     */
    @Query("SELECT t FROM Defect t WHERE t.vehicle.registrationPlate=:registrationPlate and t.type=:type")
    Defect findDefectByregistrationPlateAndType(String registrationPlate,String type);
}
