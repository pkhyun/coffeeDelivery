package com.sparta.coffeedeliveryproject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.web.config.EnableSpringDataWebSupport;

@SpringBootApplication
public class CoffeeDeliveryProjectApplication {

	public static void main(String[] args) {
		SpringApplication.run(CoffeeDeliveryProjectApplication.class, args);
	}

}
