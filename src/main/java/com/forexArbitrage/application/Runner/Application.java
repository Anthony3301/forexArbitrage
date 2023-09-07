package com.forexArbitrage.application.Runner;

import com.forexArbitrage.application.verification.CurrencyVerifier;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.forexArbitrage.application")
public class Application {
	@Autowired
	CurrencyVerifier currVer;

	public static void main(String[] args) {

		SpringApplication.run(Application.class, args);
		System.out.println("intial setup");
	}

	@PostConstruct
	public void runAfterStartup() {
		System.out.println("Loading all currencies...");
		if (currVer.getCurrencyCache()) {
			System.out.println("Moving on!");
		} else {
			System.out.println("Failed to load currencies.");
		}
	}
}
