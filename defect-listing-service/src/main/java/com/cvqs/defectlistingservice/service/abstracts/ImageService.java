package com.cvqs.defectlistingservice.service.abstracts;

import com.cvqs.defectlistingservice.model.Image;


/**
 * ImageService arayüzü, Image nesneleriyle ilgili işlemleri gerçekleştirir
 *
 * @author Enes Bekkaya
 * @since  12.02.2023
 */
public interface ImageService {


    /**
     * Belirtilen  Image varlığına ait resmi getirir
     * @param image getirilecek resmi içeren  Image
     * @return belirtilen  Image varlığına ait resim verilerini içeren byte dizisi
     */
    Image getImage(Image image);


}
