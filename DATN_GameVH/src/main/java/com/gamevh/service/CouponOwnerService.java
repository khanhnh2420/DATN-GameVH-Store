package com.gamevh.service;

import java.util.List;

import com.gamevh.entities.CouponOwner;

public interface CouponOwnerService {
	CouponOwner findCouponByAccount(String username, String couponcode);
	
	List<CouponOwner> findCouponByUsername(String username);
	
	CouponOwner update(CouponOwner couponOwner);
}
