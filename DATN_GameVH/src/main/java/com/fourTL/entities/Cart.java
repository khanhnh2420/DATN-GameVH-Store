package com.fourTL.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Cart {
    private Product product;
    private Accessory accessory;
    private int quantity;
}
