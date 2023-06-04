package com.fourTL.controller.site.LoginAndRegister;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.fourTL.dao.AccountDAO;
import com.fourTL.dao.AuthorityDAO;
import com.fourTL.dao.RoleDAO;
import com.fourTL.entities.Account;
import com.fourTL.entities.Authority;
import com.fourTL.entities.Role;

@CrossOrigin("*")
@RestController
public class RegisterRestController {
	@Autowired
	AccountDAO accountsDAO;

	@Autowired
	AuthorityDAO authoritiesDAO;

	@Autowired
	RoleDAO rolesDAO;

	@Autowired
	BCryptPasswordEncoder pe;

	@PostMapping("/register")
	public ResponseEntity<Account> post(@RequestBody Account account) {
		if (accountsDAO.existsById(account.getUsername())) {
			return ResponseEntity.badRequest().build();
		} else {
			if (account.getUsername() != null && account.getPassword() != null && account.getFullname() != null
					&& account.getEmail() != null && account.getAddress() != null) {
				account.setPhoto("user.png");
				account.setPassword(pe.encode(account.getPassword()));
				Role role = rolesDAO.findById("CUST").get();

				Authority authorities = new Authority();
				authorities.setAccount(account);
				authorities.setRole(role);

				accountsDAO.save(account);
				authoritiesDAO.save(authorities);
				return ResponseEntity.ok(account);
			} else {
				return ResponseEntity.badRequest().build();
			}
		}
	}
}
