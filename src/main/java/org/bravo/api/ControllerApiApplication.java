package org.bravo.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@EntityScan("org.bravo.api.entity")
@SpringBootApplication
public class ControllerApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(ControllerApiApplication.class, args);
	}

}
