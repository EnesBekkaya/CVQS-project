package com.cvqs.usermanagementservice.controller;

import com.cvqs.usermanagementservice.dto.UserDto;
import com.cvqs.usermanagementservice.service.abstracts.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/getAll")
    public ResponseEntity<List<UserDto>> getAllUser(){
        return ResponseEntity.ok(userService.getAll());
    }

    @PostMapping ("/save")
    public ResponseEntity<UserDto> saveUser(@RequestBody UserDto userDto){

        return ResponseEntity.ok(userService.save(userDto));
    }

    @PostMapping ("/update")
    public ResponseEntity<UserDto> updateUser(@RequestBody UserDto userDto){

        return ResponseEntity.ok(userService.updateUser(userDto));
    }
    @DeleteMapping ("/delete")
    public ResponseEntity<UserDto> deleteUser(@RequestBody UserDto userDto){

        return ResponseEntity.ok(userService.delete(userDto));
    }
}
