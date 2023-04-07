package com.cvqs.defectlistingservice.client;

import com.cvqs.defectlistingservice.dto.DefectDto;
import jakarta.transaction.Transactional;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.sql.SQLException;
import java.util.List;
/**
 *  Bu Sınıf,defect-save servisine istek gönderir.
 *
 * @author Enes Bekkaya
 * @since  12.02.2023
 */
@FeignClient(name = "defect-save-service", path = "/defects")
public interface DefectListingServiceClient {
    /**
     * Veritabanındaki bütün hataları döndürür.
     *
     * @return sonuç liste olarak DefectDto veri tipinde döndürür.
     */
    @RequestMapping("/getAll")
    ResponseEntity<List<DefectDto>> getAllDefects();
    /**
     * Plakaya göre kayıtlı bütün hataları döndürür.
     *
     * @param registrationPlate listenlenmek istenen hatanın ait olduğu aracın registrationPlate kodu.
     * @return sonuc liste olarak DefectDto veri tipinde döndürür.
     */
    @RequestMapping("/getByPlate")
    ResponseEntity<List<DefectDto>> getDefectsByPlate(@RequestParam String registrationPlate);
    /**
     * Verilen plaka koduna ve hata tipine göre hata resmini döndürür.
     *
     * @param registrationPlate hatanın ait olduğu aracın registrationPlate kodu.
     * @param defectType hatanın türü.
     * @return sonuc byte dizisi olarak döndürülür.
     * @throws SQLException bir SQL hatası oluştuğunda fırlatılır.
     */
    @GetMapping("/getImage")
    @Transactional
    ResponseEntity<byte[]> getDefectImage(@RequestParam String registrationPlate, @RequestParam String defectType) throws SQLException;
    /**
     * Verilen sayfa numarası, sayfa boyutu ve sıralama seçeneğine göre hataları sıralı bir şekilde döndürür.
     *
     * @param pageNo döndürülecek sayfanın numarası.
     * @param pageSize döndürülecek sayfa boyutu.
     * @param sortBy sıralama kriteri.
     * @return sonuc liste olarak DefectDto veri tipinde döndürür.
     */
    @GetMapping("/sort")
     ResponseEntity<List<DefectDto>> getDefectSorted(@RequestParam Integer pageNo, @RequestParam Integer pageSize, @RequestParam String sortBy);

}
