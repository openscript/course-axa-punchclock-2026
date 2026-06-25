package ch.axa.punchclock;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;

@SpringBootApplication
@OpenAPIDefinition
public class PunchclockApplication {

	public static void main(String[] args) {
		SpringApplication.run(PunchclockApplication.class, args);
	}

}
