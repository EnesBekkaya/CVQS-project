package com.cvqs.terminalservice.service.concretes;

import com.cvqs.terminalservice.controller.TerminalController;
import com.cvqs.terminalservice.dto.SectionDto;
import com.cvqs.terminalservice.exception.EntityNotFoundException;
import com.cvqs.terminalservice.model.Section;
import com.cvqs.terminalservice.repository.SectionRepository;
import com.cvqs.terminalservice.service.abstracts.SectionService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SectionManager implements SectionService {
    private static final Logger LOGGER= LoggerFactory.getLogger(TerminalController.class);

    private final SectionRepository sectionRepository;
    private final ModelMapper modelMapper;
    @Override
    public List<Section> saveSection(List<Section> sections) {
        List<Section> newSection=new ArrayList<>();
        sections.forEach(section -> {
            Section savedSection=this.findSectionByName(section.getName());
            if(savedSection!=null){
                newSection.add(savedSection);
            }
            else{
               newSection.add(sectionRepository.save(section));

            }
        });
      return newSection;
    }
    @Override
    public List<SectionDto> getAllSection() {

        return sectionRepository.findAll().stream().map(section -> modelMapper.map(section,SectionDto.class)).collect(Collectors.toList());
    }
    @Override
    public Section findSectionByName(String sectionName) {
        Section section=sectionRepository.findSectionByName(sectionName);
        if(section==null) {
            LOGGER.error("Bu isimde bir terminal bulunamadı");
            throw new EntityNotFoundException("Terminal bulunamadı");
        }
        else
            return section;
    }
}
