package com.cvqs.defectsaveservice.service.abstracts;

import com.cvqs.defectsaveservice.dto.DefectDto;
import com.cvqs.defectsaveservice.model.Defect;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
/**
 * DefectService arayüzü, Defect nesneleriyle ilgili işlemleri gerçekleştirir
 *
 * @author Enes Bekkaya
 * @since  12.02.2023
 */
public interface DefectService {
    /**
     *Verilen DefectDto ve dosya verilerini kullanarak bir Defect nesnesi oluşturur ve bu nesneyi veritabanına kaydeder.
     * @param defectDto kaydedilecek DefectDto nesnesi
     * @param file kaydedilecek dosya verisi
     * @return kaydedilen DefectDto nesnesi
     * @throws IOException dosya okuma/yazma hatası oluştuğunda fırlatılır
     * @throws SQLException veritabanına erişilemediğinde veya kaydetme işlemi başarısız olduğunda fırlatılır
     */
    DefectDto save(DefectDto defectDto, MultipartFile file) throws IOException, SQLException;

    /**
     * Bir hata kaydını güncellemek için kullanılan metottur.
     * @param defectDto Hata kaydı verilerini içeren DefectDto nesnesi
     * @param file Güncellenen hata kaydına eklenen dosya
     * @return Güncellenen hata kaydını temsil eden DefectDto nesnesi
     * @throws IOException Bir I/O hatası oluştuğunda fırlatılır
     * @throws SQLException Veritabanı işlemleri sırasında bir hata oluştuğunda fırlatılır
     */
    DefectDto update(DefectDto defectDto,MultipartFile file) throws IOException, SQLException;
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
