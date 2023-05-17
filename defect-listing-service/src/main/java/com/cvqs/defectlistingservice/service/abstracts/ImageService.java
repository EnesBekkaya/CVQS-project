package com.cvqs.defectlistingservice.service.abstracts;

import com.cvqs.defectlistingservice.model.Image;


/**
 * This is an interface for performing operations related to Image objects. *
 * @author Enes Bekkaya
 * @since  12.02.2023
 */
public interface ImageService {


    /**
     * Get the specified image
     * @param image the Image entity containing the image to get
     * @return a byte array containing the image data associated with the specified Image entity
     */
    Image getImage(Image image);


}
