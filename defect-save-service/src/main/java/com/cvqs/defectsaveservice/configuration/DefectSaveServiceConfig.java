package com.cvqs.defectsaveservice.configuration;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DefectSaveServiceConfig {
    @Bean
    public ModelMapper modelMapper(){
        return new ModelMapper();
    }
}
