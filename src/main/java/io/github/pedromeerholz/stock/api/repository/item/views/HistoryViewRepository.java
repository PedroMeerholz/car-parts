package io.github.pedromeerholz.stock.api.repository.item.views;

import io.github.pedromeerholz.stock.api.model.item.views.HistoryView;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface HistoryViewRepository extends JpaRepository<HistoryView, Long> {
    @Query(nativeQuery = true, value = "select * from historyview where name = :name")
    Optional<List<HistoryView>> findByName(@Param("name") String name);
}
