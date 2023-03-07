package com.cvqs.usermanagementservice.controller;

import com.cvqs.usermanagementservice.dto.RoleDto;
import com.cvqs.usermanagementservice.service.abstracts.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/roles")
@RequiredArgsConstructor
public class RoleController {
    private final RoleService roleService;

    @PostMapping("/save")
    public ResponseEntity<RoleDto> saveRole(@RequestBody RoleDto roleDto){

        return ResponseEntity.ok(roleService.save(roleDto));
    }

}
