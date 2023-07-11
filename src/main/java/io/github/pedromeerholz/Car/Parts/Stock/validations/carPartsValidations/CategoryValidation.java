package io.github.pedromeerholz.Car.Parts.Stock.validations.carPartsValidations;

import io.github.pedromeerholz.Car.Parts.Stock.api.model.partCategory.CarPartCategory;
import io.github.pedromeerholz.Car.Parts.Stock.api.repository.CarPartCategoryRepository;
import io.github.pedromeerholz.Car.Parts.Stock.validations.EmptyValueValidator;

import java.util.Optional;

public class CategoryValidation {
    private final EmptyValueValidator emptyValueValidator = new EmptyValueValidator();
    private boolean isRegisteredCategory(CarPartCategoryRepository carPartCategoryRepository, String category) {
        Optional<CarPartCategory> optionalCarPartCategory = carPartCategoryRepository.findByCategory(category);
        if (optionalCarPartCategory.isPresent()) {
            return false;
        }
        return true;
    }

    public boolean validateCategory(CarPartCategoryRepository carPartCategoryRepository, String category) {
        boolean isEmptyCategoryName = this.emptyValueValidator.emptyValueValidation(category);
        boolean isRegisteredCategory = this.isRegisteredCategory(carPartCategoryRepository, category);
        System.out.println(isEmptyCategoryName);
        System.out.println(isRegisteredCategory);
        if (!isEmptyCategoryName || !isRegisteredCategory) {
            return false;
        }
        return true;
    }
}
