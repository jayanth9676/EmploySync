package com.example.employwise;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.StandardEnvironment;

@SpringBootApplication
public class EmploywiseApplication {

	public static void main(String[] args) {
		Dotenv dotenv = Dotenv.configure().ignoreIfMissing().load();
		
		SpringApplication application = new SpringApplication(EmploywiseApplication.class);
		ConfigurableEnvironment environment = new StandardEnvironment();
		
		dotenv.entries().forEach(entry -> 
			environment.getSystemProperties().put(entry.getKey(), entry.getValue())
		);
		
		application.setEnvironment(environment);
		application.run(args);
	}

}
