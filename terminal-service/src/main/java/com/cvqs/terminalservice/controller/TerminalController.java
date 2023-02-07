package com.cvqs.terminalservice.controller;

import com.cvqs.terminalservice.dto.TerminalDto;
import com.cvqs.terminalservice.service.abstracts.TerminalService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/terminals")
@RequiredArgsConstructor
public class TerminalController {
    private final TerminalService terminalService;

    @RequestMapping
    public ResponseEntity<List<TerminalDto>>getActiveTerminals(){
        return ResponseEntity.ok(terminalService.getActiveTerminals(true));

    }

}
