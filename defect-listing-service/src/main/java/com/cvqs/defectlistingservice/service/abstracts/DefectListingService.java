package com.cvqs.defectlistingservice.service.abstracts;

import com.cvqs.defectlistingservice.dto.DefectDto;

import java.sql.SQLException;
import java.util.List;
/**
 *The DefectListingService interface provides methods for retrieving and sorting defects.
 * @author Enes Bekkaya
 * @since 12.02.2023
 */
public interface DefectListingService {
    /**
     * Finds all Defect objects in the database and returns them as a list of DefectDto objects.
     * @return A list containing DefectDto equivalents of all Defect objects in the database
     *
     */
    List< DefectDto> getAll();

    /**
     * Finds Defect objects with the given registration plate and converts them to a list of DefectDto objects.
     * @param registrationPlate the registration plate of the Defect objects to be found
     * @return a list containing DefectDto counterparts of Defect objects with the given registration plate
     *
     */
    List<DefectDto> findByRegistrationPlate(String registrationPlate);

    /**
     * Returns the image data of a Defect object with the given registration plate and defect type.
     * @param registrationPlate the registration plate of the Defect object to retrieve the image data
     * @param defectType the defect type of the Defect object to get the image data
     * @return the image data
     * @throws SQLException if there is an error accessing the database or getting the image data
     */
    byte[] getDefectImage(String registrationPlate,String defectType) throws SQLException;

    /**
     * Returns a sorted list of DefectDto objects according to the specified sorting parameters.
     * @param pageNo the page number to be get
     * @param pageSize the maximum number of items per page
     * @param sortBy the field to be sorted by
     * @return a sorted list of DefectDto objects according to the specified parameters
     */
    List<DefectDto> getDefectSorted(Integer pageNo, Integer pageSize, String sortBy);
}
