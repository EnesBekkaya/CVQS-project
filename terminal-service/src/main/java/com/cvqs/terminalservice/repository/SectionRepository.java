package com.cvqs.terminalservice.repository;

import com.cvqs.terminalservice.model.Section;
import org.springframework.data.jpa.repository.JpaRepository;
public interface SectionRepository extends JpaRepository<Section,String> {
    Section findSectionByName(String name);
}
