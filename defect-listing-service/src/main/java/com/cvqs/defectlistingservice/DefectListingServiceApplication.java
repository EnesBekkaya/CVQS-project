package com.cvqs.defectlistingservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients

public class DefectListingServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(DefectListingServiceApplication.class,args);
    }
}
