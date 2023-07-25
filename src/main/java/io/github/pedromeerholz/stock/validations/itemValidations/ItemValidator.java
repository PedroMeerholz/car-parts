package io.github.pedromeerholz.stock.validations.itemValidations;

import io.github.pedromeerholz.stock.api.model.item.dto.NewItemDto;
import io.github.pedromeerholz.stock.api.model.item.dto.UpdateItemDto;
import io.github.pedromeerholz.stock.api.model.item.itemCategory.ItemCategory;
import io.github.pedromeerholz.stock.api.repository.item.ItemCategoryRepository;
import io.github.pedromeerholz.stock.validations.EmptyValueValidator;

import java.util.Optional;

public class ItemValidator {
    private final EmptyValueValidator emptyValueValidator = new EmptyValueValidator();

    public boolean validateItemDataForCreate(ItemCategoryRepository itemCategoryRepository, NewItemDto newItemDto) {
        boolean isNameEmpty = this.emptyValueValidator.emptyValueValidation(newItemDto.getName());
        boolean isDescriptionEmpty = this.emptyValueValidator.emptyValueValidation(newItemDto.getDescription());
        boolean categoryExists = this.verifyCategoryExists(itemCategoryRepository, newItemDto.getCategory());
        boolean isValidQuantity = this.validateQuantity(newItemDto.getQuantity());
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
