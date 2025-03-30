package ru.improve.abs;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.cache.annotation.EnableCaching;
import ru.improve.abs.util.FillBaseEntityUtil;

@EnableCaching
@ConfigurationPropertiesScan
@SpringBootApplication
public class AbsApplication {

	private static FillBaseEntityUtil fillBaseEntityUtil;

	public static void main(String[] args) {
		SpringApplication.run(AbsApplication.class, args);

		fillBaseEntityUtil.fillBaseEntity();
	}
}
