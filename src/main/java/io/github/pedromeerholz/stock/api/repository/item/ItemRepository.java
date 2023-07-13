package io.github.pedromeerholz.stock.api.repository.item;

import io.github.pedromeerholz.stock.api.model.item.Item;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ItemRepository extends JpaRepository<Item, Long> {

    Optional<Item> findByName(String carPart);
}
