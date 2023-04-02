package com.cvqs.securityservice.service;

import com.cvqs.securityservice.client.SecurityClient;
import com.cvqs.securityservice.dto.AuthRequest;
import com.cvqs.securityservice.dto.AuthenticationResponse;
import com.cvqs.securityservice.dto.UserSecurityDto;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final SecurityClient securityClient;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;
    private final ModelMapper modelMapper;
    private static final Logger LOGGER= LoggerFactory.getLogger(AuthenticationService.class);



    public AuthenticationResponse authenticate(AuthRequest request) {

        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
        var user = securityClient.findUserByUsername(request.getUsername());
        var jwtToken = jwtService.generateToken(modelMapper.map(user, UserSecurityDto.class));
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

    public boolean isInvalid(String token,String role) {
        try {
            String username = jwtService.extractUsername(token);
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);
            if (jwtService.isTokenValidAndHasRole(token, userDetails,role)) {
                return true;
            }
        }
      catch (Exception e){
          LOGGER.info("Invalid access...!");
          return false;
        }
        return false;
    }
}
