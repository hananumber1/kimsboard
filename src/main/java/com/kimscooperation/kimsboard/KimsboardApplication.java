package com.kimscooperation.kimsboard;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@EnableJpaAuditing()
public class KimsboardApplication {

	public static final String APPLICATION_LOCATIONS = "spring.config.location=" 
		+ "classpath:application.yml,"
		+ "classpath:application-key.yml,"
		+ "/home/ubuntu/applications/kimsboard/config/application-key.yml";
			
	public static void main(String[] args) {
		// SpringApplication.run(KimsboardApplication.class, args);
		new SpringApplicationBuilder(KimsboardApplication.class).properties(APPLICATION_LOCATIONS).run(args);
	}

	@Bean
	public RestTemplate getRestTemplate() {
		return new RestTemplate();
	}

}
