package com.cvqs.defectsaveservice.repository;

import com.cvqs.defectsaveservice.model.Defect;
import com.cvqs.defectsaveservice.model.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
/**
 * DefectRepository, Defect nesnelerinin veritabanı işlemlerini yapmak için kullanılan arayüz.
 *
 * @author Enes Bekkaya
 * @since  12.02.2023
 */
public interface DefectRepository extends JpaRepository<Defect,String> {
   /**
    * Belirtilen tipte ve araçta olan arıza nesnesini getirir.
    * @param type arıza tipi
    * @param vehicle araç nesnesi
    * @return Arıza nesnesi
    */
   Defect getDefectsByTypeAndVehicle(String type, Vehicle vehicle);

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
