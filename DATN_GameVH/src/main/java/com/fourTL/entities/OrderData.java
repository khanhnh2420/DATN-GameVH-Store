package com.fourTL.entities;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Data;

@SuppressWarnings("serial")
@Data
@Entity
@Table(name="order_data", indexes={@Index(name="order_data_OrderId_IX", columnList="OrderId", unique=true), @Index(name="order_data_PaymentCode_IX", columnList="PaymentCode", unique=true)})
public class OrderData  implements Serializable{
	@Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="Id", unique=true, nullable=false, precision=19)
    private Long id;
	
    @Column(name="OrderId", unique=true, nullable=false, length=14)
    private String orderId;
    
    @Column(name="Fullname", nullable=false, length=50)
    private String fullname;
    
    @Temporal(TemporalType.DATE)
    @Column(name="CreateDate")
    private Date createDate;
    
    @Column(name="Address", length=255)
    private String address;
    
    @Column(name="City", nullable=false, length=100)
    private String city;
    
    @Column(name="District", nullable=false, length=100)
    private String district;
    
    @Column(name="Ward", nullable=false, length=100)
    private String ward;
    
    @Column(name="PaymentType", nullable=false, length=6)
    private String paymentType;
    
    @Column(name="ShippingFee", nullable=false, precision=22)
    private Double shippingFee;
    
    @Column(name="CouponCode", length=10)
    private String couponCode;
    
    @Column(name="Email", nullable=false, length=50)
    private String email;
    
    @Column(name="Phone", nullable=false, length=10)
    private String phone;
    
    @Column(name="Status", nullable=false, length=13)
    private String status;
    
    @Column(name="Note", length=255)
    private String note;
    
    @Column(name="TotalPrice", nullable=false, precision=22)
    private Double totalPrice;
    
    @Column(name="Qty", nullable=false, precision=10)
    private Integer qty;
    
    @Column(name="PaymentCode", unique=true, length=100)
    private String paymentCode;
    
    @ManyToOne(optional=false)
    @JoinColumn(name="AccountId", nullable=false)
    private Account account;
    
    @JsonIgnore
    @OneToMany(mappedBy="orderData")
    private List<OrderDetail> orderDetail;
}