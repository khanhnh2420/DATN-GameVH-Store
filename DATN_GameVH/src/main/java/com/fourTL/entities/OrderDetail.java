package com.fourTL.entities;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@SuppressWarnings("serial")
@Data
@Entity
@Table(name="order_detail")
public class OrderDetail  implements Serializable{
	@Id
    @Column(name="Id", unique=true, nullable=false, precision=19)
    private Long id;
	
    @Column(name="Price", precision=22)
    private Double price;
    
    @Column(name="AccessoryQty", nullable=false, precision=10)
    private Integer accessoryQty;
    
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
