package com.talan.onlineshopping.controller;

import com.talan.onlineshopping.dto.ItemCreateRequest;
import com.talan.onlineshopping.dto.ItemUpdateRequest;
import com.talan.onlineshopping.entity.Item;
import com.talan.onlineshopping.exception.ItemNotFoundException;
import com.talan.onlineshopping.service.ItemService;
import io.swagger.annotations.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Implements ItemController to add APIs
 */
@Tag(name = "Items", description = "Endpoints for Item operations")
@RestController
@Slf4j
@RequestMapping("/api/v1")
public class ItemController {

    @Autowired
    ItemService itemService;

    @Operation(summary = "Insert Item,", description = "Insert a new Item")
    @ApiResponse(code = 201, message = "Successful Operation",
            response = Item.class)
    @PostMapping("/item")
    public ResponseEntity<Item> createItem(@RequestBody ItemCreateRequest itemCreateRequest) {
        return new ResponseEntity<>(itemService.saveItem(itemCreateRequest), HttpStatus.CREATED);
    }

    @Operation(summary = "Insert multiple Items,", description = "Insert a new multiple Items")
    @ApiResponse(code = 201, message = "Successful Operation",
            response = String.class)
    @PostMapping("/items")
    public ResponseEntity<String> createListOfItems(@RequestBody List<ItemCreateRequest> itemCreateRequestList) {
        for (ItemCreateRequest itemCreateRequest : itemCreateRequestList) {
            itemService.saveItem(itemCreateRequest);
        }
        return new ResponseEntity<>("All Items created successfully", HttpStatus.CREATED);
    }

    @Operation(summary = "Update Item,", description = "Update a existing Item")
    @ApiResponse(code = 200, message = "Successful Operation",
            response = Item.class)
    @PutMapping("/item/{id}")
    public ResponseEntity<Item> updateItem(@PathVariable(value = "id") Long itemId,
                                           @RequestBody ItemUpdateRequest itemUpdateRequest) throws ItemNotFoundException {
        return ResponseEntity.ok(itemService.updatedItem(itemId, itemUpdateRequest));
    }

    @Operation(summary = "Fetch a specific Item,", description = "Fetch the item using itemId")
    @ApiResponse(code = 200, message = "Successful Operation",
            response = Item.class)
    @GetMapping("/item/{id}")
    public ResponseEntity<Item> getItemById(@PathVariable(value = "id") Long itemId)
            throws ItemNotFoundException {
        return ResponseEntity.ok().body(itemService.getItem(itemId));
    }

    @Operation(summary = "Fetch all Items,", description = "Fetch all items using pagination")
    @ApiResponse(code = 200, message = "Successful Operation",
            response = List.class)
    @GetMapping("/items")
    public List<Item> getAllItems(@RequestParam(defaultValue = "0") Integer pageNo,
                                  @RequestParam(defaultValue = "10") Integer pageSize) {
        return itemService.getAllItems(pageNo, pageSize);
    }

    @Operation(summary = "Search Item,", description = "Search items based on itemId," +
            " itemName and price. " +
            "If there are no filter and items are available in DB." +
            " The search api will display all records but we are using pagination which have default pageSize as a 10.")
    @ApiResponse(code = 200, message = "Successful Operation",
            response = List.class)
    @GetMapping("/search")
    public List<Item> searchItems(@RequestParam(defaultValue = "0") Integer pageNo,
                                  @RequestParam(defaultValue = "10") Integer pageSize,
                                  @RequestParam(required = false) Long itemId,
                                  @RequestParam(required = false) String itemName,
                                  @RequestParam(required = false) Double price) {
        if (itemId == null && itemName == null && price == null) {
            return itemService.getAllItems(pageNo, pageSize);
        }
        return itemService.searchItems(pageNo, pageSize, itemId, itemName, price);
    }

    @Operation(summary = "Delete Item,", description = "Delete a existing Item")
    @ApiResponse(code = 200, message = "Successful Operation",
            response = String.class)
    @DeleteMapping("/item/{id}")
    public ResponseEntity<String> deleteItemById(@PathVariable(value = "id") Long itemId)
            throws ItemNotFoundException {
        itemService.deleteItem(itemId);
        return ResponseEntity.ok().body("Item deleted :: " + itemId);
    }
}
