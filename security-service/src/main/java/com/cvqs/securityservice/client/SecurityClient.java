package com.cvqs.securityservice.client;

import com.cvqs.securityservice.dto.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
/**
 *  Bu Sınıf,user-management servisine istek gönderir.
 *
 * @author Enes Bekkaya
 * @since  25.03.2023
 */
@FeignClient(name = "user-management-service",path = "/users")

public interface SecurityClient {
    /**
     * Verilen kullanıcı adına sahip kullanıcıyı döndürür.
     * @param username Kullanıcı adı
     * @return Kullanıcı
     */
    @GetMapping("/findUserByUsername")
     User findUserByUsername(@RequestParam("username") String username);
}
