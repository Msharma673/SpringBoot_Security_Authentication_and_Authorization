package com.SpringBootSecurity.SpringBootSecurityAuthenticationAndAuthorization;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan(basePackages = {"config", "security", "controller", "service", "exception", "com.SpringBootSecurity.SpringBootSecurityAuthenticationAndAuthorization"})
@EntityScan(basePackages = {"model", "com.SpringBootSecurity.SpringBootSecurityAuthenticationAndAuthorization.model"})
@EnableJpaRepositories(basePackages = {"repository", "com.SpringBootSecurity.SpringBootSecurityAuthenticationAndAuthorization.repository"})
public class SpringBootSecurityAuthenticationAndAuthorizationApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringBootSecurityAuthenticationAndAuthorizationApplication.class, args);
	}

}
