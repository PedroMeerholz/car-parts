package io.github.pedromeerholz.stock.api.controller.item;

import io.github.pedromeerholz.stock.api.model.item.views.HistoryView;
import io.github.pedromeerholz.stock.api.model.item.dto.NewItemDto;
import io.github.pedromeerholz.stock.api.model.item.dto.UpdateItemDto;
import io.github.pedromeerholz.stock.api.model.item.views.ItemsView;
import io.github.pedromeerholz.stock.api.model.responsesDtos.ResponseDto;
import io.github.pedromeerholz.stock.api.service.item.ItemService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/item")
public class ItemController {
    private final ItemService itemService;

    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    @PostMapping("/create")
    @Operation(summary = "Register a new item", method = "POST")
    public ResponseEntity<ResponseDto> createItem(@RequestBody NewItemDto newItemDto, @RequestParam String email, @RequestHeader("Authorization") String authorizationToken) {
        return this.itemService.createItem(newItemDto, email, authorizationToken);
    }

    @PatchMapping("/updateInfo")
    @Operation(summary = "Update the fields name, description, category and enabled from a registered item", method = "PATCH")
    public ResponseEntity<ResponseDto> updateItemInfo(@RequestBody UpdateItemDto updateItemDto, @RequestParam String itemToUpdate, @RequestParam String email, @RequestHeader("Authorization") String authorizationtoken) {
        return this.itemService.updateItemInfo(updateItemDto, itemToUpdate, email, authorizationtoken);
    }

    @PatchMapping("/updateQuantity")
    @Operation(summary = "Increase or decrease the available amount of registered item", method = "PATCH")
    public ResponseEntity<ResponseDto> updateItemQuantity(@RequestParam String itemToUpdate, @RequestParam int quantityToUpdate, @RequestParam String email, @RequestHeader("Authorization") String authorizationtoken) {
        return this.itemService.updateItemQuantity(itemToUpdate, quantityToUpdate, email, authorizationtoken);
    }

    @GetMapping("/listAll")
    @Operation(summary = "Visualize all registered items", method = "GET")
    public List<ItemsView> listAll() {
        return this.itemService.listAll();
    }

    @GetMapping("/listHistory")
    @Operation(summary = "View all update history of items information", method = "GET")
    public List<HistoryView> listHistory() {
        return this.itemService.listHistory();
    }
}
