package com.authservice;

import java.util.Optional;

import com.authservice.core.model.EmailConfirmation;
import com.authservice.core.model.User;
import com.authservice.core.queue.EmailConfirmationQueue;
import com.authservice.domain.queue.EmailConfirmationQueueImpl;

/**
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.SpringApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
//import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "com.authservice.core.repository") // adjust as needed
*/
public class AuthserviceApplication {
	public static void main(String[] args) {
		EmailConfirmationQueue emailConfirmationQueue = new EmailConfirmationQueueImpl();

		emailConfirmationQueue.startWatcher();
		emailConfirmationQueue.add(EmailConfirmation.builder()
			.user(User.builder()
				.id("123")
				.email("okaa")
				.password("123")
				.name("okaabe")
				.build()
			)
			.confirmationCode("1234")
			.expirationTime(System.currentTimeMillis() + 5000) // 10 seconds from now
			.build()
		);

		try {
			Optional<EmailConfirmation> emailConfirmationA = emailConfirmationQueue.getByConfirmationCode("1234");

			if (emailConfirmationA.isPresent()) {
				System.out.println("Email confirmation found: " + emailConfirmationA.get());
			} else {
				System.out.println("Email confirmation not found or expired.");
			}
			Thread.sleep(6000);
			Optional<EmailConfirmation> emailConfirmation = emailConfirmationQueue.getByConfirmationCode("1234");

			if (emailConfirmation.isPresent()) {
				System.out.println("Email confirmation found: " + emailConfirmation.get());
			} else {
				System.out.println("Email confirmation not found or expired.");
			}
			emailConfirmationQueue.interruptWatcher();
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
		}
//		SpringApplication.run(AuthserviceApplication.class, args);
	}

	/**
	@Bean
	PassportEncoder passportEncoder() {
		return new JWTPassportEncoder("test");
	}
		*/
}
