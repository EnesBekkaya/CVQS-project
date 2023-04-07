package com.cvqs.terminalservice.repository;

import com.cvqs.terminalservice.model.Section;
import org.springframework.data.jpa.repository.JpaRepository;
/**
 * SectionRepository, Section nesnelerinin veritabanı işlemlerini yapmak için kullanılan arayüz.
 *
 * @author Enes Bekkaya
 * @since  18.02.2023
 */
public interface SectionRepository extends JpaRepository<Section,String> {
    /**
     * İlgili isme sahip bir bölümü veritabanından bulur ve döndürür.
     * @param name Bölüm ismi.
     * @return İlgili isme sahip bölüm nesnesi.
     */
    Section findSectionByName(String name);

}
