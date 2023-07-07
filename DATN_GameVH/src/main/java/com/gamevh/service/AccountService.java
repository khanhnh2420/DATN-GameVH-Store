package com.gamevh.service;

import java.util.List;

import com.gamevh.entities.Account;


public interface AccountService {

	List<Account> findAll();

	Account findById(Integer AccountId);
	
	Account add(Account account);
	
	List<Account> findByUsername(String username);
	
	List<Account> findByEmail(String email);
}
