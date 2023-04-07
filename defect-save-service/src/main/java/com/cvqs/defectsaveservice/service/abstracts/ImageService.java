package com.cvqs.defectsaveservice.service.abstracts;

import com.cvqs.defectsaveservice.model.Image;
import com.cvqs.defectsaveservice.model.Location;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
/**
 * ImageService arayüzü, Image nesneleriyle ilgili işlemleri gerçekleştirir
 *
 * @author Enes Bekkaya
 * @since  12.02.2023
 */
public interface ImageService {
    /**
     * Belirtilen MultipartFile dosyasını, belirtilen lokasyonlarla ilişkilendirilmiş bir  Image varlığı olarak kaydeder
     * @param file resim verilerini içeren  MultipartFile
     * @param locations resimle ilişkilendirilmiş Location listesi
     * @return kaydedilen Image
     * @throws IOException eğer dosya işlenirken bir hata oluşursa
     * @throws SQLException eğer veritabanına kaydetmek için bir sorun oluşursa
     */
    Image  saveImage( MultipartFile file,List<Location> locations) throws IOException, SQLException;

    /**
     * Belirtilen MultipartFile dosyasını, belirtilen lokasyonlarla işleyerek işlenmiş resim verilerini byte dizisi olarak döndürür
     * @param file işlenecek resim verilerini içeren MultipartFile
     * @param locations resimle ilişkilendirilmiş Location listesi
     * @return işlenmiş resim verilerini içeren byte dizisi
     * @throws IOException eğer dosya işlenirken bir hata oluşursa
     */
     byte[] processingImage(MultipartFile file, List<Location> locations) throws IOException;

    /**
     * Belirtilen  Image varlığına ait resmi getirir
     * @param image getirilecek resmi içeren  Image
     * @return belirtilen  Image varlığına ait resim verilerini içeren byte dizisi
     */
    Image getImage(Image image);


}
