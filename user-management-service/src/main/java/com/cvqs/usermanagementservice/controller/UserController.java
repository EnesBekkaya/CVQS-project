package com.cvqs.usermanagementservice.controller;

import com.cvqs.usermanagementservice.dto.AuthRequest;
import com.cvqs.usermanagementservice.dto.UserDto;
import com.cvqs.usermanagementservice.dto.UserResponseDto;
import com.cvqs.usermanagementservice.service.abstracts.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
/**
 * The UserController class is used to handle requests for the User service.
 *
 * @author Enes Bekkaya
 * @since  26.02.2023
 */
@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    /**
     * Makes a call using the HTTP GET method to retrieve all users.
     * @return ResponseEntity containing the list of users.
     */
    @GetMapping("/getAll")
    public ResponseEntity<List<UserResponseDto>> getAllUser(){
        return ResponseEntity.ok(userService.getAll());
    }

    /**
     * Makes an HTTP POST request to add a new user.
     * @param userDto UserDto object containing the data of the user to be saved.
     * @return ResponseEntity containing the data of the saved user.

     */
    @PostMapping ("/save")
    public ResponseEntity<UserResponseDto> saveUser(@RequestBody @Valid UserDto userDto){
        return ResponseEntity.ok(userService.save(userDto));
    }

    /**
     * Uses http post method to update an existing user's information.
     * @param userDto UserDto object containing the updated user data.
     * @return ResponseEntity containing the updated user's data.
     */
    @PostMapping ("/update")
    public ResponseEntity<UserResponseDto> updateUser(@RequestBody UserDto userDto){
        return ResponseEntity.ok(userService.updateUser(userDto));
    }

    /**
     * Deletes a user by making an HTTP DELETE request.
     * @param username The username of the user to be deleted.
     * @return A ResponseEntity containing the deleted user's information in a UserDto object.
     */
    @DeleteMapping ("/delete")
    public ResponseEntity<UserResponseDto> deleteUser(@RequestParam String username){
        return ResponseEntity.ok(userService.delete(username));
    }

    /**
     * Used for authenticating the user.
     * @param authRequest Request object to be used for authentication.
     * @return Response object generated after authentication.
     */
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody AuthRequest authRequest){
        return ResponseEntity.ok(userService.login(authRequest));
    }
}
