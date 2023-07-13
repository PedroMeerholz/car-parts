package io.github.pedromeerholz.stock.api.repository.item.views;

import io.github.pedromeerholz.stock.api.model.item.views.HistoryView;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HistoryViewRepository extends JpaRepository<HistoryView, Long> {

}
