package com.cvqs.defectlistingservice.controller;

import com.cvqs.defectlistingservice.dto.DefectDto;
import com.cvqs.defectlistingservice.service.abstracts.DefectListingService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLException;
import java.util.List;
/**
 * DefectListingController sınıfı, hata listeleme servislerinin isteklerini karşılamak için kullanılır.
 *
 * @author Enes Bekkaya
 * @since  12.02.2023
 */
@RestController
    @RequestMapping("/defectListing")
@RequiredArgsConstructor
public class DefectListingController {
    private static final Logger LOGGER= LoggerFactory.getLogger(DefectListingController.class);
    private final DefectListingService defectListingService;
    /**
     * Tüm hataları getirme isteklerini karşılamak için kullanılır.
     *
     * @return Tüm hataların listesini döndürür.
     */
    @GetMapping("/getDefects")
    public ResponseEntity<List<DefectDto>> getDefect(){
        LOGGER.info("Incoming request for /defectListing/getDefects");
        return ResponseEntity.ok(defectListingService.getAllDefects());
    }
    /**
     * Plaka numarasına göre hata getirme isteklerini karşılamak için kullanılır.
     *
     * @param registrationPlate Hataların getirileceği aracın plaka numarası.
     * @return Verilen plaka numarasına ait hataların listesini döndürür.
     */
    @GetMapping("/getByPlate")
    public ResponseEntity<List<DefectDto> > getDefect(@RequestParam String registrationPlate){
        LOGGER.info("Incoming request for /defectListing/getByPlate");
        return ResponseEntity.ok(defectListingService.findDefectByPlate(registrationPlate));
    }
    /**
     * Plaka numarası ve hata tipine göre hata resimlerini getirme isteklerini karşılamak için kullanılır.
     *
     * @param registrationPlate Resmi getirilecek aracın plaka numarası.
     * @param defectType        Resmi getirilecek hatanın tipi.
     * @return Verilen plaka numarası ve hata tipine ait resmin byte dizisini döndürür.
     * @throws SQLException Veritabanı işlemleri sırasında bir hata oluşursa fırlatılır.
     */
    @GetMapping("/getImage")
    @Transactional
    ResponseEntity<byte[]> getDefectImage(@RequestParam String registrationPlate, @RequestParam String defectType) throws SQLException{
        LOGGER.info("Incoming request for /defectListing/getImage");
        return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(defectListingService.getDefectImage(registrationPlate,defectType));

    }
    /**
     * Sayfa numarası, sayfa boyutu ve sıralama ölçütüne göre hata listesi getirme isteklerini karşılamak için kullanılır.
     *
     * @param pageNo   Getirilecek sayfanın numarası.
     * @param pageSize Sayfa boyutu.
     * @param sortBy   Sıralama ölçütü.
     * @return Verilen sayfa numarası, sayfa boyutu ve sıralama ölçütüne göre hataların listesini döndürür.
     */
    @GetMapping("/sort")
    ResponseEntity<List<DefectDto>> getDefectSorted(@RequestParam Integer pageNo, @RequestParam Integer pageSize, @RequestParam String sortBy){
        LOGGER.info("Incoming request for /defectListing/sort");
        return ResponseEntity.ok(defectListingService.getDefectSorted(pageNo,pageSize,sortBy));
    }
}
