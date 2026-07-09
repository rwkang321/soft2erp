package com.soft2erp.erp.master.item;

import com.soft2erp.erp.common.response.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/master/items")
@RequiredArgsConstructor
public class ItemController {

    private final ItemService itemService;

    @GetMapping
    public ApiResponse<List<ItemDto>> findItems(ItemSearchCondition condition) {
        return ApiResponse.ok(itemService.findItems(condition));
    }

    @GetMapping("/{itemCode}")
    public ApiResponse<ItemDto> findItem(@PathVariable String itemCode) {
        return ApiResponse.ok(itemService.findItem(itemCode));
    }

    @PostMapping
    public ApiResponse<ItemDto> createItem(@Valid @RequestBody ItemCreateRequest request) {
        return ApiResponse.ok("Created", itemService.createItem(request));
    }

    @PutMapping("/{itemCode}")
    public ApiResponse<ItemDto> updateItem(
            @PathVariable String itemCode,
            @Valid @RequestBody ItemUpdateRequest request
    ) {
        return ApiResponse.ok("Updated", itemService.updateItem(itemCode, request));
    }

    @DeleteMapping("/{itemCode}")
    public ApiResponse<Void> deleteItem(@PathVariable String itemCode) {
        itemService.deleteItem(itemCode);
        return ApiResponse.ok("Deleted", null);
    }
}

