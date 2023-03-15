package com.cvqs.terminalservice.controller;

import com.cvqs.terminalservice.dto.SectionDto;
import com.cvqs.terminalservice.model.Section;
import com.cvqs.terminalservice.service.abstracts.SectionService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/sections")
@RequiredArgsConstructor
public class SectionController {
    private static final Logger LOGGER= LoggerFactory.getLogger(TerminalController.class);
    private final SectionService sectionService;
    @PostMapping("/save")
    public ResponseEntity<List<Section>>saveSection(@RequestBody List<Section> sections){
        LOGGER.info("Incoming request for /sections/save");

        return ResponseEntity.ok(sectionService.saveSection(sections));
    }
    @GetMapping("/getAll")
    public ResponseEntity<List<SectionDto>>getAllSections(){
        LOGGER.info("Incoming request for /sections/getAll");
        return ResponseEntity.ok(sectionService.getAllSection());
    }

}
