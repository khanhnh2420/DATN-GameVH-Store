package com.fourTL.entities;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@SuppressWarnings("serial")
@Data
@Entity(name="coupon_owner")
public class CouponOwner implements Serializable{
	@Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="Id", unique=true, nullable=false, precision=19)
    private long id;
    @Column(name="Status", nullable=false, length=1)
    private boolean status;
    @ManyToOne(optional=false)
    @JoinColumn(name="Username", nullable=false)
    private Account account;
    @ManyToOne(optional=false)
    @JoinColumn(name="CouponCode", nullable=false)
    private Coupon coupon;
}
