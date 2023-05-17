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
/**
 * The ImageManager class is derived from the ImageService interface and
 * is responsible for uploading, processing, and storing image files in the database.
 * This class uses the imageRepository for database operations.
 *
 *  @author Enes Bekkaya
 *  @since  18.03.2023
 */
@Service
@RequiredArgsConstructor
public class ImageManager implements ImageService {

    private final ImageRepository imageRepository;

    /**
     *  Processes the given file and saves an image object to the database that marks the objects at the specified locations.
     * @param file the image file to be saved
     * @param locations list of locations to mark
     * @return the saved image object
     * @throws IOException if an error occurs during file processing
     * @throws SQLException if an error occurs during database insertion
     *
     */
    @Override
    public Image saveImage(MultipartFile file, List<Location> locations) throws IOException, SQLException {
        try {
            byte[] imageData = processingImage(file, locations);

            Blob imageBlob = new SerialBlob(imageData);

            Image image = new Image();
            image.setData(imageBlob);

            return imageRepository.save(image);
        }catch (Exception e){
            throw new IOException("kaydetme işlemi başarısız");
        }
    }

    /**
     * Processes the given image file and marks objects at specified locations, returning the marked image as a byte array.
     * @param file the image file to process
     * @param locations the locations of the objects to mark
     * @return the byte array of the marked image
     * @throws IOException if an error occurs during file processing
     *
     */
    @Override
    public byte[] processingImage(MultipartFile file, List<Location> locations) throws IOException {
        try {
            byte[] photoBytes = file.getBytes();
            ByteArrayInputStream inputStream = new ByteArrayInputStream(photoBytes);
            BufferedImage image = ImageIO.read(inputStream);


            String imagePath = "/path/in/container/hata_PHOTO.jpg";
            BufferedImage overlay = ImageIO.read(new File(imagePath));
            Graphics2D g2d = image.createGraphics();
            for (Location location : locations) {
                g2d.drawImage(overlay, location.getX(), location.getY(), 40, 40, null);
            }

            g2d.dispose();

            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            ImageIO.write(image, "jpg", outputStream);
            byte[] markedBytes = outputStream.toByteArray();
            return markedBytes;
        }catch (IOException e){
            throw  new IOException("Dosyadan bayt okunurken hata oluştu",e);
        }

    }
}
