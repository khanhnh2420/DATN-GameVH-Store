package com.gamevh.service;

import java.util.List;
import java.util.Optional;

import com.gamevh.dto.RegisterAccountDTO;
import com.gamevh.dto.UpdateAccountDTO;
import com.gamevh.entities.Account;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;


public interface AccountService {

	List<Account> findAll();
	Page<Account> findAll(Optional<Integer> pageNo, Optional<Integer> pageSize,
						  String username, String name, String roleId);
	Account toggleAccountStatus(String username);
	Account findById(String username);

	Account updateAccount(String userName, String updateAccountDTO,MultipartFile image);
	byte[] getAccountImage(String userName);
	Account registerAccount(String registerAccountDTOString, MultipartFile image);
	void deleteAccount(String userName);
}
