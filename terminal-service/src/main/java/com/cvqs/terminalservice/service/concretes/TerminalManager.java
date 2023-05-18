package com.cvqs.terminalservice.service.concretes;

import com.cvqs.terminalservice.dto.TerminalDto;
import com.cvqs.terminalservice.model.Section;
import com.cvqs.terminalservice.model.Terminal;
import com.cvqs.terminalservice.repository.TerminalRepository;
import com.cvqs.terminalservice.service.abstracts.SectionService;
import com.cvqs.terminalservice.service.abstracts.TerminalService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
/**
 * The TerminalManager class is derived from the TerminalService interface and manages terminal operations.
 * This class uses TerminalRepository objects for database operations and SectionService objects for section operations.
 *
 *  @author Enes Bekkaya
 *  @since  18.02.2023
 */
@Service
@RequiredArgsConstructor
public class TerminalManager implements TerminalService {
    private final ModelMapper modelMapper;
    private final TerminalRepository terminalRepository;
    private final SectionService sectionService;

    /**
     * gets all terminals with the specified active status.
     * @param active the active status of the terminals to retrieve
     * @return a list of TerminalDto objects filtered based on the active parameter
     */
    @Override
    public List<TerminalDto> getActiveTerminals(Boolean active) {

       List <Terminal> terminals=terminalRepository.findTerminalByActive(active);
       List<TerminalDto> terminalDtos=terminals.stream().map(terminal1 -> modelMapper.map(terminal1,TerminalDto.class)).collect(Collectors.toList());
       return terminalDtos;
    }
    /**
     * Returns a page of active terminals with the specified page size and page number.
     *
     * @param active the status of the terminals to be get
     * @param pageSize the size of the page
     * @param page the page number
     * @return a page of active terminals with the specified page size and page number
     */
    @Override
    public Page<TerminalDto> pagination(Boolean active,int pageSize, int page) {
        Pageable pageable= PageRequest.of(page,pageSize);

        Page<Terminal> terminals = terminalRepository.findTerminalByActive(active,pageable);
        Page<TerminalDto> terminalDtos = terminals.map(terminal -> modelMapper.map(terminal, TerminalDto.class));
        return terminalDtos;
    }

    /**
     * Returns the terminals belonging to the given section name.
     * @param sectionName The name of the section where the terminals will be get
     * @return A list of terminals belonging to the section
     */
    @Override
    public List<TerminalDto> findTerminalBySection(String sectionName) {
        Section section=sectionService.findSectionByName(sectionName);
        List <Terminal> terminals=terminalRepository.findTerminalBySections(section);
        List<TerminalDto> terminalDtos=terminals.stream().map(terminal1 -> modelMapper.map(terminal1,TerminalDto.class)).collect(Collectors.toList());
        return terminalDtos;
    }


    /**
     * Creates a new terminal or updates an existing one.
     * @param terminalDto DTO object containing terminal data
     * @return DTO object containing the saved or updated terminal data
     */
    @Override
    public TerminalDto SaveTerminal(TerminalDto terminalDto) {
        List<Section> sections= sectionService.saveSection(terminalDto.getSections());
        Terminal terminal=terminalRepository.findTerminalByName(terminalDto.getName());
        if(terminal!=null){
            terminal.setSections(sections);
            return modelMapper.map(terminalRepository.save(terminal),TerminalDto.class);
        }
        else{
            Terminal newTerminal=new Terminal();
            newTerminal.setName(terminalDto.getName());
            newTerminal.setActive(true);
            newTerminal.setDeleted(false);
            newTerminal.setCreateDate(new Date());
            newTerminal.setSections(sections);
            return modelMapper.map(terminalRepository.save(newTerminal),TerminalDto.class);

        }

    }

    /**
     * This method returns a list of active terminals sorted by date.
     * @return List of TerminalDto objects sorted by date
     */
    public List<TerminalDto> getTerminalSortedByDate() {
        List<TerminalDto>terminals=getActiveTerminals(true);
        Comparator<TerminalDto> dateComparator = new Comparator<TerminalDto>() {
            @Override
            public int compare(TerminalDto t1, TerminalDto t2) {
                return t2.getCreateDate().compareTo(t1.getCreateDate());            }

        };

        Collections.sort(terminals, dateComparator);

        return terminals;
    }
}
