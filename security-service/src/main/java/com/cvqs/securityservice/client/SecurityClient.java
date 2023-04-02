package com.cvqs.securityservice.client;

import com.cvqs.securityservice.dto.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "user-management-service",path = "/users")

public interface SecurityClient {
    @GetMapping("/findUserByUsername")
     User findUserByUsername(@RequestParam("username") String username);
}
