package com.fourTL.service;

import java.util.List;

import com.fourTL.entities.Account;



public interface AcountService {

	List<Account> findAll();

	Account findById(String username);
}
