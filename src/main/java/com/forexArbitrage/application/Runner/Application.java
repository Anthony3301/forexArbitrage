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
	Runner runner;

	public static void main(String[] args) {

		SpringApplication.run(Application.class, args);
	}

	@PostConstruct
	public void runAfterStartup() {
		runner.run();
	}
}
