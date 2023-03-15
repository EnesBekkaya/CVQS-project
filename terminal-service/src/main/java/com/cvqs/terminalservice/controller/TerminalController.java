package com.cvqs.terminalservice.controller;

import com.cvqs.terminalservice.dto.TerminalDto;
import com.cvqs.terminalservice.service.abstracts.TerminalService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/terminals")
@RequiredArgsConstructor
public class TerminalController {
    private static final Logger LOGGER= LoggerFactory.getLogger(TerminalController.class);
    private final TerminalService terminalService;

    @GetMapping("/active")
    public ResponseEntity<List<TerminalDto>>getActiveTerminals(){
        LOGGER.info("Incoming request for /terminals/active");
        return ResponseEntity.ok(terminalService.getActiveTerminals(true));

    }

    @GetMapping("/page")
    public ResponseEntity<Page<TerminalDto>>getActiveTerminalsWithPage(@RequestParam int pageSize, @RequestParam int page ){
        LOGGER.info("Incoming request for /terminals/page");

        return ResponseEntity.ok(terminalService.pagination(true,pageSize,page));

    }
    @GetMapping("/filter")
    public ResponseEntity<List<TerminalDto>>getBySection(@RequestParam String sectionName ){
        LOGGER.info("Incoming request for /terminals/filter");

        return ResponseEntity.ok(terminalService.findTerminalBySection(sectionName));

    }
    @PostMapping("/save")
    public ResponseEntity<TerminalDto> saveTerminal(@RequestBody TerminalDto terminalDto){
        LOGGER.info("Incoming request for /terminals/save");

        return ResponseEntity.ok(terminalService.SaveTerminal(terminalDto));
    }

}
