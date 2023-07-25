package io.github.pedromeerholz.stock.api.controller.item;

import io.github.pedromeerholz.stock.api.model.itemCategory.ItemCategory;
import io.github.pedromeerholz.stock.api.model.itemCategory.dto.ItemCategoryDto;
import io.github.pedromeerholz.stock.api.model.responsesDtos.ResponseDto;
import io.github.pedromeerholz.stock.api.service.item.ItemCategoryService;
import io.swagger.v3.oas.annotations.Operation;
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
    @Operation(summary = "Visualize all registered item categories", method = "GET")
    public List<ItemCategoryDto> listAll() {
        return this.itemCategoryService.listAll();
    }

    @GetMapping("/listAllByStatus")
    @Operation(summary = "Visualize all registered item categories by status", method = "GET")
    public List<ItemCategoryDto> listAllCategoriesByStatus(@RequestParam boolean status) {
        return this.itemCategoryService.listAllCategoriesByStatus(status);
    }

    @PutMapping("/update")
    @Operation(summary = "Update a registered item category", method = "PUT")
    public ResponseEntity<ResponseDto> updateItemCategory(@RequestBody ItemCategoryDto itemCategoryDto, @RequestParam String categoryToUpdate, @RequestParam String email, @RequestHeader("Authorization") String authorizationToken) {
        return this.itemCategoryService.updateItemCategory(itemCategoryDto, categoryToUpdate, email, authorizationToken);
    }
}
