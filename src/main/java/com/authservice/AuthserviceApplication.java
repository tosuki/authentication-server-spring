package com.authservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.authservice.core.ports.PassportEncoder;
import com.authservice.domain.ports.JWTPassportEncoder;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "com.authservice.core.repository") // adjust as needed
public class AuthserviceApplication {
	public static void main(String[] args) {
		SpringApplication.run(AuthserviceApplication.class, args);
	}

	@Bean
	PassportEncoder passportEncoder() {
		return new JWTPassportEncoder("test");
	}
}
