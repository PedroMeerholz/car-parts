package io.github.pedromeerholz.Car.Parts.Stock.validations.carPartValidations;

import io.github.pedromeerholz.Car.Parts.Stock.api.model.part.dto.NewCarPartDto;
import io.github.pedromeerholz.Car.Parts.Stock.api.model.partCategory.CarPartCategory;
import io.github.pedromeerholz.Car.Parts.Stock.api.repository.CarPartCategoryRepository;
import io.github.pedromeerholz.Car.Parts.Stock.validations.EmptyValueValidator;

import java.util.Optional;

public class CarPartValidator {
    private final EmptyValueValidator emptyValueValidator = new EmptyValueValidator();

    public boolean validateCarPartDataForCreate(CarPartCategoryRepository carPartCategoryRepository, NewCarPartDto newCarPartDto) {
        boolean isNameEmpty = this.emptyValueValidator.emptyValueValidation(newCarPartDto.getName());
        boolean isDescriptionEmpty = this.emptyValueValidator.emptyValueValidation(newCarPartDto.getDescription());
        boolean categoryExists = this.verifyCategoryExists(carPartCategoryRepository, newCarPartDto.getCategory());
        boolean isValidQuantity = this.validateQuantity(newCarPartDto.getQuantity());
        if (!isNameEmpty || !isDescriptionEmpty || !categoryExists || !isValidQuantity) {
            return false;
        }
        return true;
    }

    private boolean verifyCategoryExists(CarPartCategoryRepository carPartCategoryRepository, String category) {
        Optional<CarPartCategory> carPartCategory = carPartCategoryRepository.findByCategory(category);
        if (carPartCategory.isPresent()) {
            return true;
        }
        return false;
    }

    private boolean validateQuantity(int quantity) {
        if (quantity > 0) {
            return true;
        }
        return false;
    }
}