package io.github.pedromeerholz.stock.api.repository.item;

import io.github.pedromeerholz.stock.api.model.itemCategory.ItemCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ItemCategoryRepository extends JpaRepository<ItemCategory, Long> {
    @Query(nativeQuery = true, value = "SELECT * FROM itemcategory WHERE category = :category")
    Optional<ItemCategory> findByCategory(@Param("category") String category);

    @Query(nativeQuery = true, value = "SELECT * FROM itemcategory WHERE enabled = :status")
    Optional<List<ItemCategory>> findByStatus(@Param("status") boolean status);
}
