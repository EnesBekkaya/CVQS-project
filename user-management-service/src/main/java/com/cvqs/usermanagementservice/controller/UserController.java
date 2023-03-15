package com.cvqs.usermanagementservice.controller;

import com.cvqs.usermanagementservice.dto.UserDto;
import com.cvqs.usermanagementservice.service.abstracts.UserService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private static final Logger LOGGER= LoggerFactory.getLogger(UserController.class);
    @GetMapping("/getAll")

    public ResponseEntity<List<UserDto>> getAllUser(){
        LOGGER.info("Incoming request for /users/getAll");
        return ResponseEntity.ok(userService.getAll());
    }

    @PostMapping ("/save")
    public ResponseEntity<UserDto> saveUser(@RequestBody UserDto userDto){
        LOGGER.info("Incoming request for /users/saveUser");

        return ResponseEntity.ok(userService.save(userDto));
    }

    @PostMapping ("/update")
    public ResponseEntity<UserDto> updateUser(@RequestBody UserDto userDto){
        LOGGER.info("Incoming request for /users/update");

        return ResponseEntity.ok(userService.updateUser(userDto));
    }
    @DeleteMapping ("/delete")
    public ResponseEntity<UserDto> deleteUser(@RequestBody UserDto userDto){
        LOGGER.info("Incoming request for /users/delete");

        return ResponseEntity.ok(userService.delete(userDto));
    }
}
