package com.cvqs.terminalservice.service.abstracts;

import com.cvqs.terminalservice.dto.TerminalDto;

import java.util.List;

public interface TerminalService {
    public List<TerminalDto> getActiveTerminals(Boolean active);
}
