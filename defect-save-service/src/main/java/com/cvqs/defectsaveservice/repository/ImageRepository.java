package com.cvqs.defectsaveservice.repository;

import com.cvqs.defectsaveservice.model.Image;
import org.springframework.data.jpa.repository.JpaRepository;
/**
 * ImageRepository, Image nesnelerinin veritabanı işlemlerini yapmak için kullanılan arayüz.
 *
 * @author Enes Bekkaya
 * @since  12.02.2023
 */
public interface ImageRepository extends JpaRepository<Image,String> {
}
