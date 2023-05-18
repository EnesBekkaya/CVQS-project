package com.cvqs.terminalservice.controller;

import com.cvqs.terminalservice.dto.SectionDto;
import com.cvqs.terminalservice.model.Section;
import com.cvqs.terminalservice.service.abstracts.SectionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
/**
 * SectionController sınıfı,Section servisinin isteklerini karşılamak için kullanılır.
 *
 * @author Enes Bekkaya
 * @since  18.02.2023
 */
@RestController
@RequestMapping("/sections")
@RequiredArgsConstructor
public class SectionController {
    private final SectionService sectionService;
    /**
     * Verilen bir Section listesini kaydetmek için http post metodu kullanılarak çağrı yapılır.
     *
     * @param sections Kaydedilecek Section listesi
     * @return Kaydedilen Section listesi
     */
    @PostMapping("/save")
    public ResponseEntity<List<Section>>saveSection(@RequestBody @Valid List<Section> sections){
        return ResponseEntity.ok(sectionService.saveSection(sections));
    }
    /**
     * Bütün Section'ların listesini döndürmek için http get metodu kullanılarak çağrı yapılır.
     *
     * @return Bütün Section'ların listesi
     */
    @GetMapping("/getAll")
    public ResponseEntity<List<SectionDto>>getAllSections(){
        return ResponseEntity.ok(sectionService.getAllSection());
    }

}
