package com.rest.moodiary;

import com.rest.moodiary.config.LoggerConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@Import(LoggerConfig.class)
@SpringBootApplication
public class MoodiaryApplication {

	public static void main(String[] args) {
		SpringApplication.run(MoodiaryApplication.class, args);
	}
}
