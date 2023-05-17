package com.cvqs.defectlistingservice.repository;

import com.cvqs.defectlistingservice.model.Image;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *ImageRepository interface is used to perform database operations on Image objects.
 *
 * @author Enes Bekkaya
 * @since 12.02.2023
 */
public interface ImageRepository extends JpaRepository<Image,String> {
}
