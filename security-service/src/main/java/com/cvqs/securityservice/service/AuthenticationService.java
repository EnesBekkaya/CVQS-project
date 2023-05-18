package com.cvqs.securityservice.service;

import com.cvqs.securityservice.dto.AuthRequest;
import com.cvqs.securityservice.dto.UserSecurityDto;
import com.cvqs.securityservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

/**
 * The AuthenticationService is a service used for user authentication and JWT operations.
 *
 * @author Enes Bekkaya
 * @since 25.03.2023
 */
@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;
    private final ModelMapper modelMapper;


    /**
     * Performs user authentication and creates a JWT token.
     *
     * @param request an AuthRequest object containing the user's credentials
     * @return an AuthenticationResponse object containing a JWT token if the user's authentication is successful
     */
    public String authenticate(AuthRequest request) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
            var user = userRepository.findByUsername(request.getUsername());
            var jwtToken = jwtService.generateToken(modelMapper.map(user, UserSecurityDto.class));
            return jwtToken;
        }catch (Exception e){
            return "failed";
        }
    }

    /**
     * Checks the given JWT token is valid and contains the specified role.
     *
     * @param token String, JWT token to be checked.
     * @param role  String, role to be checked.
     * @return boolean, true if the token is valid and contains the specified role, false otherwise.
     */
    public String isInvalid(String token, String role) {
        try {
            String username = jwtService.extractUsername(token);
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);

            if (jwtService.isTokenValid(token, userDetails)) {
                if (jwtService.hasRole(token, role)) {
                    return "valid";
                } else {
                    return "unauthorized";
                }
            }
            return "notValid";

        } catch (Exception e) {
            return "notValid";
        }
    }
}
