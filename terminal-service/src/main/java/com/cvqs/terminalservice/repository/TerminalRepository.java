package com.cvqs.terminalservice.repository;

import com.cvqs.terminalservice.model.Section;
import com.cvqs.terminalservice.model.Terminal;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
/**
 * TerminalRepository, Terminal nesnelerinin veritabanı işlemlerini yapmak için kullanılan arayüz.
 *
 * @author Enes Bekkaya
 * @since  18.02.2023
 */
public interface TerminalRepository extends JpaRepository<Terminal,String> {
    /**
     * Belirtilen durumda tüm terminalleri döndürür.
     *
     * @param active terminal durumu
     * @return belirtilen durumda tüm terminallerin listesi
     */
    List<Terminal>findTerminalByActive(Boolean active);
    /**
     * Belirtilen durumda  terminalleri sayfalandırarak döndürür.
     *
     * @param active terminal durumu (true: aktif, false: pasif)
     * @param pageable nesnesi
     * @return sayfalara bölünmüş terminal listesi
     */
    Page<Terminal> findTerminalByActive(Boolean active, Pageable pageable);
    /**
     * Belirtilen bölümdeki tüm terminalleri döndürür.
     *
     * @param section belirtilen bölüm
     * @return belirtilen bölümdeki terminallerin listesi
     */

    List<Terminal> findTerminalBySections(Section section);
    /**
     * Belirtilen isme sahip terminali döndürür.
     *
     * @param name terminal ismi
     * @return belirtilen isme sahip terminal
     */
    Terminal findTerminalByName(String name);

}
