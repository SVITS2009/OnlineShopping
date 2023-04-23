package com.talan.onlineshopping.service;

import com.talan.onlineshopping.dto.ItemCreateRequest;
import com.talan.onlineshopping.dto.ItemUpdateRequest;
import com.talan.onlineshopping.entity.Item;
import com.talan.onlineshopping.exception.ItemNotFoundException;

import java.util.List;

/**
 * Implements ItemService to declare
 * service API which interacts with repository
 */
public interface ItemService {
    Item saveItem(ItemCreateRequest itemCreateRequest);

    Item updatedItem(Long itemId, ItemUpdateRequest itemUpdateRequest) throws ItemNotFoundException;
    Item getItem(Long itemId) throws ItemNotFoundException;
    void deleteItem(Long id) throws ItemNotFoundException;
    List<Item> getAllItems(Integer pageNo, Integer pageSize);

    List<Item> searchItems(Integer pageNo, Integer pageSize, Long itemId, String itemName, Double price);
}
