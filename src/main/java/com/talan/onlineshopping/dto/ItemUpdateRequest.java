package com.talan.onlineshopping.dto;

import lombok.*;

import java.io.Serializable;

/**
 * Implements ItemUpdateRequest as DTO to store Item
 * information when we do online shopping from customer end
 */
@Setter
@Getter
@NoArgsConstructor
public class ItemUpdateRequest implements Serializable {

    private static final long serialVersionUID = 1L;

    @NonNull
    private Integer quantity;

    @NonNull
    private Double price;
}
