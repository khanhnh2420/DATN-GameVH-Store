package com.fourTL.controller.site.MyAccount;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fourTL.dao.AccountDAO;
import com.fourTL.dao.OrderDataDAO;
import com.fourTL.dao.OrderDetailDAO;
import com.fourTL.entities.Account;

@CrossOrigin("*")
@RestController
@RequestMapping("/account")
public class MyAccountRestController {
	@Autowired
	AccountDAO accountsDAO;

	@Autowired
	OrderDataDAO ordersDAO;

	@Autowired
	OrderDetailDAO orderDetailsDAO;

	@GetMapping("{username}")
	public ResponseEntity<Account> getOne(@PathVariable("username") String username) {
		if (!accountsDAO.existsById(username)) {
			return ResponseEntity.notFound().build();
		}
		Account user = accountsDAO.findById(username).get();
		user.getOrderData().sort((o1, o2) -> o2.getCreateDate().compareTo(o1.getCreateDate()));
		return ResponseEntity.ok(user);
	}
	
	@PutMapping("{username}")
	public ResponseEntity<Account> put(@PathVariable("username") String username, @RequestBody Account account) {
		if(!accountsDAO.existsById(username)) {
			return ResponseEntity.notFound().build();
		}
		accountsDAO.save(account);
		return ResponseEntity.ok(account);
	}
}
