package com.authservice;

import com.authservice.core.io.Logger;
import com.authservice.core.model.Session;
import com.authservice.core.model.User;
import com.authservice.core.ports.PassportEncoder;
import com.authservice.domain.ports.JSONSerializer;
import com.authservice.domain.ports.JWTPassportEncoder;

//import org.springframework.boot.SpringApplication;
//import org.springframework.boot.autoconfigure.SpringBootApplication;
//import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

//@SpringBootApplication
//@EnableJpaRepositories(basePackages = "com.authservice.core.repository") // adjust as needed
public class AuthserviceApplication {
	public static void main(String[] args) {
		User user = User.builder()
			.id("1234-5")
			.email("okaabe2006@gmail.com")
			.password("123")
			.name("carlos")
			.timestamp(123)
			.build();

		PassportEncoder passportEncoder = new JWTPassportEncoder("hello?");

		String passport = passportEncoder.encode(user);
		Logger.info("Passport: " + passport);

		try {
			Session session = passportEncoder.decode(passport);
			Logger.info("Decoded Session: " + JSONSerializer.encode(session));
		} catch (Exception e) {
			Logger.error("Error decoding passport: " + e.getMessage());
		}
	}
}
