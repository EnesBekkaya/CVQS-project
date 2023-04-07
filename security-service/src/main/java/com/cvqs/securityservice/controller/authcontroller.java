package com.cvqs.securityservice.controller;

import com.cvqs.securityservice.dto.AuthRequest;
import com.cvqs.securityservice.dto.AuthenticationResponse;
import com.cvqs.securityservice.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
/**
 * authcontroller sınıfı, authcontroller servisinin isteklerini karşılamak için kullanılır.
 *
 * @author Enes Bekkaya
 * @since  25.03.2023
 */
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class authcontroller {
    private  final AuthenticationService authenticationService;
    private static final Logger LOGGER= LoggerFactory.getLogger(authcontroller.class);

    /**
     * Kullanıcı kimlik bilgilerini doğrular ve JWT token oluşturmak için http post metodu kullanılarak çağrı yapılır.
     * @param request kullanıcının kimlik bilgilerini içeren AuthRequest nesnesi
     * @return oluşturulan JWT token'ı içeren AuthenticationResponse nesnesi
     */
    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody AuthRequest request){
        LOGGER.info("Incoming request for /auth/authenticate");

        return ResponseEntity.ok(authenticationService.authenticate(request));

    }

    /**
     * Verilen JWT token'ın geçerliliğini ve ilgili rolü içerip içermediğini kontrol etmek için http get metodu kullanılarak çağrı yapılır.
     * @param token JWT token'ı
     * @param role kullanıcının sahip olması gereken rol
     * @return JWT token geçerli ve rol içeriyorsa true, aksi takdirde false
     */
    @GetMapping("/validateToken")
    public boolean validateToken(@RequestParam("token") String token,@RequestParam("role") String role) {
            LOGGER.info("Incoming request for /auth/validateToken");
            return authenticationService.isInvalid(token,role);
    }


}
