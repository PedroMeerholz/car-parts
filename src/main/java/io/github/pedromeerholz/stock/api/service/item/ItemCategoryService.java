package io.github.pedromeerholz.stock.api.service.item;

import io.github.pedromeerholz.stock.api.model.itemCategory.ItemCategory;
import io.github.pedromeerholz.stock.api.model.itemCategory.dto.ItemCategoryDto;
import io.github.pedromeerholz.stock.api.model.responsesDtos.ErrorMessageDto;
import io.github.pedromeerholz.stock.api.model.responsesDtos.ResponseDto;
import io.github.pedromeerholz.stock.api.repository.UserRepository;
import io.github.pedromeerholz.stock.api.repository.item.ItemCategoryRepository;
import io.github.pedromeerholz.stock.validations.AuthorizationTokenValidator;
import io.github.pedromeerholz.stock.validations.itemValidations.CategoryValidation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ItemCategoryService {
    private final ItemCategoryRepository itemCategoryRepository;
    private final UserRepository userRepository;
    private final CategoryValidation categoryValidation;
    private final AuthorizationTokenValidator authorizationTokenValidator;

    public ItemCategoryService(ItemCategoryRepository itemCategoryRepository, UserRepository userRepository) {
        this.itemCategoryRepository = itemCategoryRepository;
        this.userRepository = userRepository;
        this.authorizationTokenValidator = new AuthorizationTokenValidator();
        this.categoryValidation = new CategoryValidation();
    }

    public ResponseEntity<ResponseDto> createItemCategory(ItemCategoryDto itemCategoryDto, String email, String authorizationToken) {
        try {
            boolean isUserAuthorized = this.authorizationTokenValidator.validateAuthorizationToken(this.userRepository, email, authorizationToken);
            boolean isValidCategory = this.categoryValidation.validateCategory(this.itemCategoryRepository, itemCategoryDto.getCategory());
            if (isUserAuthorized && isValidCategory) {
                ItemCategory itemCategory = new ItemCategory();
                itemCategory.setCategory(itemCategoryDto.getCategory());
                itemCategory.setEnabled(itemCategoryDto.isEnabled());
                this.itemCategoryRepository.save(itemCategory);
                return new ResponseEntity(HttpStatus.OK);
            }
            return new ResponseEntity(new ErrorMessageDto("O usuário informado não está autorizado para acessar esse serviço"), HttpStatus.UNAUTHORIZED);
        } catch (Exception exception) {
            exception.printStackTrace();
            return new ResponseEntity(new ErrorMessageDto(exception.getCause().getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public List<ItemCategoryDto> listAll() {
        List<ItemCategory> categories = this.itemCategoryRepository.findAll();
        if (!categories.isEmpty()) {
            List<ItemCategoryDto> categoryDtos = new ArrayList<>();
            for (ItemCategory itemCategory : categories) {
                ItemCategoryDto itemCategoryDto = new ItemCategoryDto();
                itemCategoryDto.setCategory(itemCategory.getCategory());
                itemCategoryDto.setEnabled(itemCategory.isEnabled());
                categoryDtos.add(itemCategoryDto);
            }
            return categoryDtos;
        }
        return new ArrayList<>();
    }

    public List<ItemCategoryDto> listAllCategoriesByStatus(boolean status) {
        Optional<List<ItemCategory>> optionalItemCategories = this.itemCategoryRepository.findByStatus(status);
        if (optionalItemCategories.isPresent()) {
            List<ItemCategory> categories = optionalItemCategories.get();
            return this.convertItemCategoryListToItemCategoryDtoList(categories);
        }
        return new ArrayList<>();
    }

    private List<ItemCategoryDto> convertItemCategoryListToItemCategoryDtoList(List<ItemCategory> categories) {
        List<ItemCategoryDto> itemCategoryDtos = new ArrayList<>();
        for (ItemCategory category : categories) {
            ItemCategoryDto itemCategoryDto = new ItemCategoryDto();
            itemCategoryDto.setCategory(category.getCategory());
            itemCategoryDto.setEnabled(category.isEnabled());
            itemCategoryDtos.add(itemCategoryDto);
        }
        return itemCategoryDtos;
    }

    public ResponseEntity<ResponseDto> updateItemCategory(ItemCategoryDto itemCategoryDto, String categoryToUpdate, String email, String authorizationToken) {
        try {
            boolean isUserAuthorized = this.authorizationTokenValidator.validateAuthorizationToken(this.userRepository, email, authorizationToken);
            if (!isUserAuthorized) {
                return new ResponseEntity(new ErrorMessageDto("O usuário informado não está autorizado para acessar esse serviço"), HttpStatus.UNAUTHORIZED);
            }
            Optional<ItemCategory> optionalCurrentItemCategory = this.itemCategoryRepository.findByCategory(categoryToUpdate);
            if (optionalCurrentItemCategory.isPresent()) {
                ItemCategory currentItemCategory = optionalCurrentItemCategory.get();
                System.out.println(currentItemCategory);
                ItemCategory updatedItemCategory = new ItemCategory();
                updatedItemCategory.setId(currentItemCategory.getId());
                updatedItemCategory.setCategory(itemCategoryDto.getCategory());
                updatedItemCategory.setEnabled(itemCategoryDto.isEnabled());
                this.itemCategoryRepository.save(updatedItemCategory);
                return new ResponseEntity(HttpStatus.OK);
            }
            return new ResponseEntity(new ErrorMessageDto("A categoria informada não existe"), HttpStatus.NOT_ACCEPTABLE);
        } catch (Exception exception) {
            exception.printStackTrace();
            return new ResponseEntity(new ErrorMessageDto(exception.getCause().getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
