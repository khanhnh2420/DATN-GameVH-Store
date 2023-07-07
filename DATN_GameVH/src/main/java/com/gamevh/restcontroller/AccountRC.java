package com.gamevh.restcontroller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
				AccountDTO accountDTO = accountMapper.modelToDto(accounts.get(0));
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
	
	@GetMapping("email/{email}")
	public ResponseEntity<AccountDTO> getAccountByEmail(@PathVariable("email") Optional<String> email) {
		Optional<String> optionalEmail = email;

		if (optionalEmail.isPresent() && !optionalEmail.get().trim().equals("")) {
			List<Account> accounts = accountService.findByEmail(optionalEmail.get());
			if (!accounts.isEmpty()) {
				AccountDTO accountDTO = accountMapper.modelToDto(accounts.get(0));
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

	@PostMapping("create")
	public ResponseEntity<Account> getAllAccounts(@RequestBody Account account) {
		if(accountService.findByUsername(account.getUsername()).isEmpty() && accountService.findByEmail(account.getEmail()).isEmpty()) {
			accountService.add(account);
		}
		return ResponseEntity.ok(account);
	}
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
