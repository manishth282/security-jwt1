package com.company.security_jwt1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.company.security_jwt1")
public class SecurityJwt1Application {

	public static void main(String[] args) {
		SpringApplication.run(SecurityJwt1Application.class, args);
	}

}
