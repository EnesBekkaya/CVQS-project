package com.cvqs.defectlistingservice.service.concretes;

import com.cvqs.defectlistingservice.model.Image;
import com.cvqs.defectlistingservice.repository.ImageRepository;
import com.cvqs.defectlistingservice.service.abstracts.ImageService;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.awt.*;
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
