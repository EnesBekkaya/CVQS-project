package com.cvqs.terminalservice.controller;

import com.cvqs.terminalservice.dto.TerminalDto;
import com.cvqs.terminalservice.service.abstracts.TerminalService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
/**
 * TerminalController sınıfı,Terminal servisinin isteklerini karşılamak için kullanılır.
 *
 * @author Enes Bekkaya
 * @since  18.02.2023
 */
@RestController
@RequestMapping("/terminals")
@RequiredArgsConstructor
public class TerminalController {
    private static final Logger LOGGER= LoggerFactory.getLogger(TerminalController.class);
    private final TerminalService terminalService;
    /**
     * Aktif terminal listesini getirmek için http get metodu kullanılarak çağrı yapılır.
     *
     * @return  aktif terminallerin listesi
     */
    @GetMapping("/active")
    public ResponseEntity<List<TerminalDto>>getActiveTerminals(){
        LOGGER.info("Incoming request for /terminals/active");
        return ResponseEntity.ok(terminalService.getActiveTerminals(true));

    }
    /**
     * Sayfalama özelliği ile aktif terminal listesini getirmek içi http get metodu kullanılarak çağrı yapılır.
     *
     * @param pageSize int sayfa boyutu
     * @param page int sayfa numarası
     * @return  aktif terminallerin sayfalı listesi
     */
    @GetMapping("/page")
    public ResponseEntity<Page<TerminalDto>>getActiveTerminalsWithPage(@RequestParam int pageSize, @RequestParam int page ){
        LOGGER.info("Incoming request for /terminals/page");

        return ResponseEntity.ok(terminalService.pagination(true,pageSize,page));

    }
    /**
     * Belirtilen bölüme göre terminal listesini getirmek için http get metodu kullanılarak çağrı yapılır.
     *
     * @param sectionName String bölüm adı
     * @return belirtilen bölüme ait terminallerin listesi
     */
    @GetMapping("/filter")
    public ResponseEntity<List<TerminalDto>>getBySection(@RequestParam String sectionName ){
        LOGGER.info("Incoming request for /terminals/filter");

        return ResponseEntity.ok(terminalService.findTerminalBySection(sectionName));

    }

    /**
     * Yeni bir Terminal kaydı oluşturmak veya mevcut bir Terminal'in bölümlerini güncellemek için
     * http post metodu kullanılarak çağrı yapılır.
     * @param terminalDto Kaydedilecek Terminal verileri
     * @return Kaydedilen Terminal'in bilgileri
     */
    @PostMapping("/save")
    public ResponseEntity<TerminalDto> saveTerminal(@RequestBody @Valid TerminalDto terminalDto){
        LOGGER.info("Incoming request for /terminals/save");

        return ResponseEntity.ok(terminalService.SaveTerminal(terminalDto));
    }

    /**
     * Tüm terminalleri tarihlerine göre sıralayarak döndürmek için http get metodu kullanılarak çağrı yapılır.
     * @return Tarihe göre sıralanmış Terminal listesi
     */
    @GetMapping("/sort")
    public ResponseEntity<List<TerminalDto>> getTerminalSortedByDate(){
        LOGGER.info("Incoming request for /terminals/sort");

        return ResponseEntity.ok(terminalService.getTerminalSortedByDate());
    }

}
