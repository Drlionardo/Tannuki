package com.drlionardo.registryhub;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync
@SpringBootApplication
public class RegistryHubApplication {

    public static void main(String[] args) {
        SpringApplication.run(RegistryHubApplication.class, args);
    }

}
