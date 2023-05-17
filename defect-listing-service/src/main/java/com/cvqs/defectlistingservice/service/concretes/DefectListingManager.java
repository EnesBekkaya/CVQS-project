package com.cvqs.defectlistingservice.service.concretes;


import com.cvqs.defectlistingservice.dto.DefectDto;
import com.cvqs.defectlistingservice.exception.EntityNotFoundException;
import com.cvqs.defectlistingservice.model.Defect;
import com.cvqs.defectlistingservice.model.Image;
import com.cvqs.defectlistingservice.repository.DefectListingRepository;
import com.cvqs.defectlistingservice.service.abstracts.DefectListingService;
import com.cvqs.defectlistingservice.service.abstracts.ImageService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Defect listing class
 *
 * @author Enes Bekkaya
 * @since  12.02.2023
 */
@Service
@RequiredArgsConstructor
public class DefectListingManager implements DefectListingService {
    private final DefectListingRepository defectListingRepository;
    private final ModelMapper modelMapper;
    private final ImageService imageService;

    /**
     Method for getting all defects.
     @return A list of DefectDto containing all defects.
     */
    @Override
    public List<DefectDto> getAll() {
        List<Defect> defects = defectListingRepository.findAll();
        return defects.stream().map(defect1 -> modelMapper.map(defect1, DefectDto.class)).collect(Collectors.toList());
    }

    /**
     * Method that gets the list of damages for the given registration plate.
     *
     * @param registrationPlate the registration plate of the vehicle for which damages are being searched
     * @return a list of damages for the given registration plate
     * @throws EntityNotFoundException if no damages are found for the given registration plate
     */
    @Override
    public List<DefectDto> findByRegistrationPlate(String registrationPlate) {
        List<Defect> defects = defectListingRepository.findByRegistrationPlate(registrationPlate);

        if (defects.isEmpty()) {
            throw new EntityNotFoundException("No defect found for this vehicle.");
        }
        return defects.stream().map(defect -> modelMapper.map(defect, DefectDto.class)).collect(Collectors.toList());
    }

    /**
     * Method that gets the damage image as a byte array for the given registration plate and defect type.
     *
     * @param registrationPlate the registration plate of the vehicle for which the damage image is being searched
     * @param defectType the type of the damage for which the image is being searched
     * @return a byte array of the damage image
     * @throws SQLException if there is an error during the database operations
     */
    @Override
    public byte[] getDefectImage(String registrationPlate, String defectType) throws SQLException {
        Defect defect = defectListingRepository.findDefectByregistrationPlateAndType(registrationPlate, defectType);
        if(defect==null){

            throw new EntityNotFoundException("No defect records found for the vehicle with the license plate code "+registrationPlate);
        }
        Image image = imageService.getImage(defect.getImage());
        byte[] imageData = image.getData().getBytes(1, (int) image.getData().length());
        return imageData;
    }

    /**
     * Method that returns the defects in a paginated manner, sorted according to the specified page number, page size and sorting criteria.
     *
     * @param pageNo the page number to be returned
     * @param pageSize the number of defects to be returned per page
     * @param sortBy the sorting criteria for how the defects should be sorted
     * @return paginated sorted defect DTOs
     */
    public List<DefectDto> getDefectSorted(Integer pageNo, Integer pageSize, String sortBy) {
        Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));

        Page<Defect> defectDtos = defectListingRepository.findAll(paging);
        return defectDtos.getContent().stream().map(defect -> modelMapper.map(defect, DefectDto.class)).collect(Collectors.toList());
    }
}
