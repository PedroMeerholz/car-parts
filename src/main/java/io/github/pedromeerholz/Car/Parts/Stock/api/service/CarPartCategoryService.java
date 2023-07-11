package io.github.pedromeerholz.Car.Parts.Stock.api.service;

import io.github.pedromeerholz.Car.Parts.Stock.api.model.partCategory.CarPartCategory;
import io.github.pedromeerholz.Car.Parts.Stock.api.model.partCategory.dto.NewCarPartCategoryDto;
import io.github.pedromeerholz.Car.Parts.Stock.api.repository.CarPartCategoryRepository;
import io.github.pedromeerholz.Car.Parts.Stock.validations.carPartsValidations.CategoryValidation;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CarPartCategoryService {
    private final CarPartCategoryRepository carPartCategoryRepository;
    private CategoryValidation categoryValidation;

    public CarPartCategoryService(CarPartCategoryRepository carPartCategoryRepository) {
        this.carPartCategoryRepository = carPartCategoryRepository;
        this.categoryValidation = new CategoryValidation();
    }

    public HttpStatus createCarPartCategory(NewCarPartCategoryDto newCarPartCategoryDto) {
        try {
            boolean isValidCategory = this.categoryValidation.validateCategory(this.carPartCategoryRepository,
                    newCarPartCategoryDto.getCategory());
            if (isValidCategory) {
                CarPartCategory carPartCategory = new CarPartCategory();
                carPartCategory.setCategory(newCarPartCategoryDto.getCategory());
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
}
