package io.github.pedromeerholz.Car.Parts.Stock.api.service;

import io.github.pedromeerholz.Car.Parts.Stock.api.model.part.CarPart;
import io.github.pedromeerholz.Car.Parts.Stock.api.model.part.dto.NewCarPartDto;
import io.github.pedromeerholz.Car.Parts.Stock.api.model.part.dto.UpdateCarPartDto;
import io.github.pedromeerholz.Car.Parts.Stock.api.model.partCategory.CarPartCategory;
import io.github.pedromeerholz.Car.Parts.Stock.api.repository.CarPartCategoryRepository;
import io.github.pedromeerholz.Car.Parts.Stock.api.repository.CarPartRepository;
import io.github.pedromeerholz.Car.Parts.Stock.validations.carPartValidations.CarPartValidator;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
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
                CarPart newCarPart = this.generateCarPartToCreate(newCarPartDto.getName(), newCarPartDto.getDescription(),
                        newCarPartDto.getQuantity(), categoryId, newCarPartDto.isEnabled());
                this.carPartRepository.save(newCarPart);
                return HttpStatus.OK;
            }
        } catch (Exception exception) {
            exception.printStackTrace();
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return HttpStatus.NOT_ACCEPTABLE;
    }

    private CarPart generateCarPartToCreate(String name, String description, int quantity, Long categoryId,
                                            boolean enabled) {
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

    public List<CarPart> listAll() {
        return this.carPartRepository.findAll();
    }

    public HttpStatus updateCarPart(UpdateCarPartDto updateCarPartDto, String carPartToUpdate) {
        try {
            Long updatedCategoryId = this.getCategoryId(updateCarPartDto.getCategory());
            if (!this.carPartValidator.validateCarPartDataForUpdate(this.carPartCategoryRepository, updateCarPartDto) ||
                    updatedCategoryId == null) {
                return HttpStatus.NOT_ACCEPTABLE;
            }
            Optional<CarPart> optionalCurrentCarPart = this.carPartRepository.findByName(carPartToUpdate);
            if (optionalCurrentCarPart.isPresent()) {
                CarPart currentCarPart = optionalCurrentCarPart.get();
                CarPart updatedCarPart = this.generateCarPartToUpdate(currentCarPart, updateCarPartDto.getName(),
                        updateCarPartDto.getDescription(), updatedCategoryId, updateCarPartDto.isEnabled());
                this.carPartRepository.save(updatedCarPart);
                return HttpStatus.OK;
            }
        } catch (Exception exception) {
            exception.printStackTrace();
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return HttpStatus.NOT_MODIFIED;
    }

    private CarPart generateCarPartToUpdate(CarPart currentCarPart, String name, String description, Long categoryId,
                                            boolean enabled) {
        CarPart carPart = currentCarPart;
        carPart.setName(name);
        carPart.setDescription(description);
        carPart.setCategory(categoryId);
        carPart.setEnabled(enabled);
        return carPart;
    }
}
