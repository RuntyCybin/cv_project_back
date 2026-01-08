package com.cybindev.cvproject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.cybindev.cvproject")
public class CvprojectApplication {

	public static void main(String[] args) {
		SpringApplication.run(CvprojectApplication.class, args);
		System.out.println("Application Started Successfully");
	}

}
