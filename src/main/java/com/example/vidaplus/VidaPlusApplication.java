package com.example.vidaplus;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.example")
public class VidaPlusApplication {

	public static void main(String[] args) {
		SpringApplication.run(VidaPlusApplication.class, args);
	}

}
