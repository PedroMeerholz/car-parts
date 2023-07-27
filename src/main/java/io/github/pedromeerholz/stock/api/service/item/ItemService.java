package io.github.pedromeerholz.stock.api.service.item;

import io.github.pedromeerholz.stock.api.model.item.Item;
import io.github.pedromeerholz.stock.api.model.item.dto.HistoryViewDto;
import io.github.pedromeerholz.stock.api.model.item.views.HistoryView;
import io.github.pedromeerholz.stock.api.model.item.dto.ItemDto;
import io.github.pedromeerholz.stock.api.model.item.dto.UpdateItemDto;
import io.github.pedromeerholz.stock.api.model.itemCategory.ItemCategory;
import io.github.pedromeerholz.stock.api.model.item.views.ItemsView;
import io.github.pedromeerholz.stock.api.model.responsesDtos.ErrorMessageDto;
import io.github.pedromeerholz.stock.api.model.responsesDtos.ResponseDto;
import io.github.pedromeerholz.stock.api.repository.UserRepository;
import io.github.pedromeerholz.stock.api.repository.item.ItemCategoryRepository;
import io.github.pedromeerholz.stock.api.repository.item.ItemRepository;
import io.github.pedromeerholz.stock.api.repository.item.views.HistoryViewRepository;
import io.github.pedromeerholz.stock.api.repository.item.views.ItemsViewRepository;
import io.github.pedromeerholz.stock.redis.RedisValueCache;
import io.github.pedromeerholz.stock.validations.AuthorizationTokenValidator;
import io.github.pedromeerholz.stock.validations.itemValidations.ItemValidator;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ItemService {
    private final ItemRepository itemRepository;
    private final ItemCategoryRepository itemCategoryRepository;
    private final HistoryViewRepository historyViewRepository;
    private final ItemsViewRepository itemsViewRepository;
    private final ItemValidator itemValidator;
    private final RedisValueCache redisValueCache;
    private final UserRepository userRepository;
    private final AuthorizationTokenValidator authorizationTokenValidator;

    public ItemService(ItemRepository itemRepository, ItemCategoryRepository itemCategoryRepository, HistoryViewRepository historyViewRepository, ItemsViewRepository itemsViewRepository, RedisValueCache redisValueCache, UserRepository userRepository) {
        this.itemRepository = itemRepository;
        this.itemCategoryRepository = itemCategoryRepository;
        this.historyViewRepository = historyViewRepository;
        this.itemsViewRepository = itemsViewRepository;
        this.redisValueCache = redisValueCache;
        this.userRepository = userRepository;
        this.authorizationTokenValidator = new AuthorizationTokenValidator();
        this.itemValidator = new ItemValidator();
    }

    public ResponseEntity<ResponseDto> createItem(ItemDto itemDto, String email, String authorizationToken) {
        try {
            boolean isUserAuthorized = this.authorizationTokenValidator.validateAuthorizationToken(this.userRepository, email, authorizationToken);
            if (!isUserAuthorized) {
                return new ResponseEntity(new ErrorMessageDto("O usuário informado não está autorizado para acessar esse serviço"), HttpStatus.UNAUTHORIZED);
            }
            if (!this.itemValidator.validateItemDataForCreate(this.itemCategoryRepository, itemDto)) {
                return new ResponseEntity(new ErrorMessageDto("Item não cadastrado! Verifique as informações."), HttpStatus.NOT_ACCEPTABLE);
            }
            Long categoryId = this.getCategoryId(itemDto.getCategory());
            if (categoryId != null) {
                Item newItem = this.generateItemToCreate(itemDto.getName(), itemDto.getDescription(), itemDto.getQuantity(), categoryId, itemDto.isEnabled());
                this.itemRepository.save(newItem);
                this.redisValueCache.clearAllCachedValues();
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
                this.redisValueCache.clearAllCachedValues();
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
                this.redisValueCache.clearAllCachedValues();
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
        int updatedQuantity = item.getQuantity() + quantity;
        item.setQuantity(updatedQuantity);
        return item;
    }

    public List<ItemDto> listAll() {
        List<ItemDto> cachedValues = this.getItemListFromCache();
        if (cachedValues != null) {
            System.out.println("From cache");
            return cachedValues;
        }
        return this.getItemListFromDatabase();
    }

    private List<ItemDto> getItemListFromCache() {
        return (List<ItemDto>) this.redisValueCache.getCachedValue(this.redisValueCache.ALL_ITEMS_KEY);
    }

    private List<ItemDto> getItemListFromDatabase() {
        List<ItemsView> items = this.itemsViewRepository.findAll();
        if (!items.isEmpty()) {
            List<ItemDto> itemDtos = this.convertItemsViewToDto(items);
            this.redisValueCache.saveInCache(this.redisValueCache.ALL_ITEMS_KEY, itemDtos);
            return itemDtos;
        }
        return new ArrayList<>();
    }

    private List<ItemDto> convertItemsViewToDto(List<ItemsView> items) {
        List<ItemDto> itemDtos = new ArrayList<>();
        for (ItemsView item : items) {
            ItemDto itemDto = new ItemDto();
            itemDto.setName(item.getName());
            itemDto.setDescription(item.getDescription());
            itemDto.setQuantity(item.getQuantity());
            itemDto.setCategory(item.getCategory());
            itemDto.setEnabled(item.isEnabled());
            itemDtos.add(itemDto);
        }
        return itemDtos;
    }

    public ItemDto listItem(String itemName) {
        Optional<ItemsView> item = this.itemsViewRepository.findByName(itemName);
        if (item.isPresent()) {
            ItemsView itemView = item.get();
            return this.convertItemsViewToItemDto(itemView);
        }
        return new ItemDto();
    }

    public List<ItemDto> listItemByStatus(boolean status) {
        List<ItemDto> cachedValues = this.getItemsByStatusFromCache(status);
        if (cachedValues != null) {
            System.out.println("From cache");
            return cachedValues;
        }
        return this.getItemsByStatusFromDatabase(status);
    }

    private List<ItemDto> getItemsByStatusFromCache(boolean status) {
        if (status) {
            return (List<ItemDto>) this.redisValueCache.getCachedValue(this.redisValueCache.ENABLED_ITEMS_KEY);
        }
        return (List<ItemDto>) this.redisValueCache.getCachedValue(this.redisValueCache.DISABLED_ITEMS_KEY);
    }

    private List<ItemDto> getItemsByStatusFromDatabase(boolean status) {
        Optional<List<ItemsView>> itemsView = this.itemsViewRepository.findByStatus(status);
        if (itemsView.isPresent()) {
            List<ItemDto> itemDtos = this.convertItemsViewListToItemDtoList(itemsView.get());
            if (status) {
                this.redisValueCache.saveInCache(this.redisValueCache.ENABLED_ITEMS_KEY, itemDtos);
            } else {
                this.redisValueCache.saveInCache(this.redisValueCache.DISABLED_ITEMS_KEY, itemDtos);
            }
            return itemDtos;
        }
        return new ArrayList<>();
    }

    private List<ItemDto> convertItemsViewListToItemDtoList(List<ItemsView> itemView) {
        List<ItemDto> itemDtos = new ArrayList<>();
        for (ItemsView view : itemView) {
            ItemDto itemDto = this.convertItemsViewToItemDto(view);
            itemDtos.add(itemDto);
        }
        return itemDtos;
    }

    private ItemDto convertItemsViewToItemDto(ItemsView itemView) {
        ItemDto itemDto = new ItemDto();
        itemDto.setName(itemView.getName());
        itemDto.setDescription(itemView.getDescription());
        itemDto.setQuantity(itemView.getQuantity());
        itemDto.setCategory(itemView.getCategory());
        itemDto.setEnabled(itemView.isEnabled());
        return itemDto;
    }

    public List<HistoryViewDto> listHistory() {
        List<HistoryViewDto> cachedValues = this.getAllHistoryFromCache();
        if (cachedValues != null) {
            System.out.println("From cache");
            return cachedValues;
        }
        return this.getAllHistoryFromDatabase();
    }

    private List<HistoryViewDto> getAllHistoryFromCache() {
        return (List<HistoryViewDto>) this.redisValueCache.getCachedValue(this.redisValueCache.ALL_HISTORY_KEY);
    }

    private List<HistoryViewDto> getAllHistoryFromDatabase() {
        List<HistoryView> historyView = this.historyViewRepository.findAll();
        if (!historyView.isEmpty()) {
            List<HistoryViewDto> historyViewDtos = this.convertHistoryViewToDtos(historyView);
            this.redisValueCache.saveInCache(this.redisValueCache.ALL_HISTORY_KEY, historyViewDtos);
            return historyViewDtos;
        }
        return new ArrayList<>();
    }

    public List<HistoryViewDto> listItemHistory(String itemName) {
        Optional<List<HistoryView>> historyView = this.historyViewRepository.findByName(itemName);
        if (historyView.isPresent()) {
            List<HistoryView> history = historyView.get();
            return this.convertHistoryViewToDtos(history);
        }
        return new ArrayList<HistoryViewDto>();
    }

    private List<HistoryViewDto> convertHistoryViewToDtos(List<HistoryView> historyView) {
        List<HistoryViewDto> historyViewDtos = new ArrayList<>();
        for (HistoryView history : historyView) {
            HistoryViewDto historyViewDto = new HistoryViewDto();
            historyViewDto.setName(history.getName());
            historyViewDto.setDescription(history.getDescription());
            historyViewDto.setQuantity(history.getQuantity());
            historyViewDto.setCategory(history.getCategory());
            historyViewDto.setDate(history.getDate());
            historyViewDto.setEnabled(history.isEnabled());
            historyViewDtos.add(historyViewDto);
        }
        return historyViewDtos;
    }
}
