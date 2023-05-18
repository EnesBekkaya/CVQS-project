package com.cvqs.terminalservice.service.abstracts;

import com.cvqs.terminalservice.dto.SectionDto;
import com.cvqs.terminalservice.model.Section;

import java.util.List;
/**
 *  SectionService interface performs operations related to Section objects.
 *
 * @author Enes Bekkaya
 * @since  18.02.2023
 */
public interface SectionService {
    /**
     * Saves the given list of sections.
     *
     * @param sections list of sections to be saved
     * @return the saved list of sections
     */
    List<Section> saveSection(List<Section> sections);
    /**
     * Returns all sections.
     *
     * @return All sections
     */
    List<SectionDto> getAllSection();
    /**
     * Returns the section with the given name.
     *
     * @param sectionName the name of the section to search for
     * @return the section with the given name
     */
    Section findSectionByName(String sectionName);
}