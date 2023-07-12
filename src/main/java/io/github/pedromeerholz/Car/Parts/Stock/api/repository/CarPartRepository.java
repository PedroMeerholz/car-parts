package io.github.pedromeerholz.Car.Parts.Stock.api.repository;

import io.github.pedromeerholz.Car.Parts.Stock.api.model.part.CarPart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CarPartRepository extends JpaRepository<CarPart, Long> {

}
