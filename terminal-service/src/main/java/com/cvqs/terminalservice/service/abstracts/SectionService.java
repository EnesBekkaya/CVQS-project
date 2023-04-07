package com.cvqs.terminalservice.service.abstracts;

import com.cvqs.terminalservice.dto.SectionDto;
import com.cvqs.terminalservice.model.Section;

import java.util.List;
/**
 * SectionService arayüzü, Section nesneleriyle ilgili işlemleri gerçekleştirir
 *
 * @author Enes Bekkaya
 * @since  18.02.2023
 */
public interface SectionService {
    /**
     * Verilen section listesini kaydeder.
     *
     * @param sections Kaydedilecek section listesi
     * @return Kaydedilen section listesi
     */
    List<Section> saveSection(List<Section> sections);
    /**
     * Tüm section'ları döndürür.
     *
     * @return Tüm section'lar
     */
    List<SectionDto> getAllSection();
    /**
     * Verilen isimdeki section'ı döndürür.
     *
     * @param sectionName Aranacak section'ın ismi
     * @return Verilen isimdeki section
     */
    Section findSectionByName(String sectionName);
}