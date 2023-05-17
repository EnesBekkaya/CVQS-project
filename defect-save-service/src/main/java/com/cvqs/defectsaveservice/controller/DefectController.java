package com.cvqs.defectsaveservice.controller;

import com.cvqs.defectsaveservice.dto.DefectDto;
import com.cvqs.defectsaveservice.service.abstracts.DefectService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.sql.SQLException;
/**
 * The DefectController class is used to handle requests for the Defect service.
 *
 * @author Enes Bekkaya
 * @since  12.02.2023
 */
@RestController
@RequestMapping("/defects")
@RequiredArgsConstructor
public class DefectController {
    private final DefectService defectService;


    /**
     * Used to handle requests for creating a new defect record
     * @param defectDto Information to be used for creating the new defect record
     * @param file Image file for the defect
     * @return Information of the saved defect record
     */
    @PostMapping("/save")
    public ResponseEntity<DefectDto> saveDefect(@RequestPart("defect")@Valid DefectDto defectDto, @RequestPart("file") MultipartFile file) throws IOException, SQLException {
        return ResponseEntity.ok(defectService.save(defectDto,file));
    }

    /**
     *
     * Used to handle requests for updating a defect record.
     * @param defectDto DefectDto object containing the data for the updated defect record
     * @param file File added to the updated defect record (optional)
     * @return DefectDto object representing the updated defect record
     * @throws IOException Thrown when an I/O error occurs
     * @throws SQLException Thrown when a database error occurs
     */
    @PostMapping("/update")
    public ResponseEntity<DefectDto>updateDefect(@RequestPart("defect")@Valid DefectDto defectDto,@RequestPart("file") MultipartFile file) throws IOException, SQLException{
    return ResponseEntity.ok(defectService.update(defectDto,file));
    }

}
