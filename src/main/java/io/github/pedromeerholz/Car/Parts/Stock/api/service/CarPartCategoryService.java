package io.github.pedromeerholz.Car.Parts.Stock.api.service;

import io.github.pedromeerholz.Car.Parts.Stock.api.model.partCategory.CarPartCategory;
import io.github.pedromeerholz.Car.Parts.Stock.api.model.partCategory.dto.CarPartCategoryDto;
import io.github.pedromeerholz.Car.Parts.Stock.api.repository.CarPartCategoryRepository;
import io.github.pedromeerholz.Car.Parts.Stock.validations.carPartsValidations.CategoryValidation;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CarPartCategoryService {
    private final CarPartCategoryRepository carPartCategoryRepository;
    private CategoryValidation categoryValidation;

    public CarPartCategoryService(CarPartCategoryRepository carPartCategoryRepository) {
        this.carPartCategoryRepository = carPartCategoryRepository;
        this.categoryValidation = new CategoryValidation();
    }

    public HttpStatus createCarPartCategory(CarPartCategoryDto carPartCategoryDto) {
        try {
            boolean isValidCategory = this.categoryValidation.validateCategory(this.carPartCategoryRepository,
                    carPartCategoryDto.getCategory());
            if (isValidCategory) {
                CarPartCategory carPartCategory = new CarPartCategory();
                carPartCategory.setCategory(carPartCategoryDto.getCategory());
                this.carPartCategoryRepository.save(carPartCategory);
                return HttpStatus.OK;
            }
        } catch (Exception exception) {
            exception.printStackTrace();
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return HttpStatus.NOT_ACCEPTABLE;
    }

    public List<CarPartCategory> listAll() {
        return this.carPartCategoryRepository.findAll();
    }

    public HttpStatus updateCarPartCategory(CarPartCategoryDto carPartCategoryDto, String categoryToUpdate) {
        try {
            Optional<CarPartCategory> optionalCurrentCarPartCategory = this.carPartCategoryRepository
                    .findByCategory(categoryToUpdate);
            if (optionalCurrentCarPartCategory.isPresent()) {
                CarPartCategory currentCarPartCategory = optionalCurrentCarPartCategory.get();
                CarPartCategory updatedCarPartCategory = new CarPartCategory();
                updatedCarPartCategory.setId(currentCarPartCategory.getId());
                updatedCarPartCategory.setCategory(carPartCategoryDto.getCategory());
                this.carPartCategoryRepository.save(updatedCarPartCategory);
                return HttpStatus.ACCEPTED;
            }
        } catch (Exception exception) {
            exception.printStackTrace();
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return HttpStatus.NOT_ACCEPTABLE;
    }
}
