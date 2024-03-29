package com.cvqs.usermanagementservice.controller;

import com.cvqs.usermanagementservice.dto.RoleDto;
import com.cvqs.usermanagementservice.service.abstracts.RoleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
/**
 * RoleController sınıfı,Role servisinin isteklerini karşılamak için kullanılır.
 *
 * @author Enes Bekkaya
 * @since  26.02.2023
 */
@RestController
@RequestMapping("/roles")
@RequiredArgsConstructor
public class RoleController {
    private final RoleService roleService;

    /**
     * Yeni bir rol eklemek için http post metodu kullanılarak çağrı yapılır.
     * @param roleDto Kaydedilecek Role'un DTO'su
     * @return Kaydedilen Role DTO'su
     */
    @PostMapping("/save")
    public ResponseEntity<RoleDto> saveRole(@RequestBody @Valid RoleDto roleDto){
        return ResponseEntity.ok(roleService.save(roleDto));
    }

}
