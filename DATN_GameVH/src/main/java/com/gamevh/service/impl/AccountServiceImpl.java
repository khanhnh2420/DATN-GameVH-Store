package com.gamevh.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gamevh.entities.Account;
import com.gamevh.reponsitory.AccountRepository;
import com.gamevh.service.AccountService;


@Service
public class AccountServiceImpl implements AccountService {

	@Autowired
	AccountRepository accountRepository;

	@Override
	public List<Account> findAll() {
		return accountRepository.findAll();
	}

	@Override
	public Account findById(Integer AccountId) {
		return accountRepository.findById(AccountId);
	}

	@Override
	public List<Account> findByUsername(String username) {
		return accountRepository.findByUsernameContaining(username);
	}
	
}
