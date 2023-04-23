package com.talan.onlineshopping.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "Item")
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Data
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Setter @Getter
    private Long itemId;

    @Setter @Getter
    @NonNull
    private String itemName;

    @Setter @Getter
    @NonNull
    private Integer quantity;

    @Setter @Getter
    @NonNull
    private Double price;

    @Setter @Getter
    private Double totalAmount;
}
