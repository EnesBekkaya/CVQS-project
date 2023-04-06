package com.cvqs.terminalservice.service.concretes;

import com.cvqs.terminalservice.dto.SectionDto;
import com.cvqs.terminalservice.exception.EntityNotFoundException;
import com.cvqs.terminalservice.model.Section;
import com.cvqs.terminalservice.repository.SectionRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.internal.verification.Times;
import org.modelmapper.ModelMapper;

import java.util.*;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

class SectionManagerTest {
    private SectionManager sectionManager;
    private SectionRepository sectionRepository;
    private ModelMapper modelMapper;

    @BeforeEach
    public void setUp() {
        sectionRepository = Mockito.mock(SectionRepository.class);
        modelMapper = Mockito.mock(ModelMapper.class);
        sectionManager = new SectionManager(sectionRepository,modelMapper);
    }


    @DisplayName("should return sectionDto list")
    @Test
    void shouldReturnSectionDtoList(){
        Section section1=new Section();
        section1.setName("testname");
        Section section2=new Section();
        section2.setName("testname2");
        Section section3=new Section();
        section3.setName("testname3");

        List<Section> sections = new ArrayList<>(Arrays.asList(section1,section2,section3));
        List<SectionDto> expectedResult = sections.stream()
                .map(section -> modelMapper.map(section, SectionDto.class))
                .collect(Collectors.toList());

        Mockito.when(sectionRepository.findAll()).thenReturn(sections);



        List<SectionDto> result=sectionManager.getAllSection();

        Assertions.assertEquals(expectedResult,result);
        Mockito.verify(sectionRepository).findAll();
    }
    @DisplayName("should Find Section By Name And Return Section")
    @Test
    void shouldFindSectionByNameAndReturnSection(){
        Section section1=new Section();
        section1.setName("testname");
        Section expectedResult=section1;
        Mockito.when(sectionRepository.findSectionByName(section1.getName())).thenReturn(section1);

        Section result=sectionManager.findSectionByName(section1.getName());

        Assertions.assertEquals(expectedResult,result);
        Mockito.verify(sectionRepository).findSectionByName(section1.getName());
    }
    @DisplayName("should Throw Entity Not Found Exception when SectionName Does Not Exist")
    @Test
    void shouldThrowEntityNotFoundException_whenSectionNameDoesNotExist(){
        Section section1=new Section();
        section1.setName("testname");
        Mockito.when(sectionRepository.findSectionByName(section1.getName())).thenReturn(null);

        EntityNotFoundException exception = null;
        try {
            sectionManager.findSectionByName(section1.getName());
        } catch (EntityNotFoundException e) {
            exception = e;
        }

        Assertions.assertNotNull(exception);
        Mockito.verify(sectionRepository).findSectionByName(section1.getName());
    }

}