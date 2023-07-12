package io.github.pedromeerholz.Car.Parts.Stock.api.controller;

import io.github.pedromeerholz.Car.Parts.Stock.api.model.part.dto.NewCarPartDto;
import io.github.pedromeerholz.Car.Parts.Stock.api.service.CarPartService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/part")
public class CarPartController {
    private final CarPartService carPartService;

    public CarPartController(CarPartService carPartService) {
        this.carPartService = carPartService;
    }

    @PostMapping("/create")
    public HttpStatus createCarPart(@RequestBody NewCarPartDto newCarPartDto) {
        return this.carPartService.createCarPart(newCarPartDto);
    }
}
