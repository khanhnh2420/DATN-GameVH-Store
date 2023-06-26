package com.fourTL.controller.admin;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fourTL.dao.OrderDetailDAO;
import com.fourTL.entities.OrderDetail;

@RestController
public class testController {

	@Autowired
	OrderDetailDAO ad;
	
	@GetMapping("/t")
	public ResponseEntity<List<OrderDetail>> getIndex() {
		return ResponseEntity.ok(ad.findAll());
	}

	
}
