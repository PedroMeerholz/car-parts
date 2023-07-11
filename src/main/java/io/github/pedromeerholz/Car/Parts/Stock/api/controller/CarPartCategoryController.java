package io.github.pedromeerholz.Car.Parts.Stock.api.controller;

import io.github.pedromeerholz.Car.Parts.Stock.api.model.partCategory.dto.NewCarPartCategoryDto;
import io.github.pedromeerholz.Car.Parts.Stock.api.service.CarPartCategoryService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/partCategory")
public class CarPartCategoryController {
    private final CarPartCategoryService carPartCategoryService;

    public CarPartCategoryController(CarPartCategoryService carPartCategoryService) {
        this.carPartCategoryService = carPartCategoryService;
    }

    @PostMapping("/create")
    public HttpStatus createCarPartCategory(@RequestBody NewCarPartCategoryDto newCarPartCategoryDto) {
        return this.carPartCategoryService.createCarPartCategory(newCarPartCategoryDto);
    }
}
