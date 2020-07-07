package com.kimscooperation.kimsboard;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class KimsboardApplication {

	public static void main(String[] args) {
		SpringApplication.run(KimsboardApplication.class, args);
	}

	/**
	 * Spring Security에서 사용하는 
	 * @return
	 */
	/*
	 * @Bean public PasswordEncoder passwordEncoder() { return
	 * PasswordEncoderFactories.createDelegatingPasswordEncoder(); }
	 */
	
	

}
