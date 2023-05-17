package com.cvqs.defectsaveservice.service.abstracts;

import com.cvqs.defectsaveservice.model.Image;
import com.cvqs.defectsaveservice.model.Location;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
/**
 * ImageService interface performs operations on Image objects
 *
 * @author Enes Bekkaya
 * @since  12.02.2023
 */
public interface ImageService {
    /**
     * Saves the specified MultipartFile as an Image entity associated with the given locations.
     * @param file MultipartFile containing the image data
     * @param locations list of Location entities associated with the image
     * @return the saved Image entity
     * @throws IOException if an error occurs while processing the file
     * @throws SQLException if there is an issue saving to the database
     */
    Image  saveImage( MultipartFile file,List<Location> locations) throws IOException, SQLException;

    /**
     * Processes the specified MultipartFile containing image data associated with the given locations and returns the processed image data as a byte array.
     * @param file MultipartFile containing image data to be processed
     * @param locations List of locations associated with the image
     * @return Byte array containing the processed image data
     * @throws IOException if an error occurs while processing the file
     */
     byte[] processingImage(MultipartFile file, List<Location> locations) throws IOException;

}
