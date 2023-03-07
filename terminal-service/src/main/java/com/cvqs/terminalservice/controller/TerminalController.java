package com.cvqs.terminalservice.controller;

import com.cvqs.terminalservice.dto.TerminalDto;
import com.cvqs.terminalservice.service.abstracts.TerminalService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/terminals")
@RequiredArgsConstructor
public class TerminalController {
    private final TerminalService terminalService;

    @GetMapping("/active")
    public ResponseEntity<List<TerminalDto>>getActiveTerminals(){
        return ResponseEntity.ok(terminalService.getActiveTerminals(true));

    }

    @GetMapping("/page")
    public ResponseEntity<Page<TerminalDto>>getActiveTerminalsWithPage(@RequestParam int pageSize, @RequestParam int page ){
        return ResponseEntity.ok(terminalService.pagination(true,pageSize,page));

    }
    @GetMapping("/filter")
    public ResponseEntity<List<TerminalDto>>getBySection(@RequestParam String sectionName ){
        return ResponseEntity.ok(terminalService.findTerminalBySection(sectionName));

    }
    @PostMapping("/save")
    public ResponseEntity<TerminalDto> saveTerminal(@RequestBody TerminalDto terminalDto){
        return ResponseEntity.ok(terminalService.SaveTerminal(terminalDto));
    }

}
