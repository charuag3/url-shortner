package com.url.shortner.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan({"com.url.shortner.controller"})
@EntityScan("com.url.shortner.entity")
@EnableJpaRepositories("com.url.shortner.data.repo")
public class UrlShortnerApplication {

	public static void main(String[] args) {
		SpringApplication.run(UrlShortnerApplication.class, args);
	}

}
