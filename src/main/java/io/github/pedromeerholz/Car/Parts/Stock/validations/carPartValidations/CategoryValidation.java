package io.github.pedromeerholz.Car.Parts.Stock.validations.carPartValidations;

import io.github.pedromeerholz.Car.Parts.Stock.api.model.part.partCategory.CarPartCategory;
import io.github.pedromeerholz.Car.Parts.Stock.api.repository.carPart.CarPartCategoryRepository;
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
        if (!isEmptyCategoryName || !isRegisteredCategory) {
            return false;
        }
        return true;
    }
}
