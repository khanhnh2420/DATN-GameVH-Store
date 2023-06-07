package com.fourTL.controller.site.ShopAccessory;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fourTL.dao.AccessoryDAO;
import com.fourTL.entities.Accessory;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/accessory")
public class ShopAccessoryRestController {

	@Autowired
	AccessoryDAO accessoryDAO;

	@RequestMapping("/getAll")
	public ResponseEntity<Page<Accessory>> getAllAccessories(
			@RequestParam("page") Optional<Integer> page, 
			@RequestParam("size") Optional<Integer> size) {
		Pageable pageable = PageRequest.of(page.orElse(0), size.orElse(9));
		Page<Accessory> accessories = accessoryDAO.findAll(pageable);
	    return ResponseEntity.ok(accessories);
	}
}
