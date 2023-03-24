package com.cvqs.defectsaveservice.service.concretes;

import com.cvqs.defectsaveservice.dto.DefectDto;
import com.cvqs.defectsaveservice.exception.EntityNotFoundException;
import com.cvqs.defectsaveservice.model.Defect;
import com.cvqs.defectsaveservice.model.Image;
import com.cvqs.defectsaveservice.model.Location;
import com.cvqs.defectsaveservice.model.Vehichle;
import com.cvqs.defectsaveservice.repository.DefectRepository;
import com.cvqs.defectsaveservice.service.abstracts.DefectService;
import com.cvqs.defectsaveservice.service.abstracts.ImageService;
import com.cvqs.defectsaveservice.service.abstracts.LocationService;
import com.cvqs.defectsaveservice.service.abstracts.VehichleService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class    DefectManager implements DefectService {
    private final DefectRepository defectRepository;
    private final LocationService locationService;
    private final VehichleService vehichleService;
    private final ImageService imageService;
    private final ModelMapper modelMapper;
    private static final Logger LOGGER= LoggerFactory.getLogger(DefectManager.class);
    @Override
    public DefectDto save(DefectDto defectDto, MultipartFile file) throws IOException, SQLException {
        Vehichle vehichle=vehichleService.findVehichleByRegistrationPlate(defectDto.getVehichle().getRegistrationPlate());

        List<Location> locations=new ArrayList<>();
        Defect newDefect=new Defect();

        Defect defect=defectRepository.getDefectsByTypeAndVehichle(defectDto.getType(),vehichle);

        if(defect==null) {
            defectDto.getLocations().forEach(location -> {
                Location location1=locationService.findLocationByXAndY(location.getX(),location.getY());
                locations.add(location1);
            });
            newDefect.setType(defectDto.getType());
            newDefect.setVehichle(vehichle);
            newDefect.setLocations(locations);
            newDefect.setImage(imageService.saveImage(file,defectDto.getLocations()));
            return modelMapper.map(defectRepository.save(newDefect), DefectDto.class);
        }
        else {
            defectDto.getLocations().forEach(location -> {
                Location location1=locationService.findLocationByXAndY(location.getX(),location.getY());
                locations.add(location1);
            });
            defect.setLocations(locations);
            return modelMapper.map(defectRepository.save(defect),DefectDto.class);
        }
    }
    @Override
    public List<DefectDto> getAll() {
        List <Defect> defects=defectRepository.findAll();
        return defects.stream().map(defect1 -> modelMapper.map(defect1,DefectDto.class)).collect(Collectors.toList());
    }

    @Override
    public List<DefectDto> findByRegistrationPlate(String registrationPlate) {
        List<Defect> defects=defectRepository.findByRegistrationPlate(registrationPlate);

        if(defects.isEmpty()) {
            LOGGER.error("Araca kayıtlı hata bulunamadı");
            throw new EntityNotFoundException("Araca kayıtlı hata bulunamadı");
        }
        return defects.stream().map(defect -> modelMapper.map(defect, DefectDto.class)).collect(Collectors.toList());
    }

    @Override
    public byte[] getDefectImage(String registrationPlate, String defectType) throws SQLException {
        Defect defect=defectRepository.findDefectByregistrationPlateAndType(registrationPlate,defectType);
        Image image=imageService.getImage(defect.getImage());
        byte[] imageData = image.getData().getBytes(1, (int) image.getData().length());
        return imageData;
    }

    public List<DefectDto> getDefectSorted(Integer pageNo, Integer pageSize, String sortBy) {
        Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));

        Page<Defect> defectDtos=defectRepository.findAll(paging);
        return  defectDtos.getContent().stream().map(defect -> modelMapper.map(defect, DefectDto.class)).collect(Collectors.toList());
    }


}
