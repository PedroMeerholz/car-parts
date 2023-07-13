package io.github.pedromeerholz.Car.Parts.Stock.api.repository.carPart;

import io.github.pedromeerholz.Car.Parts.Stock.api.model.part.partCategory.CarPartCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface CarPartCategoryRepository extends JpaRepository<CarPartCategory, Long> {
    @Query(nativeQuery = true, value = "SELECT * FROM category WHERE category = :category")
    Optional<CarPartCategory> findByCategory(@Param("category") String category);
}
