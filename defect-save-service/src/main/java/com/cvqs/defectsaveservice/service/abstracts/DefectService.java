package com.cvqs.defectsaveservice.service.abstracts;

import com.cvqs.defectsaveservice.dto.DefectDto;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.sql.SQLException;
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
    DefectDto update(DefectDto defectDto,MultipartFile file) throws SQLException;

}
