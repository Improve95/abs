package ru.improve.abs.info.service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@ConfigurationPropertiesScan
@SpringBootApplication
public class InfoServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(InfoServiceApplication.class, args);
    }
}
