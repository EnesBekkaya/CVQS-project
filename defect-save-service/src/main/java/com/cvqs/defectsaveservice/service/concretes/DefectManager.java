package com.cvqs.defectsaveservice.service.concretes;

import com.cvqs.defectsaveservice.dto.DefectDto;
import com.cvqs.defectsaveservice.exception.EntityNotFoundException;
import com.cvqs.defectsaveservice.model.Defect;
import com.cvqs.defectsaveservice.model.Location;
import com.cvqs.defectsaveservice.model.Vehicle;
import com.cvqs.defectsaveservice.repository.DefectRepository;
import com.cvqs.defectsaveservice.service.abstracts.DefectService;
import com.cvqs.defectsaveservice.service.abstracts.ImageService;
import com.cvqs.defectsaveservice.service.abstracts.LocationService;
import com.cvqs.defectsaveservice.service.abstracts.VehicleService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

/**
 * The DefectManager class is derived from the DefectService interface and manages defect management operations.
 * This class uses the DefectRepository object for database operations, the LocationService object for location operations,
 * the VehicleService object for vehicle operations, and the ImageService object for image operations.
 *
 * @author Enes Bekkaya
 * @since 12.02.2023
 */
@Service
@RequiredArgsConstructor
public class DefectManager implements DefectService {
    private final DefectRepository defectRepository;
    private final LocationService locationService;
    private final VehicleService vehicleService;
    private final ImageService imageService;
    private final ModelMapper modelMapper;

    /**
     * Saves a new defect object with the given data to the database.
     *
     * @param defectDto The DTO object containing the defect data.
     * @param file The image file associated with the defect.
     * @return A DTO object representing the saved defect.
     * @throws EntityNotFoundException If a defect with the same type and vehicle already exists in the database.
     * @throws IOException If there is an error while reading or writing the image file.
     * @throws SQLException If there is an error while saving the defect to the database.
     */
    @Override
    public DefectDto save(DefectDto defectDto, MultipartFile file) throws IOException, SQLException {
        Vehicle vehicle = vehicleService.findVehicleByRegistrationPlate(defectDto.getVehicle().getRegistrationPlate());

        if (defectRepository.getDefectsByTypeAndVehicle(defectDto.getType(), vehicle) != null) {
            throw new EntityNotFoundException("Operation failed!! The defect record exists for the vehicle with registration plate " +
                    defectDto.getVehicle().getRegistrationPlate());
        }

        List<Location> locations = defectDto.getLocations().stream()
                .map(location -> locationService.findLocationByXAndY(location.getX(), location.getY()))
                .collect(Collectors.toList());

        Defect newDefect = new Defect(defectDto.getType(), vehicle, locations, imageService.saveImage(file, defectDto.getLocations()));
        return modelMapper.map(defectRepository.save(newDefect), DefectDto.class);
    }

    /**
     * Updates an existing Defect entity with the given DefectDto object and image file. The Defect is identified by the type and vehicle registration plate in the DefectDto object.
     * @param defectDto the DefectDto object containing the updated information
     * @param file the image file to be associated with the Defect entity
     * @return the updated DefectDto object
     * @throws IOException if there is an error with reading or writing the image file
     * @throws SQLException if there is an error with the SQL database operation
     * @throws EntityNotFoundException if there is no Defect entity matching the type and vehicle registration plate in the DefectDto object
     */
    @Override
    public DefectDto update(DefectDto defectDto, MultipartFile file)  throws IOException, SQLException {
            Vehicle vehicle = vehicleService.findVehicleByRegistrationPlate(defectDto.getVehicle().getRegistrationPlate());
            Defect defect = defectRepository.getDefectsByTypeAndVehicle(defectDto.getType(), vehicle);
            if (defect == null) {
                    throw new EntityNotFoundException("Operation failed!! No such defect is recorded for the vehicle with the registration plate "+defectDto.getVehicle().getRegistrationPlate());
            }

            List<Location> locations = defectDto.getLocations().stream()
                    .map(location -> locationService.findLocationByXAndY(location.getX(), location.getY()))
                    .collect(Collectors.toList());

            defect.setLocations(locations);
            defect.setImage(imageService.saveImage(file, defectDto.getLocations()));
            return modelMapper.map(defectRepository.save(defect), DefectDto.class);
    }
}
