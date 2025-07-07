package com.example.dbem;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class DbemApplication {

	public static void main(String[] args) {
		SpringApplication.run(DbemApplication.class, args);
	}

}
