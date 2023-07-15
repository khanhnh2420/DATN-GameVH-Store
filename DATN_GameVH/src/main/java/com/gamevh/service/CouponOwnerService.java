package com.gamevh.service;

import com.gamevh.entities.CouponOwner;

public interface CouponOwnerService {
	CouponOwner findCouponByAccount(String username, String couponcode);
}
