package com.cvqs.defectsaveservice.service.abstracts;

import com.cvqs.defectsaveservice.model.Image;
import com.cvqs.defectsaveservice.model.Location;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public interface ImageService {
         Image  saveImage( MultipartFile file,List<Location> locations) throws IOException, SQLException;

     byte[] processingImage(MultipartFile file, List<Location> locations) throws IOException;

    Image getImage(Image image);


}
