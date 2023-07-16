package com.gamevh.restcontroller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gamevh.dto.CouponDTO;
import com.gamevh.entities.CouponOwner;
import com.gamevh.mapper.CouponMapper;
import com.gamevh.service.CouponOwnerService;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/couponowner")
public class CouponOwnerRC {
	@Autowired
	CouponOwnerService couponOwnerService;
	
	@Autowired
	CouponMapper couponMapper;
	
	@GetMapping("getcoupon/{username}/{couponcode}")
	public ResponseEntity<CouponDTO> getAccountLogin(@PathVariable("username") Optional<String> username, @PathVariable("couponcode") Optional<String> couponcode) {		
		Optional<String> optionalUsername = username;
		Optional<String> optionalCouponcode = couponcode;

		if ((optionalUsername.isPresent() && !optionalUsername.get().trim().equals("")) && (optionalCouponcode.isPresent() && !optionalCouponcode.get().trim().equals(""))) {
			CouponOwner couponOwner = couponOwnerService.findCouponByAccount(optionalUsername.get(), optionalCouponcode.get());
			if (couponOwner != null) {
				CouponDTO couponDTO = couponMapper.modelToDto(couponOwner.getCoupon());
				return ResponseEntity.ok(couponDTO);
			}
		}

		return null;
	}
}
