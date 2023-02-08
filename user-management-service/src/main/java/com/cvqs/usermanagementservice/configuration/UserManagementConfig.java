package com.cvqs.usermanagementservice.configuration;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UserManagementConfig {
    @Bean
    public ModelMapper modelMapper(){
        return new ModelMapper();
    }
}
