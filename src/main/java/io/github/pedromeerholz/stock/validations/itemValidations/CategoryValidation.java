package io.github.pedromeerholz.stock.validations.itemValidations;

import io.github.pedromeerholz.stock.api.model.itemCategory.ItemCategory;
import io.github.pedromeerholz.stock.api.repository.item.ItemCategoryRepository;
import io.github.pedromeerholz.stock.validations.EmptyValueValidator;

import java.util.Optional;

public class CategoryValidation {
    private final EmptyValueValidator emptyValueValidator = new EmptyValueValidator();
    private boolean isRegisteredCategory(ItemCategoryRepository itemCategoryRepository, String category) {
        Optional<ItemCategory> optionalItemCategory = itemCategoryRepository.findByCategory(category);
        if (optionalItemCategory.isPresent()) {
            return false;
        }
        return true;
    }

    public boolean validateCategory(ItemCategoryRepository itemCategoryRepository, String category) {
        boolean isEmptyCategoryName = this.emptyValueValidator.emptyValueValidation(category);
        boolean isRegisteredCategory = this.isRegisteredCategory(itemCategoryRepository, category);
        if (!isEmptyCategoryName || !isRegisteredCategory) {
            return false;
        }
        return true;
    }
}
