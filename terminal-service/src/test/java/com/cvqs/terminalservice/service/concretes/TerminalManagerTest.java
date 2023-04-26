package com.cvqs.terminalservice.service.concretes;

import com.cvqs.terminalservice.dto.TerminalDto;
import com.cvqs.terminalservice.model.Section;
import com.cvqs.terminalservice.model.Terminal;
import com.cvqs.terminalservice.repository.TerminalRepository;
import com.cvqs.terminalservice.service.abstracts.SectionService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class TerminalManagerTest {
    private TerminalManager terminalManager;
    private  TerminalRepository terminalRepository;
    private  SectionService sectionService;
    private  ModelMapper modelMapper;


    @BeforeEach
    public void setUp() {
        terminalRepository = Mockito.mock(TerminalRepository.class);
        sectionService = Mockito.mock(SectionService.class);
        modelMapper = Mockito.mock(ModelMapper.class);
        terminalManager = new TerminalManager(modelMapper,terminalRepository,sectionService);
    }

    @DisplayName("should return TerminalDto list")
    @Test
    void shouldReturnTerminalDtoList(){
        Terminal terminal=new Terminal();
        terminal.setName("testname1");
        terminal.setActive(true);
        Terminal terminal2=new Terminal();
        terminal2.setName("testname2");
        terminal2.setActive(true);

        List<Terminal> terminals=new ArrayList<>(Arrays.asList(terminal,terminal2));
        List<TerminalDto> expectedResult = terminals.stream()
                .map(terminal1 -> modelMapper.map(terminal1, TerminalDto.class))
                .collect(Collectors.toList());

        Mockito.when(terminalRepository.findTerminalByActive(true)).thenReturn(terminals);



        List<TerminalDto> result=terminalManager.getActiveTerminals(true);

        Assertions.assertEquals(expectedResult,result);
        Mockito.verify(terminalRepository).findTerminalByActive(true);
    }

    @DisplayName("should return TerminalDto list")
    @Test
    void shouldReturnTerminalDtoListWithPage(){

        Boolean active=true;
        int pageSize=2;
        int page=0;
        Terminal terminal=new Terminal();
        terminal.setName("testname1");
        terminal.setActive(true);
        Terminal terminal2=new Terminal();
        terminal2.setName("testname2");
        terminal2.setActive(true);
        Page<Terminal> terminalsPage = new PageImpl<>(Arrays.asList(terminal,terminal2));

        Page<TerminalDto> expectedResult = terminalsPage.map(terminal1 -> modelMapper.map(terminal1, TerminalDto.class));

        Mockito.when(terminalRepository.findTerminalByActive(Mockito.anyBoolean(), Mockito.any(Pageable.class))).thenReturn(terminalsPage);

        Page<TerminalDto> result = terminalManager.pagination(active,pageSize , page);

        Assertions.assertEquals(expectedResult,result);

        Mockito.verify(terminalRepository).findTerminalByActive(active, PageRequest.of(page, pageSize));

    }

    @DisplayName("should Find Terminal By SectionName And Return TerminalDto List")
    @Test
    void shouldFindTerminalBySectionNameAndReturnTerminalDtoList(){
        Section section1=new Section();
        section1.setName("testname");

        Terminal terminal=new Terminal();
        terminal.setName("testname1");
        terminal.setActive(true);
        Terminal terminal2=new Terminal();
        terminal2.setName("testname2");
        terminal2.setActive(true);

        List<Terminal> terminals=new ArrayList<>(Arrays.asList(terminal,terminal2));

        List<TerminalDto> expectedResult = terminals.stream()
                .map(terminal1 -> modelMapper.map(terminal1, TerminalDto.class))
                .collect(Collectors.toList());
        Mockito.when(sectionService.findSectionByName(section1.getName())).thenReturn(section1);
        Mockito.when(terminalRepository.findTerminalBySections(section1)).thenReturn(terminals);

        List<TerminalDto> result=terminalManager.findTerminalBySection(section1.getName());

        Assertions.assertEquals(expectedResult,result);
        Mockito.verify(sectionService).findSectionByName(section1.getName());
        Mockito.verify(terminalRepository).findTerminalBySections(section1);

    }
    @DisplayName("should Save Terminal By TerminalDto When Terminal Not Null")
    @Test
    void shouldSaveTerminalByTerminalDtoWhenTerminalNotNull(){
        Section section1=new Section();
        section1.setName("testname");
        Section section2=new Section();
        section2.setName("testname2");
        List<Section> sections = new ArrayList<>(Arrays.asList(section1,section2));

        TerminalDto terminalDto=new TerminalDto();
        terminalDto.setName("testDtoName");
        terminalDto.setActive(true);
        terminalDto.setSections(sections);

        Terminal terminal=new Terminal();
        terminal.setName("testname1");
        terminal.setActive(true);

        TerminalDto expectedResult = modelMapper.map(terminal,TerminalDto.class);

        Mockito.when(sectionService.saveSection(sections)).thenReturn(sections);
        Mockito.when(terminalRepository.findTerminalByName(terminalDto.getName())).thenReturn(terminal);
        Mockito.when(terminalRepository.save(terminal)).thenReturn(terminal);

        TerminalDto result=terminalManager.SaveTerminal(terminalDto);
        Assertions.assertEquals(expectedResult,result);

        Mockito.verify(sectionService).saveSection(sections);
        Mockito.verify(terminalRepository).findTerminalByName(terminalDto.getName());
        Mockito.verify(terminalRepository).save(terminal);
    }

    @DisplayName("should Save Terminal By TerminalDto When Terminal  Null")
    @Test
    void shouldSaveTerminalByTerminalDtoWhenTerminalNull(){
        Section section1=new Section();
        section1.setName("testname");
        Section section2=new Section();
        section2.setName("testname2");
        List<Section> sections = new ArrayList<>(Arrays.asList(section1,section2));

        TerminalDto terminalDto=new TerminalDto();
        terminalDto.setName("testDtoName");
        terminalDto.setActive(true);
        terminalDto.setSections(sections);



        Terminal terminal=new Terminal();
        terminal.setName("testname1");
        terminal.setActive(true);

        TerminalDto expectedResult = modelMapper.map(terminal,TerminalDto.class);

        Mockito.when(sectionService.saveSection(sections)).thenReturn(sections);
        Mockito.when(terminalRepository.findTerminalByName(terminalDto.getName())).thenReturn(null);
        Mockito.when(terminalRepository.save(terminal)).thenReturn(terminal);

        TerminalDto result=terminalManager.SaveTerminal(terminalDto);
        Assertions.assertEquals(expectedResult,result);

        Mockito.verify(sectionService).saveSection(sections);
        Mockito.verify(terminalRepository).findTerminalByName(terminalDto.getName());
    }


}