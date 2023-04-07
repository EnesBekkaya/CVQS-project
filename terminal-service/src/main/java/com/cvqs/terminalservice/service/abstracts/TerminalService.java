package com.cvqs.terminalservice.service.abstracts;

import com.cvqs.terminalservice.dto.TerminalDto;
import com.cvqs.terminalservice.model.Terminal;
import org.springframework.data.domain.Page;

import java.util.List;
/**
 * TerminalService arayüzü, Terminal nesneleriyle ilgili işlemleri gerçekleştirir
 *
 * @author Enes Bekkaya
 * @since  18.02.2023
 */
public interface TerminalService {
    /**
     * Verilen duruma göre aktif tüm terminalleri döndürür.
     *
     * @param active Terminallerin aktiflik durumunu belirten boolean değer
     * @return Terminallerin TerminalDto nesneleri olarak listesi
     */
     List<TerminalDto> getActiveTerminals(Boolean active);
    /**
     * Belirtilen sayfa boyutu ve sayfa numarasına göre sayfalama yaparak terminalleri döndürür.
     *
     * @param active Terminallerin aktiflik durumunu belirten boolean değer
     * @param pageSize Sayfa boyutu
     * @param page Sayfa numarası
     * @return Terminallerin TerminalDto nesneleri olarak sayfalanmış listesi
     */
     Page<TerminalDto> pagination(Boolean active,int pageSize,int page);
    /**
     * Belirtilen bölüm adına göre terminalleri döndürür.
     *
     * @param sectionName Bölüm adı
     * @return Belirtilen bölüme ait terminallerin TerminalDto nesneleri olarak listesi
     */

     List<TerminalDto> findTerminalBySection(String sectionName);
    /**
     * Belirtilen TerminalDto nesnesine göre terminalleri kaydeder ya da günceller.
     *
     * @param terminalDto Kaydedilecek ya da güncellenecek TerminalDto nesnesi
     * @return Kaydedilen ya da güncellenen TerminalDto nesnesi
     */
     TerminalDto SaveTerminal(TerminalDto terminalDto);
    /**
     * Terminalleri oluşturulma tarihine göre sıralı bir şekilde döndürür.
     *
     * @return Terminallerin TerminalDto nesneleri olarak oluşturulma tarihine göre sıralanmış listesi
     */
      List<TerminalDto> getTerminalSortedByDate();

}
