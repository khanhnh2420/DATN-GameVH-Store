package com.fourTL.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fourTL.entities.Account;

public interface AccountDAO extends JpaRepository<Account, String>{
	List<Account> findByUsernameContaining(String search);
}
