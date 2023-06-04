package com.fourTL.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fourTL.entities.Coupon;

public interface CouponDAO extends JpaRepository<Coupon, String>{

}
