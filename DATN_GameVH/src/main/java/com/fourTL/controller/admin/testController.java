package com.fourTL.controller.admin;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fourTL.dao.AccountDAO;
import com.fourTL.entities.Account;

@RestController
public class testController {

	@Autowired
	AccountDAO ad;
	
	@GetMapping("/t")
	public ResponseEntity<List<Account>> getIndex() {
		return ResponseEntity.ok(ad.findAll());
	}

	
}
