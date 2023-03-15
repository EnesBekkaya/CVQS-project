package com.cvqs.defectsaveservice.service.concretes;

import com.cvqs.defectsaveservice.dto.VehichleDto;
import com.cvqs.defectsaveservice.exception.EntityNotFoundException;
import com.cvqs.defectsaveservice.model.Vehichle;
import com.cvqs.defectsaveservice.repository.VehichleRepository;
import com.cvqs.defectsaveservice.service.abstracts.VehichleService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;
@Service
@RequiredArgsConstructor
public class VehichleManager implements VehichleService {
    private final VehichleRepository vehichleRepository;
    private final ModelMapper modelMapper;
    private static final Logger LOGGER= LoggerFactory.getLogger(DefectManager.class);

    @Override
    public VehichleDto save(VehichleDto vehichleDto) {
        Vehichle vehichle=modelMapper.map(vehichleDto,Vehichle.class);
        return modelMapper.map(vehichleRepository.save(vehichle), VehichleDto.class);
    }

    @Override
    public List<VehichleDto> getAll() {
        List <Vehichle> vehichles=vehichleRepository.findAll();
        List<VehichleDto> vehichleDtos=vehichles.stream().map(defect1 -> modelMapper.map(defect1,VehichleDto.class)).collect(Collectors.toList());
        return vehichleDtos;
    }

    @Override
    public Vehichle findVehichleByRegistrationPlate(String registrationPlate) {
        Vehichle vehichle=vehichleRepository.findVehichleByRegistrationPlate(registrationPlate);
        if(vehichle==null) {
            LOGGER.error("Plakaya ait Araç bulunamadı");
            throw new EntityNotFoundException("Plakaya ait Araç bulunamadı");
        }
        return vehichle;
    }
}
