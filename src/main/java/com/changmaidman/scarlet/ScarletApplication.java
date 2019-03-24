package com.changmaidman.scarlet;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories(basePackages = "com.changmaidman.scarlet.repository")
@SpringBootApplication
public class ScarletApplication {

	public static void main(String[] args) {
		SpringApplication.run(ScarletApplication.class, args);
	}

}
