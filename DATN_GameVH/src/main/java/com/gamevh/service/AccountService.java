package com.gamevh.service;

import java.util.List;
import java.util.Optional;

import com.gamevh.entities.Account;
import org.springframework.data.domain.Page;


public interface AccountService {

	List<Account> findAll();
	Page<Account> findAll(Optional<Integer> pageNo, Optional<Integer> pageSize,
						  String username, String name, String roleId);

	Account findById(Integer AccountId);
	
	Account add(Account account);
	
	Account findByUsername(String username);
	
	Account findByEmail(String email);
}
