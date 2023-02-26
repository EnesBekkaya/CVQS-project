package com.cvqs.defectsaveservice.service.concretes;

import com.cvqs.defectsaveservice.dto.DefectDto;
import com.cvqs.defectsaveservice.model.Defect;
import com.cvqs.defectsaveservice.model.Location;
import com.cvqs.defectsaveservice.model.Vehichle;
import com.cvqs.defectsaveservice.repository.DefectRepository;
import com.cvqs.defectsaveservice.service.abstracts.DefectService;
import com.cvqs.defectsaveservice.service.abstracts.LocationService;
import com.cvqs.defectsaveservice.service.abstracts.VehichleService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DefectManager implements DefectService {
    private final DefectRepository defectRepository;
    private final LocationService locationService;
    private final VehichleService vehichleService;
    private final ModelMapper modelMapper;
    @Override
    public DefectDto save(DefectDto defectDto) {
        Vehichle vehichle=vehichleService.findVehichleByRegistrationPlate(defectDto.getVehichle().getRegistrationPlate());
        List<Location> locations=new ArrayList<>();
        Location savedLocation=new Location();
        Defect newDefect=new Defect();

        Defect defect=defectRepository.getDefectsByTypeAndVehichle(defectDto.getType(),vehichle);
        if(defect==null) {
            defectDto.getLocation().forEach(location -> {
                savedLocation.setX(location.getX());
                savedLocation.setY(location.getY());
                savedLocation.setDefect(newDefect);
                locations.add(savedLocation);
            });
            newDefect.setType(defectDto.getType());
            newDefect.setVehichle(vehichle);
            newDefect.setLocations(locations);
            return modelMapper.map(defectRepository.save(newDefect), DefectDto.class);
        }
        else {
            locationService.save(defectDto.getLocation(),defect);
            return modelMapper.map(defect,DefectDto.class);
        }
    }
    @Override
    public List<DefectDto> getAll() {
        List <Defect> defects=defectRepository.findAll();
        List<DefectDto> defectDtos=defects.stream().map(defect1 -> modelMapper.map(defect1,DefectDto.class)).collect(Collectors.toList());
        return defectDtos;
    }

}
