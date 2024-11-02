package com.delivery.delivery;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.delivery")
public class GreenDogDeliveryApplication {

	public static void main(String[] args) {
		SpringApplication.run(GreenDogDeliveryApplication.class, args);
	}
}
