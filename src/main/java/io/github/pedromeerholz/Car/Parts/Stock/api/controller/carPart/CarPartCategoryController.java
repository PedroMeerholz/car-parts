package io.github.pedromeerholz.Car.Parts.Stock.api.controller.carPart;

import io.github.pedromeerholz.Car.Parts.Stock.api.model.part.partCategory.CarPartCategory;
import io.github.pedromeerholz.Car.Parts.Stock.api.model.part.partCategory.dto.CarPartCategoryDto;
import io.github.pedromeerholz.Car.Parts.Stock.api.service.carPart.CarPartCategoryService;
import io.swagger.v3.oas.annotations.Operation;
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
    @Operation(summary = "Create a new part category", method = "POST")
    public HttpStatus createCarPartCategory(@RequestBody CarPartCategoryDto carPartCategoryDto) {
        return this.carPartCategoryService.createCarPartCategory(carPartCategoryDto);
    }

    @GetMapping("/listAll")
    @Operation(summary = "Visualize all registered part cateogories", method = "GET")
    public List<CarPartCategory> listAll() {
        return this.carPartCategoryService.listAll();
    }

    @PatchMapping("/update")
    @Operation(summary = "Update a registered part category", method = "PATCH")
    public HttpStatus updateCarPartCategory(@RequestBody CarPartCategoryDto carPartCategoryDto,
                                            @RequestParam String categoryToUpdate) {
        return this.carPartCategoryService.updateCarPartCategory(carPartCategoryDto, categoryToUpdate);
    }
}
