package io.github.pedromeerholz.Car.Parts.Stock;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
public class CarPartsStockApplication {

	public static void main(String[] args) {
		SpringApplication.run(CarPartsStockApplication.class, args);
	}

}
