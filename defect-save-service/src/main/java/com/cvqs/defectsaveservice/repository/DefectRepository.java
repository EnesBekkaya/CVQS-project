package com.cvqs.defectsaveservice.repository;

import com.cvqs.defectsaveservice.model.Defect;
import com.cvqs.defectsaveservice.model.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;
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




}
