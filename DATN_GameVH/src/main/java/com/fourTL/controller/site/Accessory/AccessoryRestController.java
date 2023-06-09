package com.fourTL.controller.site.Accessory;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fourTL.dao.AccessoryDAO;
import com.fourTL.entities.Accessory;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/accessory")
public class AccessoryRestController {
	@Autowired
	AccessoryDAO accessoryDAO;

	@GetMapping("/getall")
	public ResponseEntity<List<Accessory>> getAll() {
		List<Accessory> accessorys = accessoryDAO.findAll();
	    Collections.reverse(accessorys);
		return ResponseEntity.ok(accessoryDAO.findAll());
	}
}
