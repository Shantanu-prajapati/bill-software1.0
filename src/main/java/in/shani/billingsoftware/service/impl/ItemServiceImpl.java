package in.shani.billingsoftware.service.impl;

import in.shani.billingsoftware.entity.CategoryEntity;
import in.shani.billingsoftware.entity.ItemEntity;
import in.shani.billingsoftware.io.ItemRequest;
import in.shani.billingsoftware.io.ItemResponse;
import in.shani.billingsoftware.repository.CategoryRepository;
import in.shani.billingsoftware.repository.ItemRepository;
import in.shani.billingsoftware.service.FileUploadService;
import in.shani.billingsoftware.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService {

    private final FileUploadService fileUploadService;
    private final CategoryRepository categoryRepository;
    private final ItemRepository itemRepository;

    @Override
    public ItemResponse add(ItemRequest request, MultipartFile file) throws IOException {
          String imgUrl = fileUploadService.uploadFile(file);

        ItemEntity newItem = convertToEntity(request);
        CategoryEntity existingCategory =categoryRepository.findByCategoryId(request.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Category not found: "+request.getCategoryId()));
          newItem.setCategory(existingCategory);
          newItem.setImgUrl(imgUrl);
         newItem = itemRepository.save(newItem);
         return convertToResponse(newItem);
    }

    private ItemResponse convertToResponse(ItemEntity newItem) {
        return ItemResponse.builder()
                .itemId(newItem.getItemId())
                .name(newItem.getName())
                .description(newItem.getDescription())
                .price(newItem.getPrice())
                .imgUrl(newItem.getImgUrl())
                .categoryName(newItem.getCategory().getName())
                .categoryId(newItem.getCategory().getCategoryId())
                .createdAt(newItem.getCreatedAt())
                .updatedAt(newItem.getUpdatedAt())
                .build();
    }

    private ItemEntity convertToEntity(ItemRequest request) {
        return ItemEntity.builder()
                .itemId(UUID.randomUUID().toString())
                .name(request.getName())
                .description(request.getDescription())
                .price(request.getPrice())
                .build();
    }

    @Override
    public List<ItemResponse> fetchItems() {
       return itemRepository.findAll()
                .stream()
                .map(itemEntity -> convertToResponse(itemEntity))
                .collect(Collectors.toList());
    }

    @Override
    public void deleteItem(String itemId) {
           ItemEntity existingItem= itemRepository.findByItemId(itemId)
                    .orElseThrow(() -> new RuntimeException("Item not found: "+itemId));
        boolean isFileDelete = fileUploadService.deleteFile(existingItem.getImgUrl());

        itemRepository.delete(existingItem);

    }
}
