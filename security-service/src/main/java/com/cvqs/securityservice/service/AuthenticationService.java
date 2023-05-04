package com.cvqs.securityservice.service;

import com.cvqs.securityservice.dto.AuthRequest;
import com.cvqs.securityservice.dto.AuthenticationResponse;
import com.cvqs.securityservice.dto.UserSecurityDto;
import com.cvqs.securityservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
/**
 * AuthenticationService, kullanıcı kimlik doğrulama ve JWT işlemleri için bir servistir.
 *
 * @author Enes Bekkaya
 * @since  25.03.2023
 */
@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;
    private final ModelMapper modelMapper;
    private static final Logger LOGGER= LoggerFactory.getLogger(AuthenticationService.class);


    /**
     * Kullanıcının kimlik doğrulamasını yapar ve JWT token oluşturur.
     *
     * @param request  AuthRequest, kullanıcının kimlik bilgilerini içeren  nesne.
     * @return AuthenticationResponse, kullanıcının kimlik doğrulaması başarılıysa JWT token döndürür.
     */
    public AuthenticationResponse authenticate(AuthRequest request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
        var user = userRepository.findByUsername(request.getUsername());
        var jwtToken = jwtService.generateToken(modelMapper.map(user, UserSecurityDto.class));
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }
    /**
     * Verilen JWT token'ın geçerli olup olmadığını ve belirtilen rolü içerip içermediğini kontrol eder.
     *
     * @param token String, kontrol edilecek JWT token'ı.
     * @param role String, kontrol edilecek rol.
     * @return boolean, token geçerliyse ve belirtilen rolü içeriyorsa true, aksi halde false döndürür.
     */
    public boolean isInvalid(String token,String role) {
        try {
            String username = jwtService.extractUsername(token);
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);
            if (jwtService.isTokenValidAndHasRole(token, userDetails,role)) {
                return true;
            }
        }
      catch (Exception e){
          LOGGER.warn("Invalid access...!");
          return false;
        }
        return false;
    }
}
