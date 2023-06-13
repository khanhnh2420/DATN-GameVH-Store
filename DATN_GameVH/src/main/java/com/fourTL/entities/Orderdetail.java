package com.fourTL.entities;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@SuppressWarnings("serial")
@Data
@Entity(name="orderdetail")
public class Orderdetail  implements Serializable{
	@Id
    @Column(name="Id", unique=true, nullable=false, precision=19)
    private long id;
    @Column(name="Price", precision=22)
    private double price;
    @Column(name="AccessoryQty", nullable=false, precision=10)
    private int accessoryQty;
    @ManyToOne
    @JoinColumn(name="AccessoryId")
    private Accessory accessory;
    @ManyToOne
    @JoinColumn(name="order_dataId")
    private OrderData orderData;
    @ManyToOne
    @JoinColumn(name="ProductId")
    private Product product;
}
