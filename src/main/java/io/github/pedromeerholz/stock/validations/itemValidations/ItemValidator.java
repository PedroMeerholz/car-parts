package io.github.pedromeerholz.stock.validations.itemValidations;

import io.github.pedromeerholz.stock.api.model.item.dto.ItemDto;
import io.github.pedromeerholz.stock.api.model.item.dto.UpdateItemDto;
import io.github.pedromeerholz.stock.api.model.itemCategory.ItemCategory;
import io.github.pedromeerholz.stock.api.repository.item.ItemCategoryRepository;
import io.github.pedromeerholz.stock.validations.EmptyValueValidator;

import java.util.Optional;

public class ItemValidator {
    private final EmptyValueValidator emptyValueValidator = new EmptyValueValidator();

    public boolean validateItemDataForCreate(ItemCategoryRepository itemCategoryRepository, ItemDto itemDto) {
        boolean isNameEmpty = this.emptyValueValidator.emptyValueValidation(itemDto.getName());
        boolean isDescriptionEmpty = this.emptyValueValidator.emptyValueValidation(itemDto.getDescription());
        boolean categoryExists = this.verifyCategoryExists(itemCategoryRepository, itemDto.getCategory());
        boolean isValidQuantity = this.validateQuantity(itemDto.getQuantity());
        if (!isNameEmpty || !isDescriptionEmpty || !categoryExists || !isValidQuantity) {
            return false;
        }
        return true;
    }

    public boolean validateItemDataForUpdate(ItemCategoryRepository itemCategoryRepository, UpdateItemDto updateItemDto) {
        boolean isNameEmpty = this.emptyValueValidator.emptyValueValidation(updateItemDto.getName());
        boolean isDescriptionEmpty = this.emptyValueValidator.emptyValueValidation(updateItemDto.getDescription());
        boolean categoryExists = this.verifyCategoryExists(itemCategoryRepository, updateItemDto.getCategory());
        if (!isNameEmpty || !isDescriptionEmpty || !categoryExists) {
            return false;
        }
        return true;
    }

    private boolean verifyCategoryExists(ItemCategoryRepository itemCategoryRepository, String category) {
        Optional<ItemCategory> ItemCategory = itemCategoryRepository.findByCategory(category);
        if (ItemCategory.isPresent()) {
            return true;
        }
        return false;
    }

    private boolean validateQuantity(int quantity) {
        if (quantity > 0) {
            return true;
        }
        return false;
    }
}
