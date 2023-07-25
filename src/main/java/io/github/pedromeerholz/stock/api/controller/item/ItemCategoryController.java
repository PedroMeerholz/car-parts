package io.github.pedromeerholz.stock.api.controller.item;

import io.github.pedromeerholz.stock.api.model.item.itemCategory.ItemCategory;
import io.github.pedromeerholz.stock.api.model.item.itemCategory.dto.ItemCategoryDto;
import io.github.pedromeerholz.stock.api.model.responsesDtos.ResponseDto;
import io.github.pedromeerholz.stock.api.service.item.ItemCategoryService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/itemCategory")
public class ItemCategoryController {
    private final ItemCategoryService itemCategoryService;

    public ItemCategoryController(ItemCategoryService itemCategoryService) {
        this.itemCategoryService = itemCategoryService;
    }

    @PostMapping("/create")
    @Operation(summary = "Create a new item category", method = "POST")
    public ResponseEntity<ResponseDto> createItemCategory(@RequestBody ItemCategoryDto itemCategoryDto, @RequestParam String email, @RequestHeader("Authorization") String authorizationToken) {
        return this.itemCategoryService.createItemCategory(itemCategoryDto, email, authorizationToken);
    }

    @GetMapping("/listAll")
    @Operation(summary = "Visualize all registered item cateogories", method = "GET")
    public List<ItemCategory> listAll() {
        return this.itemCategoryService.listAll();
    }

    @PutMapping("/update")
    @Operation(summary = "Update a registered item category", method = "PUT")
    public ResponseEntity<ResponseDto> updateItemCategory(@RequestBody ItemCategoryDto itemCategoryDto, @RequestParam String categoryToUpdate, @RequestParam String email, @RequestHeader("Authorization") String authorizationToken) {
        return this.itemCategoryService.updateItemCategory(itemCategoryDto, categoryToUpdate, email, authorizationToken);
    }
}
