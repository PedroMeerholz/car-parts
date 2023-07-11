package io.github.pedromeerholz.Car.Parts.Stock.api.controller;

import io.github.pedromeerholz.Car.Parts.Stock.api.model.partCategory.CarPartCategory;
import io.github.pedromeerholz.Car.Parts.Stock.api.model.partCategory.dto.CarPartCategoryDto;
import io.github.pedromeerholz.Car.Parts.Stock.api.service.CarPartCategoryService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/partCategory")
public class CarPartCategoryController {
    private final CarPartCategoryService carPartCategoryService;

    public CarPartCategoryController(CarPartCategoryService carPartCategoryService) {
        this.carPartCategoryService = carPartCategoryService;
    }

    @PostMapping("/create")
    public HttpStatus createCarPartCategory(@RequestBody CarPartCategoryDto carPartCategoryDto) {
        return this.carPartCategoryService.createCarPartCategory(carPartCategoryDto);
    }

    @GetMapping("/listAll")
    public List<CarPartCategory> listAll() {
        return this.carPartCategoryService.listAll();
    }

    @PatchMapping("/update")
    public HttpStatus updateCarPartCategory(@RequestBody CarPartCategoryDto carPartCategoryDto, @RequestParam String categoryToUpdate) {
        return this.carPartCategoryService.updateCarPartCategory(carPartCategoryDto, categoryToUpdate);
    }
}
