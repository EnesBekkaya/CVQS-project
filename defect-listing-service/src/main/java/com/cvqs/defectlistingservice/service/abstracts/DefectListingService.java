package com.cvqs.defectlistingservice.service.abstracts;

import com.cvqs.defectlistingservice.dto.DefectDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.sql.SQLException;
import java.util.List;
/**
 * DefectListingService arayüzü, hataları getirme ve sıralama için yöntemler sağlar.
 *
 * @author Enes Bekkaya
 * @since  12.02.2023
 */
public interface DefectListingService {
    /**
     * Veritabanındaki tüm Defect nesnelerini bulur ve bunları DefectDto nesnelerine dönüştürerek bir liste olarak döndürür.
     * @return Veritabanındaki tüm Defect nesnelerinin DefectDto karşılıklarını içeren bir liste
     *
     */
    List< DefectDto> getAll();

    /**
     * Verilen kayıt plakasına sahip Defect nesnelerini bulur ve bunları DefectDto nesnelerine dönüştürerek bir liste olarak döndürür.
     * @param registrationPlate bulunacak Defect nesnelerinin kayıt plakası
     * @return kayıt plakasına sahip Defect nesnelerinin DefectDto karşılıklarını içeren bir liste
     *
     */
    List<DefectDto> findByRegistrationPlate(String registrationPlate);

    /**
     * Verilen kayıt plakası ve kusur tipine sahip bir Defect nesnesinin resim verisini döndürür.
     * @param registrationPlate resmi alınacak Defect nesnesinin kayıt plakası
     * @param defectType resmi alınacak Defect nesnesinin kusur tipi
     * @return resim verisi
     * @throws SQLException veritabanına erişilemediğinde veya resim verisi alınırken hata oluştuğunda fırlatılır
     */
    byte[] getDefectImage(String registrationPlate,String defectType) throws SQLException;

    /**
     * Belirtilen sıralama parametrelerine göre sıralanmış bir DefectDto listesi döndürür
     * @param pageNo alınacak sayfa numarası
     * @param pageSize sayfa başına maksimum öğe sayısı
     * @param sortBy ile sıralanacak alan
     * @return belirtilen parametrelere göre sıralanmış bir  DefectDto listesi
     */
    List<DefectDto> getDefectSorted(Integer pageNo, Integer pageSize, String sortBy);
}
