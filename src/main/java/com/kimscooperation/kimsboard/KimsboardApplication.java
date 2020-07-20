package com.kimscooperation.kimsboard;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@EnableJpaAuditing()
public class KimsboardApplication {

	public static final String APPLICATION_LOCATIONS = "spring.config.location=" 
		+ "classpath:application.yml,"
		+ "classpath:application-local.yml,"
		+ "/home/ubuntu/applications/kimsboard/config/application-production.yml";
			
	public static void main(String[] args) {
		// SpringApplication.run(KimsboardApplication.class, args);
		new SpringApplicationBuilder(KimsboardApplication.class).properties(APPLICATION_LOCATIONS).run(args);
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return PasswordEncoderFactories.createDelegatingPasswordEncoder();
	}

	@Bean
	public RestTemplate getRestTemplate() {
		return new RestTemplate();
	}

}
