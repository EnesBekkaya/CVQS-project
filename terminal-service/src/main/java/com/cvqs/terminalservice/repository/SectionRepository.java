package com.cvqs.terminalservice.repository;

import com.cvqs.terminalservice.model.Section;
import org.springframework.data.jpa.repository.JpaRepository;
/**
 * SectionRepository is an interface used for performing database operations on Section objects.
 *
 * @author Enes Bekkaya
 * @since  18.02.2023
 */
public interface SectionRepository extends JpaRepository<Section,String> {
    /**
     * Finds and returns the section object with the given name from the database.
     * @param name Name of the section.
     * @return Section object with the given name.
     */
    Section findSectionByName(String name);

}
