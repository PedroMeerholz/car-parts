package io.github.pedromeerholz.Car.Parts.Stock.api.controller;

import io.github.pedromeerholz.Car.Parts.Stock.api.model.part.CarPart;
import io.github.pedromeerholz.Car.Parts.Stock.api.model.part.dto.NewCarPartDto;
import io.github.pedromeerholz.Car.Parts.Stock.api.model.part.dto.UpdateCarPartDto;
import io.github.pedromeerholz.Car.Parts.Stock.api.service.CarPartService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping("/listAll")
    public List<CarPart> listAll() {
        return this.carPartService.listAll();
    }

    @PatchMapping("/update")
    public HttpStatus updateCarPart(@RequestBody UpdateCarPartDto updateCarPartDto, @RequestParam String carPartToUpdate) {
        return this.carPartService.updateCarPart(updateCarPartDto, carPartToUpdate);
    }
}
