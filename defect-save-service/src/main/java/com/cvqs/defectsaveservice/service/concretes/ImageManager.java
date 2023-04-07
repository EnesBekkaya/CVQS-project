package com.cvqs.defectsaveservice.service.concretes;

import com.cvqs.defectsaveservice.model.Image;
import com.cvqs.defectsaveservice.model.Location;
import com.cvqs.defectsaveservice.repository.ImageRepository;
import com.cvqs.defectsaveservice.service.abstracts.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.sql.rowset.serial.SerialBlob;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
/**
 * ImageManager sınıfı,ImageService arayüzünden türetilmiştir ve
 *  Bu sınıf, resim dosyalarının yüklenmesi, işlenmesi ve veritabanında saklanmasını sağlar.
 *  Bu sınıf, veritabanı işlemleri için imageRepository
 *
 *  @author Enes Bekkaya
 *  @since  18.03.2023
 */
@Service
@RequiredArgsConstructor
public class ImageManager implements ImageService {

    private final ImageRepository imageRepository;
    /**
     * Verilen dosyayı işleyerek belirtilen konumlardaki nesneleri işaretleyen bir resim veritabanına kaydeder.
     * @param file kaydedilecek resim dosyası
     * @param locations işaretlenecek konumların listesi
     * @return kaydedilen resim nesnesini döndürür
     * @throws IOException eğer dosya işleme sırasında bir hata oluşursa
     * @throws SQLException eğer veritabanına kayıt sırasında bir hata oluşursa
     *
     */
    @Override
    public Image saveImage(MultipartFile file, List<Location> locations) throws IOException, SQLException {

        byte[] imageData = processingImage(file, locations);

        Blob imageBlob = new SerialBlob(imageData);

        Image image = new Image();
        image.setData(imageBlob);

        return imageRepository.save(image);
    }

    /**
     * Verilen resim dosyasını işleyerek belirtilen konumlarda nesneleri işaretleyen ve işaretli resmin byte dizisini döndürür.
     * @param file işlenecek resim dosyası
     * @param locations işaretlenecek nesnelerin konumları
     * @return işaretlenmiş resmin byte dizisi döndürür
     * @throws IOException eğer dosya işleme sırasında bir hata oluşursa
     *
     */
    @Override
    public byte[] processingImage(MultipartFile file, List<Location> locations) throws IOException {
        byte[] photoBytes = file.getBytes();
        ByteArrayInputStream inputStream = new ByteArrayInputStream(photoBytes);
        BufferedImage image = ImageIO.read(inputStream);


        String imagePath = "/path/in/container/hata_PHOTO.jpg";
        BufferedImage overlay = ImageIO.read(new File(imagePath));
        Graphics2D g2d = image.createGraphics();
        for (Location location : locations) {
            g2d.drawImage(overlay, location.getX(), location.getY(), 20, 20, null);
        }

        g2d.dispose();

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ImageIO.write(image, "jpg", outputStream);
        byte[] markedBytes = outputStream.toByteArray();
        return markedBytes;
    }
    /**
     * Verilen Image nesnesinin veritabanındaki karşılığını bulup geri döndürür.
     * @param image veritabanındaki Image nesnesinin kimliğini taşıyan Image nesnesi
     * @return veritabanındaki Image nesnesi döndürür
     *
     */
    @Override
    public Image getImage(Image image) {
        Optional<Image> savedImage=imageRepository.findById(image.getId());
        return savedImage.get();
    }
}
