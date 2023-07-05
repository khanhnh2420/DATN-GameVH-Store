package com.gamevh.restcontroller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gamevh.dto.AccountDTO;
import com.gamevh.entities.Account;
import com.gamevh.mapper.AccountMapper;
import com.gamevh.service.AccountService;
import com.gamevh.service.AuthorityService;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/account")
public class AccountRC {
	@Autowired
	AccountService accountService;

	@Autowired
	AuthorityService authorityService;

	@Autowired
	AccountMapper accountMapper;

	@GetMapping("{username}")
	public ResponseEntity<AccountDTO> getAccountLogin(@PathVariable("username") Optional<String> username) {
		Optional<String> optionalUsername = username;

		if (optionalUsername.isPresent() && !optionalUsername.get().trim().equals("")) {
			List<Account> accounts = accountService.findByUsername(optionalUsername.get());
			if (!accounts.isEmpty()) {
				AccountDTO accountDTO = accountMapper.modelToLoginDto(accounts.get(0));
				if (accountDTO != null) {
					if (authorityService.findByAccountAndRole(accountDTO.getId(), "CUST") != null) {
						accountDTO.setRole("CUST");
					}
				}
				return ResponseEntity.ok(accountDTO);
			}
		}

		return null;
	}

//	@GetMapping("")
//	public ResponseEntity<Page<Account>> getAllAccounts(
//			@RequestParam("page") Optional<Integer> page, 
//			@RequestParam("size") Optional<Integer> size) {
//		Pageable pageable = PageRequest.of(page.orElse(0), size.orElse(6));
//		Page<Account> accounts = accountsDAO.findAll(pageable);
//		return ResponseEntity.ok(accounts);
//	}
//	
//	@GetMapping("/search/{search}")
//	public ResponseEntity<List<Account>> search(Model model, @PathVariable("search") String search) {
//		return ResponseEntity.ok(accountsDAO.findByUsernameContaining(search));
//	}
//	
//	@GetMapping("{id}")
//	public ResponseEntity<Account> getOne(@PathVariable("id") String username) {
//		if(!accountsDAO.existsById(username)) {
//			return ResponseEntity.notFound().build();
//		}
//		return ResponseEntity.ok(accountsDAO.findById(username).get());
//	}
//	
//	@DeleteMapping("{id}")
//	public ResponseEntity<Void> delete(@PathVariable("id") String username) {
//		if(!accountsDAO.existsById(username)) {
//			return ResponseEntity.notFound().build();
//		}
//		accountsDAO.deleteById(username);
//		return ResponseEntity.ok().build();
//	}
}
