package io.github.pedromeerholz.stock.api.repository.item.views;

import io.github.pedromeerholz.stock.api.model.item.views.ItemsView;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ItemsViewRepository extends JpaRepository<ItemsView, Long> {
    @Query(nativeQuery = true, value = "select * from itemview where name = :name")
    Optional<ItemsView> findByName(@Param("name") String name);

    @Query(nativeQuery = true, value = "select * from itemview where enabled = :status")
    Optional<List<ItemsView>> findByStatus(@Param("status") boolean status);
}
