package com.fourTL.controller.admin.Account;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fourTL.dao.AccountDAO;
import com.fourTL.dao.CategoryDAO;
import com.fourTL.entities.Account;



@CrossOrigin("*")
@RestController
@RequestMapping("/admin/api/accounts")
public class AccountsRestAPI {
	@Autowired
	AccountDAO accountsDAO;

	@Autowired
	CategoryDAO categoriesDAO;
	
	@GetMapping("")
	public ResponseEntity<Page<Account>> getAllAccounts(
			@RequestParam("page") Optional<Integer> page, 
			@RequestParam("size") Optional<Integer> size) {
		Pageable pageable = PageRequest.of(page.orElse(0), size.orElse(6));
		Page<Account> accounts = accountsDAO.findAll(pageable);
		return ResponseEntity.ok(accounts);
	}
	
	@GetMapping("/search/{search}")
	public ResponseEntity<List<Account>> search(Model model, @PathVariable("search") String search) {
		return ResponseEntity.ok(accountsDAO.findByUsernameContaining(search));
	}
	
	@GetMapping("{id}")
	public ResponseEntity<Account> getOne(@PathVariable("id") String username) {
		if(!accountsDAO.existsById(username)) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(accountsDAO.findById(username).get());
	}
	
	@DeleteMapping("{id}")
	public ResponseEntity<Void> delete(@PathVariable("id") String username) {
		if(!accountsDAO.existsById(username)) {
			return ResponseEntity.notFound().build();
		}
		accountsDAO.deleteById(username);
		return ResponseEntity.ok().build();
	}
}
