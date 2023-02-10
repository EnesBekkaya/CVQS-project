package com.cvqs.defectsaveservice.service.concretes;

import com.cvqs.defectsaveservice.dto.DefectDto;
import com.cvqs.defectsaveservice.model.Defect;
import com.cvqs.defectsaveservice.repository.DefectRepository;
import com.cvqs.defectsaveservice.service.abstracts.DefectService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DefectManager implements DefectService {
    private final DefectRepository defectRepository;
    private final ModelMapper modelMapper;
    @Override
    public DefectDto save(DefectDto defectDto) {
        Defect defect=modelMapper.map(defectDto,Defect.class);
        return modelMapper.map(defectRepository.save(defect), DefectDto.class);
    }

    @Override
    public List<DefectDto> getAll() {
        List <Defect> defects=defectRepository.findAll();
        List<DefectDto> defectDtos=defects.stream().map(defect1 -> modelMapper.map(defect1,DefectDto.class)).collect(Collectors.toList());
        return defectDtos;
    }
}
