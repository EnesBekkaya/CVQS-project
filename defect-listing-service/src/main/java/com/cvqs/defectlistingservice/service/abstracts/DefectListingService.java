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
     * Tüm hataların bir listesini döndürür.
     *
     * @return DefectDto nesneleri olarak tüm hataların bir listesini döndürür.
     */
    List<DefectDto> getAllDefects();
    /**
     * Belirtilen plakaya sahip hataların bir listesini döndürür.
     *
     * @param registrationPlate aranacak plaka kodu
     * @return Belirtilen plakaya sahip hataların bir listesi olarak DefectDto nesneleri döndürür.
     */
    List<DefectDto>  findDefectByPlate(String registrationPlate);
    /**
     * Belirtilen kayıt plakası ile ilişkili belirtilen hatanın resmini döndürür.
     *
     * @param registrationPlate hata ile ilişkili aracın kayıt plakası
     * @param defectType resmini almak için hata türü
     * @return belirtilen hatanın resmi, bir byte dizisi olarak döndürür
     * @throws SQLException veritabanından resmi alırken bir sorun oluşursa fırlatılır
     */
    byte[] getDefectImage(String registrationPlate, String defectType) throws SQLException;
    /**
     * Sayfalanmış ve sıralanmış bir hata listesi döndürür.
     *
     * @param pageNo getirilecek sonuçların sayfa numarası
     * @param pageSize sayfa başına getirilecek sonuç sayısı
     * @param sortBy sonuçların hangi özellikle sıralanacağı
     * @return sayfalanmış ve sıralanmış bir hata listesi, DefectDto nesneleri olarak döndürür
     */
     List<DefectDto> getDefectSorted( Integer pageNo,  Integer pageSize,  String sortBy);
}
