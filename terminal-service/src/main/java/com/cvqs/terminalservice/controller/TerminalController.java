package com.cvqs.terminalservice.controller;

import com.cvqs.terminalservice.dto.TerminalDto;
import com.cvqs.terminalservice.service.abstracts.TerminalService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
/**
 * The TerminalController class is used to handle requests for the Terminal service.
 *
 * @author Enes Bekkaya
 * @since  18.02.2023
 */
@RestController
@RequestMapping("/terminals")
@RequiredArgsConstructor
public class TerminalController {
    private final TerminalService terminalService;
    /**
     * Sends an HTTP GET request to retrieve the list of active terminals.
     *
     * @return The list of active terminals.
     */
    @GetMapping("/active")
    public ResponseEntity<List<TerminalDto>>getActiveTerminals(){
        return ResponseEntity.ok(terminalService.getActiveTerminals(true));

    }
    /**
     * Makes an HTTP GET request with pagination to get a list of active terminals.
     *
     * @param pageSize the size of a page
     * @param page the page number
     * @return a paginated list of active terminals
     */
    @GetMapping("/page")
    public ResponseEntity<Page<TerminalDto>>getActiveTerminalsWithPage(@RequestParam int pageSize, @RequestParam int page ){
        return ResponseEntity.ok(terminalService.pagination(true,pageSize,page));

    }
    /**
     * Makes an http get request to get the list of terminals according to the specified section.
     * @param sectionName String name of the section
     * @return the list of terminals belonging to the specified section
     */
    @GetMapping("/filter")
    public ResponseEntity<List<TerminalDto>>getBySection(@RequestParam String sectionName ){
        return ResponseEntity.ok(terminalService.findTerminalBySection(sectionName));

    }

    /**
     * Used to create a new Terminal record or update the sections of an existing Terminal by making a call using the HTTP POST method.
     * @param terminalDto The data to be saved for the Terminal
     * @return The information of the saved Terminal
     */
    @PostMapping("/save")
    public ResponseEntity<TerminalDto> saveTerminal(@RequestBody @Valid TerminalDto terminalDto){
        return ResponseEntity.ok(terminalService.SaveTerminal(terminalDto));
    }

    /**
     * Makes an HTTP GET request to get the list of all terminals sorted by their dates.
     *
     * @return List of terminals sorted by date.
     */
    @GetMapping("/sort")
    public ResponseEntity<List<TerminalDto>> getTerminalSortedByDate(){
        return ResponseEntity.ok(terminalService.getTerminalSortedByDate());
    }

}
