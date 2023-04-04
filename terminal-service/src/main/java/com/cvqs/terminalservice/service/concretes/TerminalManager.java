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

@Service
@RequiredArgsConstructor
public class TerminalManager implements TerminalService {
    private final ModelMapper modelMapper;
    private final TerminalRepository terminalRepository;
    private final SectionService sectionService;
    @Override
    public List<TerminalDto> getActiveTerminals(Boolean active) {

       List <Terminal> terminals=terminalRepository.findTerminalByActive(active);
       List<TerminalDto> terminalDtos=terminals.stream().map(terminal1 -> modelMapper.map(terminal1,TerminalDto.class)).collect(Collectors.toList());
       return terminalDtos;
    }
    @Override
    public Page<TerminalDto> pagination(Boolean active,int pageSize, int page) {
        Pageable pageable= PageRequest.of(page,pageSize);

        Page<Terminal> terminals = terminalRepository.findTerminalByActive(active,pageable);
        Page<TerminalDto> terminalDtos = terminals.map(terminal -> modelMapper.map(terminal, TerminalDto.class));
        return terminalDtos;
    }
    @Override
    public List<TerminalDto> findTerminalBySection(String sectionName) {
        Section section=sectionService.findSectionByName(sectionName);
        List <Terminal> terminals=terminalRepository.findTerminalBySections(section);
        List<TerminalDto> terminalDtos=terminals.stream().map(terminal1 -> modelMapper.map(terminal1,TerminalDto.class)).collect(Collectors.toList());
        return terminalDtos;
    }

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
