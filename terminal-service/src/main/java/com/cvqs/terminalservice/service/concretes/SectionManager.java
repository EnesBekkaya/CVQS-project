package com.cvqs.terminalservice.service.concretes;

import com.cvqs.terminalservice.dto.SectionDto;
import com.cvqs.terminalservice.exception.EntityNotFoundException;
import com.cvqs.terminalservice.model.Section;
import com.cvqs.terminalservice.repository.SectionRepository;
import com.cvqs.terminalservice.service.abstracts.SectionService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
/**
 * SectionManager class is derived from the SectionService interface and manages section operations.
 * This class uses SectionRepository objects for database operations.
 *
 *  @author Enes Bekkaya
 *  @since  18.02.2023
 */
@Service
@RequiredArgsConstructor
public class  SectionManager implements SectionService {

    private final SectionRepository sectionRepository;
    private final ModelMapper modelMapper;

    /**
     * For each section in the given section list, searches for a section in the database using its name and adds the matching record to a new section list.
     * If there is no matching record, saves the section to the database using sectionRepository and adds it to the new section list.
     * @param sections list of Section objects to be saved
     * @return list of saved Section objects
     */
    @Override
    public List<Section> saveSection(List<Section> sections) {
        List<Section> newSection=new ArrayList<>();
        sections.forEach(section -> {
            Section savedSection=sectionRepository.findSectionByName(section.getName());
            if(savedSection!=null){
                newSection.add(savedSection);
            }
            else{
               newSection.add(sectionRepository.save(section));

            }
        });
      return newSection;
    }

    /**
     * Returns all section records.
     * @return a list of SectionDto.
     */
    @Override
    public List<SectionDto> getAllSection() {

        return sectionRepository.findAll().stream().map(section -> modelMapper.map(section,SectionDto.class)).collect(Collectors.toList());
    }


    /**
     * Returns the Section object with the given name.
     *
     * @param sectionName The name of the Section as a String
     * @return The Section object with the given name
     * @throws EntityNotFoundException if the Section with the given name cannot be found
     */
    @Override
    public Section findSectionByName(String sectionName) {
        Section section=sectionRepository.findSectionByName(sectionName);
        if(section==null) {
            throw new EntityNotFoundException("Terminal not found.");
        }
        else
            return section;
    }
}
