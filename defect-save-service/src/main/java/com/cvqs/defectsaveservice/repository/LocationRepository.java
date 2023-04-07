package com.cvqs.defectsaveservice.repository;

import com.cvqs.defectsaveservice.model.Location;
import org.springframework.data.jpa.repository.JpaRepository;
import com.cvqs.defectsaveservice.model.Defect;
import java.util.List;
/**
 * LocationRepository, Location nesnelerinin veritabanı işlemlerini yapmak için kullanılan arayüz.
 *
 * @author Enes Bekkaya
 * @since  12.02.2023
 */
public interface LocationRepository extends JpaRepository<Location,String> {
  /**
   * Verilen bir Defect objesine ait olan tüm Location kayıtlarını getirir.
   *
   * @param defect Bir Defect objesi
   * @return Defect objesine ait olan tüm Location kayıtlarını içeren bir liste döndürür.
   */
  List<Location> findLocationByDefects(Defect defect);
  /**
   * Verilen x ve y koordinatlarına sahip bir Location kaydının var olup olmadığını kontrol eder.
   *
   * @param x X koordinatı
   * @param y Y koordinatı
   * @return Eğer verilen koordinatlara sahip bir Location kaydı varsa true, aksi halde false döndürür.
   */
  Boolean existsLocationsByXAndY(int x,int y);
  /**
   * Verilen x ve y koordinatlarına sahip olan bir Location kaydını getirir.
   *
   * @param x X koordinatı
   * @param y Y koordinatı
   * @return Verilen koordinatlara sahip bir Location kaydı varsa o kaydı, aksi halde null döndürür.
   */
  Location findLocationByXAndY(int x,int y);
}
