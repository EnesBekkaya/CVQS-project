package com.cvqs.defectlistingservice.configuration;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DefectListingServiceConfig {
    @Bean
    public ModelMapper modelMapper(){
        return new ModelMapper();
    }
}
