package io.github.pedromeerholz.Car.Parts.Stock.api.repository.carPart;

import io.github.pedromeerholz.Car.Parts.Stock.api.model.part.CarPart;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CarPartRepository extends JpaRepository<CarPart, Long> {

    Optional<CarPart> findByName(String carPart);
}
