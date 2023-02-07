package com.cvqs.terminalservice.service.concretes;

import com.cvqs.terminalservice.dto.TerminalDto;
import com.cvqs.terminalservice.model.Terminal;
import com.cvqs.terminalservice.repository.TerminalRepository;
import com.cvqs.terminalservice.service.abstracts.TerminalService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TerminalManager implements TerminalService {

    private final ModelMapper modelMapper;
    private final TerminalRepository terminalRepository;
    @Override
    public List<TerminalDto> getActiveTerminals(Boolean active) {

       List <Terminal> terminal=terminalRepository.findTerminalByActive(active);
       List<TerminalDto> terminalDtos=terminal.stream().map(terminal1 -> modelMapper.map(terminal1,TerminalDto.class)).collect(Collectors.toList());
       return terminalDtos;

    }
}
