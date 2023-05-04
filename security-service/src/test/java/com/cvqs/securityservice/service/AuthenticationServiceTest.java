package com.cvqs.securityservice.service;

import com.cvqs.securityservice.dto.AuthRequest;
import com.cvqs.securityservice.dto.AuthenticationResponse;
import com.cvqs.securityservice.dto.User;
import com.cvqs.securityservice.dto.UserSecurityDto;
import com.cvqs.securityservice.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import java.io.IOException;
import java.sql.SQLException;



class AuthenticationServiceTest {
    private AuthenticationService authenticationService;
    private UserRepository userRepository;
    private  JwtService jwtService;
    private  AuthenticationManager authenticationManager;
    private  UserDetailsService userDetailsService;
    private  ModelMapper modelMapper;
    @BeforeEach
    public void setUp() {
        userRepository = Mockito.mock(UserRepository.class);
        jwtService = Mockito.mock(JwtService.class);
        authenticationManager = Mockito.mock(AuthenticationManager.class);
        modelMapper = Mockito.mock(ModelMapper.class);
        userDetailsService=Mockito.mock(UserDetailsService.class);
        authenticationService = new AuthenticationService(userRepository,jwtService,authenticationManager, userDetailsService,modelMapper);
    }

    @DisplayName("should Authenticate And Return AuthenticationResponse")
    @Test
    void shouldAuthenticateandReturnAuthenticationResponse() throws SQLException, IOException {
        AuthRequest request=new AuthRequest();
        request.setUsername("testuser");
        request.setPassword("testpassword");
        String jwt="testjwt";
        User user=new User();
        user.setName("testuser");
        user.setPassword("testpassword");
        UserSecurityDto userSecurityDto = new UserSecurityDto();
        userSecurityDto=modelMapper.map(user,UserSecurityDto.class);

        AuthenticationResponse expectedResult= AuthenticationResponse.builder()
                .token(jwt)
                .build();
        Mockito.when(userRepository.findByUsername(request.getUsername())).thenReturn(user);


        Mockito.when(jwtService.generateToken(userSecurityDto)).thenReturn(jwt);


        AuthenticationResponse result = authenticationService.authenticate(request);

        Assertions.assertEquals(expectedResult,result);
    }


}