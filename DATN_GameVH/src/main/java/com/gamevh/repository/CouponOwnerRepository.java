package com.gamevh.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.gamevh.entities.CouponOwner;

public interface CouponOwnerRepository extends JpaRepository<CouponOwner, Long>{
	@Query("SELECT c FROM CouponOwner c WHERE (c.coupon.status = true) AND (c.status = true) AND (c.account.username = :username) AND (c.coupon.code = :couponcode)")
	CouponOwner findCouponByAccount(String username, String couponcode);
}
