package io.github.pedromeerholz.stock.api.service.item;

import io.github.pedromeerholz.stock.api.model.item.Item;
import io.github.pedromeerholz.stock.api.model.item.views.HistoryView;
import io.github.pedromeerholz.stock.api.model.item.dto.NewItemDto;
import io.github.pedromeerholz.stock.api.model.item.dto.UpdateItemDto;
import io.github.pedromeerholz.stock.api.model.item.itemCategory.ItemCategory;
import io.github.pedromeerholz.stock.api.model.item.views.ItemsView;
import io.github.pedromeerholz.stock.api.repository.item.ItemCategoryRepository;
import io.github.pedromeerholz.stock.api.repository.item.ItemRepository;
import io.github.pedromeerholz.stock.api.repository.item.views.HistoryViewRepository;
import io.github.pedromeerholz.stock.api.repository.item.views.ItemsViewRepository;
import io.github.pedromeerholz.stock.validations.itemValidations.CarPartValidator;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ItemService {
    private final ItemRepository itemRepository;
    private final ItemCategoryRepository itemCategoryRepository;
    private final HistoryViewRepository historyViewRepository;
    private final ItemsViewRepository itemsViewRepository;
    private final CarPartValidator carPartValidator = new CarPartValidator();

    public ItemService(ItemRepository itemRepository, ItemCategoryRepository itemCategoryRepository,
                       HistoryViewRepository historyViewRepository, ItemsViewRepository itemsViewRepository) {
        this.itemRepository = itemRepository;
        this.itemCategoryRepository = itemCategoryRepository;
        this.historyViewRepository = historyViewRepository;
        this.itemsViewRepository = itemsViewRepository;
    }

    public HttpStatus createCarPart(NewItemDto newItemDto) {
        try {
            if (!this.carPartValidator.validateCarPartDataForCreate(this.itemCategoryRepository, newItemDto)) {
                return HttpStatus.NOT_ACCEPTABLE;
            }
            Long categoryId = this.getCategoryId(newItemDto.getCategory());
            if (categoryId != null) {
                Item newItem = this.generateCarPartToCreate(newItemDto.getName(), newItemDto.getDescription(),
                        newItemDto.getQuantity(), categoryId, newItemDto.isEnabled());
                this.itemRepository.save(newItem);
                return HttpStatus.OK;
            }
        } catch (Exception exception) {
            exception.printStackTrace();
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return HttpStatus.NOT_ACCEPTABLE;
    }

    private Item generateCarPartToCreate(String name, String description, int quantity, Long categoryId,
                                         boolean enabled) {
        Item item = new Item();
        item.setName(name);
        item.setDescription(description);
        item.setQuantity(quantity);
        item.setCategory(categoryId);
        item.setEnabled(enabled);
        return item;
    }

    private Long getCategoryId(String category) {
        Optional<ItemCategory> optionalCarPartCategory = this.itemCategoryRepository.findByCategory(category);
        if (optionalCarPartCategory.isPresent()) {
            ItemCategory itemCategory = optionalCarPartCategory.get();
            return itemCategory.getId();
        }
        return null;
    }

    public List<ItemsView> listAll() {
        return this.itemsViewRepository.findAll();
    }

    public HttpStatus updateCarPartInfo(UpdateItemDto updateItemDto, String carPartToUpdate) {
        try {
            Long updatedCategoryId = this.getCategoryId(updateItemDto.getCategory());
            if (!this.carPartValidator.validateCarPartDataForUpdate(this.itemCategoryRepository, updateItemDto) ||
                    updatedCategoryId == null) {
                return HttpStatus.NOT_ACCEPTABLE;
            }
            Optional<Item> optionalCurrentCarPart = this.itemRepository.findByName(carPartToUpdate);
            if (optionalCurrentCarPart.isPresent()) {
                Item currentItem = optionalCurrentCarPart.get();
                Item updatedItem = this.generateCarPartToUpdateInfo(currentItem, updateItemDto.getName(),
                        updateItemDto.getDescription(), updatedCategoryId, updateItemDto.isEnabled());
                this.itemRepository.save(updatedItem);
                return HttpStatus.OK;
            }
        } catch (Exception exception) {
            exception.printStackTrace();
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return HttpStatus.NOT_MODIFIED;
    }

    private Item generateCarPartToUpdateInfo(Item currentItem, String name, String description, Long categoryId,
                                             boolean enabled) {
        Item item = currentItem;
        item.setName(name);
        item.setDescription(description);
        item.setCategory(categoryId);
        item.setEnabled(enabled);
        return item;
    }

    public HttpStatus updateCarPartQuantity(String carPartToUpdate, int quantityToUpdate) {
        try {
            Optional<Item> optionalCurrentCarPart = this.itemRepository.findByName(carPartToUpdate);
            if (optionalCurrentCarPart.isPresent()) {
                Item currentItem = optionalCurrentCarPart.get();
                Item updatedItem = this.generateCarPartToUpdateQuantity(currentItem, quantityToUpdate);
                this.itemRepository.save(updatedItem);
                return HttpStatus.OK;
            }
        } catch (Exception exception) {
            exception.printStackTrace();
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return HttpStatus.NOT_MODIFIED;
    }

    private Item generateCarPartToUpdateQuantity(Item currentItem, int quantity) {
        Item item = currentItem;
        System.out.printf("Quantity: %d\n", quantity);
        System.out.println(item.getQuantity());
        int updatedQuantity = item.getQuantity() + quantity;
        item.setQuantity(updatedQuantity);
        System.out.println(item.getQuantity());
        return item;
    }

    public List<HistoryView> listHistory() {
        return this.historyViewRepository.findAll();
    }
}
