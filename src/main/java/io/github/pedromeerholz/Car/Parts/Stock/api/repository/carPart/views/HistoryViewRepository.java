package io.github.pedromeerholz.Car.Parts.Stock.api.repository.carPart.views;

import io.github.pedromeerholz.Car.Parts.Stock.api.model.part.views.HistoryView;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HistoryViewRepository extends JpaRepository<HistoryView, Long> {

}
