package io.github.pedromeerholz.stock.api.service.item;

import io.github.pedromeerholz.stock.api.model.item.Item;
import io.github.pedromeerholz.stock.api.model.item.views.HistoryView;
import io.github.pedromeerholz.stock.api.model.item.dto.NewItemDto;
import io.github.pedromeerholz.stock.api.model.item.dto.UpdateItemDto;
import io.github.pedromeerholz.stock.api.model.item.itemCategory.ItemCategory;
import io.github.pedromeerholz.stock.api.model.item.views.ItemsView;
import io.github.pedromeerholz.stock.api.model.responsesDtos.ErrorMessageDto;
import io.github.pedromeerholz.stock.api.model.responsesDtos.ResponseDto;
import io.github.pedromeerholz.stock.api.repository.UserRepository;
import io.github.pedromeerholz.stock.api.repository.item.ItemCategoryRepository;
import io.github.pedromeerholz.stock.api.repository.item.ItemRepository;
import io.github.pedromeerholz.stock.api.repository.item.views.HistoryViewRepository;
import io.github.pedromeerholz.stock.api.repository.item.views.ItemsViewRepository;
import io.github.pedromeerholz.stock.validations.AuthorizationTokenValidator;
import io.github.pedromeerholz.stock.validations.itemValidations.ItemValidator;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ItemService {
    private final ItemRepository itemRepository;
    private final ItemCategoryRepository itemCategoryRepository;
    private final HistoryViewRepository historyViewRepository;
    private final ItemsViewRepository itemsViewRepository;
    private final ItemValidator itemValidator = new ItemValidator();
    private final UserRepository userRepository;
    private final AuthorizationTokenValidator authorizationTokenValidator;

    public ItemService(ItemRepository itemRepository, ItemCategoryRepository itemCategoryRepository, HistoryViewRepository historyViewRepository, ItemsViewRepository itemsViewRepository, UserRepository userRepository) {
        this.itemRepository = itemRepository;
        this.itemCategoryRepository = itemCategoryRepository;
        this.historyViewRepository = historyViewRepository;
        this.itemsViewRepository = itemsViewRepository;
        this.userRepository = userRepository;
        this.authorizationTokenValidator = new AuthorizationTokenValidator();
    }

    public ResponseEntity<ResponseDto> createItem(NewItemDto newItemDto, String email, String authorizationToken) {
        try {
            boolean isUserAuthorized = this.authorizationTokenValidator.validateAuthorizationToken(this.userRepository, email, authorizationToken);
            if (!isUserAuthorized) {
                return new ResponseEntity(new ErrorMessageDto("O usuário informado não está autorizado para acessar esse serviço"), HttpStatus.UNAUTHORIZED);
            }
            if (!this.itemValidator.validateItemDataForCreate(this.itemCategoryRepository, newItemDto)) {
                return new ResponseEntity(new ErrorMessageDto("Item não cadastrado! Verifique as informações."), HttpStatus.NOT_ACCEPTABLE);
            }
            Long categoryId = this.getCategoryId(newItemDto.getCategory());
            if (categoryId != null) {
                Item newItem = this.generateItemToCreate(newItemDto.getName(), newItemDto.getDescription(), newItemDto.getQuantity(), categoryId, newItemDto.isEnabled());
                this.itemRepository.save(newItem);
                return new ResponseEntity(HttpStatus.OK);
            }
            return new ResponseEntity(new ErrorMessageDto("Item não cadastrado! Verifique as informações."), HttpStatus.NOT_ACCEPTABLE);
        } catch (Exception exception) {
            exception.printStackTrace();
            return new ResponseEntity(new ErrorMessageDto(exception.getCause().getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private Item generateItemToCreate(String name, String description, int quantity, Long categoryId, boolean enabled) {
        Item item = new Item();
        item.setName(name);
        item.setDescription(description);
        item.setQuantity(quantity);
        item.setCategory(categoryId);
        item.setEnabled(enabled);
        return item;
    }

    private Long getCategoryId(String category) {
        Optional<ItemCategory> optionalItemCategory = this.itemCategoryRepository.findByCategory(category);
        if (optionalItemCategory.isPresent()) {
            ItemCategory itemCategory = optionalItemCategory.get();
            return itemCategory.getId();
        }
        return null;
    }

    public ResponseEntity<ResponseDto> updateItemInfo(UpdateItemDto updateItemDto, String itemToUpdate, String email, String authorizationToken) {
        try {
            boolean isUserAuthorized = this.authorizationTokenValidator.validateAuthorizationToken(this.userRepository, email, authorizationToken);
            if (isUserAuthorized == false) {
                return new ResponseEntity(new ErrorMessageDto("O usuário informado não está autorizado para acessar esse serviço"), HttpStatus.UNAUTHORIZED);
            }
            Long updatedCategoryId = this.getCategoryId(updateItemDto.getCategory());
            if (!this.itemValidator.validateItemDataForUpdate(this.itemCategoryRepository, updateItemDto) || updatedCategoryId == null) {
                return new ResponseEntity(new ErrorMessageDto("Item não cadastrado! Verifique as informações."), HttpStatus.NOT_ACCEPTABLE);
            }
            Optional<Item> optionalCurrentItem = this.itemRepository.findByName(itemToUpdate);
            if (optionalCurrentItem.isPresent()) {
                Item currentItem = optionalCurrentItem.get();
                Item updatedItem = this.generateItemToUpdateInfo(currentItem, updateItemDto.getName(), updateItemDto.getDescription(), updatedCategoryId, updateItemDto.isEnabled());
                this.itemRepository.save(updatedItem);
                return new ResponseEntity(HttpStatus.OK);
            }
            return new ResponseEntity(new ErrorMessageDto("Item não cadastrado! Verifique as informações."), HttpStatus.NOT_ACCEPTABLE);
        } catch (Exception exception) {
            exception.printStackTrace();
            return new ResponseEntity(new ErrorMessageDto(exception.getCause().getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private Item generateItemToUpdateInfo(Item currentItem, String name, String description, Long categoryId, boolean enabled) {
        Item item = currentItem;
        item.setName(name);
        item.setDescription(description);
        item.setCategory(categoryId);
        item.setEnabled(enabled);
        return item;
    }

    public ResponseEntity<ResponseDto> updateItemQuantity(String itemToUpdate, int quantityToUpdate, String email, String authorizationToken) {
        try {
            boolean isUserAuthorized = this.authorizationTokenValidator.validateAuthorizationToken(this.userRepository, email, authorizationToken);
            if (isUserAuthorized == false) {
                return new ResponseEntity(new ErrorMessageDto("O usuário informado não está autorizado para acessar esse serviço"), HttpStatus.UNAUTHORIZED);
            }
            Optional<Item> optionalCurrentItem = this.itemRepository.findByName(itemToUpdate);
            if (optionalCurrentItem.isPresent()) {
                Item currentItem = optionalCurrentItem.get();
                Item updatedItem = this.generateItemToUpdateQuantity(currentItem, quantityToUpdate);
                this.itemRepository.save(updatedItem);
                return new ResponseEntity(HttpStatus.OK);
            }
            return new ResponseEntity(new ErrorMessageDto("Item não cadastrado! Verifique as informações."), HttpStatus.NOT_ACCEPTABLE);
        } catch (Exception exception) {
            exception.printStackTrace();
            return new ResponseEntity(new ErrorMessageDto(exception.getCause().getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private Item generateItemToUpdateQuantity(Item currentItem, int quantity) {
        Item item = currentItem;
        System.out.printf("Quantity: %d\n", quantity);
        System.out.println(item.getQuantity());
        int updatedQuantity = item.getQuantity() + quantity;
        item.setQuantity(updatedQuantity);
        System.out.println(item.getQuantity());
        return item;
    }

    public List<ItemsView> listAll() {
        return this.itemsViewRepository.findAll();
    }

    public List<HistoryView> listHistory() {
        return this.historyViewRepository.findAll();
    }
}
