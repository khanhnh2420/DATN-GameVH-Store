package com.gamevh.restcontroller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gamevh.entities.OrderDetail;
import com.gamevh.service.OrderDetailService;
import com.gamevh.service.OrderService;
import com.gamevh.service.ProductService;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/orderdetail")
public class OrderDetailRC {
	@Autowired
	OrderDetailService orderDetailService;

	@Autowired
	OrderService orderService;
	
	@Autowired
	ProductService productService;

	@PostMapping("/create")
	public ResponseEntity<OrderDetail> createOrderAndUpdateCouponOwner(@RequestBody OrderDetail orderDetailData) {
		if (orderDetailData != null) {
			orderDetailService.add(orderDetailData);
			return ResponseEntity.ok(orderDetailData);
		}

		return null;
	}
}
