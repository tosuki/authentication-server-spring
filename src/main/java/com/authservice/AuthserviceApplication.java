package com.authservice;

import org.springframework.context.annotation.Bean;

import com.authservice.core.ports.EmailServiceProvider;
import com.authservice.core.ports.PassportEncoder;
import com.authservice.core.queue.EmailConfirmationQueue;
import com.authservice.domain.ports.EmailServiceProviderImpl;
import com.authservice.domain.ports.JWTPassportEncoder;
import com.authservice.domain.queue.EmailConfirmationQueueImpl;


import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.SpringApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

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

	@Bean
	EmailConfirmationQueue emailConfirmationQueue() {
		EmailConfirmationQueueImpl queue = new EmailConfirmationQueueImpl();

		queue.startWatcher();
		return queue;
	}

	@Bean
	EmailServiceProvider emailServiceProvider() {
		return new EmailServiceProviderImpl(true);
	}
}
