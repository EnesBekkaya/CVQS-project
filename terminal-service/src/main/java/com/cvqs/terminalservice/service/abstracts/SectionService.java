package com.cvqs.terminalservice.service.abstracts;

import com.cvqs.terminalservice.dto.SectionDto;
import com.cvqs.terminalservice.model.Section;

import java.util.List;

public interface SectionService {
    List<Section> saveSection(List<Section> sections);
    List<SectionDto> getAllSection();
}