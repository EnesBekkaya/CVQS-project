package com.cvqs.terminalservice.configuration;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TerminalConfig {
    @Bean
    public ModelMapper modelMapper(){
        return new ModelMapper();
    }
}
