package io.github.pedromeerholz.Car.Parts.Stock;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
@OpenAPIDefinition(info = @Info(title = "Car Parts API", version = "1.0.1",
		description = "API to manage your auto parts inventory"))
public class CarPartsStockApplication {

	public static void main(String[] args) {
		SpringApplication.run(CarPartsStockApplication.class, args);
	}

}
