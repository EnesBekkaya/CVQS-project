package com.cvqs.terminalservice.controller;

import com.cvqs.terminalservice.dto.SectionDto;
import com.cvqs.terminalservice.model.Section;
import com.cvqs.terminalservice.service.abstracts.SectionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/sections")
@RequiredArgsConstructor
public class SectionController {
    private final SectionService sectionService;
    @PostMapping("/save")
    public ResponseEntity<List<Section>>saveSection(@RequestBody List<Section> sections){
        return ResponseEntity.ok(sectionService.saveSection(sections));
    }
    @GetMapping("/getAll")
    public ResponseEntity<List<SectionDto>>getAllSections(){
        return ResponseEntity.ok(sectionService.getAllSection());
    }

}
