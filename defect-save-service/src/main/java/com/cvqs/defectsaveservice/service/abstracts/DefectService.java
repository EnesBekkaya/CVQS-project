package com.cvqs.defectsaveservice.service.abstracts;

import com.cvqs.defectsaveservice.dto.DefectDto;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.sql.SQLException;
/**
 * DefectService interface handles operations on Defect objects
 * @author Enes Bekkaya
 * @since  12.02.2023
 */
public interface DefectService {
    /**
     * Creates a Defect object using the given DefectDto and file data, and saves this object to the database.
     * @param defectDto DefectDto object to be saved
     * @param file file data to be saved
     * @return the saved DefectDto object
     * @throws IOException thrown when there is an error in reading/writing the file
     * @throws SQLException thrown when there is an error in accessing the database or the save operation fails
     */
    DefectDto save(DefectDto defectDto, MultipartFile file) throws IOException, SQLException;

    /**
     * Method used for updating a defect record.
     * @param defectDto DefectDto object containing the updated defect data
     * @param file File added to the updated defect record
     * @return DefectDto object representing the updated defect record
     * @throws IOException thrown when an I/O error occurs
     * @throws SQLException thrown when an error occurs during database operations
     */
    DefectDto update(DefectDto defectDto,MultipartFile file) throws SQLException, IOException;

}
