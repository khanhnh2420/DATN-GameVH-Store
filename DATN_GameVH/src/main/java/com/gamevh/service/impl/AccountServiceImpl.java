package com.gamevh.service.impl;

import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import com.gamevh.ExceptionHandling.CustomException;
import com.gamevh.dto.RegisterAccountDTO;
import com.gamevh.dto.UpdateAccountDTO;
import com.gamevh.entities.Authority;
import com.gamevh.mapper.AccountMapper;
import com.gamevh.reponsitory.AuthorityRepository;
import com.gamevh.service.FileManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.gamevh.entities.Account;
import com.gamevh.reponsitory.AccountRepository;
import com.gamevh.service.AccountService;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;


@Service
public class AccountServiceImpl implements AccountService {

	@Autowired
	AccountRepository accountsDAO;

	@Autowired
	AccountMapper accountMapper;

	@Autowired
	FileManagerService fileManagerService;
	@Autowired
	AuthorityRepository authorityRepository;

	@Override
	public List<Account> findAll() {
		return accountsDAO.findAll();
	}

	@Override
	public Page<Account> findAll(Optional<Integer> pageNo, Optional<Integer> pageSize,
								 String username, String name, String roleId){
		Pageable pageable = PageRequest.of(pageNo.orElse(0), pageSize.orElse(6));

		// Áp dụng filter họ tên và roleId của account
		Page<Account> accounts = accountsDAO.filterAccountByUsernameAndNameAndRoleId(pageable, username, name, roleId);
		return accounts;
	}
	@Override
	public Account findById(String username) {
		return accountsDAO.findById(username).get();
	}

	@Override
	public Account toggleAccountStatus(String username){
		Account account = accountsDAO.findByUsername(username)
				.orElseThrow(() ->
						new CustomException("Không thể tìm thấy user có username là" + username, HttpStatus.NOT_FOUND));
		account.setStatus(!account.getStatus());
		return accountsDAO.save(account);
	}

	@Override
	public Account updateAccount(String userName, String updateAccountJSON, MultipartFile image){
		UpdateAccountDTO updateAccountDTO = accountMapper.parseUpdateAccountDTOFromJSON(updateAccountJSON);
		Account account = accountsDAO.findByUsername(userName)
				.orElseThrow(() ->
						new CustomException("Không thể tìm thấy user có username là" + userName, HttpStatus.NOT_FOUND));
		account.setUsername(updateAccountDTO.getUsername());
		// Encrypt mật khẩu mới
		if (updateAccountDTO.getPassword() != null){
			account.setPassword(new BCryptPasswordEncoder().encode(updateAccountDTO.getPassword()));
		}
		account.setEmail(updateAccountDTO.getEmail());
		account.setFullname(updateAccountDTO.getFullName());
		account.setStatus(updateAccountDTO.getStatus());
		if (image != null){
			updateAccountImage(account, image);
		}
		return accountsDAO.save(account);
	}


	public void updateAccountImage(Account account,MultipartFile image){
		MultipartFile[] files = new MultipartFile[1];
		files[0] = image;
		List<String> imageUrls = fileManagerService.save("accountImage",files);
		System.out.println(imageUrls);
		// Xóa ảnh cũ khỏi file system
		fileManagerService.delete("accountImage", account.getPhoto());
		account.setPhoto(imageUrls.get(0));
	}

	@Override
	public byte[] getAccountImage(String userName) {
		Account account = accountsDAO.findByUsername(userName)
				.orElseThrow(() ->
						new CustomException("Không thể tìm thấy user có username là" + userName, HttpStatus.NOT_FOUND));
		return fileManagerService.read("accountImage", account.getPhoto());
	}

	@Override
	@Transactional(rollbackFor = {Exception.class, Throwable.class})
	public Account registerAccount(String registerAccountDTOString, MultipartFile image){
		RegisterAccountDTO registerAccountDTO = accountMapper.parseRegisterAccountDTOFromJSON(registerAccountDTOString);
		Account newAccount = accountMapper.mapRegisterAccountToAccount(registerAccountDTO);
		MultipartFile[] files = new MultipartFile[1];
		files[0] = image;
		List<String> imageUrls = fileManagerService.save("accountImage",files);
		newAccount.setPhoto(imageUrls.get(0));
		newAccount = accountsDAO.save(newAccount);
		System.out.println(newAccount);
		for(Authority authority:newAccount.getAuthority()){
			authorityRepository.save(authority);
		}
		return newAccount;
	}
	@Override
	public void deleteAccount(String userName){
		Account account = accountsDAO.findByUsername(userName)
				.orElseThrow(() ->
						new CustomException("Không thể tìm thấy user có username là" + userName, HttpStatus.NOT_FOUND));
		accountsDAO.delete(account);
	}
	
}
