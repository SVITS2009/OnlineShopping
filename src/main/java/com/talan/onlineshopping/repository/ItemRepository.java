package com.talan.onlineshopping.repository;

import com.talan.onlineshopping.entity.Item;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ItemRepository extends JpaRepository<Item, Long> {
    Page<Item> findAll(Pageable pageable);
    List<Item> findByItemIdOrItemNameOrPrice(Long itemId, String itemName, Double price, Pageable pageable);
}
