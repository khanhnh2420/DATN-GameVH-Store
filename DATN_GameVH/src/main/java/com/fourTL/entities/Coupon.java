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
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Data;

@SuppressWarnings("serial")
@Data
@Entity
@Table(name="coupon", indexes={@Index(name="coupon_Code_IX", columnList="Code", unique=true)})
public class Coupon implements Serializable{
	@Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="Id", unique=true, nullable=false, precision=10)
    private Integer id;
	
    @Column(name="CouponName", nullable=false, length=255)
    private String couponName;
    
    @Column(name="Code", unique=true, nullable=false, length=10)
    private String code;
    
    @Column(name="Amount", nullable=false, precision=10)
    private Integer amount;
    
    @Column(name="Value", nullable=false, precision=22)
    private Double value;
    
    @Column(name="MinSpend", nullable=false, precision=22)
    private Double minSpend;
    
    @Temporal(TemporalType.DATE)
    @Column(name="MfgDate", nullable=false)
    private Date mfgDate;
    
    @Temporal(TemporalType.DATE)
    @Column(name="ExpDate", nullable=false)
    private Date expDate;
    
    @Column(name="Status", nullable=false, length=1)
    private Boolean status;
    
    @Column(name="Image", nullable=false, length=50)
    private String image;
    
    @Column(name="Description", length=255)
    private String description;
    
    @JsonIgnore
    @OneToMany(mappedBy="coupon")
    private List<CouponOwner> couponOwner;
}
