package io.github.pedromeerholz.stock.api.repository.item;

import io.github.pedromeerholz.stock.api.model.item.itemCategory.ItemCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ItemCategoryRepository extends JpaRepository<ItemCategory, Long> {
    @Query(nativeQuery = true, value = "SELECT * FROM itemcategory WHERE category = :category")
    Optional<ItemCategory> findByCategory(@Param("category") String category);
}
