package com.pet;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class PetConnectApplication {

	public static void main(String[] args) {
		SpringApplication.run(PetConnectApplication.class, args);
	}

}
