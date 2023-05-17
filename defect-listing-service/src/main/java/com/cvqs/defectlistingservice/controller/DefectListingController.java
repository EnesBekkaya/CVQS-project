package com.cvqs.defectlistingservice.controller;

import com.cvqs.defectlistingservice.dto.DefectDto;
import com.cvqs.defectlistingservice.service.abstracts.DefectListingService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLException;
import java.util.List;
/**
 * DefectListingController class is used to handle the requests of defect listing services.
 * @author Enes Bekkaya
 * @since 12.02.2023
 */
@RestController
@RequestMapping("/defectListing")
@RequiredArgsConstructor
public class DefectListingController {
    private final DefectListingService defectListingService;
    /**
     * Used to handle requests for get all defects.
     * @return the list of all defects
     */
    @GetMapping("/getAll")
    public ResponseEntity<List<DefectDto>> getAllDefects(){

        return ResponseEntity.ok(defectListingService.getAll());
    }
    /**
     * Used to handle requests for get defects by registration plate number of a vehicle.
     *
     * @param registrationPlate the registration plate number of the vehicle for which defects will be listed
     * @return  the list of defects for the given registration plate number
     */
    @GetMapping("/getByPlate")
    public ResponseEntity<List<DefectDto>> getDefectsByPlate(@RequestParam String registrationPlate){
        return ResponseEntity.ok(defectListingService.findByRegistrationPlate(registrationPlate));
    }
    /**
     *  This method is used to handle requests for getting defect images based on the registration plate number and defect type.
     * @param registrationPlate The registration plate number of the vehicle for which the image will be get.
     * @param defectType The type of the defect for which the image will be get.
     * @return The byte array of the image for the given registration plate number and defect type.
     * @throws SQLException If an error occurs during database operations.
     */
    @GetMapping("/getImage")
    @Transactional
    public ResponseEntity<byte[]> getDefectImage(@RequestParam String registrationPlate,@RequestParam String defectType) throws SQLException {
        return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(defectListingService.getDefectImage(registrationPlate,defectType));
    }
    /**
     * Used to handle requests for getting defect list based on page number, page size, and sorting criteria.
     * @param pageNo Number of the page to retrieve.
     * @param pageSize Size of the page.
     * @param sortBy Sorting criteria.
     * @return List of defects based on the given page number, page size, and sorting criteria.
     */
    @GetMapping("/sort")
    public ResponseEntity<List<DefectDto>> getDefectSorted(@RequestParam Integer pageNo, @RequestParam Integer pageSize, @RequestParam String sortBy){
        return ResponseEntity.ok(defectListingService.getDefectSorted(pageNo,pageSize,sortBy));
    }
}
