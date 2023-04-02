package com.cvqs.securityservice.controller;

import com.cvqs.securityservice.dto.AuthRequest;
import com.cvqs.securityservice.dto.AuthenticationResponse;
import com.cvqs.securityservice.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class authcontroller {
    private  final AuthenticationService authenticationService;
    private static final Logger LOGGER= LoggerFactory.getLogger(authcontroller.class);

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody AuthRequest request){
        LOGGER.info("Incoming request for /auth/authenticate");

        return ResponseEntity.ok(authenticationService.authenticate(request));

    }

        @GetMapping("/validateToken")
    public boolean validateToken(@RequestParam("token") String token,@RequestParam("role") String role) {
            LOGGER.info("Incoming request for /auth/validateToken");
            return authenticationService.isInvalid(token,role);
    }


}
