package com.cvqs.defectsaveservice.controller;

import com.cvqs.defectsaveservice.dto.DefectDto;
import com.cvqs.defectsaveservice.service.abstracts.DefectService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
/**
 * DefectController sınıfı,Defect servisinin isteklerini karşılamak için kullanılır.
 *
 * @author Enes Bekkaya
 * @since  12.02.2023
 */
@RestController
@RequestMapping("/defects")
@RequiredArgsConstructor
public class DefectController {
    private final DefectService defectService;
    private static final Logger LOGGER= LoggerFactory.getLogger(DefectController.class);


    /**
     * Yeni bir hata kaydı oluşturma isteklerini karşılamak için kullanılır
     * @param defectDto Yeni hata kaydı için kullanılacak bilgiler
     * @param file Arızaya ait resim dosyası
     * @return Kaydedilen hata kaydı bilgileri
     */
    @PostMapping("/save")
    public ResponseEntity<DefectDto> saveDefect(@RequestPart("defect")@Valid DefectDto defectDto, @RequestPart("file") MultipartFile file) throws IOException, SQLException {
        LOGGER.info("Incoming request for /defects/save");
        return ResponseEntity.ok(defectService.save(defectDto,file));
    }

    /**
     *
     * Bir hata kaydını güncelleme isteklerini karşılamak için kullanılır
     * @param defectDto Hata kaydı verilerini içeren DefectDto nesnesi
     * @param file Güncellenen hata kaydına eklenen dosya (isteğe bağlı)
     * @return Güncellenen hata kaydını temsil eden DefectDto nesnesi
     * @throws IOException Bir I/O hatası oluştuğunda fırlatılır
     * @throws SQLException Veritabanı işlemleri sırasında bir hata oluştuğunda fırlatılır
     */
    @PostMapping("/update")
    public ResponseEntity<DefectDto>updateDefect(@RequestPart("defect")@Valid DefectDto defectDto,@RequestPart("file") MultipartFile file) throws IOException, SQLException{
    LOGGER.info("Incoming request for /defects/update");
    return ResponseEntity.ok(defectService.update(defectDto,file));
    }

}
