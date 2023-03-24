package com.cvqs.defectsaveservice.repository;

import com.cvqs.defectsaveservice.model.Image;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<Image,String> {
}
