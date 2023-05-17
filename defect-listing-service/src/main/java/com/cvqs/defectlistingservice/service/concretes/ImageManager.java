package com.cvqs.defectlistingservice.service.concretes;

import com.cvqs.defectlistingservice.model.Image;
import com.cvqs.defectlistingservice.repository.ImageRepository;
import com.cvqs.defectlistingservice.service.abstracts.ImageService;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.Optional;

/**
 * The ImageManager class is derived from the ImageService interface and
 * This class provides functionality for retrieving image files from the database.
 *
 *  @author Enes Bekkaya
 *  @since  18.03.2023
 */
@Service
@RequiredArgsConstructor
public class ImageManager implements ImageService {

    private final ImageRepository imageRepository;

    /**
     * Finds and returns the equivalent of the given Image object in the database.
     * @param image the Image object carrying the identity of the Image object in the database
     * @return the Image object in the database
     *
     */
    @Override
    public Image getImage(Image image) {
        Optional<Image> savedImage=imageRepository.findById(image.getId());
        return savedImage.get();
    }
}
