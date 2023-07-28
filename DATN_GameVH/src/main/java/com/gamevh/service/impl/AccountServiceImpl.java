package com.gamevh.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.gamevh.entities.Account;
import com.gamevh.repository.AccountRepository;
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
	public Page<Account> findAll(Optional<Integer> pageNo, Optional<Integer> pageSize, String username, String name, String roleId) {
		Pageable pageable = PageRequest.of(pageNo.orElse(0), pageSize.orElse(6));

		// Áp dụng filter họ tên và roleId của account
		Page<Account> accounts = accountRepository.filterAccountByUsernameAndNameAndRoleId(pageable, username, name, roleId);
		return accounts;
	}

	@Override
	public Account findById(Integer AccountId) {
		return accountRepository.findById(AccountId);
	}

	@Override
	public List<Account> findByUsername(String username) {
		return accountRepository.findByUsernameContaining(username);
	}

	@Override
	public Account add(Account account) {
		return accountRepository.save(account);
	}

	@Override
	public List<Account> findByEmail(String email) {
		return accountRepository.findByEmailContaining(email);
	}	
}
