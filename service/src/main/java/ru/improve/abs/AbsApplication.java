package ru.improve.abs;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.ConfigurableApplicationContext;
import ru.improve.abs.util.FillBaseEntityUtil;

@EnableCaching
@ConfigurationPropertiesScan
@SpringBootApplication
public class AbsApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext context = SpringApplication.run(AbsApplication.class, args);

		FillBaseEntityUtil fillBaseEntityUtil = (FillBaseEntityUtil) context.getBean("fillBaseEntityUtil");
		fillBaseEntityUtil.fillBaseEntity();
	}
}
