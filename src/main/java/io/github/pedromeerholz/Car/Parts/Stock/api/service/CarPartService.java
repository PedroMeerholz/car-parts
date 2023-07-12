package io.github.pedromeerholz.Car.Parts.Stock.api.service;

import io.github.pedromeerholz.Car.Parts.Stock.api.model.part.CarPart;
import io.github.pedromeerholz.Car.Parts.Stock.api.model.part.dto.NewCarPartDto;
import io.github.pedromeerholz.Car.Parts.Stock.api.model.partCategory.CarPartCategory;
import io.github.pedromeerholz.Car.Parts.Stock.api.repository.CarPartCategoryRepository;
import io.github.pedromeerholz.Car.Parts.Stock.api.repository.CarPartRepository;
import io.github.pedromeerholz.Car.Parts.Stock.validations.carPartValidations.CarPartValidator;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CarPartService {
    private final CarPartRepository carPartRepository;
    private final CarPartCategoryRepository carPartCategoryRepository;
    private final CarPartValidator carPartValidator = new CarPartValidator();

    public CarPartService(CarPartRepository carPartRepository, CarPartCategoryRepository carPartCategoryRepository) {
        this.carPartRepository = carPartRepository;
        this.carPartCategoryRepository = carPartCategoryRepository;
    }

    public HttpStatus createCarPart(NewCarPartDto newCarPartDto) {
        try {
            if (!this.carPartValidator.validateCarPartDataForCreate(this.carPartCategoryRepository, newCarPartDto)) {
                return HttpStatus.NOT_ACCEPTABLE;
            }
            Long categoryId = this.getCategoryId(newCarPartDto.getCategory());
            if (categoryId != null) {
                CarPart newCarPart = this.createCarPart(newCarPartDto.getName(), newCarPartDto.getDescription(),
                        newCarPartDto.getQuantity(), categoryId, newCarPartDto.isEnabled());
                this.carPartRepository.save(newCarPart);
                return HttpStatus.ACCEPTED;
            }
        } catch (Exception exception) {
            exception.printStackTrace();
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return HttpStatus.NOT_ACCEPTABLE;
    }

    private CarPart createCarPart(String name, String description, int quantity, Long categoryId, boolean enabled) {
        CarPart carPart = new CarPart();
        carPart.setName(name);
        carPart.setDescription(description);
        carPart.setQuantity(quantity);
        carPart.setCategory(categoryId);
        carPart.setEnabled(enabled);
        return carPart;
    }

    private Long getCategoryId(String category) {
        Optional<CarPartCategory> optionalCarPartCategory = this.carPartCategoryRepository.findByCategory(category);
        if (optionalCarPartCategory.isPresent()) {
            CarPartCategory carPartCategory = optionalCarPartCategory.get();
            return carPartCategory.getId();
        }
        return null;
    }
}
