package com.talan.onlineshopping.service.impl;

import com.talan.onlineshopping.constant.Constants;
import com.talan.onlineshopping.dto.ItemCreateRequest;
import com.talan.onlineshopping.dto.ItemUpdateRequest;
import com.talan.onlineshopping.entity.Item;
import com.talan.onlineshopping.exception.ItemNotFoundException;
import com.talan.onlineshopping.kafka.Producer;
import com.talan.onlineshopping.repository.ItemRepository;
import com.talan.onlineshopping.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of ItemService
 * This class implements ItemService  API
 * This class is responsible to perform all validation on input param, Filter the
 * account statements based on Date and amount filter.
 */
@Service
public class ItemServiceImpl implements ItemService {

    @Autowired
    ItemRepository itemRepository;

    @Autowired
    Producer producer;
    @Override
    //@CachePut(value = "myCache", key = "#savedItem.itemId")
    public Item saveItem(ItemCreateRequest itemCreateRequest) {
        Item item = new Item();
        item.setItemName(itemCreateRequest.getItemName());
        item.setQuantity(itemCreateRequest.getQuantity());
        item.setPrice(itemCreateRequest.getPrice());
        item.setTotalAmount(itemCreateRequest.getPrice() * itemCreateRequest.getQuantity());
        Item savedItem = itemRepository.save(item);
        producer.sendMessage(savedItem.toString());
        return savedItem;

    }

    @Override
    public Item updatedItem(Long itemId, ItemUpdateRequest itemUpdateRequest) throws ItemNotFoundException {
        Item existingItem = itemRepository.findById(itemId)
                .orElseThrow(() -> new ItemNotFoundException(Constants.ITEM_NOT_FOUND + itemId));

        existingItem.setQuantity(itemUpdateRequest.getQuantity());
        existingItem.setPrice(itemUpdateRequest.getPrice());
        existingItem.setTotalAmount(itemUpdateRequest.getPrice() * itemUpdateRequest.getQuantity());
        return itemRepository.save(existingItem);
    }

    @Override
    //@Cacheable(value = "myCache", key = "#itemId")
    public Item getItem(Long itemId) throws ItemNotFoundException {
        return itemRepository.findById(itemId)
                .orElseThrow(() -> new ItemNotFoundException(Constants.ITEM_NOT_FOUND + itemId));
    }

    @Override
    //@CacheEvict(value = "myCache", key = "#itemId")
    public void deleteItem(Long itemId) throws ItemNotFoundException {
        Item existingItem = itemRepository.findById(itemId)
                .orElseThrow(() -> new ItemNotFoundException(Constants.ITEM_NOT_FOUND + itemId));

        itemRepository.delete(existingItem);
    }

    @Override
    public List<Item> getAllItems(Integer pageNo, Integer pageSize) {
        Pageable paging = PageRequest.of(pageNo, pageSize);

        Page<Item> pagedResult = itemRepository.findAll(paging);

        if (pagedResult.hasContent()) {
            return pagedResult.getContent();
        } else {
            return new ArrayList<>();
        }
    }

    @Override
    public List<Item> searchItems(Integer pageNo, Integer pageSize, Long itemId, String itemName, Double price) {
        Pageable paging = PageRequest.of(pageNo, pageSize);
        return itemRepository.findByItemIdOrItemNameOrPrice(itemId, itemName, price, paging);
    }
}
