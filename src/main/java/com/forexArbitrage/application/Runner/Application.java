package com.forexArbitrage.application.Runner;

import com.forexArbitrage.application.verification.CurrencyVerifier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application {
	@Autowired
	CurrencyVerifier currVer;

	public static void main(String[] args) {

		SpringApplication.run(Application.class, args);
		System.out.println("intial setup");
	}

}
