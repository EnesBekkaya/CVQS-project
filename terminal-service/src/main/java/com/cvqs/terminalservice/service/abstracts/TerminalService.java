package com.cvqs.terminalservice.service.abstracts;

import com.cvqs.terminalservice.dto.TerminalDto;
import org.springframework.data.domain.Page;

import java.util.List;

public interface TerminalService {
     List<TerminalDto> getActiveTerminals(Boolean active);
     Page<TerminalDto> pagination(Boolean active,int pageSize,int page);

}
