package com.gamevh.reponsitory;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gamevh.entities.Account;

public interface AccountRepository extends JpaRepository<Account, String>{
	List<Account> findByUsernameContaining(String username);
	
	List<Account> findByEmailContaining(String email);
	
	Account findById(Integer accountId);
}
