package com.cvqs.securityservice.controller;

import com.cvqs.securityservice.dto.AuthRequest;
import com.cvqs.securityservice.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
/**
 * AuthController class is used to handle the requests of the AuthController service.
 * @author Enes Bekkaya
 * @since  25.03.2023
 */
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class authcontroller {
    private  final AuthenticationService authenticationService;

    /**
     * Authenticates user credentials and calls the HTTP POST method to create a JWT token.
     * @param request an AuthRequest object containing the user's credentials
     * @return an AuthenticationResponse object containing the created JWT token
     */
    @PostMapping("/authenticate")
    public ResponseEntity<String> register(@RequestBody AuthRequest request){

        return ResponseEntity.ok(authenticationService.authenticate(request));

    }

    /**
     * Calls the HTTP GET method to check if the given JWT token is valid and contains the required role.
     * @param token the JWT token
     * @param role the role that the user should have
     * @return true if the JWT token is valid and contains the role, false otherwise
     */
    @GetMapping("/validateToken")
    public String  validateToken(@RequestParam("token") String token,@RequestParam("role") String role) {
            return authenticationService.isInvalid(token,role);
    }


}
