package com.gamevh.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gamevh.entities.CouponOwner;
import com.gamevh.repository.CouponOwnerRepository;
import com.gamevh.service.CouponOwnerService;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class CouponOwnerServiceImpl implements CouponOwnerService{
	@Autowired
	CouponOwnerRepository couponOwnerRepository;

	@Override
	public CouponOwner findCouponByAccount(String username, String couponcode) {
		return couponOwnerRepository.findCouponByAccount(username, couponcode);
	}

	@Override
	public CouponOwner update(CouponOwner couponOwner) {
		return couponOwnerRepository.save(couponOwner);
	}
	
}
