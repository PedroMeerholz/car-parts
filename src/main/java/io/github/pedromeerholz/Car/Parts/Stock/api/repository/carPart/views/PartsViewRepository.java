package io.github.pedromeerholz.Car.Parts.Stock.api.repository.carPart.views;

import io.github.pedromeerholz.Car.Parts.Stock.api.model.part.views.PartsView;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PartsViewRepository extends JpaRepository<PartsView, Long> {

}
