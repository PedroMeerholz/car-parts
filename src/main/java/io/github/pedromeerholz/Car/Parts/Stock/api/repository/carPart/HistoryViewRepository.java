package io.github.pedromeerholz.Car.Parts.Stock.api.repository.carPart;

import io.github.pedromeerholz.Car.Parts.Stock.api.model.part.history.HistoryView;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HistoryViewRepository extends JpaRepository<HistoryView, Long> {

}
