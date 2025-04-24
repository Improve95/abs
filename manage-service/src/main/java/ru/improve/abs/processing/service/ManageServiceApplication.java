package ru.improve.abs.processing.service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.ConfigurableApplicationContext;
import ru.improve.abs.processing.service.util.FillBaseEntityUtil;

@EnableCaching
@ConfigurationPropertiesScan
@SpringBootApplication
public class ManageServiceApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext context = SpringApplication.run(ManageServiceApplication.class, args);

		FillBaseEntityUtil fillBaseEntityUtil = (FillBaseEntityUtil) context.getBean("fillBaseEntityUtil");
		fillBaseEntityUtil.fillBaseEntity();
	}
}
