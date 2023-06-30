package com.gamevh.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gamevh.entities.Account;
import com.gamevh.reponsitory.AccountRepository;
import com.gamevh.service.AccountService;


@Service
public abstract class AccountServiceImpl implements AccountService {

	@Autowired
	AccountRepository accountsDAO;

	@Override
	public List<Account> findAll() {
		return accountsDAO.findAll();
	}

	@Override
	public Account findById(String username) {
		return accountsDAO.findById(username).get();
	}
	
}
